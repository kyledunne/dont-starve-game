package entities;

import event.StandardRectListener;
import aamain.*;

public class PlantedCarrot extends Entity implements Harvestable {
	public static final float WIDTH = 35;
	public static final float HEIGHT = 35;
	
	public static final Image plantedCarrotImage = new Image("res/images/entities/plantedCarrot.png");
	
	public PlantedCarrot() {
		super(null, plantedCarrotImage, false, true, WIDTH, HEIGHT, true);
		
		rectListener = new StandardRectListener(this) {
			
			@Override
			public void leftClickedOn() {
				container.remove(getThis());
				Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new Carrot());
			}
		};
	}
	
}
