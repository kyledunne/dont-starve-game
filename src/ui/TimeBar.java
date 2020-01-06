package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import aamain.*;

public class TimeBar extends Rect {
	/**
	 * One entire day and night
	 */
	public static final float LENGTH_OF_DAY_IN_MILLISECONDS = 150 * 1000;
	
	public boolean daytime;
	
	public Rect dayBar;
	public Rect nightBar;
	public Rect currentTimeMarker;
	
	public Timer dayNightCycleTimer;
	
//	public ActionListener nightImplementer;
	
	public TimeBar() {
		super(new CoordinatesLayout(), null, false);
		
		daytime = true;
		
		dayBar = new Rect(Color.adjustAColorsAlpha(Color.WHITE, .5f), false);
		nightBar = new Rect(Color.adjustAColorsAlpha(Color.BLACK, .5f), false);
		currentTimeMarker = new Rect(Color.RED, false);
		
		dayNightCycleTimer = new Timer(1000 / (Main.currentFPS * 2), new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				float widthOfIncrement = w / (LENGTH_OF_DAY_IN_MILLISECONDS / dayNightCycleTimer.getDelay());				
				if (currentTimeMarker.getX() >= w - widthOfIncrement) {
					currentTimeMarker.relocate(0, currentTimeMarker.getY());
					if (!daytime)
						startDay();
				}
				else {
					currentTimeMarker.relocate(currentTimeMarker.getX() + widthOfIncrement, currentTimeMarker.getY());
				}
				
				if (currentTimeMarker.getX() >= nightBar.getX()) {
					if (daytime)
						startNight();
					
//					TODO implement instant death by darkness
				}
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		if (visible) {
			this.add(dayBar, new CoordinateConstraints(0, 0, this.w * .8f, this.h));
			this.add(nightBar, new CoordinateConstraints(dayBar.getRightX(), 0, this.w - dayBar.getWidth(), this.h));
			
			this.add(currentTimeMarker, new CoordinateConstraints(0, 0, 10, this.h));
			dayNightCycleTimer.start();
		}
	}
	
	protected void startDay() {
		daytime = true;
		Main.MAIN_GAME_INSTANCE.map.removeDarkness();
	}

	protected void startNight() {
		daytime = false;
		Main.MAIN_GAME_INSTANCE.map.addDarkness();
		
//		nightImplementer = new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//		
//		dayNightCycleTimer.addActionListener(nightImplementer);
	}
}
