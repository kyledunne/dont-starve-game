package aamain;


public class EdgesLayout extends LayoutManager {
	
	public enum Location {
		TOP, BOTTOM, LEFT, RIGHT, CENTER
	}

	public EdgesLayout() {
	}
	
	@Override
	public void add(Rect component, Object constraints) {
		super.add(component, constraints);
		
		if(!(constraints instanceof EdgesLayoutConstraints))
			throw new RuntimeException("constraints not null for FlowLayout!!");
		EdgesLayoutConstraints c = (EdgesLayoutConstraints)constraints;
		
		float x = 0, y = 0, rightX = 0, bottomY = 0;
		switch(c.getLocation()) {
		case BOTTOM:
			x = 0;
			bottomY = client.getHeight();
			rightX = client.getWidth();
			for(int i = 0; i < client.getComponents().size(); i++) {
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.BOTTOM) {
					bottomY -= client.getComponents().get(i).getHeight();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.LEFT) {
					x += client.getComponents().get(i).getWidth();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.RIGHT) {
					rightX -= client.getComponents().get(i).getWidth();
				}
			}
			component.setCoordinates(x, bottomY - c.getSize(), rightX - x, c.getSize());
			
			break;
			
		case CENTER:
			break;
			
		case LEFT:
			x = 0;
			y = 0;
			bottomY = client.getHeight();
			for(int i = 0; i < client.getComponents().size(); i++) {
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.TOP) {
					y += client.getComponents().get(i).getHeight();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.LEFT) {
					x += client.getComponents().get(i).getWidth();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.BOTTOM) {
					bottomY -= client.getComponents().get(i).getHeight();
				}
			}
			component.setCoordinates(x, y, c.getSize(), bottomY - y);
			
			break;
			
		case RIGHT:
			rightX = client.getWidth();
			y = 0;
			bottomY = client.getHeight();
			for(int i = 0; i < client.getComponents().size(); i++) {
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.TOP) {
					y += client.getComponents().get(i).getHeight();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.RIGHT) {
					rightX -= client.getComponents().get(i).getWidth();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.BOTTOM) {
					bottomY -= client.getComponents().get(i).getHeight();
				}
			}
			component.setCoordinates(rightX - c.getSize(), y, c.getSize(), bottomY - y);
			
			break;
			
		case TOP:
			x = 0;
			y = 0;
			rightX = client.getWidth();
			for(int i = 0; i < client.getComponents().size(); i++) {
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.TOP) {
					y += client.getComponents().get(i).getHeight();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.LEFT) {
					x += client.getComponents().get(i).getWidth();
				}
				
				if(((EdgesLayoutConstraints)client.getComponentsConstraints().get(i)).getLocation() == Location.RIGHT) {
					rightX -= client.getComponents().get(i).getWidth();
				}
			}
			component.setCoordinates(x, y, rightX - x, c.getSize());
			
			break;
			
		default:
			if(c.getLocation() == null)
				throw new RuntimeException("Location null");
			
			break;
		}
	}

}
