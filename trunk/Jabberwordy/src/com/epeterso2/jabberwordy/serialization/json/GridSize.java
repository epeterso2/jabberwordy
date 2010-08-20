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

package com.epeterso2.jabberwordy.serialization.json;

/**
 * Contains the size of a {@link JSONPuzzle} grid in rows and columns. The properties of this class are named in such a way as to support
 * easy serialization into JSON format.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/JSON">Crossword puzzle data in JSON format</a>
 * @see <a href="http://www.json.org">Introducing JSON</a>
 */
public class GridSize {
	
	private int rows = 0;
	
	private int cols = 0;

	/**
	 * Returns the number of rows in the grid
	 * @return The number of rows in the grid
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Sets the number of rows in the gird
	 * @param rows The number of rows in the grid
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Returns the number of columns in the grid
	 * @return The number of columns in the grid
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Sets the number of columns in the grid
	 * @param cols The number of columns in the grid
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}

}
