package entities;

import aamain.*;

public class Carrot extends Entity implements Cookable, Storeable, Edible {
	public static final Image carrotImage = new Image("res/images/entities/carrot.png");

	public Carrot() {
		super (carrotImage);
		this.setPreferredSize(100, 100);
	}
	
	@Override
	public void cook() {
		Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new CookedCarrot());
	}
}

/*		((InventorySlot)container).numberInSlotChanged(-1);
		Input.trackWithMouse(this);
//		Main.MAIN_GAME_INSTANCE.hungerMeter.changeCurrentHunger(.2f);
		return false;
*/