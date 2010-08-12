package com.epeterso2.jabberwordy.model;

public abstract class Border {
	
	private BorderStyle style = BorderStyle.SOLID;
	
	private int width = 1;
	
	private boolean visible = true;
	
	public Border()
	{
		;
	}

	public void setStyle(BorderStyle style) {
		this.style = style;
	}

	public BorderStyle getStyle() {
		return style;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}
