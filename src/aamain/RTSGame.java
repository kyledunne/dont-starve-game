package aamain;

import static event.Input.*;
import java.util.ArrayList;
import ui.*;

public class RTSGame {
	public Map map;
	public Rect ui;
	public InventoryBar inventoryBar;
	public CraftingBar craftingBar;
	public TimeBar timeBar;
	public HungerMeter hungerMeter;
	
	private boolean wasAnEntityHighlighted = false;
	private Rect thisEntityWasHighlighted;
	
	public RTSGame() {
		map = new Map(100, 100, 0);
		ui = new Rect(new EdgesLayout(), Color.TRANSPARENT_BLACK, false);
		inventoryBar = new InventoryBar();
		timeBar = new TimeBar();
		craftingBar = new CraftingBar();
		hungerMeter = new HungerMeter();
	}

	public void display() {
		Rect.CONTAINER.add(map, new CoordinateConstraints(-40, -40, map.columns * Map.TILE_SIZE, map.rows * Map.TILE_SIZE));
		Rect.CONTAINER.addNewLayer(ui, new CoordinateConstraints(0, 0, Rect.CONTAINER.w, Rect.CONTAINER.h));
		ui.add(inventoryBar, new EdgesLayoutConstraints(EdgesLayout.Location.BOTTOM, 100));
		ui.add(timeBar, new EdgesLayoutConstraints(EdgesLayout.Location.TOP, 50));
		ui.add(craftingBar, new EdgesLayoutConstraints(EdgesLayout.Location.RIGHT, 100));
		ui.add(hungerMeter, new EdgesLayoutConstraints(EdgesLayout.Location.LEFT, 40));
	}
	
	public void checkEvents() {
		if (!(inventoryBar.isMousedOver() || timeBar.isMousedOver() || craftingBar.isMousedOver() || hungerMeter.isMousedOver())) {
			Rect thisEntityIsHighlighted = null;
			
			if (isMouseMoving()) {
				ArrayList<Integer> currentlyMousedOverEntitiesIndeces = new ArrayList<Integer>();
				for (int i = 0; i < map.getEntityLayer().getComponents().size(); i++) {
					if (map.getEntityLayer().getComponents().get(i).isMousedOver()) {
						currentlyMousedOverEntitiesIndeces.add(i);
					}
				}
				if (currentlyMousedOverEntitiesIndeces.size() != 0) {
					int actuallyHighlightedEntityIndex = currentlyMousedOverEntitiesIndeces.get(currentlyMousedOverEntitiesIndeces.size() - 1);
					thisEntityIsHighlighted = map.getEntityLayer().getComponents().get(actuallyHighlightedEntityIndex);
					for (int i = 0; i < currentlyMousedOverEntitiesIndeces.size() - 1; i++) {
						if (i != actuallyHighlightedEntityIndex)
							map.getEntityLayer().getComponents().get(i).setToNormalStyle();
					}
				}
			}
			
			if (thisEntityIsHighlighted != null) {
				if (wasAnEntityHighlighted)
					thisEntityWasHighlighted.setToNormalStyle();
				thisEntityIsHighlighted.setToHighlightedStyle();
				wasAnEntityHighlighted = true;
			}
			
		}
		else {
			if (wasAnEntityHighlighted) {
				thisEntityWasHighlighted.setToNormalStyle();
				wasAnEntityHighlighted = false;
			}
		}
	
		checkKeyEvents();
	}

}
