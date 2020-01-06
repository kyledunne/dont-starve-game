package entities;

import event.*;
import ui.InventorySlot;
import aamain.*;

public class Twig extends Entity implements Storeable {
	public static final Image twigImage = new Image("res/images/entities/twig.png");
	
	public static final float WIDTH = 80;
	public static final float HEIGHT = 10;

	public Twig() {
		super(twigImage);
		
		rectListener = new StandardRectListener(this) {
			
			@Override
			public void leftClickedOn() {
				if (!(container instanceof InventorySlot)) {
					container.remove(getThis());
					Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new Twig());
				}
			}
		};
	}
}
