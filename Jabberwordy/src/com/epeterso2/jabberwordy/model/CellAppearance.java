package com.epeterso2.jabberwordy.model;

public class CellAppearance {
	
	private int number = 0;
	
	private boolean numberVisible = true;
	
	private boolean block = false;
	
	private boolean previouslyWrong = false;
	
	private boolean currentlyWrong = false;
	
	private boolean revealed = false;
	
	private boolean circled = true;
	
	private String color = null;
	
	private boolean borderVisible = true;
	
	public CellAppearance()
	{
		;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append( "number=" );
		builder.append( number );
		builder.append( ",numberVisible=" );
		builder.append( numberVisible );
		builder.append( ",block=" );
		builder.append( block );
		builder.append( ",previousWrong=" );
		builder.append( previouslyWrong );
		builder.append( ",currentlyWrong=" );
		builder.append( currentlyWrong );
		builder.append( ",revealed=" );
		builder.append( revealed );
		builder.append( ",circled=" );
		builder.append( circled );
		
		return builder.toString();
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isNumberVisible() {
		return numberVisible;
	}

	public void setNumberVisible(boolean numberVisible) {
		this.numberVisible = numberVisible;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public boolean isPreviouslyWrong() {
		return previouslyWrong;
	}

	public void setPreviouslyWrong(boolean previouslyWrong) {
		this.previouslyWrong = previouslyWrong;
	}

	public boolean isCurrentlyWrong() {
		return currentlyWrong;
	}

	public void setCurrentlyWrong(boolean currentlyWrong) {
		this.currentlyWrong = currentlyWrong;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

	public boolean isCircled() {
		return circled;
	}

	public void setCircled(boolean circled) {
		this.circled = circled;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setBorderVisible(boolean borderVisible) {
		this.borderVisible = borderVisible;
	}

	public boolean isBorderVisible() {
		return borderVisible;
	}

}
