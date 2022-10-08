package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a item that moves vertically up or down.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Item extends Entity {

	/**
	 * Speed of the item, positive or negative depending on direction -
	 */
	private int speed;

	/**
	 * Constructor, establishes the item's properties.
	 * 
	 * @param positionX
	 *            Initial position of the item in the X axis.
	 * @param positionY
	 *            Initial position of the item in the Y axis.
	 * @param speed
	 *            Speed of the bullet, positive or negative depending on
	 *            direction - positive is down.
	 */
	public Item(final int positionX, final int positionY, final int speed) {
		super(positionX, positionY, 3 * 2, 5 * 2, Color.WHITE);

		this.speed = speed;
		setSprite();
	}

	/**
	 * Sets correct sprite for the item.
	 */
	public final void setSprite() {
		this.spriteType = SpriteType.Item;
	}

	/**
	 * Updates the item's position.
	 */
	public final void update() {
		this.positionY += this.speed;
	}

	/**
	 * Setter of the speed of the item.
	 * 
	 * @param speed
	 *            New speed of the item.
	 */
	public final void setSpeed(final int speed) {
		this.speed = speed;
	}

	/**
	 * Getter for the speed of the item.
	 * 
	 * @return Speed of the item.
	 */
	public final int getSpeed() {
		return this.speed;
	}
}
