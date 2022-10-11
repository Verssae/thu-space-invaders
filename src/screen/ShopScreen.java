package screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.Cooldown;
import engine.Core;
import engine.Item;
import engine.DrawManager.shopmodaltype;

//notimplementedexception
public class ShopScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;

	private boolean modalp;

	/**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *               Screen width.
	 * @param height
	 *               Screen height.
	 * @param fps
	 *               Frames per second, frame rate at which the game is run.
	 */
	public ShopScreen(final int width, final int height, final int fps, final int retpos) {
		super(width, height, fps);
		this.returnCode = retpos;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
	}

	/**
	 * Starts the action.
	 * 
	 * @return Next screen code.
	 */
	public final int run() {
		this.state = shopstates.SHOP_INVEN;
		viewing=new ArrayList<Item>();
		super.run();
		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	public enum shopstates {
		SHOP_INVEN, SHOP_RET, SHOP_MODAL, SHOP_FILTER
	}

	shopstates state;
	int invrow = 0;
	int invcol = 0;

	shopmodaltype modaltype;
	int modaloption;

	protected final void update() {
		super.update();

		draw();
		switch (state) {
			case SHOP_INVEN:
				if (this.selectionCooldown.checkFinished()
						&& this.inputDelay.checkFinished()) {
					if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
							|| inputManager.isKeyDown(KeyEvent.VK_A)) {
						if (invrow == 0)
							invrow = 0;
						else
							invrow--;
						this.selectionCooldown.reset();
					} else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
							|| inputManager.isKeyDown(KeyEvent.VK_D)) {
						if (invrow == 2)
							invrow = 2;
						else
							invrow++;
						this.selectionCooldown.reset();
					} else if (inputManager.isKeyDown(KeyEvent.VK_UP)
							|| inputManager.isKeyDown(KeyEvent.VK_W)) {
						if (invcol == 0)
							this.state = shopstates.SHOP_RET;
						else
							invcol--;
						this.selectionCooldown.reset();
					} else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
							|| inputManager.isKeyDown(KeyEvent.VK_S)) {
						if (invcol == 4)
							invcol = 4;
						else
							invcol++;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						this.state=shopstates.SHOP_MODAL;
						//
						this.selectionCooldown.reset();
					}
				}
				break;
			case SHOP_RET:
				if (this.selectionCooldown.checkFinished()
						&& this.inputDelay.checkFinished()) {
					if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
						this.isRunning = false;
					else if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
							|| inputManager.isKeyDown(KeyEvent.VK_S)) {
						this.state = shopstates.SHOP_INVEN;
						this.selectionCooldown.reset();
					}
				}
				if (inputManager.isKeyDown(KeyEvent.VK_SPACE))
					this.isRunning = false;
				break;
			case SHOP_MODAL:
			if (this.selectionCooldown.checkFinished()
						&& this.inputDelay.checkFinished()) {
					if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
							|| inputManager.isKeyDown(KeyEvent.VK_A)) {
						this.selectionCooldown.reset();
					} else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
							|| inputManager.isKeyDown(KeyEvent.VK_D)) {

						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						//item selection!!
						this.state=shopstates.SHOP_INVEN;
						this.selectionCooldown.reset();
					}
				}
				break;
			case SHOP_FILTER:
				break;

		}
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawshop(this, invrow, invcol, this.state);
		if (this.state == shopstates.SHOP_MODAL) {
			drawManager.drawshopmodal(this, "HELLO", "BYE",  shopmodaltype.SM_YESNO, modaloption);
		}

		drawManager.completeDrawing(this);
	}

	public boolean purchase(engine.Item item, int qty) {
		return (engine.Coin.spend(item.price * qty) == -1) ? true : false;
	}

	static ArrayList<Item> viewing;
	private void layoutitem()
	{
		engine.Item.itemregistry.size();
	}

	public engine.Item selecteditem()
	{
		return viewing.get(3*invrow+invcol);
	}
}
