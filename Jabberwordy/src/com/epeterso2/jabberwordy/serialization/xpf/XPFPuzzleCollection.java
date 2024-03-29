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

import java.util.ArrayList;

/**
 * A container class for a collection of {@link XPFPuzzle} objects. This class is simply an {@link ArrayList} of {@link XPFPuzzle} objects. 
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XPF Universal Crossword Puzzle Data Format</a>
 */
@SuppressWarnings("serial")
public class XPFPuzzleCollection extends ArrayList<XPFPuzzle> {

	/**
	 * Constructs a new puzzle collection with no puzzles in it.
	 */
	public XPFPuzzleCollection()
	{
		super();
	}

	/**
	 * Constructs a new puzzle collection with a single puzzle in it.
	 * @param puzzle The puzzle to add to the collection
	 */
	public XPFPuzzleCollection( XPFPuzzle puzzle )
	{
		super();
		add( puzzle );
	}
}
