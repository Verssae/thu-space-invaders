package engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.ArrayList;



import screen.Screen;
import screen.GameScreen;

import screen.*;
import screen.ShopScreen.shopstates;

import screen.*;
import screen.Screen;
import screen.GameScreen;
import screen.ShopScreen.shopstates;

import entity.Entity;
import entity.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;

import static screen.ShopScreen.selecteditem;

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

	private static Font fontSmall;

	/** Item icon and Image observer */
	BufferedImage Dummy_icon;
	BufferedImage Dummy_data_icon;
	BufferedImage coin_icon;
	BufferedImage ship_1;
	BufferedImage ship_2;
	BufferedImage ship_3;
	BufferedImage bgm_1;
	BufferedImage bgm_2;
	BufferedImage bgm_3;
	ImageObserver observer;

	/** Sprite types mapped to their images. */
	private static Map<SpriteType, boolean[][]> spriteMap;
	public static Map<String, BufferedImage> imagemap;

	/** Sprite types. */
	public static enum SpriteType {
		/** Player ship. */
		Ship,
		/** Destroyed player ship. */
		ShipDestroyed,
		/** Player bullet. */
		Bullet,
		/** Enemy bullets. */
		EnemyBullet,
		EnemyBulletN,
		EnemyBulletH,
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
		/** Destroyed enemy ship - first form. */
		Explosion,
		/** Destroyed enemy ship - second form. */
		Explosion2,
		/** Destroyed enemy ship - third form. */
		Explosion3,
		/** Destroyed enemy ship - fourth form. */
		Explosion4,
		/** Custom Ship Image */
		ShipCustom,
		/** Custom Ship Image */
		ShipCustomDestroyed,
		/** dropped item */
		Item;
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
			spriteMap.put(SpriteType.EnemyBulletN, new boolean[5][5]);
			spriteMap.put(SpriteType.EnemyBulletH, new boolean[7][5]);
			spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
			spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
			spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion2, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion3, new boolean[13][7]);
			spriteMap.put(SpriteType.Explosion4, new boolean[13][9]);
			spriteMap.put(SpriteType.Item, new boolean[9][8]);
			fileManager.loadSprite(spriteMap);
			logger.info("Finished loading the sprites.");

			// Font loading.
			fontRegular = fileManager.loadFont(14f);
			fontRegular2 = fileManager.loadFont(17f);
			fontBig = fileManager.loadFont(24f);
			fontSmall = fileManager.loadFont(12f);
			logger.info("Finished loading the fonts.");

			// Images Loading
			imagemap = new LinkedHashMap<String, BufferedImage>();
			imagemap.put("macarona", fileManager.loadImage("macarona.png"));
			imagemap.put("coin", fileManager.loadImage("coin.png"));
			imagemap.put("sel", fileManager.loadImage("selected.png"));
			imagemap.put("shipr", fileManager.loadImage("shipred.png"));
			imagemap.put("shipg", fileManager.loadImage("shipgreen.png"));
			imagemap.put("shipb", fileManager.loadImage("shipblue.png"));
			imagemap.put("bgm1", fileManager.loadImage("bgm_1.png"));
			imagemap.put("bgm2", fileManager.loadImage("bgm_2.png"));
			imagemap.put("bgm3", fileManager.loadImage("bgm_3.png"));
			imagemap.put("item_heart", fileManager.loadImage("heart.png"));
			imagemap.put("item_bulletspeed", fileManager.loadImage("bulspeed.png"));
			imagemap.put("item_movespeed", fileManager.loadImage("movspeed.png"));


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
	 *                     Frame to draw on.
	 */
	public void setFrame(final Frame currentFrame) {
		frame = currentFrame;
	}

	/**
	 * First part of the drawing process. Initialices buffers, draws the
	 * background and prepares the images.
	 * 
	 * @param screen
	 *               Screen to draw in.
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
	 *               Screen to draw on.
	 */
	public void completeDrawing(final Screen screen) {
		graphics.drawImage(backBuffer, frame.getInsets().left,
				frame.getInsets().top, frame);
	}

	/**
	 * Draws an entity, using the apropiate image.
	 *
	 * @param entity
	 *                  Entity to be drawn.
	 * @param positionX
	 *                  Coordinates for the left side of the image.
	 * @param positionY
	 *                  Coordinates for the upper side of the image.
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

	public void drawimg(String name, int positionX, int positionY, int sizex, int sizey) {
		try {
			backBufferGraphics.drawImage(imagemap.get(name), positionX, positionY, sizex, sizey, observer);
		} catch (Exception e) {
		}

	}

	/**
	 * For debugging purpouses, draws the canvas borders.
	 *
	 * @param screen
	 *               Screen to draw in.
	 */
	@SuppressWarnings("unused")
	private void drawBorders(final Screen screen) {
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
	 *               Screen to draw in.
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
	 *               Screen to draw on.
	 * @param score
	 *               Current score.
	 */
	public void drawScore(final Screen screen, final int score) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String scoreString = String.format("%04d", score);
		backBufferGraphics.drawString("Score:" + scoreString, screen.getWidth() - 100, 25);
	}

	public void drawCoin(final Screen screen, final int coin) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		String coinString = String.format("%04d", coin);
		drawimg("coin", screen.getWidth() - 250, 8, 22, 22);
		backBufferGraphics.drawString(coinString, screen.getWidth() - 212, 25);
	}

	/**
	 * Draws number of remaining lives on screen.
	 *
	 * @param screen
	 *               Screen to draw on.
	 * @param lives
	 *               Current lives.
	 */
	public void drawLives(final Screen screen, final int lives) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		
		Ship dummyShip = null;
		switch (Inventory.getcurrentship()) {
			case 1000 -> dummyShip = new Ship(0, 0, Color.GREEN);
			case 1001 -> dummyShip = new Ship(0, 0, Color.RED);
			case 1002 -> dummyShip = new Ship(0, 0, Color.BLUE);
		}
		
		if(lives == -99) {
			backBufferGraphics.drawString("Infin.", 20, 25);	
			drawEntity(dummyShip, 40 + 35, 10);
		} else {
			backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
			for (int i = 0; i < lives; i++)
				drawEntity(dummyShip, 40 + 35 * i, 10);
		}
	}

	/**
	 * Draws a thick line from side to side of the screen.
	 *
	 * @param screen
	 *                  Screen to draw on.
	 * @param positionY
	 *                  Y coordinate of the line.
	 */
	public void drawHorizontalLine(final Screen screen, final int positionY) {
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
		backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(),
				positionY + 1);
	}

	/**
	 * Draws game title.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */
	public void drawTitle(final Screen screen) {
		String titleString = "Invaders";
		String instructionsString = "select with w+s / arrows, confirm with space";

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 2);

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, titleString, screen.getHeight() / 3);
	}

	/**
	 * Draws main menu.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */

	public void drawSettingsMenu(final Screen screen) {
		String settingsString = "Settings";
		String instructionsString = "Press Space to Save Changes";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, playString,
				screen.getHeight() / 3 * 2);

		// returnCode == 3 : highscores
		if (option == 3)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, highScoresString, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 2);

		// returnCode == 4 : settings
		if (option == 4)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, settingString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 3);

		// returnCode == 5 : store
		if (option == 5)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, storeString, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 1);

		// returnCode == 0 : exit
		if (option == 0)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, screensizeString, screen.getHeight() / 3);

		// returnCode == 400020 : masterSound
		if (option == 400020)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, mastersoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);

		// returnCode == 400030 : musicSound
		if (option == 400030)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, musicsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

		// returnCode == 400040 : effectSound
		if (option == 400040)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawLeftRegular2String(screen, effectsoundString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);

		// returnCode == 400050 : HUD Options
		if (option == 400050)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, hudoptionString,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 9);

		// returnCode == 400060 : help
		if (option == 400060)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, helpString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 11);

		// returnCode == 1 : exit
		if (option == 1)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegular2String(screen, exitString, screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 13);
	}

	// 스크린 설정
	public void drawSettingOption(final Screen screen, final int option, final int screenchange,
			final int MasterSoundchange, final int MusicSoundchange, final int EffectSoundchange) {
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
		if (option == 400010) {
			if (screenchange == 1) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption1;
			}
			if (screenchange == 2) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption2;
			}
			if (screenchange == 3) {
				backBufferGraphics.setColor(Color.white);
				defaultScreenmessage = screenSizeOption3;
			}
		} else {
			backBufferGraphics.setColor(Color.darkGray);
		}
		// drawRightRegular2String(screen, defaultScreenmessage, screen.getHeight() /
		// 3);
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
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMasterSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 2);

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
				defaultMusicSoundmessage = SoundOption4;
			}
			if (MusicSoundchange == 5) {
				backBufferGraphics.setColor(Color.white);
				defaultMusicSoundmessage = SoundOption5;
			}
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultMusicSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 4);

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
		} else
			backBufferGraphics.setColor(Color.darkGray);
		drawRightRegular2String(screen, defaultEffectSoundmessage,
				screen.getHeight() / 3 + fontRegular2Metrics.getHeight() * 6);
	}

	/**
	 * Draws game results.
	 *
	 * @param screen
	 *                       Screen to draw on.
	 * @param score
	 *                       Score obtained.
	 * @param livesRemaining
	 *                       Lives remaining when finished.
	 * @param shipsDestroyed
	 *                       Total ships destroyed.
	 * @param accuracy
	 *                       Total accuracy.
	 * @param isNewRecord
	 *                       If the score is a new high score.
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
	 *                         Screen to draw on.
	 * @param name
	 *                         Current name selected.
	 * @param nameCharSelected
	 *                         Current character selected for modification.
	 */
	public void drawNameInput(final Screen screen, final char[] name,
			final int nameCharSelected) {
		String newRecordString = "New Record!";
		String introduceNameString = "Introduce name:";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
				backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
	 *                     Screen to draw on.
	 * @param acceptsInput
	 *                     If the screen accepts input.
	 * @param isNewRecord
	 *                     If the score is a new high score.
	 */
	public void drawGameOver(final Screen screen, final boolean acceptsInput,
			final boolean isNewRecord) {
		String gameOverString = "Game Over";
		String continueOrExitString = "Press Space to play again, Escape to exit";

		int height = isNewRecord ? 4 : 2;

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, gameOverString, screen.getHeight()
				/ height - fontBigMetrics.getHeight() * 2);

		if (acceptsInput)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, continueOrExitString,
				screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
	}

	/**
	 * Draws high score screen title and instructions.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */
	public void drawHighScoreMenu(final Screen screen) {
		String highScoreString = "High Scores";
		String instructionsString = "Press Space to return";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString,
				screen.getHeight() / 5);
	}

	public void drawHighScores_submenu(final Screen screen) {
		String name = "Name";
		String score = "Score";
		String killed = "Killed";
		String bullet = "Bullets";
		String accuracy = "Accuracy";
		String stage = "Stage";

		backBufferGraphics.setColor(Color.gray);
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.fillRect(0, 105, 450, 35);

		backBufferGraphics.setColor(Color.red);
		backBufferGraphics.drawString(name, 13, 105 + 24);
		backBufferGraphics.drawString(score, 63, 129);
		backBufferGraphics.drawString(killed, 128, 129);
		backBufferGraphics.drawString(bullet, 203, 129);
		backBufferGraphics.drawString(accuracy, 290, 129);
		backBufferGraphics.drawString(stage, 388, 129);

	}

	/**
	 * Draws high scores.
	 *
	 * @param screen
	 *                   Screen to draw on.
	 * @param highScores
	 *                   List of high scores.
	 */
	public void drawHighScores(final Screen screen,
			final List<Score> highScores) {
		backBufferGraphics.setColor(Color.WHITE);
		int i = 0;
		String nameString = "";
		String scoreString = "";
		String killedString = "";
		String bulletString = "";
		String accuracyString = "";
		String stageString = "";
		for (Score score : highScores) {
			scoreString = String.format("%s    %04d    %04d           %04d           %02.02f            %d   ",
					score.getName(),
					score.getScore(), score.getKilled(), score.getBullets(), score.getAccuracy(),
					score.getStage()); // need change 5th variables and score.getStage()
			drawCenteredRegularString(screen, scoreString, screen.getHeight()
					/ 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
			i++;
		}
	}

	/**
	 * Draws settings screen title and instructions.
	 *
	 * @param screen
	 *               Screen to draw on.
	 */

	public void drawStoreMenu(final Screen screen) {
		String storeString = "Store";
		String instructionsString = "Store";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, storeString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
	}

	public void drawHUDSettingMenu(final Screen screen, final int option) {
		String HUDString = "HUD Setting";
		String instructionsString = "Press Space to return";
		String option1 = "GREEN";
		String option2 = "RED";
		String option3 = "BLUE";

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		drawCenteredBigString(screen, HUDString, screen.getHeight() / 8);

		backBufferGraphics.setColor(Color.GRAY);
		drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);

		if (option == 1)
			backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option1,
				screen.getHeight() / 3 * 2);

		if (option == 2)
			backBufferGraphics.setColor(Color.RED);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option2, screen.getHeight()
				/ 3 * 2 + fontRegularMetrics.getHeight() * 2);

		if (option == 3)
			backBufferGraphics.setColor(Color.BLUE);
		else
			backBufferGraphics.setColor(Color.WHITE);
		drawCenteredRegularString(screen, option3, screen.getHeight() / 3
				* 2 + fontRegularMetrics.getHeight() * 4);

	}

	public void drawHelpMenu(final Screen screen) {
		String HelpString = "Help";
		String instructionsString = "Help menu";
		String[] help1 = { "Press the arrow keys <- / -> (or the A or D keys)", "to move your ship" };
		String[] help2 = { "Press space to shoot & hit an enemy ships" };
		String[] help3 = { "Dodge enemy missiles by pressing the key" };
		String[] help4 = { "The life to play the game is 3" };
		String[] help5 = { "Eliminate all enemies", "to advance to the next level" };
		String[] help6 = { "check your highscore in highscore menu" };
		String[][] helps = { help1, help2, help3, help4, help5, help6 };
		int i = 0;
		int j = 0;

		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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
	 *               Screen to draw on.
	 * @param string
	 *               String to draw.
	 * @param height
	 *               Height of the drawing.
	 */
	public void drawCenteredRegular2String(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegular2Metrics.stringWidth(string) / 2,
				height);
	}

	public void drawCenteredRegularString(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegularMetrics.stringWidth(string) / 2,
				height);
	}

	public void drawLeftRegular2String(final Screen screen,
			final String string, final int height) {
		backBufferGraphics.setFont(fontRegular2);
		backBufferGraphics.drawString(string, screen.getWidth() / 4 - fontRegular2Metrics.stringWidth(string) / 2,
				height);
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
	 *               Screen to draw on.
	 * @param string
	 *               String to draw.
	 * @param height
	 *               Height of the drawing.
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
	 *                  Screen to draw on.
	 * @param level
	 *                  Game difficulty level.
	 * @param number
	 *                  Countdown number.
	 * @param bonusLife
	 *                  Checks if a bonus life is received.
	 */
	public void drawCountDown(final Screen screen, final int level,
			final int number, final boolean bonusLife) {
		int rectWidth = screen.getWidth();
		int rectHeight = screen.getHeight() / 6;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2,
				rectWidth, rectHeight);
		backBufferGraphics.setColor(HUDSettingScreen.getScreenColor());
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

	public int getshopgridcoordx(int c) {
		return 31 + 100 * c;
		// assumed grid size
	}

	public int getshopgridcoordy(int r) {
		return 130 + 130 * r;
		// assumed grid size
	}

	public void drawshop(Screen screen, int curr, int curc, ShopScreen.shopstates state) {
		int x = 0, y = 0;
		// draw top bar
		backBufferGraphics.setFont(fontRegular);
		backBufferGraphics.setColor(Color.WHITE);
		if (state == shopstates.SHOP_RET)
			backBufferGraphics.setColor(Color.GREEN);
		backBufferGraphics.drawString("SELECT: SPACE", 10, 25);
		backBufferGraphics.drawString("RETURN: ESCAPE", 10, 45);
		backBufferGraphics.setColor(Color.WHITE);
		drawCenteredBigString(screen, "Shop", 40);
		backBufferGraphics.setColor(Color.WHITE);
		drawimg("coin", screen.getWidth() - 100, 15, 30, 30);
		backBufferGraphics.drawString(String.valueOf(Coin.balance), screen.getWidth() - 55, 40);
		backBufferGraphics.drawLine(0, 60, backBuffer.getWidth(), 60);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				if (curr == i && curc == j && state == shopstates.SHOP_INVEN) {
					backBufferGraphics.setColor(Color.GREEN);
					backBufferGraphics.drawRect(getshopgridcoordx(i), getshopgridcoordy(j), 70, 70);
					backBufferGraphics.setColor(Color.WHITE);
					continue;
				}
				backBufferGraphics.drawRect(getshopgridcoordx(i), getshopgridcoordy(j), 70, 70);
			}
		}

		backBufferGraphics.drawString("SHIP", 31, 120);
		drawimg("shipg", getshopgridcoordx(0) + 11, getshopgridcoordy(0) + 9, 50, 50);
		drawimg("shipr", getshopgridcoordx(1) + 11, getshopgridcoordy(0) + 9, 50, 50);
		drawimg("shipb", getshopgridcoordx(2) + 11, getshopgridcoordy(0) + 9, 50, 50);
		drawimg("coin", screen.getWidth() - 100, 15, 30, 30);
		backBufferGraphics.drawString("BGM", 31, 250);
		drawimg("bgm1", getshopgridcoordx(0) + 8, getshopgridcoordy(1) + 11, 50, 50);
		drawimg("bgm2", getshopgridcoordx(1) + 8, getshopgridcoordy(1) + 11, 50, 50);
		drawimg("bgm3", getshopgridcoordx(2) + 8, getshopgridcoordy(1) + 11, 50, 50);
		backBufferGraphics.setColor(Color.WHITE);
		int leftbuf = (backBuffer.getWidth() - (50 * 5 + 30 * 4)) / 2;
		backBufferGraphics.drawRect(31, 370, backBuffer.getWidth() - 62, backBuffer.getHeight() - 415);

		String shipinfo_1 = new String(
				"<GREEN SHIP>\n: THIS SHIP IS GREEN \n>>> DEFAULT");
		String shipinfo_2 = new String(
				"<RED SHIP>\n: THIS SHIP IS RED\n>>> 100 COIN");
		String shipinfo_3 = new String(
				"<BLUE SHIP>\n: THIS SHIP IS BLUE\n>>> 1000 COIN");
		String bgminfo_1 = new String(
				"<BGM 1>\n: THIS IS DEFAULT MUSIC\n>>> DEFAULT");
		String bgminfo_2 = new String(
				"<BGM 2>\n: GOOD MUSIC\n>>> 100 COIN");
		String bgminfo_3 = new String(
				"<BGM 3>\n: AWESOME MUSIC\n>>> 1000 COIN");

		if (selecteditem().itemid == 1000)
			drawmultiline(screen, shipinfo_1, 45, 390, 3);
		else if (selecteditem().itemid == 1001)
			drawmultiline(screen, shipinfo_2, 45, 390, 3);
		else if (selecteditem().itemid == 1002)
			drawmultiline(screen, shipinfo_3, 45, 390, 3);
		else if (selecteditem().itemid == 2000)
			drawmultiline(screen, bgminfo_1, 45, 390, 3);
		else if (selecteditem().itemid == 2001)
			drawmultiline(screen, bgminfo_2, 45, 390, 3);
		else if (selecteditem().itemid == 2002)
			drawmultiline(screen, bgminfo_3, 45, 390, 3);
		/**
		 * for (int i = 0; i < Inventory.inventory.size(); i++) {
		 * backBufferGraphics.drawString(Item.itemregistry.get(i).name, x, y);
		 * }
		 */

	}

	// like MessageBox
	public enum shopmodaltype {
		SM_YESNO, SM_OK
	}

	public void drawShopModal(Screen screen, String item_name, String item_price, engine.DrawManager.shopmodaltype mode,
			int modaloption) {
		int winw = backBuffer.getWidth() * 8 / 10;
		int winh = backBuffer.getHeight() * 8 / 10;
		int winxbase = (backBuffer.getWidth() - winw) / 2;
		int winybase = (backBuffer.getHeight() - winh) / 2;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.drawRect(winxbase, winybase, winw, winh);
		backBufferGraphics.fillRect(winxbase, winybase, winw, winh);
		backBufferGraphics.setColor((Color.WHITE));
		backBufferGraphics.drawRect(winxbase, winybase, winw, winh);
		backBufferGraphics.drawRect(winxbase + 5, winybase + 5, winw - 10, winh - 10);
		drawCenteredBigString(screen, item_name, winxbase + 50);
		if (selecteditem().itemid == 1001) {
			drawimg("shipr", screen.getWidth() / 2 - 40, screen.getHeight() / 2 - 60, 80, 80);
		}
		if (selecteditem().itemid == 1002) {
			drawimg("shipb", screen.getWidth() / 2 - 40, screen.getHeight() / 2 - 60, 80, 80);
		}
		if (selecteditem().itemid == 2001) {
			drawimg("bgm2", screen.getWidth() / 2 - 40, screen.getHeight() / 2 - 60, 80, 80);
		}
		if (selecteditem().itemid == 2002) {
			drawimg("bgm3", screen.getWidth() / 2 - 40, screen.getHeight() / 2 - 60, 80, 80);
		}
		drawCenteredRegularString(screen, "Price :" + item_price, winxbase + 80);
		drawCenteredBigString(screen, "Purchase?", winybase * 7);
		if (modaloption == 0)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("YES", winxbase + (winw / 4) - fontRegularMetrics.stringWidth("YES") / 2, winybase * 8);
		if (modaloption == 1)
			backBufferGraphics.setColor(Color.GREEN);
		else
			backBufferGraphics.setColor(Color.WHITE);
		backBufferGraphics.drawString("NO", winxbase + (winw / 10) * 7 - fontRegularMetrics.stringWidth("NO") / 2, winybase * 8);
	}

	public void drawShopCheck(Screen screen, String text) {
		int winw = backBuffer.getWidth() * 8 / 10;
		int winh = backBuffer.getHeight() * 4 / 10;
		int winxbase = (backBuffer.getWidth() - winw) / 2;
		int winybase = (backBuffer.getHeight() - winh) / 2;
		backBufferGraphics.setColor(Color.BLACK);
		backBufferGraphics.drawRect(winxbase, winybase, winw, winh);
		backBufferGraphics.fillRect(winxbase, winybase, winw, winh);
		backBufferGraphics.setColor((Color.WHITE));
		backBufferGraphics.drawRect(winxbase, winybase, winw, winh);
		backBufferGraphics.drawRect(winxbase + 5, winybase + 5, winw - 10, winh - 10);
		drawCenteredBigString(screen, text, winybase * 3 / 2) ;
		backBufferGraphics.setColor(Color.GREEN);
		drawCenteredRegularString(screen, "OK", winybase * 2); ;
	}

	public void drawSelectIcon_ship(Screen screen, int x, int y) {
		backBufferGraphics.drawImage(imagemap.get("sel"), x, y, null, observer);
	}

	public void drawSelectIcon_bgm(Screen screen, int x, int y) {
		backBufferGraphics.drawImage(imagemap.get("sel"), x, y, null, observer);
	}

	private java.util.ArrayList<String> formatstr(String input) {
		int linelen = 44;
		int frontdelim = 0;
		int backdelim = 0;
		var x = new ArrayList<String>();
		while ((input.length() - frontdelim > linelen) || ((input.indexOf('\n', frontdelim) != -1))) {
			if ((input.indexOf('\n', frontdelim) != -1) && ((input.indexOf('\n', frontdelim) - frontdelim) < linelen)) {
				backdelim = input.indexOf('\n', frontdelim);
				x.add(input.substring(frontdelim, backdelim));
				frontdelim = backdelim + 1;
			} else {
				backdelim = frontdelim + linelen;
				x.add(input.substring(frontdelim, backdelim));
				frontdelim = backdelim;
			}
		}
		x.add(input.substring(frontdelim, input.length()));
		return x;
	}

	private void drawmultiline(Screen scr, String input, int x, int y, int maxlines) {
		int offset = 0;
		int c = 1;
		for (String istr : formatstr(input)) {
			if (c++ > maxlines)
				return;
			backBufferGraphics.setColor((Color.WHITE));
			backBufferGraphics.setFont(fontRegular);
			backBufferGraphics.drawString((String) istr, 45, c * 20 + 356);
			offset += fontRegularMetrics.getHeight();
		}
	}
}
