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

/**
 * Contains information for rendering a cell in a puzzle. Cells may be:
 * <ul>
 * <li><b>block:</b> A black square,</li>
 * <li><b>circled:</b> a circle is to be drawn inside the cell,</li>
 * <li><b>borderless:</b> no border is to be drawn around the cell (although neighboring cells with borders may override it), or</li>
 * <li><b>shaded:</b> filled with a particular color.</li>
 * </ul>
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFCellStyle {
	
	private boolean circled = false;
	
	private boolean block = false;
	
	private boolean borderless = false;
	
	private String shade = null;
	
	/**
	 * Returns the circled status of this cell
	 * @return <tt>true</tt> if the cell is circled, <tt>false</tt> if not
	 */
	public boolean isCircled() {
		return circled;
	}

	/**
	 * Sets the circled status of this cell
	 * @param circled The circled status of this cell
	 * @return This object
	 */
	public XPFCellStyle setCircled(boolean circled) {
		this.circled = circled;
		return this;
	}

	/**
	 * Returns the block status of this cell
	 * @return <tt>true</tt> if this cell is a block
	 */
	public boolean isBlock() {
		return block;
	}

	/**
	 * Sets the block status of this cell
	 * @param block The block status of this cell
	 * @return This object
	 */
	public XPFCellStyle setBlock(boolean block) {
		this.block = block;
		return this;
	}

	/**
	 * Returns the borderless status of this cell
	 * @return <tt>true</tt> if this cell should be drawn without borders
	 */
	public boolean isBorderless() {
		return borderless;
	}

	/**
	 * Sets the borderless status of this cell
	 * @param borderless The borderless status of this cell
	 * @return This object
	 */
	public XPFCellStyle setBorderless(boolean borderless) {
		this.borderless = borderless;
		return this;
	}

	/**
	 * Returns the shade of this cell. Legal shade values are <tt>gray</tt> or a pound sign (#) followed by a six-digit hexadecimal number
	 * that corresponds to the 24-bit RGB color value.
	 * @return The shade of this cell
	 */
	public String getShade() {
		return shade;
	}

	/**
	 * Sets the shade of this cell. Legal shade values are <tt>gray</tt> or a pound sign (#) followed by a six-digit hexadecimal number
	 * that corresponds to the 24-bit RGB color value.
	 * @param shade The shade of this cell
	 * @return This object
	 */
	public XPFCellStyle setShade(String shade) {
		this.shade = shade;
		return this;
	}
	
	/**
	 * Returns a string representation of this cell style suitable for debugging purposes.
	 */
	public String toString()
	{
		return new StringBuilder().append( block ? "B" : "-" ).append( circled ? "O" : "-" ).append( borderless ? "N" : "-" )
		.append( shade != null ? "" : ( "[" + shade + "]" ) ).toString();
	}

}
