package ui;

import java.awt.Font;

import entities.*;
import event.Input;
import event.RectListener;
import event.StandardRectListener;
import aamain.*;

public class InventorySlot extends Rect {
	public Entity inSlot;
	public int numberInSlot;
	public Text numberInSlotText;
	public RectListener listener;

	public InventorySlot() {
		super(new CoordinatesLayout(), Color.adjustAColorsAlpha(Color.TRANSPARENT_WHITE, .7f), true, true, 80, 80);
		this.numberInSlot = 0;
		
		this.rectListener = new StandardRectListener(this) {
			
			@Override
			public void leftClickedOn() {
				if (!isSlotEmpty()) {
					Input.trackWithMouse(inSlot);
					if (numberInSlotText != null)
						Input.trackWithMouse(numberInSlotText);
					numberInSlotChanged(-1 * numberInSlot);
				}
			}
		};
	}
	
	@Override
	public void add(Rect entity) {
		add(entity, new CoordinateConstraints(0, 0, this.w, this.h, true));
		this.inSlot = (Entity) entity;
		this.inSlot.setRectListener(null);
		numberInSlotChanged(1);
	}
	
	public boolean isSlotEmpty() {
		return numberInSlot == 0;
	}

	public void numberInSlotChanged(int amount) {
		numberInSlot += amount;

		if (numberInSlotText != null)
			if (numberInSlotText.getContainer() instanceof InventorySlot)
				this.remove(numberInSlotText);
		
		if (numberInSlot > 1) {
			numberInSlotText = new Text(new Font(Text.DEFAULT_FONT.getFontName(), Font.PLAIN, 40), new Integer(numberInSlot).toString(), Color.BLACK);
			this.add(numberInSlotText, new CoordinateConstraints(Text.centerTextX(this, numberInSlotText), Text.centerTextY(this, numberInSlotText),
					numberInSlotText.getWidth(), numberInSlotText.getHeight()));
		}
		else if  (numberInSlot == 0) {
			removeAll();
		}
	}
}
