package aamain;

public class GridLayout extends LayoutManager {
	private int columns, rows;
	private float w, h, hBorder, vBorder;
	
	public GridLayout(int columns, int rows) {
		this(columns, rows, 0, 0);
	}
	
	public GridLayout(int columns, int rows, float hBorder, float vBorder) {
		this.columns = columns;
		this.rows = rows;
		this.hBorder = hBorder;
		this.vBorder = vBorder;
	}
	
	@Override
	public void add(Rect component, Object constraints) {
		if(client == null) {
			client = component.getContainer();
			w = (client.getWidth() - (columns - 1) * hBorder) / (columns);
			h = (client.getHeight() - (rows - 1) * vBorder) / (rows);
		}
		
		if(constraints == null)
			constraints = new GridLayoutConstraints(0, 0);
		else if(!(constraints instanceof GridLayoutConstraints))
			throw new RuntimeException("constraints not instanceof GridLayoutConstraints");
		
		GridLayoutConstraints c = (GridLayoutConstraints)constraints;
		
		float x = c.getColumn() * (w + hBorder);
		float y = c.getRow() * (h + vBorder);
		component.setCoordinates(x, y, w, h);
	}

}
