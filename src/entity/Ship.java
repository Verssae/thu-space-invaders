package entity;

import java.awt.Color;
import java.util.Arrays;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
import engine.Sound;

import screen.ShopScreen;


/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	/** Time between shots. */
	private int SHOOTING_INTERVAL = 750;
	/** Speed of the bullets shot by the ship. */
	private int BULLET_SPEED = -6;

	/** Movement of the ship for each unit of time. */
	private int SPEED;
	public int animctr = 1;

	private boolean imagep;
	public int imageid;
	public int item_number = 0;

	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;
	/** Item acquire effect duration time. */
	private Cooldown itemCooldown;
	/** Movement of the ship for each unit of time. */
	private int destructCool = 300;

	private int frameCnt = 0;

	private boolean getItem=false;
	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *                  Initial position of the ship in the X axis.
	 * @param positionY
	 *                  Initial position of the ship in the Y axis.
	 */

	public Ship(final int positionX, final int positionY, Color color) {
		super(positionX, positionY, 13 * 2, 8 * 2, color);
		imagep = false;
		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.itemCooldown = Core.getCooldown(300);
		this.destructionCooldown = Core.getCooldown(destructCool);
		switch (Core.getDiff()) {
			case 0:
				this.SPEED = 2;
				break;
			case 1:
				this.SPEED = 1;
				break;
			case 2:
				this.SPEED = 5;
				break;
			case 3:
				this.SPEED = 10;
				break;
		}
	}
	
	/**
	 * Moves the ship speed units right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += SPEED;
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= SPEED;
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *                List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets) {
		if (this.shootingCooldown.checkFinished()) {

			new Sound().bulletsound();
			this.shootingCooldown.reset();
			bullets.add(BulletPool.getBullet(positionX + this.width / 2,
					positionY, BULLET_SPEED, 0));
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	private Color[] rainbowEffect = {Color.RED, Color.ORANGE, Color.YELLOW, Color.green, Color.blue, new Color(0, 0, 128), new Color(139, 0, 255)};
	public final void update() {
		// Item acquired additional image
		if (this.itemCooldown.checkFinished()){
			this.item_number = 0;
		}
		if (this.isDestroyed()) {
			frameCnt++;
			if (frameCnt % (destructCool * 0.01) == 0) {
				if (getColor() == Color.GREEN) {
					this.spriteType = SpriteType.ShipDestroyed;
					setColor(Color.red);
				} else {
					setColor(Color.GREEN);
					this.spriteType = SpriteType.Ship;
				}
			}
		} else if (getItem) {
			if (frameCnt >= 30) {
				getItem = false;
			} else {
				try {
					this.setColor(rainbowEffect[Arrays.asList(rainbowEffect).indexOf(this.getColor()) + 1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					this.setColor(rainbowEffect[0]);
				}
				frameCnt++;
			}
		} else {
			frameCnt = 0;
			setColor(Color.GREEN);
			this.spriteType = SpriteType.Ship;

		}
	}
	public final void getItem() {
		this.getItem = true;
	}

	public final void gameOver() {
		this.setSpriteType(SpriteType.Explosion);
		this.setColor(Color.MAGENTA);

	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		new Sound().explosionsound();
		this.destructionCooldown.reset();

		new Sound().explosionsound();
	}

	/**
	 * Checks if the ship is destroyed.
	 * 
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Switches the ship to its item acquired state.
	 */
	public final void itemimgGet(){
		this.itemCooldown.reset();
	}

	/**
	 * Checks if the ship acquired an item.
	 *
	 * @return True if the ship acquired an item.
	 */
	public final boolean isItemimgGet(){
		return !this.itemCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED;
	}

	public void setSHOOTING_COOLDOWN(int SHOOTING_INTERVAL) {
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
	}

	public void setSHOOTING_INTERVAL(int SHOOTING_INTERVAL) {
		this.SHOOTING_INTERVAL = SHOOTING_INTERVAL;
	}

	public int getSHOOTING_INTERVAL() {
		return this.SHOOTING_INTERVAL;
	}

	public void setSPEED(int SPEED) {
		this.SPEED = SPEED;
	}

	public int getSPEED() {
		return SPEED;
	}
	public int getBULLET_SPEED() {return BULLET_SPEED;}
}
