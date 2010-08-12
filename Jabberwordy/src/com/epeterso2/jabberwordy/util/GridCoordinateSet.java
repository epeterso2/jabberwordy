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

import java.util.TreeSet;
import java.util.Iterator;

/**
 * An extension of {@link TreeSet} initialized with a full collection of coordinates of a grid with given width and height.
 * When the constructor for this class is invoked, the set is populated with {@link Coordinate} objects that correspond
 * to all cells in the grid. Numbering of the rows and columns begins at 1, so the top-left cell has the coordinate (1, 1).
 * The {@link Iterator} for this class returns the coordinates in row-first, left-to-right order.
 * @author <a href="http://www.epeterso2.com/">Eric Peterson</a>
 */
@SuppressWarnings("serial")
public class GridCoordinateSet extends TreeSet<Coordinate> {
	
	/**
	 * Constructs a new {@link GridCoordinateSet} for a grid of the given width and height.
	 * @param width The width of the grid
	 * @param height The height of the grid
	 */
	public GridCoordinateSet( int width, int height )
	{
		for ( int row = 1; row <= height; ++row )
		{
			for ( int col = 1; col <= width; ++col )
			{
				add( new Coordinate( col, row ) );
			}
		}
	}

}
