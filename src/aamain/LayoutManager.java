package aamain;
import java.util.ArrayList;

public abstract class LayoutManager {
	
	/**
	 * Rect that holds all added components (container of added components)
	 */
	protected Rect client;
	
	/**
	 * components that need their coordinates to be set
	 */
	protected ArrayList<Rect> pendingComponents = new ArrayList<Rect>();
	protected ArrayList<Object> pendingComponentsConstraints = new ArrayList<Object>();
	
	public void add(Rect component, Object constraints) {
		if (client == null)
			client = component.getContainer();
		if (!client.areCoordinatesSet()) {
			pendingComponents.add(component);
			pendingComponentsConstraints.add(constraints);
		}
	}
	
	public void addPendingComponents() {
		if (pendingComponents.isEmpty()) {
			if (client != null) {
				int size = client.components.size();
				for(int i = 0; i < size; i++) {
					pendingComponents.add(client.components.get(i));
					pendingComponentsConstraints.add(client.componentsConstraints.get(i));
				}
				client.components.clear();
				client.componentsConstraints.clear();
			}
		}
		
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
