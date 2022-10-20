package entity;

import java.awt.Color;
import java.util.Set;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;
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
	private static final int BULLET_SPEED = -6;

	/** Movement of the ship for each unit of time. */
	private int SPEED;
	public int animctr = 1;

	private boolean imagep;
	public int imageid;

	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;
	/** Movement of the ship for each unit of time. */

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *                  Initial position of the ship in the X axis.
	 * @param positionY
	 *                  Initial position of the ship in the Y axis.
	 */

	public Ship(final int positionX, final int positionY, int sType) {
		super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);
		imagep = true;
		this.spriteType = SpriteType.ShipCustom;
		this.imageid = sType;
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Core.getCooldown(300);
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
	 * Moves the ship speed uni ts right, or until the right screen border is
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
	public final void update() {
		if (this.imagep) {
			if (!this.destructionCooldown.checkFinished())
				this.spriteType = SpriteType.ShipCustomDestroyed;
			// use hash map to decide which image to use
			else
				this.spriteType = SpriteType.ShipCustom;
			return;
		}
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
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
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED;
	}

	public void setSHOOTING_INTERVAL(int SHOOTING_INTERVAL) {
		this.SHOOTING_INTERVAL = SHOOTING_INTERVAL;
	}

	public int getSHOOTING_INTERVAL() {
		return SHOOTING_INTERVAL;
	}

	public void setSPEED(int SPEED) {
		this.SPEED = SPEED;
	}

	public int getSPEED() {
		return SPEED;
	}
}
