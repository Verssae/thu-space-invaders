package screen;

import java.awt.event.KeyEvent;
import java.awt.*;
import engine.Cooldown;
import engine.Core;
/**
 * Implements the setting screen, it shows setting menu.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class SettingScreen extends Screen {

    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 200;
    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;
    /**
     * Screen Change settings.
     */
    private static int Screenchange;
    /**
     * Get screen size
     */
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * MasterSound change settings.
     */
    private static int MasterSoundchange;
    /**
     * MusicSound change settings.
     */
    private static int MusicSoundchange;
    /**
     * EffectSound change settings.
     */
    private static int EffectSoundchange;



    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width  Screen width.
     * @param height Screen height.
     * @param fps    Frames per second, frame rate at which the game is run.
     */
    public SettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

        this.returnCode = 400010;
        this.Screenchange = 1;
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
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                previousScreenMenuChange();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                nextScreenMenuChange();
                this.selectionCooldown.reset();
            }
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

            if (this.returnCode == 400010) {
                changeScreenSize();
            }
        }
    }

    /**
     * change screen size
     */
    private void changeScreenSize() {
        if (this.Screenchange == 1) // Standard
            Core.setSize(448, 520);
        else if (this.Screenchange == 2) // Extended
            Core.setSize(1280, 720);
        else if (this.Screenchange == 3) // Full Screen
            Core.setSize(screenSize.width, screenSize.height);
    }


    /**
     * Shifts the focus to the next item.
     */
    private void nextItem() {
        // returnCode
        // 400010 = Screen Size
        // 400020 = Master sound
        // 400030 = Music sound
        // 400040 = Effect sound
        // 400050 = HUD Options
        // 400060 = help
        // 1 = exit
        if (this.returnCode == 400010) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400020;
        }
        else if (this.returnCode == 400020) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400030;
        }
        else if (this.returnCode == 400030) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400040;
        }
        else if (this.returnCode == 400040) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400050;
        }
        else if (this.returnCode == 400050) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400060;
        }
        else if (this.returnCode == 400060) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 1;
        }
        else if (this.returnCode == 1) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400010;
        }
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
        // 400050 = HUD Options
        // 400060 = help
        // 1 = exit
        if (this.returnCode == 1){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400060;
        }
        else if (this.returnCode == 400060){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400050;
        }
        else if (this.returnCode == 400050){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400040;
        }
        else if (this.returnCode == 400040){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400030;
        }
        else if (this.returnCode == 400030) {
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400020;
        }
        else if (this.returnCode == 400020){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 400010;
        }
        else if (this.returnCode == 400010){
            this.Screenchange = 1;
            this.MasterSoundchange = 1;
            this.EffectSoundchange = 1;
            this.MusicSoundchange = 1;
            this.returnCode = 1;
        }
    }

    /**
     * Shifts the focus to the next change in the settings option.
     */
    private void nextScreenMenuChange() {
        if (this.returnCode == 400010) {
            int screenoption_changes = 3;
            if (this.Screenchange == screenoption_changes)
                this.Screenchange = 1;
            else if (this.Screenchange == 1)
                this.Screenchange = 2;
            else
                this.Screenchange++;
        }
        if (this.returnCode == 400020) {
            int MasterSoundoption_change = 5;
            if(this.MasterSoundchange == MasterSoundoption_change)
                this.MasterSoundchange = 1;
            else if (this.MasterSoundchange == 1)
                this.MasterSoundchange = 2;
            else
                this.MasterSoundchange++;
        }
        if (this.returnCode == 400030) {
            int MasterSoundoption_change = 5;
            if(this.MusicSoundchange == MasterSoundoption_change)
                this.MusicSoundchange = 1;
            else if (this.MusicSoundchange == 1)
                this.MusicSoundchange = 2;
            else
                this.MusicSoundchange++;
        }
        if (this.returnCode == 400040) {
            int MasterSoundoption_change = 5;
            if(this.EffectSoundchange == MasterSoundoption_change)
                this.EffectSoundchange = 1;
            else if (this.EffectSoundchange == 1)
                this.EffectSoundchange = 2;
            else
                this.EffectSoundchange++;
        }
    }

    /**
     * Shifts the focus to the previous change in the settings option.
     */
    private void previousScreenMenuChange() {
        if (this.returnCode == 400010) {
            int screenoption_change = 3;
            if (this.Screenchange == 1)
                this.Screenchange = screenoption_change;
            else if (this.Screenchange == 2)
                this.Screenchange = 1;
            else
                this.Screenchange--;
        }
        if (this.returnCode == 400020) {
            int MaterSoundoption_change = 5;
            if (this.MasterSoundchange == 1)
                this.MasterSoundchange = MaterSoundoption_change;
            else if (this.MasterSoundchange == 2)
                this.MasterSoundchange = 1;
            else
                this.MasterSoundchange--;
        }
        if (this.returnCode == 400030) {
            int MusicSoundoption_change = 5;
            if (this.MusicSoundchange == 1)
                this.MusicSoundchange = MusicSoundoption_change;
            else if (this.MusicSoundchange == 2)
                this.MusicSoundchange = 1;
            else
                this.MusicSoundchange--;
        }
        if (this.returnCode == 400040) {
            int EffectSoundoption_change = 5;
            if (this.EffectSoundchange == 1)
                this.EffectSoundchange = EffectSoundoption_change;
            else if (this.EffectSoundchange == 2)
                this.EffectSoundchange = 1;
            else
                this.EffectSoundchange--;
        }
    }


    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSettingsMenu(this);
        drawManager.drawSettingOption(this, this.returnCode, Screenchange, MasterSoundchange, MusicSoundchange, EffectSoundchange);
        drawManager.drawSettingItems(this, this.returnCode);
        drawManager.completeDrawing(this);
    }

}