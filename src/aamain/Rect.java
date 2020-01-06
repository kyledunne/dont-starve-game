package aamain;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import event.Input;
import event.RectListener;
import event.StandardRectListener;

public class Rect {
	protected Rect container;
	protected ArrayList<Rect> components = new ArrayList<Rect>();
	public ArrayList<Object> componentsConstraints = new ArrayList<Object>();
	protected ArrayList<Rect> layers = new ArrayList<Rect>();
	private ArrayList<CoordinateConstraints> layersConstraints = new ArrayList<CoordinateConstraints>();
	protected ArrayList<Rect> pendingLayers = new ArrayList<Rect>();
	protected ArrayList<CoordinateConstraints> pendingLayersConstraints = new ArrayList<CoordinateConstraints>();
	protected RectListener rectListener;
	protected LayoutManager layout;
	protected float x, y, w, h;
	protected float absoluteX, absoluteY;
	protected Style normalStyle;
	protected Style currentStyle;
	/**
	 * p = preferred
	 */ 
	protected float pw, ph;
	protected boolean visible = false;
	/**
	 * Defines whether this rect will change appearance when moused over or clicked on
	 */
	protected boolean interacting = false;
	/**
	 * If a part of preferred size cannot be met, and maintainAspectRatio is true, then the box's
	 * other dimensions will be shrunk or expanded to maintain the same aspect ratio
	 * as the preferred size.
	 * 
	 * <p>If maintainAspectRatio is false, parts of preferred size that can be met will be, but will
	 * likely change the aspect ratio of the box.
	 * 
	 * <p>Aspect ratio is width/height
	 */
	protected boolean maintainAspectRatio;
	protected boolean coordinatesSet = false;
	
	public Rect(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph, boolean listenTo) {		
		if(layout != null)
			this.layout = layout;
		
		if(style != null)
			setStyle(style);
		
		this.maintainAspectRatio = maintainAspectRatio;
		
		this.pw = pw;
		this.ph = ph;
		
		this.interacting = interacting;
		if (listenTo)
			this.rectListener = new StandardRectListener(this);
	}
	
	public Rect(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph) {		
		this(layout, style, maintainAspectRatio, interacting, pw, ph, true);
	}
	
	public Rect(LayoutManager layout, Style style, boolean interacting) {
		this(layout, style, false, interacting, 0, 0);
	}
	
	public Rect(Style style, boolean interacting) {
		this(null, style, interacting);
	}
	
	public Rect(Style style) {
		this (null, style, false);
	}

	private Rect() {}
	
	public void justBeenAdded() {}
	
	public void justBeenRemoved() {}
	
	public void add(Rect component) {
		add(component, null);
	}

	/**
	 * Adds the component box to the ArrayList of components of the container box,
	 * sets the container of the component box,
	 * and calls on the layout manager to add the component box to the container.
	 * The layout manager then calls constraints.reset();
	 * 
	 * @param component The box to be added
	 * @param constraints
	 */
	public void add(Rect component, Object constraints) {
		if(coordinatesSet) {
			component.setContainer(this);
			layout.add(component, constraints);
			components.add(component);
			componentsConstraints.add(constraints);
			component.setVisible(this.isVisible());
		}
		else {
			component.setContainer(this);
			layout.pendingComponents.add(component);
			layout.pendingComponentsConstraints.add(constraints);
			component.setVisible(this.isVisible());
		}
		component.justBeenAdded();
	}

	public void remove(Rect component) {
		int i = components.indexOf(component);
		if (i != -1) {
			components.get(i).setContainer(null);
			components.remove(i);
			componentsConstraints.remove(i);
			component.setVisible(false);
			layout.addPendingComponents();
		}
		else
			throw new RuntimeException("Tried to remove a component that was not part of the Rect (Rect.remove(Rect))"); //.out.println("Tried to remove a component that was not part of the Rect (Rect.remove(Rect))");
		component.justBeenRemoved();
	}
	
//	private void removeAllListeners() {
//		rectListener.setActive(false);
//		actionListeners.clear();
//		
//		int size = components.size();
//		for (int i = 0; i < size; i++)
//			components.get(i).removeAllListeners();
//	}

	public void removeLayer(Rect layer) {
		int i = layers.indexOf(layer);
		if (i != -1) {
			layers.remove(i);
			layer.setVisible(false);
		}
		else
			System.out.println("Tried to remove a layer that was not part of the Rect (Rect.removeLayer(Rect))");
		layer.justBeenRemoved();
	}
	
	public void removeAll() {
		for(int i = components.size() - 1; i >= 0; i--)
			remove(components.get(i));
		
		for(int i = layers.size() - 1; i >= 0; i--)
			layers.remove(layers.get(i));
	}
	
	public void addNewLayer(Rect layer, CoordinateConstraints constraints, boolean isVisible) {
//		layers.add(layer);
//		layer.setContainer(this);
//		layer.setCoordinates(constraints.x, constraints.y, constraints.w, constraints.h);
//		layer.setVisible(isVisible);
//		layer.justBeenAdded();
		
		if(coordinatesSet) {
			layer.setContainer(this);
			layer.setVisible(this.isVisible());
			layer.setCoordinates(constraints.x, constraints.y, constraints.w, constraints.h);
			layers.add(layer);
			layersConstraints.add(constraints);
		}
		else {
			layer.setContainer(this);
			layer.setVisible(this.isVisible());
			pendingLayers.add(layer);
			pendingLayersConstraints.add(constraints);
		}
		layer.justBeenAdded();
	}
	
	private void addPendingLayers() {
		int size = pendingLayers.size();
		for (int i = 0; i < size; i++) {
			addNewLayer(pendingLayers.get(i), pendingLayersConstraints.get(i));
		}
	}
	
	public void addNewLayer(Rect layer, CoordinateConstraints constraints) {
		addNewLayer(layer, constraints, true);
	}

	public void draw() {
		if(currentStyle != null)
			currentStyle.draw(this);
		drawComponentsAndLayers();
	}
	
	public void drawComponentsAndLayers() {
		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).isVisible())
				components.get(i).draw();
		}
		
		for(int i = 0; i < layers.size(); i++) {
			if(layers.get(i).isVisible()) {
				layers.get(i).draw();
			}
		}
	}
	
	public float[] getDrawingBoundaryXCoords() {
		float[] xCoords = new float[4];
		xCoords[0] = this.getAbsoluteX();
		xCoords[1] = this.getAbsoluteX() + this.getWidth();
		xCoords[2] = this.getAbsoluteX() + this.getWidth();
		xCoords[3] = this.getAbsoluteX();
		return xCoords;
	}

	public float[] getDrawingBoundaryYCoords() {
		float[] yCoords = new float[4];
		yCoords[0] = this.getAbsoluteY();
		yCoords[1] = this.getAbsoluteY();
		yCoords[2] = this.getAbsoluteY() + this.getHeight();
		yCoords[3] = this.getAbsoluteY() + this.getHeight();
		return yCoords;
	}

	public float[] getUprightTriangleDrawingBoundaryXCoords() {
		float[] xCoords = new float[3];
		xCoords[0] = this.getAbsoluteX() + this.getWidth() * .5f;
		xCoords[1] = this.getAbsoluteX() + this.getWidth();
		xCoords[2] = this.getAbsoluteX();
		return xCoords;
	}

	public float[] getUprightTriangleDrawingBoundaryYCoords() {
		float[] yCoords = new float[3];
		yCoords[0] = this.getAbsoluteY();
		yCoords[1] = this.getAbsoluteY() + this.getHeight();
		yCoords[2] = this.getAbsoluteY() + this.getHeight();
		return yCoords;
	}
	
	protected float retrieveAbsoluteX() {
//		if (this instanceof Campfire) {
//			System.out.println("heya");
//			System.out.println(this.x);
//		}
		return container.retrieveAbsoluteX() + x;
	}
	
	protected float retrieveAbsoluteY() {
		return container.retrieveAbsoluteY() + y;
	}

//	public Texture getTexture() {
//		return texture;
//	}
//
//	public void setTexture(Texture texture) {
//		this.texture = texture;
//	}
	
	public LayoutManager getLayout() {
		return layout;
	}

	public void setLayout(LayoutManager layout) {
		this.layout = layout;
	}
	
	/** @param newX new X coordinate
	 *  @param newY new Y coordinate */
	public void relocate(float newX, float newY) {
		setCoordinates(newX, newY, w, h);
	}
	
	/** @param xAmount added to old X coordinate
	 *  @param yAmount added to old Y coordinate */
	public void move(float xAmount, float yAmount) {
		setCoordinates(x + xAmount, y + yAmount, w, h);
	}
	
	/** @param newW new width coordinate
	 *  @param newH new height coordinate */
	public void resize(float newW, float newH) {
		setCoordinates(x, y, newW, newH);
	}
	
	/** @param wAmount added to old width
	 *  @param hAmount added to old height */
	public void stretch(float wAmount, float hAmount) {
		setCoordinates(w + wAmount, h + hAmount, w, h);
	}
	
	public void setCoordinates(float x, float y, float w, float h) {
		if (coordinatesSet) {
			
			if (this.x != x) {
				this.x = x;
				this.setAbsoluteX(container.retrieveAbsoluteX() + x);
			}
			
			if (this.y != y) {
				this.y = y;
				this.setAbsoluteY(container.retrieveAbsoluteY() + y);
			}
			
			if (this.w != w) {
				this.w = w;
			}
			
			if (this.h != h) {
				this.h = h;
			}
			
			if (rectListener != null) {
				rectListener.setActive(true);
//				rectListener.coordinatesChanged(); TODO coordinates changed in RectListener
			}
		}
		else {
			this.x = x;
			this.setAbsoluteX(container.retrieveAbsoluteX() + x);
			this.y = y;
			this.setAbsoluteY(container.retrieveAbsoluteY() + y);
			this.w = w;
			this.h = h;
			coordinatesSet = true;
			
			if(layout != null)
				layout.addPendingComponents();
			
			if (rectListener != null) {
				rectListener.setActive(true);
			}
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		
		if (visible && (layout != null))
			layout.addPendingComponents();
		if (visible)
			addPendingLayers();
		if (rectListener != null)
			rectListener.setActive(visible);
		
//		if (!visible)
//			actionListeners.clear();
		
		int size = components.size();
		for(int i = 0; i < size; i++)
			components.get(i).setVisible(visible);
		
		size = layers.size();
		for(int i = 0; i < size; i++)
			layers.get(i).setVisible(visible);
	}
	
	/**
	 * @return true if components.size() == 0
	 */
	public boolean isEmpty() {
		return components.size() == 0;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public boolean maintainAspectRatio() {
		return maintainAspectRatio;
	}
	
	public boolean isInteracting() {
		return interacting;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}
	
	public void setPreferredSize(float pw, float ph) {
		this.pw = pw;
		this.ph = ph;
	}
	
	public float getPreferredWidth() {
		return pw;
	}
	
	public float getPreferredHeight() {
		return ph;
	}

	public boolean isMousedOver() {
		return isMousedOver(Input.getMouseX(), Input.getMouseY());
	}

	public boolean isMousedOver(int mouseX, int mouseY) {
		return (mouseX >= absoluteX && mouseX < getAbsoluteRightX() && mouseY >= absoluteY && mouseY < getAbsoluteBottomY());
	}

	public boolean wasMousedOver() {
		return isMousedOver(Input.getOldMouseX(), Input.getOldMouseY());
	}
	
	public RectListener getRectListener() {
		return rectListener;
	}

	public void setRectListener(RectListener rectListener) {
		this.rectListener = rectListener;
	}

	public Rect getContainer() {
		return container;
	}

	public void setContainer(Rect container) {
		this.container = container;
	}

	public LayoutManager getMgr() {
		return layout;
	}

	public void setMgr(LayoutManager layout) {
		this.layout = layout;
	}

	public Style getCurrentStyle() {
		return currentStyle;
	}

	public Style getNormalStyle() {
		return normalStyle;
	}

	public void setStyle(Style style) {
		normalStyle = style;
		currentStyle = style;
	}
	
	public void setToHighlightedStyle() {
		currentStyle = normalStyle.getHighlightedStyle();
	}
	
	public void setToSelectedStyle() {
		currentStyle = normalStyle.getSelectedStyle();
	}
	
	public void setToNormalStyle() {
		this.currentStyle = normalStyle;	
	}

	public float getX() {
		return x;
	}

	public float getRightX() {
		return x + w;
	}
	
	public float getCenterX() {
		return x + w * .5f;
	}

	public float getY() {
		return y;
	}

	public float getBottomY() {
		return y + h;
	}

	public float getWidth() {
		return w;
	}

	public float getHeight() {
		return h;
	}
	
	public boolean areCoordinatesSet() {
		return coordinatesSet;
	}

	public ArrayList<Rect> getComponents() {
		return components;
	}
	
	public float getAbsoluteX() {
		return absoluteX;
	}

	public float getAbsoluteY() {
		return absoluteY;
	}
	
	public float getAbsoluteRightX() {
		return absoluteX + w;
	}

	public float getAbsoluteBottomY() {
		return absoluteY + h;
	}
	
	public void setAbsoluteX(float absoluteX) {
		this.absoluteX = absoluteX;
		
		for (int i = 0; i < components.size(); i++)
			components.get(i).setAbsoluteX(components.get(i).retrieveAbsoluteX());
		
		for (int i = 0; i < layers.size(); i++)
			layers.get(i).setAbsoluteX(layers.get(i).retrieveAbsoluteX());
	}

	public void setAbsoluteY(float absoluteY) {
		this.absoluteY = absoluteY;
		
		for (int i = 0; i < components.size(); i++)
			components.get(i).setAbsoluteY(components.get(i).retrieveAbsoluteY());
		
		for (int i = 0; i < layers.size(); i++)
			layers.get(i).setAbsoluteY(layers.get(i).retrieveAbsoluteY());
	}
	
	public ArrayList<Rect> getLayers() {
		return layers;
	}
	
	public ArrayList<Object> getComponentsConstraints() {
		return componentsConstraints;
	}
	
	public Style getHighlightedStyle() {
		return normalStyle.getHighlightedStyle();
	}
	
	public Style getSelectedStyle() {
		return normalStyle.getSelectedStyle();
	}
	
	public void addMenu(final Rect menuButton, final Rect menu, final CoordinateConstraints constraints) {
		menuButton.setRectListener(new StandardRectListener(menu) {
			
			@Override
			public void mouseEntered() {
				if(!getLayers().contains(menu)) {
					super.mouseEntered();
					addNewLayer(menu, constraints);
				}

			}
			
			@Override
			public void mouseExited() {
				if(!menu.isMousedOver()) {
					super.mouseExited();
					removeLayer(menu);
				}
			}
		});
		
		menu.setRectListener(new RectListener(menu) {
			
			@Override
			public void mouseExited() {
				if(!isMousedOver()) {
					getRectListener().mouseExited();
					removeLayer(menu);
				}

			}
		});
	}
	
	protected Rect getThis() {
		return this;
	}
	
	public final class Container extends Rect {

		public Container() {
			super();
			setCoordinates(0, 0, Display.getWidth(), Display.getHeight());
			setLayout(new LayoutManager() {});
		}
		
		@Override
		public void draw() {
			for(int i = 0; i < components.size(); i++)
				components.get(i).draw();
			
			for(int i = 0; i < layers.size(); i++)
				layers.get(i).draw();
		}
		
		@Override
		public void add(Rect component, Object constraints) {
			component.setContainer(this);
			if(!(constraints instanceof CoordinateConstraints)) {
				throw new RuntimeException("constraints of a Rect added to Rect.CONTAINER is not a CoordinateConstraints");
			}
			CoordinateConstraints c = (CoordinateConstraints)constraints;
			component.setCoordinates(c.x, c.y, c.w, c.h);
			components.add(component);
			componentsConstraints.add(constraints);
			component.setVisible(true);
		}
		
		@Override
		public void remove(Rect component) {
			int i = components.indexOf(component);
			if (i != -1) {
				components.remove(i);
				componentsConstraints.remove(i);
				component.setVisible(false);
				layout.addPendingComponents();
			}
		}
		
		@Override
		protected float retrieveAbsoluteX() {
			return 0;
		}
		
		@Override
		protected float retrieveAbsoluteY() {
			return 0;
		}
		
		@Override
		public void setCoordinates(float x, float y, float w, float h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			coordinatesSet = true;
			setVisible(true);
		}
		
		@Override
		public void setVisible(boolean visible) {
			this.visible = visible;
			if (rectListener != null)
				rectListener.setActive(visible);
			
			int size = components.size();
			for(int i = 0; i < size; i++)
				components.get(i).setVisible(visible);
			
			size = layers.size();
			for(int i = 0; i < size; i++)
				layers.get(i).setVisible(visible);
		}
	}
	
	public static final Container CONTAINER = new Rect().new Container();
	
	public static int getNumColumns(float rowWidth, float rowHeight, float aspect) {
		int cellWidth = Math.round(rowHeight * aspect);
		return Math.round(rowWidth / cellWidth);
	}

	public static int getNumRows(float columnWidth, float columnHeight, float aspect) {
		int cellHeight = Math.round(columnWidth / aspect);
		return Math.round(columnHeight / cellHeight);
	}

//	public boolean isBlockingEventCheckingOfCoveredRegion() {
//		return blockEventCheckingOfCoveredRegion;
//	}
//
//	public void setBlockEventCheckingOfCoveredRegion(boolean blockEventCheckingOfCoveredRegion) {
//		this.blockEventCheckingOfCoveredRegion = blockEventCheckingOfCoveredRegion;
//	}
}
