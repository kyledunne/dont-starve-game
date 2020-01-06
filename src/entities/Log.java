package entities;

import aamain.*;

public class Log extends Entity implements Storeable {
	
	public static final Image logImage = new Image("res/images/entities/log.png");
	public static final float WIDTH = 80;
	public static final float HEIGHT = 10;

	public Log() {
		super(logImage);
//		this.setPreferredSize(WIDTH, HEIGHT);
		
//		rectListener = new StandardRectListener(this) { TODO
//			
//			@Override
//			public void leftClickedThenReleasedOn() {
//				if (container.getContainer() instanceof Map) {
//					container.remove(getThis());
//					Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new Log());
//				}
//			}
//		};
	}

}
