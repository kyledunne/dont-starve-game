package aamain;

public class HexGridLayout extends LayoutManager {
	private int columns;
	private int rows;
	private int widthOfHexes;
	
	public HexGridLayout(int columns, int rows, int widthOfHexes) {
		this.columns = columns;
		this.rows = rows;
		this.widthOfHexes = widthOfHexes;
	}
	
	public HexGridLayout(int columns, int rows) {
		this(columns, rows, Hex.DEFAULT_HEX_WIDTH);
	}
	
	@Override
	public void add(Rect component, Object constraints) {
		super.add(component, constraints);
		if(constraints != null)
			throw new RuntimeException("constraints not null for HexGridLayout!!");
		
		if (this.client.areCoordinatesSet()) {
			if (client.getComponents().size() > columns * rows)
				throw new RuntimeException("client.getComponents().size() > columns * rows in HexGridLayout");
		}
//		else {
//			System.out.println(columns + ", " + rows + pendingComponents.size());
////			if (pendingComponents.size() > columns * rows)
////				throw new RuntimeException("pendingComponents.size() > columns * rows in HexGridLayout");
//		}
		
		int column;
		int row;
		float x;
		float y;
		float xOffset;
		
		int currentNumberOfHexesAdded = client.getComponents().size();
		column  = currentNumberOfHexesAdded % columns;
		row = (currentNumberOfHexesAdded / columns) + 1;
		
		if (row % 2 == 1)
			xOffset = 0;
		else
			xOffset = widthOfHexes * .5f;
		
		float heightOfRows = ((widthOfHexes * Hex.HEX_ASPECT_RATIO) * .75f);
		float heightOfHexes = (widthOfHexes * Hex.HEX_ASPECT_RATIO);
		
		x = ((column) * widthOfHexes) + xOffset;
		y = ((row - 1) * heightOfRows);
		
		component.setCoordinates(x, y, widthOfHexes, heightOfHexes);
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}


