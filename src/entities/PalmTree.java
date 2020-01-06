package entities;

import aamain.*;

public class PalmTree extends Tree {
	public static final Image palmTreeImage = new Image("res/images/entities/palmTree.png", Color.TRANSPARENT_WHITE);
	public static final float WIDTH = 300;
	public static final float HEIGHT = 600;
	public float hitPoints;
	
	public PalmTree() {
		super(palmTreeImage);
	}
}
