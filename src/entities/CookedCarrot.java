package entities;

import aamain.*;

public class CookedCarrot extends Entity implements Storeable, Edible {
	public static final Image cookedCarrotImage = new Image("res/images/entities/cookedCarrot.png");
	
	public CookedCarrot() {
		super(cookedCarrotImage);
	}

}

//Main.MAIN_GAME_INSTANCE.hungerMeter.changeCurrentHunger(.4f);
//return true;
