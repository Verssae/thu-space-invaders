package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import screen.Screen;
import screen.GameScreen;
import entity.Entity;
import entity.Ship;

/**
 * Manages screen drawing.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class DrawManager {

	/** Singleton instance of the class. */
	private static DrawManager instance;
	/** Current frame. */
	private static Frame frame;
	/** FileManager instance. */
	private static FileManager fileManager;
	/** Application logger. */
	private static Logger logger;
	/** Graphics context. */
	private static Graphics graphics;
	/** Buffer Graphics. */
	private static Graphics backBufferGraphics;
	/** Buffer image. */
	private static BufferedImage backBuffer;
	/** Normal sized font. */
	private static Font fontRegular;
	private static Font fontRegular2;
	/** Normal sized font properties. */
	private static FontMetrics fontRegularMetrics;
	private static FontMetrics fontRegular2Metrics;
	/** Big sized font. */
	private static Font fontBig;
	/** Big sized font properties. */
	private static FontMetrics fontBigMetrics;

	/** Sprite types mapped to their images. */
	private static Map<SpriteType, boolean[][]> spriteMap;

	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		Ship,
		/** Destroyed player ship. */
		ShipDestroyed,
		/** Player bullet. */
		Bullet,
		/** Enemy bullet. */
		EnemyBullet,
		/** First enemy ship - first form. */
		EnemyShipA1,
		/** First enemy ship - second form. */
		EnemyShipA2,
		/** Second enemy ship - first form. */
		EnemyShipB1,
		/** Second enemy ship - second form. */
		EnemyShipB2,
		/** Third enemy ship - first form. */
		EnemyShipC1,
		/** Third enemy ship - second form. */
		EnemyShipC2,
		/** Bonus ship. */
		EnemyShipSpecial,
		/** Destroyed enemy ship. */
		Item,
		/** dropped item */
		Explosion,
		
	};

	/**
	 * Private constructor.
	 */
	private DrawManager() {
		fileManager = Core.getFileManager();
		logger = Core.getLogger();
		logger.info("Started loading resources.");

		try {
			spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();

			spriteMap.put(SpriteType.Ship, new boolean[13][8]);
			spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
			spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.Item, new boolean[9][8]);
			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontRegular = fileManager.loadFont(14f);
			fontRegular2 = fileManager.loadFont(17f);
			fontBig = fileManager.loadFont(24f);
			logger.info("Finished loading the fonts.");

		} catch (IOException e) {
			logger.warning("Loading failed.");
		} catch (FontFormatException e) {
			logger.warning("Font formating failed.");
		}
	}

	/**
	 * Returns shared instance of DrawManager.
	 * 
	 * @return Shared instance of DrawManager.
	 */
	protected static DrawManager getInstance() {
		if (instance == null)
			instance = new DrawManager();
		return instance;
	}


	/**
	 * Sets the frame to draw the image on.
	 * 
	 * @param currentFrame
	 *            Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
	}

	/**
	 * First part of the drawing process. Initialices buffers, draws the
	 * background and prepares the images.
	 * 
	 * @param screen
	 *            Screen to draw in.
	 */
	Color[] bg_colors = {Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY};
	public void initDrawing(final Screen screen) {
		backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		graphics = frame.getGraphics();
		backBufferGraphics = backBuffer.getGraphics();

		if(GameScreen.lives > 0 && GameScreen.lives <= 3){
			backBufferGraphics.setColor(bg_colors[3 - GameScreen.lives]);
		}
		else{
			backBufferGraphics.setColor(Color.BLACK);
		}

		backBufferGraphics
				.fillRect(0, 0, screen.getWidth(), screen.getHeight());

		fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
		fontRegular2Metrics = backBufferGraphics.getFontMetrics(fontRegular2);
		fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);

		// drawBorders(screen);
		// drawGrid(screen);
	}

	/**
	 * Draws the completed drawing on screen.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * Draws an entity, using the apropiate image.
	 * 
	 * @param entity
	 *            Entity to be drawn.
	 * @param positionX
	 *            Coordinates for the left side of the image.
	 * @param positionY
	 *            Coordinates for the upper side of the image.
	 */
	public void drawEntity(final Entity entity, final int positionX,
			final int positionY) {
		boolean[][] image = spriteMap.get(entity.getSpriteType());

		backBufferGraphics.setColor(entity.getColor());
		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j])
					backBufferGraphics.drawRect(positionX + i * 2, positionY
							+ j * 2, 1, 1);
	}

	/**
	 * For debugging purpouses, draws the canvas borders.
	 * 
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
		backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
		backBufferGraphics.drawLine(screen.getWidth() - 1, 0,
				screen.getWidth() - 1, screen.getHeight() - 1);
		backBufferGraphics.drawLine(0, screen.getHeight() - 1,
				screen.getWidth() - 1, screen.getHeight() - 1);
	}

	/**
	 * For debugging purpouses, draws a grid over the canvas.
	 * 
	 * @param screen
	 *            Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawGrid(final Screen screen) {
		backBufferGraphics.setColor(Color.DARK_GRAY);
		for (int i = 0; i < screen.getHeight() - 1; i += 2)
			backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
		for (int j = 0; j < screen.getWidth() - 1; j += 2)
			backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
	}

	/**
	 * Draws current score on screen.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param score
	 *            Current score.
	 */
	public void drawScore(final Screen screen, final int score) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String scoreString = String.format("%04d", score);
		backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
	}

	/**
	 * Draws number of remaining lives on screen.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param lives
	 *            Current lives.
	 */
	public void drawLives(final Screen screen, final int lives) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
		Ship dummyShip = new Ship(0, 0);
		for (int i = 0; i < lives; i++)
			drawEntity(dummyShip, 40 + 35 * i, 10);
	}

	/**
	 * Draws a thick line from side to side of the screen.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param positionY
	 *            Y coordinate of the line.
	 */
	public void drawHorizontalLine(final Screen screen, final int positionY) {
		backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
		backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
				positionY + 1);
	}

	/**
	 * Draws game title.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "Invaders";
		String instructionsString =
				"select with w+s / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 2);

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, titleString, screen.getHeight() / 3);
	}

	/**
	 * Draws main menu.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 */

	public void drawSettingsMenu(final Screen screen) {
		String settingsString = "Settings";
		String instructionsString = "Press Space to Save Changes";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, settingsString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
	}

	public void drawMenu(final Screen screen, final int option) {
		String playString = "Play";
		String highScoresString = "High scores";
		String storeString = "Store";
		String settingString = "Settings";
		String exitString = "exit";

		// returnCode == 2 : play
		if (option == 2)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2);

		// returnCode == 3 : highscores
		if (option == 3)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, highScoresString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 2);

		// returnCode == 4 : settings
		if (option == 4)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, settingString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 3);

		// returnCode == 5 : store
		if (option == 5)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, storeString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 1);

		// returnCode == 0 : exit
		if (option == 0)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, exitString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 4);
	}

	public void drawSettingItems(final Screen screen, final int option) {

		String screensizeString = "Screen Size";
		String mastersoundString = "Master Sound";
		String musicsoundString = "Music Sound";
		String effectsoundString = "Effect Sound";
		String hudoptionString = "Hud Option";
		String helpString = "Help";
		String exitString = "Exit";


		// returnCode == 400010 : Screen Size
		if (option == 400010)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, screensizeString, screen.getHeight() / 3 );

		// returnCode == 400020 : masterSound
		if (option == 400020)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, mastersoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);

		// returnCode == 400030 : musicSound
		if (option == 400030)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, musicsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

		// returnCode == 400040 : effectSound
		if (option == 400040)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, effectsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);

		// returnCode == 400050 : HUD Options
		if (option == 400050)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, hudoptionString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 9);

		// returnCode == 400060 : help
		if (option == 400060)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, helpString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 11);

		// returnCode == 1 : exit
		if (option == 1)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, exitString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 13);
	}

	// 스크린 설정
	public void drawSettingOption(final Screen screen, final int option, final int screenchange, final int MasterSoundchange, final int MusicSoundchange, final int EffectSoundchange) {
		String defaultScreenmessage = "Three mod";
		String defaultMasterSoundmessage = "Five mod";
		String defaultMusicSoundmessage = "Five mod";
		String defaultEffectSoundmessage = "Five mod";
		String screenSizeOption1 = "Standard";
		String screenSizeOption2 = "Wide  Mode";
		String screenSizeOption3 = "Full Mode";
		String SoundOption1 = "25%";
		String SoundOption2 = "50%";
		String SoundOption3 = "75%";
		String SoundOption4 = "100%";
		String SoundOption5 = "0%";

		// 스크롤로 대체 예정이니까 Sound 담당하는 사람이 지우고 사용하면 됩니다.

		// screenSize
		if (option == 400010){
			if(screenchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption1;
			}
			if(screenchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption2;
			}
			if(screenchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption3;
			}
		}
		else {
			backBufferGraphics.setColor(Color.darkGray);
		}
		//drawRightRegular2String(screen, defaultScreenmessage, screen.getHeight() / 3);
		drawRightRegular2String(screen, defaultScreenmessage, screen.getHeight() / 3);


		// Master sound, Music sound, Effect sound
		if (option == 400020) {
			if (MasterSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption1;
			}
			if (MasterSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption2;
			}
			if (MasterSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption3;
			}
			if (MasterSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption4;
			}
			if (MasterSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultMasterSoundmessage = SoundOption5;
			}
		}
		else
				backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMasterSoundmessage, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);


			// 추후 수정
		if (option == 400030) {
			if (MusicSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption1;
			}
			if (MusicSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption2;
			}
			if (MusicSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption3;
			}
			if (MusicSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage= SoundOption4;
			}
			if (MusicSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption5;
			}
		}
		else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMusicSoundmessage, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

		// 추후 수정
		if (option == 400040) {
			if (EffectSoundchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption1;
			}
			if (EffectSoundchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption2;
			}
			if (EffectSoundchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption3;
			}
			if (EffectSoundchange == 4) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption4;
			}
			if (EffectSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultEffectSoundmessage = SoundOption5;
			}
		}
		else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultEffectSoundmessage, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);
	}



	/**
	 * Draws game results.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param score
	 *            Score obtained.
	 * @param livesRemaining
	 *            Lives remaining when finished.
	 * @param shipsDestroyed
	 *            Total ships destroyed.
	 * @param accuracy
	 *            Total accuracy.
	 * @param isNewRecord
	 *            If the score is a new high score.
	 */
	public void drawResults(final Screen screen, final int score,
			final int livesRemaining, final int shipsDestroyed,
			final float accuracy, final boolean isNewRecord) {
		String scoreString = String.format("score %04d", score);
		String livesRemainingString = "lives remaining " + livesRemaining;
		String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
		String accuracyString = String
				.format("accuracy %.2f%%", accuracy * 100);

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, scoreString, screen.getHeight()
				/ height);
		drawCenteredRegularString(screen, livesRemainingString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 2);
		drawCenteredRegularString(screen, shipsDestroyedString,
				screen.getHeight() / height + fontRegularMetrics.getHeight()
						* 4);
		drawCenteredRegularString(screen, accuracyString, screen.getHeight()
				/ height + fontRegularMetrics.getHeight() * 6);
	}

	/**
	 * Draws interactive characters for name input.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param name
	 *            Current name selected.
	 * @param nameCharSelected
	 *            Current character selected for modification.
	 */
	public void drawNameInput(final Screen screen, final char[] name,
			final int nameCharSelected) {
		String newRecordString = "New Record!";
		String introduceNameString = "Introduce name:";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredRegularString(screen, newRecordString, screen.getHeight()
				/ 4 + fontRegularMetrics.getHeight() * 10);
		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, introduceNameString,
				screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 12);

		// 3 letters name.
		int positionX = screen.getWidth()
				/ 2
				- (fontRegularMetrics.getWidths()[name[0]]
						+ fontRegularMetrics.getWidths()[name[1]]
						+ fontRegularMetrics.getWidths()[name[2]]
								+ fontRegularMetrics.getWidths()[' ']) / 2;

		for (int i = 0; i < 3; i++) {
			if (i == nameCharSelected)
				backBufferGraphics.setColor(Color.GREEN);
			else
				backBufferGraphics.setColor(Color.WHITE);

			positionX += fontRegularMetrics.getWidths()[name[i]] / 2;
			positionX = i == 0 ? positionX
					: positionX
							+ (fontRegularMetrics.getWidths()[name[i - 1]]
									+ fontRegularMetrics.getWidths()[' ']) / 2;

			backBufferGraphics.drawString(Character.toString(name[i]),
					positionX,
					screen.getHeight() / 4 + fontRegularMetrics.getHeight()
							* 14);
		}
	}

	/**
	 * Draws basic content of game over screen.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param acceptsInput
	 *            If the screen accepts input.
	 * @param isNewRecord
	 *            If the score is a new high score.
	 */
	public void drawGameOver(final Screen screen, final boolean acceptsInput,
			final boolean isNewRecord) {
		String gameOverString = "Game Over";
		String continueOrExitString =
				"Press Space to play again, Escape to exit";

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws high score screen title and instructions.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 */
	public void drawHighScoreMenu(final Screen screen) {
		String highScoreString = "High Scores";
		String instructionsString = "Press Space to return";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5);
	}

	/**
	 * Draws high scores.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param highScores
	 *            List of high scores.
	 */
	public void drawHighScores(final Screen screen,
			final List<Score> highScores) {
		backBufferGraphics.setColor(Color.WHITE);
		int i = 0;
		String scoreString = "";

		for (Score score : highScores) {
			scoreString = String.format("%s        %04d", score.getName(),
					score.getScore());
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
		}
	}

	/**
	 * Draws settings screen title and instructions.
	 *
	 * @param screen
	 *            Screen to draw on.
	 */

	public void drawStoreMenu(final Screen screen) {
		String storeString = "Store";
		String instructionsString = "Store";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, storeString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
	}

	public void drawHUDSettingMenu(final Screen screen) {
		String HUDString = "HUD Setting";
		String instructionsString = "Press Space to return";

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, HUDString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
	}

	public void drawHelpMenu(final Screen screen) {
		String HelpString = "Help";
		String instructionsString = "Help menu";
		String[] help1 = {"Press the arrow keys <- / -> (or the A or D keys)", "to move your ship"};
		String[] help2 = {"Press space to shoot & hit an enemy ships"};
		String[] help3 = {"Dodge enemy missiles by pressing the key"};
		String[] help4 = {"The life to play the game is 3"};
		String[] help5 = {"Eliminate all enemies", "to advance to the next level"};
		String[] help6 = {"check your highscore in highscore menu"};
		String[][] helps = {help1, help2, help3, help4, help5, help6};
		int i = 0;
		int j = 0;

		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredBigString(screen, HelpString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 6);

		backBufferGraphics.setColor(Color.WHITE);
		for (String[] help : helps) {
			for (String helpString : help) {
				drawCenteredRegularString(screen, helpString,
						screen.getHeight() * 3 / 13 + fontRegularMetrics.getHeight() * i +
								fontRegularMetrics.getHeight() * 2 * j);
				i++;
			}
			j++;
		}
	}


	/**
	 * Draws a centered string on regular font.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public void drawCenteredRegular2String(final Screen screen,
										  final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegular2Metrics.stringWidth(string) / 2, height);
	}

	public void drawCenteredRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegularMetrics.stringWidth(string) / 2, height);
	}

	public void drawLeftRegular2String(final Screen screen,
									   final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 4 - fontRegular2Metrics.stringWidth(string) / 2, height);
	}

	public void drawRightRegular2String(final Screen screen,
									   final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, 3 * screen.getWidth() / 4
				- fontRegular2Metrics.stringWidth(string) / 2, height);
	}

	/**
	 * Draws a centered string on big font.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param string
	 *            String to draw.
	 * @param height
	 *            Height of the drawing.
	 */
	public void drawCenteredBigString(final Screen screen, final String string,
			final int height) {
		backBufferGraphics.setFont(fontBig);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontBigMetrics.stringWidth(string) / 2, height);
	}

	/**
	 * Countdown to game start.
	 * 
	 * @param screen
	 *            Screen to draw on.
	 * @param level
	 *            Game difficulty level.
	 * @param number
	 *            Countdown number.
	 * @param bonusLife
	 *            Checks if a bonus life is received.
	 */
	public void drawCountDown(final Screen screen, final int level,
			final int number, final boolean bonusLife) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(Color.GREEN);
		if (number >= 4)
			if (!bonusLife) {
				drawCenteredBigString(screen, "Level " + level,
						screen.getHeight() / 2
						+ fontBigMetrics.getHeight() / 3);
			} else {
				drawCenteredBigString(screen, "Level " + level
						+ " - Bonus life!",
						screen.getHeight() / 2
						+ fontBigMetrics.getHeight() / 3);
			}
		else if (number != 0)
			drawCenteredBigString(screen, Integer.toString(number),
					screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
		else
			drawCenteredBigString(screen, "GO!", screen.getHeight() / 2
					+ fontBigMetrics.getHeight() / 3);
	}
}