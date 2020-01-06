package entities;

import event.*;
import aamain.*;

public class Tree extends Entity implements Choppable {

	public static final Image treeImage = new Image("res/images/entities/tree.png");
	public static final float WIDTH = 200;
	public static final float HEIGHT = 400;

	public float hitPoints;

	public Tree(Image image) {
		super(image);

		hitPoints = 100;
		rectListener = new StandardRectListener(this) {
			@Override
			public void leftClickedOn() {
				if (Main.MAIN_GAME_INSTANCE.inventoryBar.isInInventory(new Axe(), 1))
					hitPoints -= 100;
				else 
					hitPoints -= 10;
				
				if (hitPoints<= 0) {
					float x = getX();
					float y = getY();
					container.add(new Log(), new CoordinateConstraints(x + (Util.RAND.nextFloat() - .5f) * 100,
							y + (Util.RAND.nextFloat() - .5f) * 100, Log.WIDTH, Log.HEIGHT));
					container.add(new Log(), new CoordinateConstraints(x + (Util.RAND.nextFloat() - .5f) * 100,
							y + (Util.RAND.nextFloat() - .5f) * 100, Log.WIDTH, Log.HEIGHT));
					container.add(new Twig(), new CoordinateConstraints(x + (Util.RAND.nextFloat() - .5f) * 100,
							y + (Util.RAND.nextFloat() - .5f) * 100, Twig.WIDTH, Twig.HEIGHT));
					container.add(new Twig(), new CoordinateConstraints(x + (Util.RAND.nextFloat() - .5f) * 100,
							y + (Util.RAND.nextFloat() - .5f) * 100, Twig.WIDTH, Twig.HEIGHT));
					container.remove(getThis());
				}
			}
		};
	}
	
	public Tree() {
		this(treeImage);
	}
	
}
