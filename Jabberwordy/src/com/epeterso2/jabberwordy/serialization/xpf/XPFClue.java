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

package com.epeterso2.jabberwordy.serialization.xpf;

import com.epeterso2.jabberwordy.util.Coordinate;

/**
 * Represents a single clue in an XPF puzzle. A clue is associated with a location in the grid and has both a number and direction.
 * Each clue also has the text to display to the solver as well as an associated solution.
 * <p>
 * The property "number" is actually a string - this means the value of the "number" property could be any symbol
 * that can be displayed. The same is true for "direction" - although "Across" and "Down" are most common, it could be "Diagonal" or
 * any other string that can be displayed.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFClue implements Comparable<XPFClue> {
	
	private Coordinate coordinate = new Coordinate( 0, 0 );
	
	private String number = null;
	
	private String direction = null;
	
	private String text = null;
	
	private String answer = null;
	
	/**
	 * Constructs a new empty clue object.
	 */
	public XPFClue()
	{
		;
	}
	
	/**
	 * Constructs a new clue with the given text and answer. The answer should be in upper case with no spaces.
	 * @param text The text of the clue
	 * @param answer The answer to the clue
	 */
	public XPFClue( String text, String answer )
	{
		this.text = text;
		this.answer = answer;
	}

	/**
	 * Returns the coordinate associated with this clue
	 * @return The coordinate of the clue
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * Sets the coordinate associated with this clue
	 * @param coordinate The coordinate of this clue
	 * @return This object
	 */
	public XPFClue setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
		return this;
	}

	/**
	 * Returns the number of this clue.
	 * @return The number of the clue
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number of this clue.
	 * @param number The number of this clue
	 * @return This object
	 */
	public XPFClue setNumber(String number) {
		this.number = number;
		return this;
	}

	/**
	 * Returns the direction of this clue
	 * @return The direction of this clue
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of this clue
	 * @param direction The direction of this clue
	 * @return This object
	 */
	public XPFClue setDirection(String direction) {
		this.direction = direction;
		return this;
	}

	/**
	 * Returns the text of this clue
	 * @return The text of this clue
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text of this clue
	 * @param text The text of this clue
	 * @return This object
	 */
	public XPFClue setText(String text) {
		this.text = text;
		return this;
	}
	
	/**
	 * Sets the answer of this clue
	 * @param answer The answer of this clue
	 * @return This object
	 */
	public XPFClue setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	/**
	 * Returns the answer of this clue
	 * @return The answer of this clue
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * Determines if the location of this clue has been fully specified or not.
	 * The location is fully specified if the coordinate, number, and direction are all not equal to <tt>null</tt>.
	 * @return <tt>true</tt> if this clue is located, <tt>false</tt> if not
	 */
	public boolean isLocated()
	{
		return coordinate != null && number != null && direction != null;
	}
	
	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode()
	{
		int hash = 37;
		
		hash = hash * 37 + hashCode( getNumber() );
		hash = hash * 37 + hashCode( getCoordinate() );
		hash = hash * 37 + hashCode( getDirection() );
		hash = hash * 37 + hashCode( getText() );
		hash = hash * 37 + hashCode( getAnswer() );
		
		return hash;
	}

	private int hashCode( Object object )
	{
		return object == null ? 1 : object.hashCode();
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object instanceof XPFClue )
		{
			XPFClue that = (XPFClue) object;
			
			return
				equal( this.getCoordinate(), that.getCoordinate() ) &&
				equal( this.getDirection(), that.getDirection() ) &&
				equal( this.getText(), that.getText() ) &&
				equal( this.getNumber(), that.getNumber() ) &&
				equal( this.getAnswer(), that.getAnswer() );
		}
		
		else
		{
			return false;
		}
	}
	
	private boolean equal( Object one, Object two )
	{
		return one == null ? two == null : one.equals( two );
	}

	/**
	 * Returns a representation of this clue as a string in the form:
	 * <p>
	 * <tt>(col,row)=number-direction: text [answer]</tt>
	 * <p>
	 * For example:
	 * <tt>(1,2)=11-Across: They go up in planes [ARMRESTS]</tt>
	 */
	@Override
	public String toString()
	{
		return new StringBuilder().append( coordinate.toString() ).append( "=" ).append( number ).append( "-" ).append( direction )
		.append( ": " ).append( text ).append( " [" ).append( answer ).append( "]").toString();
	}

	/**
	 * Compares this {@link XPFClue} with the specified {@link XPFClue} for order. The rules for comparison are as follows:
	 * <ul>
	 * <li>Direction is compared first. If the directions are not equal, the comparison of the directions is returned.</li>
	 * <li>If the directions are equal, then the comparison of the numbers is returned.</li>
	 * <li>A clue with a <tt>null</tt> direction comes after a clue with a non-<tt>null</tt> direction.</li>
	 * <li>A clue with a <tt>null</tt> number comes after a clue with a non-<tt>null</tt> number, assuming the directions of the two clues is equal.</li>
	 * <li>If two clues have the same direction and number, the comparison of text of both clues is returned.</li>
	 * </ul>
	 * <p>
	 * The XPF standard doesn't address the ordering of clues, although it does recommend that clues with the same direction be grouped
	 * together in the list of clues displayed to the user.
	 * The standard allows a clue to be any arbitrary string, including a non-numeric value.
	 * <p>
	 * A problem arises when comparing strings alphabetically instead of numerically. If sorted alphabetically,
	 * clue number 11 comes before clue number 2, which is counterintuitive because most crossword puzzle clue numbers are sorted numerically. 
	 * <p>
	 * This implementation makes the following assumption - if the number properties of two clues to be compared are both numbers,
	 * then they are compared as {@link Integer} objects. Otherwise, they are compared as {@link String} objects.
	 */
	@Override
	public int compareTo( XPFClue that )
	{
		if ( that == null )
		{
			return -1;
		}
		
		if ( this.getDirection() == null )
		{
			return that.getDirection() == null ? 0 : 1;
		}
		
		if ( this.getDirection().compareTo( that.getDirection() ) == 0 )
		{
			if ( this.getNumber() == null )
			{
				return that.getNumber() == null ? 0 : 1;
			}
			
			int result = 0;
			
			if ( this.isNumberANumber() && that.isNumberANumber() )
			{
				result = this.getNumberAsNumber().compareTo( that.getNumberAsNumber() );
			}
			
			else
			{
				result = this.getNumber().compareTo( that.getNumber() );
			}

			return result == 0 ? ( this.getText() == null ? ( that.getText() == null ? 0 : 1 ) : this.getText().compareTo( that.getText() ) )  : result;
		}
		
		else
		{
			return this.getDirection().compareTo( that.getDirection() );
		}
	}
	
	private boolean isNumberANumber()
	{
		try
		{
			Integer.valueOf( getNumber() );
			return true;
		}
		
		catch ( NumberFormatException e )
		{
			return false;
		}
	}
	
	private Integer getNumberAsNumber()
	{
		return Integer.valueOf( getNumber() );
	}
}
