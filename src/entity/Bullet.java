package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;
import engine.GameState;

/**
 * Implements a bullet that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Bullet extends Entity {

	/**
	 * Speed of the bullet, positive or negative depending on direction -
	 * positive is down.
	 */
	private int speed;
	private int shooting_pattern;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param positionX
	 *            Initial position of the bullet in the X axis.
	 * @param positionY
	 *            Initial position of the bullet in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Bullet(final int positionX, final int positionY, final int speed, int shotPattern) {
		super(positionX, positionY, 3 * 2, 5 * 3, Color.RED);

		this.speed = speed;
		this.shooting_pattern = shotPattern;
		setSprite();
	}

	/**
	 * Sets correct sprite for the bullet, based on speed.
	 */
	public final void setSprite() {
		if (speed < 0)
			this.spriteType = SpriteType.Bullet;
		else
			this.spriteType = SpriteType.EnemyBullet;
	}

	/**
	 * Updates the bullet's position.
	 */
	public final void update() {
		this.positionY += this.speed;
		if(speed > 0 && this.shooting_pattern != 0) {
			movePosX();
		}
	}

	public final void movePosX() {
		if (this.shooting_pattern == 1)
			this.positionX += (this.speed%2+1);
		else if (this.shooting_pattern == 2)
			this.positionX -= (this.speed%2+1);
	}

	/**
	 * Setter of the speed of the bullet.
	 * 
	 * @param speed
	 *            New speed of the bullet.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the bullet.
	 * 
	 * @return Speed of the bullet.
	 */
	public final int getSpeed() {
		return this.speed;
	}

	public final void setMovingPattern(final int shooting_pattern){
		this.shooting_pattern = shooting_pattern;
	}
}
