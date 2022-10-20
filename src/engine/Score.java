package engine;

/**
 * Implements a high score record.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Score implements Comparable<Score> {

	/** Player's name. */
	private String name;
	/** Score points. */
	private int score;
	/** Stage */
	private int stage;
	/** Killed Enemies */
	private int killed;
	/** Shooted Bullets */
	private int bullets;
	/** Accuracy Score. */
	private float accuracy;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Player name, three letters.
	 * @param score
	 *            Player score.
	 * @param stage
	 *            Stage.
	 * @param killed
	 *            Killed Enemies.
	 * @param bullets
	 *            Shooted Bullets.
	 * @param accuracy
	 *            Accuracy Score.
	 */
	public Score(final String name, final int score, final int stage, final int killed, final int bullets, final float accuracy) {
		this.name = name;
		this.score = score;
		this.stage = stage;
		this.killed = killed;
		this.bullets = bullets;
		this.accuracy = accuracy;
	}

	// 현재 프로젝트에서 사용되는 생성자입니다. HighScoreScreen.java 등 생성자 쓰이는 부분 모두 반영되면 해당 생성자를 삭제할 것을 요망합니다.
	public Score(final String name, final int score) {
		this.name = name;
		this.score = score;
	}


	/**
	 * Getter for the player's name.
	 *
	 * @return Name of the player.
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Getter for the player's score.
	 *
	 * @return High score.
	 */
	public final int getScore() {
		return this.score;
	}


	/**
	 * Getter for the stage.
	 *
	 * @return Stage.
	 */
	public final int getStage() { return this.stage; }

	/**
	 * Getter for the Killed Enemies.
	 *
	 * @return Killed Enemies.
	 */
	public final int getKilled() { return this.killed; }

	/**
	 * Getter for the Bullets.
	 *
	 * @return Bullets.
	 */
	public final int getBullets() { return this.bullets; }

	/**
	 * Getter for the Accuracy.
	 *
	 * @return Accuracy.
	 */
	public final float getAccuracy() { return this.accuracy; }

	/**
	 * Orders the scores descending by score.
	 *
	 * @param score
	 *            Score to compare the current one with.
	 * @return Comparison between the two scores. Positive if the current one is
	 *         smaller, positive if its bigger, zero if its the same.
	 *         정렬 1순위 : 점수(Score), 2순위 : 정확도(Accuracy)
	 */
	@Override
	public final int compareTo(final Score score) {
		int comparison_accuracy = this.accuracy < score.getAccuracy() ? 1 : this.accuracy > score.getAccuracy() ? -1 : 0;
		int comparison_score = this.score < score.getScore() ? 1 : this.score > score.getScore() ? -1 : comparison_accuracy;

		return comparison_score;
	}

}