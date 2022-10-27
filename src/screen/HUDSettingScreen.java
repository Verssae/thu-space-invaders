package screen;

import java.awt.*;
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
    public static int colorchange;
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
        this.colorchange = 1;
        this.returnCode = 4;

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
        if(this.colorchange == 3)
            this.colorchange = 1;
        else if(this.colorchange == 1)
            this.colorchange = 2;
        else if(this.colorchange == 2)
            this.colorchange = 3;
        else
            this.colorchange++;
    }

    private void previousItem(){
        if(this.colorchange == 1)
            this.colorchange = 3;
        else if(this.colorchange == 3)
            this.colorchange = 2;
        else if(this.colorchange == 2)
            this.colorchange = 1;
        else
            this.colorchange--;
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawHUDSettingMenu(this, this.colorchange);
        drawManager.completeDrawing(this);
    }

    /**
     * Exchange colors according to 'colorchange' parameter
     * 
     * @return Color information
     */
    public static Color getScreenColor(){
        if(HUDSettingScreen.colorchange == 1){
            return Color.GREEN;
        }
        else if(HUDSettingScreen.colorchange == 2){
            return Color.RED;
        }
        else if(HUDSettingScreen.colorchange == 3){
            return Color.BLUE;
        }
        else{
            return Color.GREEN;
        }
    }
}