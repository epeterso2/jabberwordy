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

package com.epeterso2.jabberwordy.util;

/**
 * Contains the result of automatically calculating the clue numbering for a given cell.
 * This includes both the cell number as well as whether or not this cell is the start of either a down clue
 * or an across clue (or both).
 * @author <a href="http://www.epeterso2.com/">Eric Peterson</a>
 */
public class StandardClueNumberResult {
	
	private int number = 0;
	
	private boolean startOfDownClue = false;
	
	private boolean startOfAcrossClue = false;

	/**
	 * Returns the clue number of this cell, or zero if the cell has no number.
	 * @return The clue number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the clue number of this cell
	 * @param number The clue number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Returns the status of this cell as the starting point of a down clue 
	 * @return <tt>true</tt> if this cell is the starting point of a down clue
	 */
	public boolean isStartOfDownClue() {
		return startOfDownClue;
	}

	/**
	 * Sets the status of this cell as the starting point of a down clue
	 * @param startOfDownClue <tt>true</tt> if this cell is the starting point of a down clue
	 */
	public void setStartOfDownClue(boolean startOfDownClue) {
		this.startOfDownClue = startOfDownClue;
	}

	/**
	 * Returns the status of this cell as the starting point of a across clue 
	 * @return <tt>true</tt> if this cell is the starting point of a across clue
	 */
	public boolean isStartOfAcrossClue() {
		return startOfAcrossClue;
	}

	/**
	 * Sets the status of this cell as the starting point of a across clue
	 * @param startOfAcrossClue <tt>true</tt> if this cell is the starting point of a across clue
	 */
	public void setStartOfAcrossClue(boolean startOfAcrossClue) {
		this.startOfAcrossClue = startOfAcrossClue;
	}
	
	/**
	 * Returns the status of this cell as the starting point of either an across or down clue
	 * @return <tt>true</tt> if this cell is the starting point of either an across or down clue
	 */
	public boolean isStartOfClue()
	{
		return isStartOfAcrossClue() || isStartOfDownClue();
	}
	
	/**
	 * Returns a string representation of the result in the following format:
	 * <p>
	 * <tt>number:AD</tt>
	 * <p>
	 * Where A or D is set if the cell is the start of an across or down, respectively, or "-" if not.
	 */
	public String toString()
	{
		return new StringBuilder().append( number ).append( ":" ).append( startOfAcrossClue ? "A" : "-" ).append( startOfDownClue ? "D" : "-" ).toString();
	}

}
