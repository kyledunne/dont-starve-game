package aamain;

public class GridLayoutConstraints {
	private int column, row;
	
	/**
	 * @param column starts at 0
	 * @param row starts at 0
	 */
	public GridLayoutConstraints(int column, int row) {
		this.column = column;
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

}
