package aamain;
import java.util.Random;

public class Util {
	public static final Random RAND = new Random();
	
	public static org.newdawn.slick.Color slickColor(Color color) {
		return new org.newdawn.slick.Color(color.r, color.g, color.b, color.a);
	}
	
//	waterBar.add(new Image("res/images/exitIcon.png", Color.TRANSPARENT, Rect.Type.TRIGGER_ACTION_WHEN_RELEASED, new ActionListener() {
//	private Text t = new Text(new java.awt.Font("Verdana", Font.PLAIN, 12), "Leave Waterfall OS", Color.GREEN, null, Color.TRANSPARENT, false, null, null);
//	
//	@Override
//	public void mousedOver() {
//		Rect.CONTAINER.addNewLayer(t, new CoordinateConstraints(actedOn.getAbsoluteRightX() - t.getWidth(), actedOn.getAbsoluteBottomY(), t.getWidth(), t.getHeight()));
//		t.setToMousedOverStyle();
//	}
//	
//	@Override
//	public void unMousedOver() {
//		Rect.CONTAINER.removeLayer(t);
//	}
//	
//	@Override
//	public void pressed() {
//		t.setToPressedStyle();
//	}
//	
//	@Override
//	public void actionTriggered() {
//		Display.destroy();
//		System.exit(83);
//	}
//}));
	
//	if(horizontalOrientation) {
//	if(size > client.getHeight()) {
//		this.size = client.getHeight();
//	}
//	else {
//		this.size = size;
//	}
//}
//else {
//	if(size > client.getWidth()) {
//		this.size = client.getWidth();
//	}
//	else {
//		this.size = size;
//	}
//}
	
//	public void setCoordinatesAtPreferredSize(int x, int y) {
//	this.x = x;
//	this.y = y;
//	this.w = pw;
//	this.h = ph;
//}
	
//	public static void runLoop() {
//		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
//			glClear(GL_COLOR_BUFFER_BIT);
////			stuff.draw();
//			Display.sync(60);
//			Display.update();
////			checkEvents();
//		}
//		System.exit(0);
//	}
//	
	/*
protected Box(Box container, int x, int y, int w, int h) {
		this.container = container;
		if(this.w < 0) {
			this.x = (this.x + this.w);
			this.w *= -1;
		}
		if(this.h < 0) {
			this.y = (this.y + this.h);
			this.h *= -1;
		}
	}

	public int getStatus() {
		if(Input.getMouseX() >= x && Input.getMouseX() < x + w && Input.getMouseY() >= y && Input.getMouseY() < y + h) {
			if(Mouse.isButtonDown(0)) {
				wasPressed = true;
				return PRESSED;
			}
			else if(wasPressed)
				return RELEASED;
			else
				return MOUSED_OVER;
		}
		else {
			wasPressed = false;
			return NONE;
		}
	}
	
	public Ocean() {
	}

	public void run() {
		runLoop();
	}
	
	private void runLoop() {
		/*
		 * while True:
				events()
    			loop()
    			render()

		 *
		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			glClear(GL_COLOR_BUFFER_BIT);
//			stuff.draw()
			Display.sync(60);
			Display.update();
//			checkEvents();
		}
		System.exit(0);
	}
	 /*
	  * 
		if(!clientIdentified) {
			this.client = component.getContainer();
			clientIdentified = true;
		}
		if(!(constraints instanceof GridLayoutConstraints))
			throw new NullPointerException("Incorrect constraints");
		c = (GridLayoutConstraints)constraints;
		
		if(c.getColumn() <= -1 && c.getRow() <= -1) {
			for(int i = 0; i < isOccupied.length; i++) {
				for(int j = 0; j < isOccupied[0].length; j++) {
					if(isOccupied[i][j] == false) {
						c.setColumn(i);
						c.setRow(j);
						break;
					}
				}
				if(c.getColumn() > -1)
					break;
			}
		}
		else if(c.getColumn() <= -1) {
			c.setColumn(1);
		}
		else if(c.getRow() <= -1) {
			c.setRow(1);
		}
		
		if(c.getNumColumns() <= -1) {
			c.setNumColumns(1);
		}
		if(c.getNumRows() <= -1) {
			c.setNumRows(1);
		}
		float containerWidth = component.getContainer().getWidth();
		float columnWidth = containerWidth / columns;
		float containerHeight = component.getContainer().getHeight();
		float columnHeight = containerHeight / rows;
		
		
		x = Math.round(c.getColumn() * (component.getContainer().getWidth() / (float)columns));
		y = Math.round(c.getRow() * (component.getContainer().getHeight() / (float)rows));
		
		
		if(c.getWidth() <= -1) {
			w = Math.round((component.getContainer().getWidth() / (float)columns) * c.getNumColumns());
			
		}
		if(c.getHeight() <= -1) {
			
		}
		
		
		
		
		
		
		// Get left, right, top, and bottom cell positions
		int lc, lr, rc, rr, tc, tr, bc, br;
		lc = c.getColumn() - 1;
		lr = c.getRow();
		rc = c.getColumn() + c.getNumColumns();
		rr = c.getRow();
		tc = c.getColumn();
		tr = c.getRow() - 1;
		bc = c.getColumn();
		br = c.getRow() + c.getNumRows();
		
		if(c.getColumn() != 0) {
			if(occupations[lc][lr] == null)
				x = Math.round(columnWidth * c.getColumn());
			else
				x = occupations[lc][lr].getRightX();
		}
		else
			x = 0;
		
		if(c.getColumn() + c.getNumColumns() < columns) {
			if(occupations[rc][rr] == null)
				w = Math.round(columnWidth * c.getNumColumns());
			else
				w = occupations[rc][rr].getX();
		}
		else
			w = Math.round(columnWidth * c.getNumColumns());
		
		
		
		if(c.getRow() != 0) {
			if(occupations[tc][tr] == null)
				y = Math.round(rowHeight * c.getRow());
			else
				y = occupations[tc][tr].getBottomY();
		}
		else
			y = 0;
		
		if(c.getRow() + c.getNumRows() < rows) {
			if(occupations[bc][br] == null)
				h = Math.round(rowHeight * c.getNumRows());
			else
				h = occupations[bc][br].getY();
		}
		else
			h = Math.round(rowHeight * c.getNumRows());
	  */
	
	
	
	
	// Set width and height
//	if(c.getWidth() <= -1) {
//		if(c.getRow() > 0) {
//			w = occupations[c.getColumn()][c.getRow() - 1].getBottomY();
//		}
//		else {
//		w = Math.round(columnWidth * c.getNumColumns());
//		}
//	}
//	else {
//		w = c.getWidth();
//	}
//	
//	if(c.getHeight() <= -1) {
//		h = Math.round(rowHeight * c.getNumRows());
//	}
//	else {
//		h = c.getHeight();
//	}
	
//	checkEvents();
//	Display.sync(60);
//	if(updateNeeded) {
//		Display.update();
//		updateNeeded = false;
//	}
//	else
//		Display.processMessages();
	
//	setUpdateNeeded(true);


//}
//
//if((Mouse.isButtonDown(0)) && (!wasButtonPressed)) {
//	setUpdateNeeded(true);
//	setWasButtonPressed(true);
//	president.fireEvent(new MousePressedEvent());
//}
//
//if((!Mouse.isButtonDown(0)) && (wasButtonPressed)) {
//	setUpdateNeeded(true);
//	setWasButtonPressed(false);
//	president.fireEvent(new MouseReleasedEvent());
//}
	
	
//	public void addEventListener(EventListener listener) {
//	listeners.add(listener);
//	setMonitored(true);
//}
//
////public void checkEvents() {
////	for(int i = 0; i < listeners.size(); i++)
////		listeners.get(i).checkEvents();
////	for(int i = 0; i < components.size(); i++)
////		if(components.get(i).isMonitored())
////			components.get(i).checkEvents();
////}
//
//public void fireEvent(Event e) {
//	for(int i = 0; i < listeners.size(); i++) {
//		listeners.get(i).handleEvent(e);
//	}
//	for(int i = 0; i < components.size(); i++) {
//		if(components.get(i).isMonitored()) {
//			components.get(i).fireEvent(e);
//		}
//	}
//	
//}
	
//	protected ArrayList<EventListener> listeners = new ArrayList<EventListener>(1);
	
//	searchIcon.addEventListener(new FilledBoxListener() {
//	
//	@Override
//	protected void mousedOver() {
//		setCurrentColor(getMousedOverColor());
//	}
//});
	
//	@Override
//	public void remove(int row, int column) {
//		for(int i = 0; i < cells.length; i++) {
//			for(int j = 0; j < cells[i].length; j++) {
//				if(cells[i][j] == cells[row][column]) {
//					cells[i][j] = null;
//				}
//			}
//		}
//		
//	}
	
//	public Box getCell(int row, int column) {
//	return cells[row][column];
//}

//	GridLayoutConstraints c = new GridLayoutConstraints();
//	c.setHeight(35);
//	container.add(this, c);
//	
//	mgr = new GridLayout(Box.getNumColumns(w, h, 1), 1);
//	notifications = new Button("res/images/notificationsIcon.png", Color.CLEAR, true);
//	settings = new Button("res/images/settingsIcon.png", Color.ORANGE, true);
//	apps = new Button("res/images/appsIcon.png", Color.CLEAR, true);
//	power = new Button("res/images/powerIcon.png", Color.CLEAR, true);
//	add(notifications);
//	add(settings);
//	add(apps);
//	add(power);
//	Input.addListener(new ButtonEventHandler(notifications));
//	Input.addListener(new ButtonEventHandler(settings));
//	Input.addListener(new ButtonEventHandler(apps));
//	Input.addListener(new ButtonEventHandler(power) {
//		@Override
//		public void actionTriggered() {
//			Pilot.pilot.addNewLayer();
//			Pilot.pilot.extraLayers.get(0).add(new FilledBox(null, null, Pilot.pilot.waterBar.components.get(3).getX(),
//					Pilot.pilot.waterBar.components.get(3).getBottomY(), 100, 40, Color.CYAN));
//		}
//	});
//	
////	for(int i = this.components.size(); i < ((GridLayout)mgr).getColumns(); i++) {
////		final Button b = new Button("res/images/settingsIcon.png", Color.randomColor(), true);
////		add(b);
////		Input.addListener(new ButtonEventHandler(b) {
////			@Override
////			public void actionTriggered() {
////				Pilot.pilot.remove(Pilot.pilot.components.get(1));
////				Pilot.pilot.add(new FilledBox(b.getFillColor()));
////			}
////		});
////	}
}

//protected boolean coordinatesSet = false;

//public Box(LayoutManager mgr, Color color, String textureFile, int x, int y, int w, int h) {
//if(mgr == null)
//	this.mgr = new GridLayout();
//else
//	this.mgr = mgr;
//
//if(color != null)
//	this.color = color;
//
//if(textureFile != null) {
//	try {
//		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(textureFile));
//	} catch (IOException e) { e.printStackTrace(); } 
//}
//setCoordinates(x, y, w, h);
//}

//if(component.coordinatesSet) {
//components.add(component);
//component.setContainer(this);
//}
//else 

//public void setCoordinates(int x, int y, int w, int h) {
//if(container != null) {
//	this.x = container.getX() + x;
//	this.y = container.getY() + y;
//}
//else {
//	this.x = x;
//	this.y = y;
//}
//this.w = w;
//this.h = h;
//coordinatesSet = true;
//}

//public void setX(int x) {
//this.x = x;
//}


//public void setY(int y) {
//	this.y = y;
//}


//public void setWidth(int w) {
//	this.w = w;
//}


//public void setHeight(int h) {
//	this.h = h;
//}

//public void widthAdjusted(Box component) {
//for(int i = 0; i < cells.length; i++) {
//	for(int j = 0; j < cells[i].length; j++) {
//		if(cells[i][j] == component) {
//			for(int l = i; l < columns; l++) {
//				if(cells[l][j] != component) {
////					cells[l][j].setX(cells[l - 1][j].getRightX());
//				}
//			}
//		}
//	}
//}
//
//}