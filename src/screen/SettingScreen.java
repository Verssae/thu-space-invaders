package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager;

/**
 * Implements the setting screen, it shows setting menu.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class SettingScreen extends Screen {

    int optioncode;

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public SettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.returnCode = 1;
        //defults to setting
        optioncode = 1;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && optioncode == 0)
                this.isRunning = false;
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && optioncode == 11)
                this.isRunning = false;
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && optioncode == 12)
                this.isRunning = false;
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) && optioncode == 13)
                this.isRunning = false;
        }
    }

    private void nextMenuItem(){
        if(optioncode == 0)
            optioncode = 1;
        else if (optioncode == 1)
            optioncode = 11;
        else if(optioncode == 11)
            optioncode = 12;
        else if(optioncode == 12)
            optioncode = 13;
        else if(optioncode == 13)
            optioncode = 0;
        else
            optioncode++;
    }

    private void previousMenuItem(){
        if(optioncode == 1)
            optioncode = 0;
        else if (optioncode == 0)
            optioncode = 13;
        else if(optioncode == 13)
            optioncode = 12;
        else if(optioncode == 12)
            optioncode = 11;
        else if(optioncode == 11)
            optioncode = 1;
        else
            optioncode--;
    }



    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSettingsMenu(this);
        drawManager.drawSettings(this, optioncode);

        drawManager.completeDrawing(this);
    }

}