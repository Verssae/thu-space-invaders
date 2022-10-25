package screen;

import java.util.Random;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import engine.*;
import engine.DrawManager.SpriteType;
import entity.*;




/**
 * Implements the game screen, where the action happens.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameScreen extends Screen {

	/**
	 * Milliseconds until the screen accepts user input.
	 */
	private static final int INPUT_DELAY = 6000;
	/**
	 * Bonus score for each life remaining at the end of the level.
	 */
	private static final int LIFE_SCORE = 100;
	/**
	 * Movement speed of the item.
	 */
	private static final int ITEM_SPEED = 3;
	/**
	 * Minimum time between bonus ship's appearances.
	 */
	private static final int BONUS_SHIP_INTERVAL = 20000;
	/**
	 * Maximum variance in the time between bonus ship's appearances.
	 */
	private static final int BONUS_SHIP_VARIANCE = 10000;
	/**
	 * Time until bonus ship explosion disappears.
	 */
	private static final int BONUS_SHIP_EXPLOSION = 500;
	/**
	 * Time from finishing the level to screen change.
	 */
	private static final int SCREEN_CHANGE_INTERVAL = 1500;
	/**
	 * Height of the interface separation line.
	 */
	private static final int SEPARATION_LINE_HEIGHT = 40;

	/**
	 * Current game difficulty settings.
	 */
	private GameSettings gameSettings;
	/**
	 * Current difficulty level number.
	 */
	private int level;
	/**
	 * Formation of enemy ships.
	 */
	private EnemyShipFormation enemyShipFormation;
	/**
	 * Player's ship.
	 */
	private Ship ship;
	/**
	 * Player's ship width.
	 */
	private int shipWidth = 13*2;
	/**
	 * Bonus enemy ship that appears sometimes.
	 */
	private EnemyShip enemyShipSpecial;
	/**
	 * Minimum time between bonus ship appearances.
	 */
	private Cooldown enemyShipSpecialCooldown;
	/**
	 * Time until bonus ship explosion disappears.
	 */
	private Cooldown enemyShipSpecialExplosionCooldown;
	/**
	 * Time from finishing the level to screen change.
	 */
	private Cooldown screenFinishedCooldown;
	/**
	 * Set of all bullets fired by on screen ships.
	 */
	private Set<Bullet> bullets;

	private Set<BulletN> bulletsN;

	private Set<BulletH> bulletsH;


	/** Current score. */
	private int score;
	/** Current coin. */
	private int coin;
	/** Player lives left. */
	public static int lives;
	/**
	 * Total bullets shot by the player.
	 */
	private int bulletsShot;
	/**
	 * Total ships destroyed by the player.
	 */
	private int shipsDestroyed;
	/**
	 * Moment the game starts.
	 */
	private long gameStartTime;
	/**
	 * Checks if the level is finished.
	 */
	private boolean levelFinished;
	/**
	 * Checks if a bonus life is received.
	 */
	private boolean bonusLife;

	public int enemyLives;
	/**
	 * Set of all items dropped by on screen enemyships.
	 */

	private Set<entity.Item> items;

	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param gameState
	 *                     Current game state.
	 * @param gameSettings
	 *                     Current game settings.
	 * @param bonusLife
	 *                     Checks if a bonus life is awarded this level.
	 * @param width
	 *                     Screen width.
	 * @param height
	 *                     Screen height.
	 * @param fps
	 *                     Frames per second, frame rate at which the game is run.
	 */
	public GameScreen(final GameState gameState,
			final GameSettings gameSettings, final boolean bonusLife,
			final int width, final int height, final int fps) {
		super(width, height, fps);

		this.gameSettings = gameSettings;
		this.bonusLife = bonusLife;
		this.level = gameState.getLevel();
		this.score = gameState.getScore();
		this.lives = gameState.getLivesRemaining();
		this.coin = gameState.getCoin();
		if (this.bonusLife)
			this.lives++;
		this.bulletsShot = gameState.getBulletsShot();
		this.shipsDestroyed = gameState.getShipsDestroyed();
	}

	/**
	 * Initializes basic screen properties, and adds necessary elements.
	 */
	public final void initialize() {
		super.initialize();
		enemyShipFormation = new EnemyShipFormation(this.gameSettings);
		enemyShipFormation.attach(this);
		/** You can add your Ship to the code below. */

		switch (Inventory.getcurrentship()) {
			case 1000 -> this.ship = new Ship(this.width / 2, this.height - 30, Color.GREEN);
			case 1001 -> this.ship = new Ship(this.width / 2, this.height - 30, Color.RED);
			case 1002 -> this.ship = new Ship(this.width / 2, this.height - 30, Color.BLUE);
		}

		// Appears each 10-30 seconds.
		this.enemyShipSpecialCooldown = Core.getVariableCooldown(
				BONUS_SHIP_INTERVAL, BONUS_SHIP_VARIANCE);
		this.enemyShipSpecialCooldown.reset();
		this.enemyShipSpecialExplosionCooldown = Core
				.getCooldown(BONUS_SHIP_EXPLOSION);
		this.screenFinishedCooldown = Core.getCooldown(SCREEN_CHANGE_INTERVAL);
		this.bullets = new HashSet<Bullet>();
		this.bulletsN = new HashSet<BulletN>();
		this.bulletsH = new HashSet<BulletH>();
		this.items = new HashSet<entity.Item>();

		// Special input delay / countdown.
		this.gameStartTime = System.currentTimeMillis();
		this.inputDelay = Core.getCooldown(INPUT_DELAY);
		this.inputDelay.reset();
	}

	/**
	 * Starts the action.
	 *
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		this.score += LIFE_SCORE * (this.lives - 1);
		this.logger.info("Screen cleared with a score of " + this.score);

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		if (this.inputDelay.checkFinished() && !this.levelFinished) {
			if (!this.ship.isDestroyed()) {
				boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT)
						|| inputManager.isKeyDown(KeyEvent.VK_D);
				boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT)
						|| inputManager.isKeyDown(KeyEvent.VK_A);

				boolean isRightBorder_ship = this.ship.getPositionX()
						+ this.ship.getWidth() + this.ship.getSpeed() > this.width - 1;
				boolean isLeftBorder_ship = this.ship.getPositionX()
						- this.ship.getSpeed() < 1;

				if (moveRight && !isRightBorder_ship) {
					this.ship.moveRight();
				}
				if (moveLeft && !isLeftBorder_ship) {
					this.ship.moveLeft();
				}
				if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
					if (this.ship.shoot(this.bullets))
						this.bulletsShot++;

				if (moveLeft)
					ship.animctr = 2;
				else if (moveRight)
					ship.animctr = 3;
				else
					ship.animctr = 1;
			}

			if (this.enemyShipSpecial != null) {
				if (!this.enemyShipSpecial.isDestroyed())
					this.enemyShipSpecial.move(2, 0);
				else if (this.enemyShipSpecialExplosionCooldown.checkFinished())
					this.enemyShipSpecial = null;

			}
			if (this.enemyShipSpecial == null
					&& this.enemyShipSpecialCooldown.checkFinished()) {
				this.enemyShipSpecial = new EnemyShip();
				this.enemyShipSpecialCooldown.reset();
				this.logger.info("A special ship appears");
			}
			if (this.enemyShipSpecial != null
					&& this.enemyShipSpecial.getPositionX() > this.width) {
				this.enemyShipSpecial = null;
				this.logger.info("The special ship has escaped");
			}

			this.ship.update();
			this.enemyShipFormation.update();

			switch (Core.getDiff()){
				case 1:
					this.enemyShipFormation.shoot(this.bullets);
					break;
				case 2:
					this.enemyShipFormation.shootN(this.bulletsN);
					break;
				case 3:
					this.enemyShipFormation.shootH(this.bulletsH);
					break;
			}

		}

		manageCollisions();
		manageCollisionsN();
		manageCollisionsH();
		cleanBullets();
		cleanBulletsN();
		cleanBulletsH();
		manageCollisionsItem();
		cleanItems();
		draw();

		if ((this.enemyShipFormation.isEmpty() || this.lives == 0)
				&& !this.levelFinished) {
			this.levelFinished = true;
			this.screenFinishedCooldown.reset();
			if(this.lives==0) this.ship.gameOver();
		}

		if (this.levelFinished && this.screenFinishedCooldown.checkFinished())
			this.isRunning = false;

	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawEntity(this.ship, this.ship.getPositionX(), this.ship.getPositionY());
		if (this.ship.item_number == 1){
			drawManager.drawimg("item_heart", this.ship.getPositionX()+15, this.ship.getPositionY()-25, 33, 33);
		}
		else if (this.ship.item_number == 2){
			drawManager.drawimg("item_bulletspeed", this.ship.getPositionX()+15, this.ship.getPositionY()-25, 33, 33);
		}
		else if (this.ship.item_number == 3){
			drawManager.drawimg("item_movespeed", this.ship.getPositionX()+15, this.ship.getPositionY()-25, 33, 33);
		}
		if (this.enemyShipSpecial != null)
			drawManager.drawEntity(this.enemyShipSpecial,
					this.enemyShipSpecial.getPositionX(),
					this.enemyShipSpecial.getPositionY());

		enemyShipFormation.draw();

		for (Bullet bullet : this.bullets)
			drawManager.drawEntity(bullet, bullet.getPositionX(),
					bullet.getPositionY());

		for (BulletN bulletN : this.bulletsN)
			drawManager.drawEntity(bulletN, bulletN.getPositionX(),
					bulletN.getPositionY());

		for (BulletH bulletH : this.bulletsH)
			drawManager.drawEntity(bulletH, bulletH.getPositionX(),
					bulletH.getPositionY());

		for (entity.Item item : this.items)
			drawManager.drawEntity(item, item.getPositionX(),
					item.getPositionY());

		// Interface.
		drawManager.drawScore(this, this.score);
		drawManager.drawLives(this, this.lives);
		drawManager.drawHorizontalLine(this, SEPARATION_LINE_HEIGHT - 1);
		drawManager.drawCoin(this, this.coin);

		// Countdown to game start.
		if (!this.inputDelay.checkFinished()) {
			int countdown = (int) ((INPUT_DELAY
					- (System.currentTimeMillis()
							- this.gameStartTime))
					/ 1000);
			drawManager.drawCountDown(this, this.level, countdown,
					this.bonusLife);
			drawManager.drawHorizontalLine(this, this.height / 2 - this.height
					/ 12);
			drawManager.drawHorizontalLine(this, this.height / 2 + this.height
					/ 12);
		}

		drawManager.completeDrawing(this);
	}

	/**
	 * Cleans bullets that go off screen.
	 */
	private void cleanBullets() {
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets) {
			bullet.update();
			if (bullet.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bullet.getPositionY() > this.height)
				recyclable.add(bullet);
		}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}

	private void cleanBulletsN() {
		Set<BulletN> recyclable = new HashSet<BulletN>();
		for (BulletN bulletN : this.bulletsN) {
			bulletN.update();
			if (bulletN.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bulletN.getPositionY() > this.height)
				recyclable.add(bulletN);
		}
		this.bulletsN.removeAll(recyclable);
		BulletPool.recycleN(recyclable);
	}

	private void cleanBulletsH() {
		Set<BulletH> recyclable = new HashSet<BulletH>();
		for (BulletH bulletH : this.bulletsH) {
			bulletH.update();
			if (bulletH.getPositionY() < SEPARATION_LINE_HEIGHT
					|| bulletH.getPositionY() > this.height)
				recyclable.add(bulletH);
		}
		this.bulletsH.removeAll(recyclable);
		BulletPool.recycleH(recyclable);
	}

	private void cleanItems() {
		Set<entity.Item> recyclable = new HashSet<entity.Item>();
		for (entity.Item item : this.items) {
			item.update();
			if (item.getPositionY() > this.height)
				recyclable.add(item);
		}
		this.items.removeAll(recyclable);
		ItemPool.recycle(recyclable);
	}

	/**
	 * Manages collisions between bullets and ships.
	 */
	private void manageCollisions() {
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets)
			if (bullet.getSpeed() > 0) {
				if (checkCollision(bullet, this.ship) && !this.levelFinished) {
					recyclable.add(bullet);
					if (!this.ship.isDestroyed()) {
						this.ship.destroy();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
				}

			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation)
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) {
						enemyLives = enemyShip.getEnemyLives();
						if (enemyLives == 1) {
							this.score += enemyShip.getPointValue();
							this.shipsDestroyed++;
							Random random = new Random();
							int per = random.nextInt(3);
							if (per == 0) {
								items.add(ItemPool.getItem(enemyShip.getPositionX() + enemyShip.getWidth() / 2,
										enemyShip.getPositionY(), ITEM_SPEED));
							}
							this.enemyShipFormation.destroy(enemyShip);
							this.coin += enemyShip.getPointValue() / 10;
							Coin.balance += enemyShip.getPointValue() / 10;
							recyclable.add(bullet);
						}
						else {
							enemyLives--;
							enemyShip.setenemyLives(enemyLives);
							recyclable.add(bullet);
						}
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) {
					this.score += this.enemyShipSpecial.getPointValue();
					this.shipsDestroyed++;
					this.enemyShipSpecial.destroy();
					this.enemyShipSpecialExplosionCooldown.reset();
					this.coin += this.enemyShipSpecial.getPointValue() / 10;
					Coin.balance += this.enemyShipSpecial.getPointValue() / 10;
					recyclable.add(bullet);
				}
			}
		this.bullets.removeAll(recyclable);
		BulletPool.recycle(recyclable);
	}

	private void manageCollisionsN() {
		Set<BulletN> recyclable = new HashSet<BulletN>();
		for (BulletN bullet : this.bulletsN)
			if (bullet.getSpeed() > 0) {
				if (checkCollision(bullet, this.ship) && !this.levelFinished) {
					recyclable.add(bullet);
					if (!this.ship.isDestroyed()) {
						this.ship.destroy();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
				}
			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation)
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) {
						this.score += enemyShip.getPointValue();
						this.shipsDestroyed++;
						Random random = new Random();
						int per = random.nextInt(2);
						if(per == 0){
							items.add(ItemPool.getItem(enemyShip.getPositionX() + enemyShip.getWidth() / 2,
									enemyShip.getPositionY(), ITEM_SPEED));
						}
						this.enemyShipFormation.destroy(enemyShip);
						recyclable.add(bullet);
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) {
					this.score += this.enemyShipSpecial.getPointValue();
					this.shipsDestroyed++;
					this.enemyShipSpecial.destroy();
					this.enemyShipSpecialExplosionCooldown.reset();
					recyclable.add(bullet);
				}
			}
		this.bullets.removeAll(recyclable);
		BulletPool.recycleN(recyclable);
	}

	private void manageCollisionsH() {
		Set<BulletH> recyclable = new HashSet<BulletH>();
		for (BulletH bullet : this.bulletsH)
			if (bullet.getSpeed() > 0) {
				if (checkCollision(bullet, this.ship) && !this.levelFinished) {
					recyclable.add(bullet);
					if (!this.ship.isDestroyed()) {
						this.ship.destroy();
						this.lives--;
						this.logger.info("Hit on player ship, " + this.lives
								+ " lives remaining.");
					}
				}
			} else {
				for (EnemyShip enemyShip : this.enemyShipFormation)
					if (!enemyShip.isDestroyed()
							&& checkCollision(bullet, enemyShip)) {
						this.score += enemyShip.getPointValue();
						this.shipsDestroyed++;
						Random random = new Random();
						int per = random.nextInt(2);
						if (per == 0) {
							items.add(ItemPool.getItem(enemyShip.getPositionX() + enemyShip.getWidth() / 2,
									enemyShip.getPositionY(), ITEM_SPEED));
						}
						this.enemyShipFormation.destroy(enemyShip);
						recyclable.add(bullet);
					}
				if (this.enemyShipSpecial != null
						&& !this.enemyShipSpecial.isDestroyed()
						&& checkCollision(bullet, this.enemyShipSpecial)) {
					this.score += this.enemyShipSpecial.getPointValue();
					this.shipsDestroyed++;
					this.enemyShipSpecial.destroy();
					this.enemyShipSpecialExplosionCooldown.reset();
					recyclable.add(bullet);
				}
			}
		this.bullets.removeAll(recyclable);
		BulletPool.recycleH(recyclable);
	}

	/**
	 * Returns a GameState object representing the status of the game.
	 *
	 * @return Current game state.
	 */
	public final GameState getGameState() {
		return new GameState(this.level, this.score, this.lives,
				this.bulletsShot, this.shipsDestroyed, this.coin);
	}

	/**
	 * Manages collisions between items and ships.
	 */

	private void manageCollisionsItem() {
		Set<entity.Item> recyclable = new HashSet<entity.Item>(); // ItemPool
		for (entity.Item item : this.items) {
			if (checkCollision(item, this.ship) && !this.levelFinished) {
				recyclable.add(item);
				Random random = new Random();
				int per = random.nextInt(6);

				if (per == 0) {
					if (this.lives < 3) {
						this.lives++;
						this.logger.info("Acquire a item_lifePoint," + this.lives + " lives remaining.");
						this.ship.item_number = 1;
						this.ship.itemimgGet();
					}
					else {
						if (ship.getSHOOTING_INTERVAL() > 300) {
							int shootingSpeed = (int) (ship.getSHOOTING_INTERVAL() -100);
							ship.setSHOOTING_INTERVAL(shootingSpeed);
							ship.setSHOOTING_COOLDOWN(shootingSpeed);
							this.logger.info("Acquire a item_shootingSpeedUp," + shootingSpeed + " Time between shots.");
						}
						else {
							this.logger.info("Acquire a item_shootingSpeedUp, MAX SHOOTING SPEED!");
						}
						this.ship.item_number = 2;
						this.ship.itemimgGet();
					}
				}else if (per == 1) {
					if (ship.getSHOOTING_INTERVAL() > 300) {
						int shootingSpeed = (int) (ship.getSHOOTING_INTERVAL() -100);
						ship.setSHOOTING_INTERVAL(shootingSpeed);
						ship.setSHOOTING_COOLDOWN(shootingSpeed);
						this.logger.info("Acquire a item_shootingSpeedUp," + shootingSpeed + " Time between shots.");
					}
					else {
						this.logger.info("Acquire a item_shootingSpeedUp, MAX SHOOTING SPEED!");
					}
					this.ship.item_number = 2;
					this.ship.itemimgGet();
				}
				else if (per == 2) {
					int shipSpeed = (int) (ship.getSPEED() + 1);
					ship.setSPEED(shipSpeed);
					this.logger.info("Acquire a item_shipSpeedUp," + shipSpeed + " Movement of the ship for each unit of time.");
					this.ship.item_number = 3;
					this.ship.itemimgGet();
				}else if (per == 3) {
					bullets.add(BulletPool.getBullet(ship.getPositionX(),
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth/2,
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth,
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					this.logger.info("Three bullets");
				}else if (per == 4) {
					bullets.add(BulletPool.getBullet(ship.getPositionX()+shipWidth/2,
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth/2,
							ship.getPositionY()+shipWidth/2, ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth/2,
							ship.getPositionY()+shipWidth, ship.getBULLET_SPEED(), 0));
					this.logger.info("Three bullets");
				}else {
					bullets.add(BulletPool.getBullet(ship.getPositionX() - shipWidth / 2,
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX(),
							ship.getPositionY() - shipWidth / 3, ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth / 2,
							ship.getPositionY() - shipWidth / 2, ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth,
							ship.getPositionY() - shipWidth / 3, ship.getBULLET_SPEED(), 0));
					bullets.add(BulletPool.getBullet(ship.getPositionX() + shipWidth + shipWidth / 2,
							ship.getPositionY(), ship.getBULLET_SPEED(), 0));
					this.logger.info("Five bullets");
				}
				this.ship.getItem();
			}
		}
		this.items.removeAll(recyclable);
		ItemPool.recycle(recyclable);
	}

	/**
	 * Checks if two entities are colliding.
	 *
	 * @param a
	 *          First entity, the bullet or item.
	 * @param b
	 *          Second entity, the ship.
	 * @return Result of the collision test.
	 */
	private boolean checkCollision(final Entity a, final Entity b) {
		// Calculate center point of the entities in both axis.
		int centerAX = a.getPositionX() + a.getWidth() / 2;
		int centerAY = a.getPositionY() + a.getHeight() / 2;
		int centerBX = b.getPositionX() + b.getWidth() / 2;
		int centerBY = b.getPositionY() + b.getHeight() / 2;
		// Calculate maximum distance without collision.
		int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
		int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
		// Calculates distance.
		int distanceX = Math.abs(centerAX - centerBX);
		int distanceY = Math.abs(centerAY - centerBY);

		return distanceX < maxDistanceX && distanceY < maxDistanceY;
	}
}
