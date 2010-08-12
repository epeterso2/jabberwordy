package com.epeterso2.jabberwordy.model;

public class VerticalBorder extends Border {
	
	private CellAppearance cellLeft = null;
	
	private CellAppearance cellRight = null;
	
	public VerticalBorder()
	{
		super();
	}

	public void setCellLeft(CellAppearance cellLeft) {
		this.cellLeft = cellLeft;
	}

	public CellAppearance getCellLeft() {
		return cellLeft;
	}

	public void setCellRight(CellAppearance cellRight) {
		this.cellRight = cellRight;
	}

	public CellAppearance getCellRight() {
		return cellRight;
	}

}
