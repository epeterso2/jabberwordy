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

import java.io.IOException;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.epeterso2.jabberwordy.serialization.PuzzleOutputStream;

/**
 * Provides an {@link OutputStream} for deserializing an XPF image into an {@link XPFPuzzleCollection} object.
 * The order in which the puzzles appear in the {@link XPFPuzzleCollection} is identical to the order in which they
 * appear in the deserialized XPF image.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzleOutputStream extends PuzzleOutputStream<XPFPuzzleCollection> {

	/**
	 * Converts a serialized XPF image into an {@link XPFPuzzleCollection}.
	 */
	@Override
	public XPFPuzzleCollection toPuzzle() throws IOException
	{
		try
		{
			return buildCollection( new SAXBuilder().build( new String( toByteArray() ) ) );
		}
		
		catch ( JDOMException e )
		{
			throw new IOException( e );
		}
	}

	private XPFPuzzleCollection buildCollection( Document document ) throws IOException
	{
		return new XPFPuzzleCollection();
	}

}
