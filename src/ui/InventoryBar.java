package ui;

import entities.*;
import aamain.*;

public class InventoryBar extends Rect {
	public static final int SLOTS = 10;
	public static final int SLOT_SIZE = 80;
	InventorySlot[] slots;
	Rect realInventoryBar;
	
	public InventoryBar() {
		super(new EdgesLayout(), Color.adjustAColorsAlpha(Color.TRANSPARENT_BLACK, .2f), false);
		
		slots = new InventorySlot[SLOTS];
	}
	
	@Override
	public void setVisible(boolean visible) {
		
		float totalDistance = slots.length * 80 + (slots.length - 1) * 20;
		float sideDistance = (this.w - totalDistance) / 2;
		float vertDistance = 10;
		
		this.add(new Rect(null, null, false), new EdgesLayoutConstraints(EdgesLayout.Location.LEFT, sideDistance));
		this.add(new Rect(null, null, false), new EdgesLayoutConstraints(EdgesLayout.Location.RIGHT, sideDistance));
		this.add(new Rect(null, null, false), new EdgesLayoutConstraints(EdgesLayout.Location.TOP, vertDistance));
		this.add(new Rect(null, null, false), new EdgesLayoutConstraints(EdgesLayout.Location.BOTTOM, vertDistance));
		
		realInventoryBar = new Rect(new GridLayout(slots.length, 1, 20, 0), null, false);
		
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new InventorySlot();
			realInventoryBar.add(slots[i], new GridLayoutConstraints(i, 0));
		}
		
		this.add(realInventoryBar, new EdgesLayoutConstraints(EdgesLayout.Location.LEFT, totalDistance));
		super.setVisible(visible);
	}
	
	/**
	 * @return true if successful (inventory not too full)
	 */
	public boolean attemptToAddToInventory(Entity entity) {
		return attemptToAddToInventory(entity, 1);
	}
	
	/**
	 * @return true if successful (inventory not too full)
	 */
	public boolean attemptToAddToInventory(Entity entity, int quantity) {
		int currentQuantity = quantity;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i].isSlotEmpty()) {
				slots[i].add(entity);
				if (currentQuantity <= entity.getMaxStackSize()) {
					slots[i].numberInSlotChanged(currentQuantity - 1);
					return true;
				}
				else {
					slots[i].numberInSlotChanged(entity.getMaxStackSize() - 1);
					currentQuantity -= entity.getMaxStackSize();
				}
			}
			else if ((slots[i].inSlot.getClass() == entity.getClass()) && (slots[i].numberInSlot < slots[i].inSlot.getMaxStackSize())) {
				int remainingSpacesInSlot = slots[i].inSlot.getMaxStackSize() - slots[i].numberInSlot;
				if (currentQuantity <= remainingSpacesInSlot) {
					slots[i].numberInSlotChanged(currentQuantity);
					return true;
				}
				else {
					slots[i].numberInSlotChanged(remainingSpacesInSlot);
					currentQuantity -= remainingSpacesInSlot;
				}
			}
		}
		
		return false;
	}
	
	public boolean isInInventory(Entity entity, int quantityRequired) {
		int quantityInInventory = 0;
		
		for (int i = 0; i < slots.length; i++) {
			if (!slots[i].isEmpty()) {
				if (entity.getClass() == slots[i].inSlot.getClass()) {
					quantityInInventory += slots[i].numberInSlot;
				}
			}
		}
		
		return quantityInInventory >= quantityRequired;
	}
	
	public void removeFromInventory(Entity entity, int quantityToRemove) {
		if (!isInInventory(entity, quantityToRemove)) { //TODO probs not necessary
			throw new RuntimeException("This shouldn't happen. Check to see if this amount of this entity is in the inventory before you try to remove it");
		}
		
		for (int i = 0; i < slots.length; i++) {
			if (!slots[i].isEmpty()) {
				if (entity.getClass() == slots[i].inSlot.getClass()) {
					if (slots[i].numberInSlot >= quantityToRemove) {
						slots[i].numberInSlotChanged(-1 * quantityToRemove);
					}
					else {
						quantityToRemove -= slots[i].numberInSlot;
						slots[i].numberInSlotChanged(-1 * slots[i].numberInSlot);
					}
				}
			}
		}
		
	}
	
	public InventorySlot[] getSlots() {
		return slots;
	}
}
