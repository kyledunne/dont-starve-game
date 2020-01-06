package aamain;

public class FlowLayout extends LayoutManager {
	
	public static enum Orientation {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP /* TODO CENTERED_LEFT_TO_RIGHT... */
	}
	
	private static final int DEFAULT_BORDER = 0;
	
	private Orientation orientation;
	
	/**
	 * # of pixels between components
	 */
	private float border;
	
	/**
	 * if true, side space will be half of border size between components
	 */
	private boolean sideBuffer;
//	private boolean centered;
	
	/**
	 * Uses Orientation.LEFT_TO_RIGHT
	 */
	public FlowLayout() {
		this(Orientation.LEFT_TO_RIGHT);
	}
	
	public FlowLayout(Orientation orientation) {
		this(orientation, DEFAULT_BORDER);
	}
	
	public FlowLayout(Orientation orientation, float border) {
		this(orientation, border, true, false);
	}

	public FlowLayout(Orientation orientation, float border, boolean sideBuffer, boolean centered) {
		this.orientation = orientation;
		this.border = border;
		this.sideBuffer = sideBuffer;
//		this.centered = centered;
	}

	@Override
	public void add(Rect component, Object constraints) {
		super.add(component, constraints);
		if(constraints != null)
			throw new RuntimeException("constraints not null for FlowLayout!!");
		
		float x, y, sideBufferSize, size;
		
		if(orientation == Orientation.LEFT_TO_RIGHT || orientation == Orientation.RIGHT_TO_LEFT)
			size = client.getHeight();
		else
			size = client.getWidth();
		
		if(sideBuffer)
			sideBufferSize = border / 2;
		else
			sideBufferSize = 0;
		
		if(orientation == Orientation.LEFT_TO_RIGHT) {
			float w;
			if (component.maintainAspectRatio()) {
				float aspect = component.getPreferredWidth() / (float)component.getPreferredHeight();
				w = (size * aspect);
			}
			else {
				w = component.getPreferredWidth();
			}
			
			if (client.getComponents().size() == 0) {
				x = sideBufferSize;
				y = 0;
			}
			else {
				if (client.components.get(client.components.size() - 1).getRightX() + border * 1.5 + component.getPreferredWidth() > client.getWidth()) {
					x = sideBufferSize;
					y = client.components.get(client.components.size() - 1).getBottomY() + border;
				}
				else {
					x = client.components.get(client.components.size() - 1).getRightX() + border;
					y = client.components.get(client.components.size() - 1).getY();
				}
			}
			component.setCoordinates(x, y, w, size);
		}
		
		else if (orientation == Orientation.TOP_TO_BOTTOM) {
			float h;
			if(component.maintainAspectRatio()) {
				float aspect = component.getPreferredWidth() / (float)component.getPreferredHeight();
				h = (int)(size / aspect);
			}
			else {
				h = component.getPreferredHeight();
			}
			if (client.getComponents().size() == 0) {
				x = 0;
				y = sideBufferSize;
			}
			else {
				if (client.components.get(client.components.size() - 1).getBottomY() + border * 1.5 + component.getPreferredHeight() > client.getHeight()) {
					y = sideBufferSize;
					x = client.components.get(client.components.size() - 1).getRightX() + border;
				}
				else {
					y = client.components.get(client.components.size() - 1).getBottomY() + border;
					x = client.components.get(client.components.size() - 1).getX();
				}
			}
			component.setCoordinates(x, y, size, h);
		}
		
		else if (orientation == Orientation.RIGHT_TO_LEFT) {
			float w;
			if (component.maintainAspectRatio()) {
				System.out.println(component.maintainAspectRatio + ", " + component.getPreferredWidth() + ", " + component.getPreferredHeight());
				float aspect = component.getPreferredWidth() / (float)component.getPreferredHeight();
				w = (int)(size * aspect);
			}
			else {
				w = component.getPreferredWidth();
			}
			float rightX;
			if (client.getComponents().size() == 0) {
				rightX = client.getWidth() - sideBufferSize;
				y = 0;
			}
			else {
				if (client.components.get(client.components.size() - 1).getX() - border * 1.5 - component.getPreferredWidth() < 0) {
					rightX = client.getWidth() - sideBufferSize;
					y = client.components.get(client.components.size() - 1).getBottomY() + border;
				}
				else {
					rightX = client.components.get(client.components.size() - 1).getX() - border;
					y = client.components.get(client.components.size() - 1).getY();
				}
			}
			x = rightX - w;
			component.setCoordinates(x, y, w, size);
		}
		
		else if(orientation == Orientation.BOTTOM_TO_TOP) {
			float h;
			if (component.maintainAspectRatio()) {
				float aspect = component.getPreferredWidth() / (float)component.getPreferredHeight();
				h = (int)(size / aspect);
			}
			else {
				h = component.getPreferredHeight();
			}
			float bottomY;
			if (client.getComponents().size() == 0) {
				x = 0;
				bottomY = client.getHeight() - sideBufferSize;
			}
			else {
				if (client.components.get(client.components.size() - 1).getY() - border * 1.5 - component.getPreferredHeight() < 0) {
					bottomY = client.getHeight() - sideBufferSize;
					x = client.components.get(client.components.size() - 1).getRightX() + border;
				}
				else {
					bottomY = client.components.get(client.components.size() - 1).getY() - border;
					x = client.components.get(client.components.size() - 1).getX();
				}
			}
			y = bottomY - h;
			component.setCoordinates(x, y, size, h);
		}
		
	}
}
