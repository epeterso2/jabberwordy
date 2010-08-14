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
 * Represents a coordinate in a Cartesian coordinate system. The X-axis of the system increases in value from left to right,
 * and the Y-axis of the system increases in value from top to bottom. The coordinates are ordered from left to right
 * starting at the top.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public class Coordinate implements Comparable<Coordinate>, Cloneable {
	
	private int x = 0;
	
	private int y = 0;

	/**
	 * Constructs a new {@link Coordinate} object with the given x and y location.
	 * @param x
	 * @param y
	 */
	public Coordinate( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the X-axis value of this coordinate
	 * @return The X-axis value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the X-axis value of this coordinate
	 * @param x The X-axis value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y-axis value of this coordinate
	 * @return The Y-axis value
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y-axis value of this coordinate
	 * @param y The Y-axis value
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode()
	{
		return x + y * 37;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one. Two coordinates are equal if both their x and y values are equal.
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object == null || ! ( object instanceof Coordinate ) )
		{
			return false;
		}
		
		else
		{
			Coordinate that = (Coordinate) object;
			
			return this.getX() == that.getX() && this.getY() == that.getY();
		}
	}
	
	/**
	 * Returns a string representation of this object: (x,y)
	 */
	@Override
	public String toString()
	{
		return new StringBuilder().append( "(" ).append( x ).append( "," ).append( y ).append( ")" ).toString();
	}

	/**
	 * Compares this object with the specified object for order.
	 */
	@Override
	public int compareTo( Coordinate that )
	{
		if ( this.equals( that ) )
		{
			return 0;
		}
		
		if ( this.getY() == that.getY() )
		{
			return this.getX() < that.getX() ? -1 : 1;
		}
		
		else
		{
			return this.getY() < that.getY() ? -1 : 1;
		}
	}

	/**
	 * Creates and returns a copy of this object.
	 */
	@Override
	public Coordinate clone()
	{
		return new Coordinate( getX(), getY() );
	}

}
