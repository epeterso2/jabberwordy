/**
 * Copyright (c) 2010 Eric Peterson
 * For contact information, visit http://www.epeterso2.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.epeterso2.jabberwordy.serialization.puz;

/**
 * Container for the collection of attributes that describe how a single cell in a grid should be presented to the user
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public class PUZCellStyle implements Cloneable {

	private int number = 0;
	
	private boolean block = true;
	
	private boolean previouslyMarkedIncorrect = false;
	
	private boolean currentlyMarkedIncorrect = false;
	
	private boolean revealed = false;
	
	private boolean circled = false;

	/**
	 * Sets the status of this cell as a block.
	 * @param block True if this cell is a block, false if not
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}

	/**
	 * Determine if this cell represents a block or not
	 * @return True if this cell is a block, false if not
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * Sets the previously-marked-incorrect status of this cell.
	 * @param previouslyMarkedIncorrect True if this cell was previously marked incorrect, false if not
	 */
	public void setPreviouslyMarkedIncorrect(boolean previouslyMarkedIncorrect) {
		this.previouslyMarkedIncorrect = previouslyMarkedIncorrect;
	}

	/**
	 * Determine if this cell was previously marked incorrect
	 * @return True if this cell was previously marked incorrect, false if not
	 */
	public boolean isPreviouslyMarkedIncorrect() {
		return previouslyMarkedIncorrect;
	}

	/**
	 * Sets the currently-marked-incorrect status of this cell.
	 * @param currentlyMarkedIncorrect True if this cell is currently marked incorrect, false if not
	 */
	public void setCurrentlyMarkedIncorrect(boolean currentlyMarkedIncorrect) {
		this.currentlyMarkedIncorrect = currentlyMarkedIncorrect;
	}

	/**
	 * Determine if this cell is currently marked incorrect
	 * @return True if this cell is currently marked incorrect, false if not
	 */
	public boolean isCurrentlyMarkedIncorrect() {
		return currentlyMarkedIncorrect;
	}

	/**
	 * Sets the revealed status of this cell.
	 * @param revealed True if the solution of this cell was revealed, false if not
	 */
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}

	/**
	 * Determines if this cell's solution has been revealed
	 * @return True if the solution has been revealed, false if not
	 */
	public boolean isRevealed() {
		return revealed;
	}

	/**
	 * Sets the circled status of this cell.
	 * @param circled True if the cell is to be drawn with a circle inside, false if not
	 */
	public void setCircled(boolean circled) {
		this.circled = circled;
	}

	/**
	 * Determines if this cell is to be drawn with a circle inside
	 * @return True if a circle is to be drawn inside this cell, false if not
	 */
	public boolean isCircled() {
		return circled;
	}
	
	/**
	 * Returns a simple string representation of this style (useful for debugging) in the following format:
	 * <p>
	 * <b>#:abcde</b>
	 * <p>
	 * With the following values:
	 * <ul>
	 * <li># = Number</li>
	 * <li>a = isBlock() == true ? 'B' : '-'</li>
	 * <li>b = isPreviouslyMarkedWrong() == true ? 'P' : '-'</li>
	 * <li>c = isCurrentlyMarkedWrong() == true ? 'W' : '-'</li>
	 * <li>d = isRevealed() == true ? 'R' : '-'</li>
	 * <li>e = isCircled() == true ? 'C' : '-'</li>
	 * </ul>
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append( getNumber() );
		builder.append( ":" );
		builder.append( isBlock() ? "B" : "-" );
		builder.append( isPreviouslyMarkedIncorrect() ? "P" : "-" );
		builder.append( isCurrentlyMarkedIncorrect() ? "W" : "-" );
		builder.append( isRevealed() ? "R" : "-" );
		builder.append( isCircled() ? "C" : "-" );
		return builder.toString();
	}

	/**
	 * Sets the clue number of this cell
	 * @param number The clue number of this cell
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Returns the clue number of this cell
	 * @return The clue number of this cell
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Computes the hashCode of this object
	 */
	@Override
	public int hashCode()
	{
		return getNumber();
	}
	
	/**
	 * Implements the equality operation for this object.
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object instanceof PUZCellStyle )
		{
			PUZCellStyle that = (PUZCellStyle) object;
			
			return this.isBlock() == that.isBlock() &&
				this.isCircled() == that.isCircled() &&
				this.isCurrentlyMarkedIncorrect() == that.isCurrentlyMarkedIncorrect() &&
				this.isPreviouslyMarkedIncorrect() == that.isPreviouslyMarkedIncorrect() &&
				this.isRevealed() == that.isRevealed() &&
				this.getNumber() == that.getNumber();
		}
		
		else
		{
			return false;
		}
	}
	
	/**
	 * Creates and returns a copy of this object.
	 */
	@Override
	public PUZCellStyle clone()
	{
		PUZCellStyle that = new PUZCellStyle();
		
		that.setBlock( isBlock() );
		that.setCircled( isCircled() );
		that.setCurrentlyMarkedIncorrect( isCurrentlyMarkedIncorrect() );
		that.setNumber( getNumber() );
		that.setPreviouslyMarkedIncorrect( isPreviouslyMarkedIncorrect() );
		that.setRevealed( isRevealed() );
		
		return that;
	}

}
