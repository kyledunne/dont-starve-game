package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import aamain.*;

public class HungerMeter extends Rect {
	public static final float TIME_TO_STARVE_FROM_FULL_HUNGER_IN_MILLISECONDS = TimeBar.LENGTH_OF_DAY_IN_MILLISECONDS;

	/**
	 * 1 = full, 0 = dead
	 */
	protected float currentHunger;
	public Rect currentHungerIndicator;

	public HungerMeter() {
		super(new EdgesLayout(), Color.adjustAColorsAlpha(Color.TRANSPARENT_WHITE, .25f), true);

		currentHunger = 1;
		currentHungerIndicator = new Rect(Color.ORANGE);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		Main.MAIN_GAME_INSTANCE.timeBar.dayNightCycleTimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCurrentHunger(-1 * Main.MAIN_GAME_INSTANCE.timeBar.dayNightCycleTimer.getDelay()
						/ TIME_TO_STARVE_FROM_FULL_HUNGER_IN_MILLISECONDS);

				currentHungerIndicator.setCoordinates(currentHungerIndicator.getX(), 
						currentHungerIndicator.getY() + (currentHungerIndicator.getHeight() - currentHunger * h), currentHungerIndicator.getWidth(), 
						currentHunger * h);
			}
		});

		this.add(currentHungerIndicator, new EdgesLayoutConstraints(EdgesLayout.Location.TOP, currentHunger * this.h));
	}
	
	public void changeCurrentHunger(float amount) {
		currentHunger += amount;
		if (currentHunger > 1)
			currentHunger = 1;
		if (currentHunger <= 0)
			new YouDied("You starved. To death. I am clapping very slowly.", "HungerMeter", 32);
	}
}
