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

import java.util.TreeMap;

/**
 * Represents an ordered mapping of {@link Coordinate} object keys into a parameterized type values. This class extends {@link TreeMap}, so iterators over the
 * coordinate keys managed by the map will be returned in left-to-right, top-down order. Convenience methods of this class give
 * uses of class the ability to address values by (x,y) coordinate pairs without requiring the user to create a new {@link Coordinate} object. 
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @param <V> The type of values stored in this map
 */
@SuppressWarnings("serial")
public class CoordinateMap<V> extends TreeMap<Coordinate, V> {

	/**
	 * Constructs a new empty coordinate map
	 */
	public CoordinateMap()
	{
		super();
	}
	
	/**
	 * Adds a new value to the map at the given (x,y) coordinate
	 * @param x The X-axis value of the coordinate
	 * @param y The Y-axis value of the coordinate
	 * @param value The value to insert at that coordinate
	 */
	public void put( int x, int y, V value )
	{
		put( new Coordinate( x, y ), value );
	}

	/**
	 * Retrieves a value from the map at the given (x,y) coordinate
	 * @param x The X-axis value of the coordinate
	 * @param y The Y-axis value of the coordinate
	 * @return The value retrieved from the map at that coordinate
	 */
	public V get( int x, int y )
	{
		return get( new Coordinate( x, y ) );
	}

	/**
	 * Returns true if the map contains a {@link Coordinate} key with the given coordinate values, false if not 
	 * @param x The X-axis value of the coordinate
	 * @param y The Y-axis value of the coordinate
	 * @return True if the map contains the (x,y) coordinate key, false if not 
	 */
	public boolean containsKey( int x, int y )
	{
		return containsKey( new Coordinate( x, y ) );
	}
}
