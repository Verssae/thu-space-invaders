package screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import engine.*;
import engine.DrawManager.shopmodaltype;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.tree.DefaultTreeSelectionModel;

//notimplementedexception
public class ShopScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;

	/** Time between changes in user selection. */
	private Cooldown selectionCooldown;
	/** Buffer Graphics. */
	private static Graphics backBufferGraphics;
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
		default_ship=engine.Inventory.getcurrentship()-1000;
		default_bgm=apply_bgm;
		super.run();
		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	public enum shopstates {
		SHOP_INVEN, SHOP_RET, SHOP_MODAL, SHOP_APPLY, SHOP_CHECK
	}

	shopstates state;
	static int invrow = 0;
	static int invcol = 0;
	static int apply_bgm = 0;
	static int ship_icon = 0;
	static int bgm_icon = 0;

	shopmodaltype modaltype;
	int modaloption = 0;
	int location = 0;
	int checkoption = 0;
	int default_ship = 0;
	int default_bgm = 0;

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
							invcol = 0;
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
						if (Inventory.hasitem(selecteditem())) {
							if (selecteditem().itemid == 1000) {
								default_ship = 0;
								if (Inventory.inventory_ship.size() == 2) {
									Inventory.inventory_ship.get(0).appliedp = true;
									Inventory.inventory_ship.get(1).appliedp = false;
								}
								else if (Inventory.inventory_ship.size() == 3) {
									Inventory.inventory_ship.get(0).appliedp = true;
									Inventory.inventory_ship.get(1).appliedp = false;
									Inventory.inventory_ship.get(2).appliedp = false;
								}
								else if (Inventory.inventory_ship.size() == 1) {
									Inventory.inventory_ship.get(0).appliedp = true;
								}
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 1001) {
								default_ship = 1;
								if (Inventory.inventory_ship.size() == 2) {
									Inventory.inventory_ship.get(0).appliedp = false;
									Inventory.inventory_ship.get(1).appliedp = true;
								}
								else if (Inventory.inventory_ship.size() == 3) {
									Inventory.inventory_ship.get(0).appliedp = false;
									Inventory.inventory_ship.get(1).appliedp = false;
									Inventory.inventory_ship.get(2).appliedp = true;
								}
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 1002) {
								default_ship = 2;
								if (Inventory.inventory_ship.size() == 2) {
									Inventory.inventory_ship.get(0).appliedp = false;
									Inventory.inventory_ship.get(1).appliedp = true;
								}
								else if (Inventory.inventory_ship.size() == 3) {
									Inventory.inventory_ship.get(0).appliedp = false;
									Inventory.inventory_ship.get(1).appliedp = false;
									Inventory.inventory_ship.get(2).appliedp = true;
								}
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 2000) {
								default_bgm = 0;
								apply_bgm = 0;
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 2001) {
								apply_bgm = 1;
								default_bgm = 1;
								this.selectionCooldown.reset();
							}
							else if (selecteditem().itemid == 2002) {
								apply_bgm = 2;
								default_bgm = 2;
								this.selectionCooldown.reset();
							}
							this.selectionCooldown.reset();
						}
						else {
							this.state = shopstates.SHOP_MODAL;
							this.selectionCooldown.reset();
						}
					}
					else if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
						this.isRunning = false;
					}
				}
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
								checkoption = 1;
								this.state = shopstates.SHOP_CHECK;
								if (selecteditem().itemid == 1001) {
									default_ship = 1;
									engine.Inventory.inventory_ship.add(selecteditem());
									if (Inventory.inventory_ship.size() == 2) {
										Inventory.inventory_ship.get(0).appliedp = false;
										Inventory.inventory_ship.get(1).appliedp = true;
									}
									else if (Inventory.inventory_ship.size() == 3) {
										Inventory.inventory_ship.get(0).appliedp = false;
										Inventory.inventory_ship.get(1).appliedp = false;
										Inventory.inventory_ship.get(2).appliedp = true;
									}
									this.selectionCooldown.reset();
								}
								else if (selecteditem().itemid == 1002) {
									default_ship = 2;
									engine.Inventory.inventory_ship.add(selecteditem());
									if (Inventory.inventory_ship.size() == 2) {
										Inventory.inventory_ship.get(0).appliedp = false;
										Inventory.inventory_ship.get(1).appliedp = true;
									}
									else if (Inventory.inventory_ship.size() == 3) {
										Inventory.inventory_ship.get(0).appliedp = false;
										Inventory.inventory_ship.get(1).appliedp = false;
										Inventory.inventory_ship.get(2).appliedp = true;
									}
									this.selectionCooldown.reset();
								}
								else if (selecteditem().itemid == 2001) {
									engine.Inventory.inventory_bgm.add(selecteditem());
									apply_bgm = 1;
									default_bgm = 1;
									this.selectionCooldown.reset();
								}
								else if (selecteditem().itemid == 2002) {
									engine.Inventory.inventory_bgm.add(selecteditem());
									apply_bgm = 2;
									default_bgm = 2;
									this.selectionCooldown.reset();
								}
								this.selectionCooldown.reset();
							}
							else {
								checkoption = 0;
								this.state = shopstates.SHOP_CHECK;
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
			/**
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
				break;*/
			case SHOP_CHECK:
				if (this.selectionCooldown.checkFinished()
						&& this.inputDelay.checkFinished()) {
					if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
						this.state = shopstates.SHOP_INVEN;
						this.selectionCooldown.reset();
					}

				}


		}
	}


	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		int items_xbase=97;
		int items_len=100;
		int ships_y=157;
		int bgms_y=327;

		drawManager.initDrawing(this);
		drawManager.drawshop(this, invrow, invcol, this.state);
		if (this.state == shopstates.SHOP_MODAL) {
			if (invrow == 0 || invrow == 1 || invrow == 2) {
				drawManager.drawShopModal(this, Item.itemregistry_ship.get(invrow).name, String.valueOf(Item.itemregistry_ship.get(invrow).price), shopmodaltype.SM_YESNO, modaloption);
			}
			if (invcol == 1) {
				if (invrow == 0 || invrow == 1 || invrow == 2)
					drawManager.drawShopModal(this, Item.itemregistry_bgm.get(invrow).name, String.valueOf(Item.itemregistry_bgm.get(invrow).price), shopmodaltype.SM_YESNO, modaloption);
			}
		}

		else if (this.state == shopstates.SHOP_CHECK) {
			if (checkoption == 1) {
				drawManager.drawShopCheck(this, "Purchase success!");
			}
			else if (checkoption == 0) {
				drawManager.drawShopCheck(this, "Not enough coin!");
			}
		}

		else if (default_ship == 0 && default_bgm == 0) {
			drawManager.drawSelectIcon_ship(this,57, 197);
			drawManager.drawSelectIcon_bgm(this, 57, 327);
		}
		else if (default_ship == 1 && default_bgm == 0) {
			drawManager.drawSelectIcon_ship(this, 157, 197);
			drawManager.drawSelectIcon_bgm(this, 57, 327);
		}
		else if (default_ship == 2 && default_bgm == 0) {
			drawManager.drawSelectIcon_ship(this, 257, 197);
			drawManager.drawSelectIcon_bgm(this, 57, 327);
		}
		else if (default_ship == 0 && default_bgm == 1) {
			drawManager.drawSelectIcon_ship(this, 57, 197);
			drawManager.drawSelectIcon_bgm(this, 157, 327);
		}
		else if (default_ship == 1 && default_bgm == 1) {
			drawManager.drawSelectIcon_ship(this, 157, 197);
			drawManager.drawSelectIcon_bgm(this, 157, 327);
		}
		else if (default_ship == 2 && default_bgm == 1) {
			drawManager.drawSelectIcon_ship(this, 257, 197);
			drawManager.drawSelectIcon_bgm(this, 157, 327);
		}
		else if (default_ship == 0 && default_bgm == 2) {
			drawManager.drawSelectIcon_ship(this, 57, 197);
			drawManager.drawSelectIcon_bgm(this, 257, 327);
		}
		else if (default_ship == 1 && default_bgm == 2) {
			drawManager.drawSelectIcon_ship(this, 157, 197);
			drawManager.drawSelectIcon_bgm(this, 257, 327);
		}
		else if (default_ship == 2 && default_bgm == 2) {
			drawManager.drawSelectIcon_ship(this, 257, 197);
			drawManager.drawSelectIcon_bgm(this, 257, 327);
		}


		drawManager.completeDrawing(this);
	}

	public boolean purchase(engine.Item item, int qty) {
		return (engine.Coin.spend(item.price * qty) == -1) ? true : false;
	}

	/**
	private void layoutitem()
	{
		engine.Item.itemregistry.size();
	}*/

	public static engine.Item selecteditem()
	{
		if (invcol == 1) {
			return Item.itemregistry_bgm.get(invrow);
		}
		return Item.itemregistry_ship.get(invrow);
	}
	public static int getApply_bgm() {
		return apply_bgm;
	}
}
