package aamain;

public class CoordinatesLayout extends LayoutManager {
	
	@Override
	public void add(Rect component, Object constraints) {
		super.add(component, constraints);
		
		CoordinateConstraints c = (CoordinateConstraints)(constraints);
		if (component.maintainAspectRatio() && component.getPreferredWidth() != 0) {
			float aspect = component.getPreferredWidth() / component.getPreferredHeight();
			float x, y, w, h;
			if (aspect > 1) {
				h = c.w / aspect;
				w = c.w;
			}	
			else {
				w = c.h * aspect;
				h = c.h;
			}

			if (c.center) {
				if (w < this.client.getWidth())
					x = (this.client.getWidth() - w) / 2;
				else
					x = c.x;

				if (h < this.client.getHeight())
					y = (this.client.getHeight() - h) / 2;
				else
					y = c.y;
			}
			else {
				x = c.x;
				y = c.y;
			}

			component.setCoordinates(x, y, w, h);
		}
		else {
			component.setCoordinates(c.x, c.y, c.w, c.h);
		}
	}
	
	@Override
	public void addPendingComponents() {
		int size = pendingComponents.size();
		
		for (int i = 0; i < size; i++) {
			add(pendingComponents.get(i), pendingComponentsConstraints.get(i));
			client.components.add(pendingComponents.get(i));
			client.componentsConstraints.add(pendingComponentsConstraints.get(i));
		}		
		pendingComponents.clear();
		pendingComponentsConstraints.clear();
	}
}
