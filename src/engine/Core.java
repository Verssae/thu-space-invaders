package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

import entity.Ship;
import screen.*;

import javax.sound.sampled.Clip;

import engine.Inventory.InventoryEntry;

/**
 * Implements core game logic.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class Core {

	/**
	 * difficulty of game
	 */
	public static int diff;

	/** Bgm player */
	private static Clip clip;

	/**
	 * Width of current screen.
	 */
	private static int WIDTH = 448;
	/**
	 * Height of current screen.
	 */
	private static int HEIGHT = 520;
	/**
	 * Max fps of current screen.
	 */
	private static final int FPS = 60;

	/**
	 * Max lives.
	 */
	private static final int MAX_LIVES = 3;
	/**
	 * Levels between extra life.
	 */
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	/**
	 * Total number of levels.
	 */
	private static final int NUM_LEVELS = 5;

	/**
	 * Difficulty settings for level 1.
	 */
	private static final GameSettings SETTINGS_LEVEL_1_E = new GameSettings(5, 4, 58, 2000);
	/**
	 * Difficulty settings for level 2.
	 */
	private static final GameSettings SETTINGS_LEVEL_2_E = new GameSettings(5, 5, 54, 1900);
	/**
	 * Difficulty settings for level 3.
	 */
	private static final GameSettings SETTINGS_LEVEL_3_E = new GameSettings(6, 5, 50, 1800);
	/**
	 * Difficulty settings for level 4.
	 */
	private static final GameSettings SETTINGS_LEVEL_4_E = new GameSettings(6, 6, 46, 1700);
	/**
	 * Difficulty settings for level 5.
	 */
	private static final GameSettings SETTINGS_LEVEL_5_E = new GameSettings(7, 6, 42, 1600);
	// NORMAL
	private static final GameSettings SETTINGS_LEVEL_1_N = new GameSettings(6, 4, 38, 1500);
	/**
	 * Difficulty settings for level 2.
	 */
	private static final GameSettings SETTINGS_LEVEL_2_N = new GameSettings(6, 5, 34, 1400);
	/**
	 * Difficulty settings for level 3.
	 */
	private static final GameSettings SETTINGS_LEVEL_3_N = new GameSettings(7, 5, 30, 1300);
	/**
	 * Difficulty settings for level 4.
	 */
	private static final GameSettings SETTINGS_LEVEL_4_N = new GameSettings(8, 6, 26, 1200);
	/**
	 * Difficulty settings for level 5.
	 */
	private static final GameSettings SETTINGS_LEVEL_5_N = new GameSettings(9, 6, 22, 1100);
	// HARD
	private static final GameSettings SETTINGS_LEVEL_1_H = new GameSettings(6, 5, 18, 1000);
	/**
	 * Difficulty settings for level 2.
	 */
	private static final GameSettings SETTINGS_LEVEL_2_H = new GameSettings(7, 5, 14, 900);
	/**
	 * Difficulty settings for level 3.
	 */
	private static final GameSettings SETTINGS_LEVEL_3_H = new GameSettings(8, 5, 9, 800);
	/**
	 * Difficulty settings for level 4.
	 */
	private static final GameSettings SETTINGS_LEVEL_4_H = new GameSettings(9, 6, 5, 700);
	/**
	 * Difficulty settings for level 5.
	 */
	private static final GameSettings SETTINGS_LEVEL_5_H = new GameSettings(10, 6, 1, 600);

	/**
	 * Frame to draw the screen on.
	 */
	private static Frame frame;
	/**
	 * Screen currently shown.
	 */
	private static Screen currentScreen;
	/**
	 * Difficulty settings list.
	 */
	private static List<GameSettings> gameSettings;
	/**
	 * Application logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(Core.class
			.getSimpleName());
	/**
	 * Logger handler for printing to disk.
	 */
	private static Handler fileHandler;
	/**
	 * Logger handler for printing to console.
	 */
	private static ConsoleHandler consoleHandler;


	/** Test only !!
	 * You can add item max 15
	 * If you have fewer than 15 items to add, refer to DrawManager's drawshop method
	 * Ship skin itemid is start 1000 ~
	 * Bgm itemid is start 2000 ~ */
	private static final Item Test1 =
			new Item(1000, "Default Ship", 0,false);
	private static final Item Test2 =
			new Item(1001, "Store Ship 1", 100,false);
	private static final Item Test3 =
			new Item(1002, "Store Ship 2", 1000,false);
	private static final Item Test4 =
			new Item(2000, "Default BGM", 0);
	private static final Item Test5 =
			new Item(2001, "Store BGM 1", 100);
	private static final Item Test6 =
			new Item(2002, "Store BGM 2", 1000);


	/**
	 * Test implementation.
	 *
	 * @param args
	 *             Program args, ignored.
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.setUseParentHandlers(false);

			fileHandler = new FileHandler("log");
			fileHandler.setFormatter(new MinimalFormatter());

			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MinimalFormatter());

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new Frame(WIDTH, HEIGHT);
		DrawManager.getInstance().setFrame(frame);
		int width = frame.getWidth();
		int height = frame.getHeight();

		/** Test only !!
		 * You can add item max 15
		 * If you have fewer than 15 items to add, refer to DrawManager's drawshop method */
		Inventory.inventory_ship=new ArrayList<Item>();
		Inventory.inventory_bgm=new ArrayList<Item>();
		Inventory.inventory_ship.add(Test1);
		Inventory.inventory_bgm.add(Test4);
		Inventory.inventory_ship.get(0).appliedp = true;
		Item.itemregistry_ship = new ArrayList<Item>();
		Item.itemregistry_bgm = new ArrayList<Item>();
		Item.itemregistry_ship.add(Test1);
		Item.itemregistry_ship.add(Test2);
		Item.itemregistry_ship.add(Test3);
		Item.itemregistry_bgm.add(Test4);
		Item.itemregistry_bgm.add(Test5);
		Item.itemregistry_bgm.add(Test6);

		gameSettings = new ArrayList<GameSettings>();
		gameSettings.add(SETTINGS_LEVEL_1_E);
		gameSettings.add(SETTINGS_LEVEL_2_E);
		gameSettings.add(SETTINGS_LEVEL_3_E);
		gameSettings.add(SETTINGS_LEVEL_4_E);
		gameSettings.add(SETTINGS_LEVEL_5_E);
		gameSettings.add(SETTINGS_LEVEL_1_N);
		gameSettings.add(SETTINGS_LEVEL_2_N);
		gameSettings.add(SETTINGS_LEVEL_3_N);
		gameSettings.add(SETTINGS_LEVEL_4_N);
		gameSettings.add(SETTINGS_LEVEL_5_N);
		gameSettings.add(SETTINGS_LEVEL_1_H);
		gameSettings.add(SETTINGS_LEVEL_2_H);
		gameSettings.add(SETTINGS_LEVEL_3_H);
		gameSettings.add(SETTINGS_LEVEL_4_H);
		gameSettings.add(SETTINGS_LEVEL_5_H);

		GameState gameState;

		int returnCode = 1;
		do {
			gameState = new GameState(1, 0, MAX_LIVES, 0, 0, Coin.balance);

			switch (returnCode) {
			case 1:
				// Main menu.
				currentScreen = new TitleScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " title screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing title screen.");
				break;
			case 2:

				// Game & score

				Scanner sc = new Scanner(System.in);
				LOGGER.info("Select your difficulty 0 is practice, 1 is easy, 2 is normal, 3 is hard");
				diff = sc.nextInt();
				while(diff < 0 || diff > 3){
					new Sound().backroundmusic();
					LOGGER.info("Select your difficulty 0 is practice, 1 is easy, 2 is normal, 3 is hard");
					diff = sc.nextInt();
				}
				if(diff == 0) {
					do {
						// One extra live every few levels.
						boolean bonusLife = gameState.getLevel()
								% EXTRA_LIFE_FRECUENCY == 0
								&& gameState.getLivesRemaining() < MAX_LIVES;
						
						currentScreen = new PracticeScreen(gameState,
								gameSettings.get(gameState.getLevel() - 1),
								bonusLife, width, height, FPS);
						LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
								+ " game screen at " + FPS + " fps.");
						frame.setScreen(currentScreen);
						LOGGER.info("Closing game screen.");

						gameState = ((PracticeScreen) currentScreen).getGameState();

						gameState = new GameState(gameState.getLevel() + 1,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed(), 0);

					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel()%NUM_LEVELS != 0);

					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					// Return to main menu.
					returnCode = 1;
					LOGGER.info("Closing score screen.");
				} else {
					do {
						new Sound().backroundmusic();
						// One extra live every few levels.
						boolean bonusLife = gameState.getLevel()
								% EXTRA_LIFE_FRECUENCY == 0
								&& gameState.getLivesRemaining() < MAX_LIVES;
						
						currentScreen = new GameScreen(gameState,
								gameSettings.get((diff - 1) * 5),
								bonusLife, width, height, FPS);
						LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
								+ " game screen at " + FPS + " fps.");
						frame.setScreen(currentScreen);
						LOGGER.info("Closing game screen.");

						gameState = ((GameScreen) currentScreen).getGameState();

						gameState = new GameState(gameState.getLevel() + 1,
								gameState.getScore(),
								gameState.getLivesRemaining(),
								gameState.getBulletsShot(),
								gameState.getShipsDestroyed(),
                gameState.getCoin());

					} while (gameState.getLivesRemaining() > 0
							&& gameState.getLevel()%NUM_LEVELS != 0);

					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
							+ " score screen at " + FPS + " fps, with a score of "
							+ gameState.getScore() + ", "
							+ gameState.getLivesRemaining() + " lives remaining, "
							+ gameState.getBulletsShot() + " bullets shot and "
							+ gameState.getShipsDestroyed() + " ships destroyed.");
					currentScreen = new ScoreScreen(width, height, FPS, gameState);
					returnCode = frame.setScreen(currentScreen);
					LOGGER.info("Closing score screen.");
				}
				break;
			case 3:
				// High scores.
				currentScreen = new HighScoreScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " high score screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing high score screen.");
				break;
			case 4:
				//Setting.
				currentScreen = new SettingScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " setting screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing setting screen.");
				break;
			case 5:
				//Store.
				currentScreen = new ShopScreen(width, height, FPS, 1);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " store screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing store screen.");
				break;

			case 400050:
				//HUDSettingScreen.
				currentScreen = new HUDSettingScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " HUDSetting screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing HUDSetting screen.");
				break;

            case 400010:
	            // Main menu.
	            /* This makes the old window disappear */
	            Frame old_frame = frame;
	            /* This creates a new window with new width & height values */
	            frame = new Frame(WIDTH, HEIGHT);
	            DrawManager.getInstance().setFrame(frame);
	            width = frame.getWidth();
	            height = frame.getHeight();
	            currentScreen = new TitleScreen(width, height, FPS);
	            old_frame.dispose();
	            LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
			            + " title screen at " + FPS + " fps.");
	            returnCode = frame.setScreen(currentScreen);
	            LOGGER.info("Closing title screen.");
	            break;

			case 400060:
				//HelpScreen.
				currentScreen = new HelpScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT
						+ " Help screen at " + FPS + " fps.");
				returnCode = frame.setScreen(currentScreen);
				LOGGER.info("Closing Help screen.");
				break;

			default:
				break;
			}

		} while (returnCode != 0);

		fileHandler.flush();
		fileHandler.close();
		System.exit(0);
	}



	/**
	 * Constructor, not called.
	 */
	private Core() {

	}

	/**
	 * Controls access to the logger.
	 *
	 * @return Application logger.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Controls access to the drawing manager.
	 *
	 * @return Application draw manager.
	 */
	public static DrawManager getDrawManager() {
		return DrawManager.getInstance();
	}

	/**
	 * Controls access to the input manager.
	 *
	 * @return Application input manager.
	 */
	public static InputManager getInputManager() {
		return InputManager.getInstance();
	}

	/**
	 * Controls access to the file manager.
	 *
	 * @return Application file manager.
	 */
	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	/**
	 * Controls creation of new cooldowns.
	 * 
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @return A new cooldown.
	 */
	public static Cooldown getCooldown(final int milliseconds) {
		return new Cooldown(milliseconds);
	}

	/**
	 * Controls creation of new cooldowns with variance.
	 * 
	 * @param milliseconds
	 *            Duration of the cooldown.
	 * @param variance
	 *            Variation in the cooldown duration.
	 * @return A new cooldown with variance.
	 */
	public static Cooldown getVariableCooldown(final int milliseconds,
											   final int variance) {
		return new Cooldown(milliseconds, variance);
	}

	public static void setSize(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
	}

	public static int getDiff(){
		return diff;
	}
}