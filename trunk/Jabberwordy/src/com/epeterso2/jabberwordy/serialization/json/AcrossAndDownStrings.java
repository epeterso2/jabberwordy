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
 * Contains a pair of string arrays in an {@link JSONPuzzle}, one for across data and one for down data.
 * The properties of this class are named in such a way as to support
 * easy serialization into JSON format.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/JSON">Crossword puzzle data in JSON format</a>
 * @see <a href="http://www.json.org">Introducing JSON</a>
 */
public class AcrossAndDownStrings {
	
	private String[] across = null;
	
	private String[] down = null;

	/**
	 * Sets the across string array to the given value
	 * @param across The across string array value
	 */
	public void setAcross(String[] across) {
		this.across = across;
	}

	/**
	 * Returns the value of the across string array
	 * @return The value of the across string array
	 */
	public String[] getAcross() {
		return across;
	}

	/**
	 * Sets the down string array to the given value
	 * @param down The down string array value
	 */
	public void setDown(String[] down) {
		this.down = down;
	}

	/**
	 * Returns the value of the down string array
	 * @return The value of the down string array
	 */
	public String[] getDown() {
		return down;
	}

}
