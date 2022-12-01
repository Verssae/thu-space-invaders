package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;


/**
 * Implements the HUD setting screen, it shows setting menu about HUD.
 */

public class LevelScreen extends Screen {
    /**
     * Screen change parameter
     */
    public static int colorchange;
    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 200;

    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public LevelScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.returnCode = 1001;
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
                previouslevel();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextlevel();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE))

                this.isRunning = false;

        }
    }

    private void nextlevel() {
        if (this.returnCode == 1001)
            this.returnCode = 1011;
        else if (this.returnCode == 1011)
            this.returnCode = 1021;
        else if (this.returnCode == 1021)
            this.returnCode = 1031;
        else if (this.returnCode == 1031)
            this.returnCode = 1001;
        else
            this.returnCode = 1001;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previouslevel() {
        if (this.returnCode == 1001)
            this.returnCode = 1031;
        else if (this.returnCode == 1011)
            this.returnCode = 1001;
        else if (this.returnCode == 1021)
            this.returnCode = 1011;
        else if (this.returnCode == 1031)
            this.returnCode = 1021;
        else
            this.returnCode = 1001;
    }


    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawLevelMenu(this, this.returnCode);
        drawManager.completeDrawing(this);
    }
}
