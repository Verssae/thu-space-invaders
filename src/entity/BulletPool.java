package entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a pool of recyclable bullets.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class BulletPool {

	/** Set of already created bullets. */
	private static Set<Bullet> pool = new HashSet<Bullet>();
	private static Set<BulletN> poolN = new HashSet<BulletN>();
	private static Set<BulletH> poolH = new HashSet<BulletH>();

	/**
	 * Constructor, not called.
	 */
	private BulletPool() {

	}

	/**
	 * Returns a bullet from the pool if one is available, a new one if there
	 * isn't.
	 * 
	 * @param positionX
	 *            Requested position of the bullet in the X axis.
	 * @param positionY
	 *            Requested position of the bullet in the Y axis.
	 * @param speed
	 *            Requested speed of the bullet, positive or negative depending
	 *            on direction - positive is down.
	 * @return Requested bullet.
	 */
	public static Bullet getBullet(final int positionX,
			final int positionY, int speed, int movingPattern) {
		Bullet bullet;
		if (!pool.isEmpty()) {
			bullet = pool.iterator().next();
			pool.remove(bullet);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
			bullet.setPositionY(positionY);
			bullet.setSpeed(speed);
			bullet.setSprite();
			bullet.setMovingPattern(movingPattern);
		} else {
			bullet = new Bullet(positionX, positionY, speed, movingPattern);
			bullet.setPositionX(positionX - bullet.getWidth() / 2);
		}
		return bullet;
	}

	public static BulletN getBulletN(final int positionX,
								   final int positionY, int speed, int movingPattern) {
		BulletN bulletN;
		if (!poolN.isEmpty()) {
			bulletN = poolN.iterator().next();
			poolN.remove(bulletN);
			bulletN.setPositionX(positionX - 1);
			bulletN.setPositionY(positionY);
			bulletN.setSpeed(speed);
			bulletN.setSprite();
			bulletN.setMovingPattern(movingPattern);
		} else {
			bulletN = new BulletN(positionX, positionY, speed, movingPattern);
			bulletN.setPositionX(positionX - 1);
		}
		return bulletN;
	}

	public static BulletH getBulletH(final int positionX,
									 final int positionY, int speed, int movingPattern) {
		BulletH bulletH;
		if (!poolH.isEmpty()) {
			bulletH = poolH.iterator().next();
			poolH.remove(bulletH);
			bulletH.setPositionX(positionX - 1);
			bulletH.setPositionY(positionY);
			bulletH.setSpeed(speed);
			bulletH.setSprite();
			bulletH.setMovingPattern(movingPattern);
		} else {
			bulletH = new BulletH(positionX, positionY, speed, movingPattern);
			bulletH.setPositionX(positionX - 1);
		}
		return bulletH;
	}


	/**
	 * Adds one or more bullets to the list of available ones.
	 * 
	 * @param bullet
	 *            Bullets to recycle.
	 */
	public static void recycle(final Set<Bullet> bullet) {pool.addAll(bullet);}
	public static void recycleN(final Set<BulletN> bulletN) {poolN.addAll(bulletN);}
	public static void recycleH(final Set<BulletH> bulletH) {poolH.addAll(bulletH);}
}
