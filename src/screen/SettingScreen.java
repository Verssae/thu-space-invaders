package screen;

import java.awt.event.KeyEvent;

import engine.Cooldown;
import engine.Core;
/**
 * Implements the setting screen, it shows setting menu.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class SettingScreen extends Screen {

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

        this.returnCode = 400010;
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


    /**
     * Shifts the focus to the next item.
     */
    private void nextItem() {
        // returnCode
        // 400001 = Screen Size
        // 2 = ScreenSizeSetting
        // 400002 = Master sound
        // 4 = soundicon
        // 400003 = Music sound
        // 6 = soundicon
        // 400004 = Effect sound
        // 8 = soundicon
        // 400005 = hudOptions
        // 400006 = help
        // 1 = exit
        if (this.returnCode == 400010)
            this.returnCode = 400020;
        else if (this.returnCode == 400020)
            this.returnCode = 400030;
        else if (this.returnCode == 400030)
            this.returnCode = 400040;
        else if (this.returnCode == 400040)
            this.returnCode = 400050;
        else if (this.returnCode == 400050)
            this.returnCode = 400060;
        else if (this.returnCode == 400060)
            this.returnCode = 1;
        else if (this.returnCode == 1)
            this.returnCode = 400010;
    }

    /**
     * Shifts the focus to the previous item.
     */
    private void previousItem() {
        // returnCode
        // 400010 = Screen Size
        // 400020 = Master sound
        // 400030 = Music sound
        // 400040 = Effect sound
        // 400050 = hudOptions
        // 400060 = help
        // 1 = exit
        if (this.returnCode == 1)
            this.returnCode = 400060;
        else if (this.returnCode == 400060)
            this.returnCode = 400050;
        else if (this.returnCode == 400050)
            this.returnCode = 400040;
        else if (this.returnCode == 400040)
            this.returnCode = 400030;
        else if (this.returnCode == 400030)
            this.returnCode = 400020;
        else if (this.returnCode == 400020)
            this.returnCode = 400010;
        else if (this.returnCode == 400010)
            this.returnCode = 1;
    }



    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawSettingsMenu(this);
        drawManager.drawSettingItems(this, this.returnCode);
        drawManager.completeDrawing(this);
    }
}