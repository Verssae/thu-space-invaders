package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;


/**
 * Implements the HUD setting screen, it shows setting menu about HUD.
 */

public class LevelMenuScreen extends Screen {

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
    public LevelMenuScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.returnCode = 101;
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
                previouslevelItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextlevelItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
                this.isRunning = false;

        }
    }

    private void nextlevelItem() {
        if (this.returnCode == 105)
            this.returnCode = 101;
        else if (this.returnCode == 101)
            this.returnCode = 102;
        else if (this.returnCode == 102)
            this.returnCode = 103;
        else if (this.returnCode == 103)
            this.returnCode = 104;
        else if (this.returnCode == 104)
            this.returnCode = 105;
        else
            this.returnCode++;
    }

    private void previouslevelItem() {
        if (this.returnCode == 101)
            this.returnCode = 105;
        else if (this.returnCode == 105)
            this.returnCode = 104;
        else if (this.returnCode == 104)
            this.returnCode = 103;
        else if (this.returnCode == 103)
            this.returnCode = 102;
        else if (this.returnCode == 102)
            this.returnCode = 101;
        else
            this.returnCode--;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        //drawManager.drawLevelMenu(this, this.returnCode);
        drawManager.completeDrawing(this);
    }
}

