package screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;

import engine.*;
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
		super.run();
		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	public enum shopstates {
		SHOP_INVEN, SHOP_RET, SHOP_MODAL, SHOP_APPLY
	}

	shopstates state;
	static int invrow = 0;
	static int invcol = 0;
	static int apply_ship = 0;
	static int apply_bgm = 0;

	shopmodaltype modaltype;
	int modaloption = 0;
	int location = 0;

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
						if (invcol == 1)
							invcol = 1;
						else
							invcol++;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						if (checkItem(selecteditem())) {
							this.state=shopstates.SHOP_APPLY;
							this.selectionCooldown.reset();
						}
						else {
							this.state = shopstates.SHOP_MODAL;
							this.selectionCooldown.reset();
						}
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
						if (modaloption == 1)
							modaloption--;
						else
							modaloption = 0;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
							|| inputManager.isKeyDown(KeyEvent.VK_D)) {
						if (modaloption == 0) {
							modaloption++;
						}
						else
							modaloption = 1;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						if (modaloption == 0) {
							if (!purchase(selecteditem(), 1)) {
								Inventory.inventory.add(selecteditem());
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
						}
						else if (modaloption == 1) {
							this.state = shopstates.SHOP_INVEN;
							this.selectionCooldown.reset();
						}
					}
				}
				break;
			case SHOP_APPLY:
				if (this.selectionCooldown.checkFinished()
						&& this.inputDelay.checkFinished()) {
					if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
							|| inputManager.isKeyDown(KeyEvent.VK_A)) {
						if (location == 1)
							location--;
						else
							location = 0;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
							|| inputManager.isKeyDown(KeyEvent.VK_D)) {
						if (location == 0) {
							location++;
						}
						else
							location = 1;
						this.selectionCooldown.reset();
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						if (location == 0) {
							/** Implement the application of the item
							 * If you make multiple ship skins, change the number of apply_ship by type
							 * example apply_ship of ship1 is 1, apply_ship of ship2 is 2...
							 * update DrawManager's ship spriteType */
							if (selecteditem().itemid == 1000) {
								apply_ship = 1;
								engine.Inventory.inventory.add(selecteditem());
								engine.Inventory.inventory.get(0).appliedp=true;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 1001) {
								apply_ship = 2;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 2000) {
								apply_bgm = 1;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 2001) {
								apply_bgm = 2;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							this.selectionCooldown.reset();

						}
						else if (location == 1) {
							if (apply_ship != 0) {
								apply_ship = 0;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							else if (apply_bgm != 0) {
								apply_bgm = 0;
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
							else {
								this.state = shopstates.SHOP_INVEN;
								this.selectionCooldown.reset();
							}
						}
					}
				}
				break;

		}
	}

	public static boolean checkItem(engine.Item item)
	{
		for (int i = 0; i < Inventory.inventory.size(); i++) {
			if (item.itemid == Inventory.inventory.get(i).itemid)
				return true;
		}
		return false;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawshop(this, invrow, invcol, this.state);
		if (this.state == shopstates.SHOP_MODAL) {
			if (invrow == 0 || invrow == 1 || invrow == 2) {
				drawManager.drawshopmodal(this, Item.itemregistry.get(invrow).name, String.valueOf(Item.itemregistry.get(invrow).price), shopmodaltype.SM_YESNO, modaloption);
			}
			if (invcol == 1) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawshopmodal(this, Item.itemregistry.get(invrow + 3).name, String.valueOf(Item.itemregistry.get(invcol + invrow + 2).price), shopmodaltype.SM_YESNO, modaloption);
			}
			if (invcol == 2) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawshopmodal(this, Item.itemregistry.get(invrow + 6).name, String.valueOf(Item.itemregistry.get(invcol + invrow + 2).price), shopmodaltype.SM_YESNO, modaloption);
			}
			if (invcol == 3) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawshopmodal(this, Item.itemregistry.get(invrow + 9).name, String.valueOf(Item.itemregistry.get(invcol + invrow + 2).price), shopmodaltype.SM_YESNO, modaloption);
			}
			if (invcol == 4) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawshopmodal(this, Item.itemregistry.get(invrow + 12).name, String.valueOf(Item.itemregistry.get(invcol + invrow + 2).price), shopmodaltype.SM_YESNO, modaloption);
			}
		}

		else if (this.state == shopstates.SHOP_APPLY) {
			if (invrow == 0 || invrow == 1 || invrow == 2) {
				drawManager.drawApplyMenu(this, Item.itemregistry.get(invrow).name, location);
			}
			if (invcol == 1) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawApplyMenu(this, Item.itemregistry.get(invrow + 3).name, location);
			}
			if (invcol == 2) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawApplyMenu(this, Item.itemregistry.get(invrow + 6).name, location);
			}
			if (invcol == 3) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawApplyMenu(this, Item.itemregistry.get(invrow + 9).name, location);
			}
			if (invcol == 4) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawApplyMenu(this, Item.itemregistry.get(invrow + 12).name, location);
			}
		}

		drawManager.completeDrawing(this);
	}

	public boolean purchase(engine.Item item, int qty) {
		return (engine.Coin.spend(item.price * qty) == -1) ? true : false;
	}


	private void layoutitem()
	{
		engine.Item.itemregistry.size();
	}

	public static engine.Item selecteditem()
	{
		if (invcol == 1) {
			return Item.itemregistry.get(invrow + 3);
		}
		if (invcol == 2) {
			return Item.itemregistry.get(invrow + 6);
		}
		if (invcol == 3) {
			return Item.itemregistry.get(invrow + 9);
		}
		if (invcol == 4) {
			return Item.itemregistry.get(invrow + 12);
		}
		return Item.itemregistry.get(invrow);
	}
	public static int getApply_ship() {
		return apply_ship;
	}
	public static int getApply_bgm() {
		return apply_bgm;
	}
}
