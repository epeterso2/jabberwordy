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
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFCellStyle {
	
	private boolean circled = false;
	
	private boolean block = false;
	
	private boolean borderless = false;
	
	private String shade = null;
	
	public boolean isCircled() {
		return circled;
	}

	public XPFCellStyle setCircled(boolean circled) {
		this.circled = circled;
		return this;
	}

	public boolean isBlock() {
		return block;
	}

	public XPFCellStyle setBlock(boolean block) {
		this.block = block;
		return this;
	}

	public boolean isBorderless() {
		return borderless;
	}

	public XPFCellStyle setBorderless(boolean borderless) {
		this.borderless = borderless;
		return this;
	}

	public String getShade() {
		return shade;
	}

	public XPFCellStyle setShade(String shade) {
		this.shade = shade;
		return this;
	}

}
