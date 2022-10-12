package screen;

import java.awt.event.KeyEvent;
import engine.Cooldown;
import engine.Core;


/**
 * Implements the HUD setting screen, it shows setting menu about HUD.
 */

public class HUDSettingScreen extends Screen {
    /**
     * Screen change parameter
     */
    private static int Screenchange;
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
    public HUDSettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        this.Screenchange = 1;
        this.returnCode = 1;

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
                previousItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
                this.isRunning = false;

        }
    }

    private void nextItem(){
        if(this.Screenchange == 3)
            this.Screenchange = 1;
        else if(this.Screenchange == 1)
            this.Screenchange = 2;
        else if(this.Screenchange == 2)
            this.Screenchange = 3;
        else
            this.Screenchange++;
    }

    private void previousItem(){
        if(this.Screenchange == 1)
            this.Screenchange = 3;
        else if(this.Screenchange == 3)
            this.Screenchange = 2;
        else if(this.Screenchange == 2)
            this.Screenchange = 1;
        else
            this.Screenchange--;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawHUDSettingMenu(this, this.Screenchange);
        drawManager.completeDrawing(this);
    }
}