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

package com.epeterso2.jabberwordy.serialization;

import java.io.IOException;
import java.io.InputStream;

/**
 * This abstract class is the superclass of all classes that implement puzzle serializers,
 * one which provides an {@link InputStream} implementation using a puzzle object as a basis.
 * <p>
 * An implementation of this class operates on a puzzle of a given type. The puzzle associated with
 * an instance of an implementor can be obtained with the {@link #getPuzzle()} method.
 * <p>
 * Subclasses must implement the {@link #toByteArray()} method, which is responsible for converting
 * the puzzle object into a serialized byte array. This method will be called during the first call to
 * the {@link #read()} method of this class to serialize the puzzle. Each call to {@link #read()} will
 * return the next byte of the serialized puzzle.
 * <p>
 * Subclasses will also need to provide an implementation of the one-argument constructor, which
 * takes a puzzle of the generic parameter type. This constructor must call the constructor of the
 * parent class with the given puzzle.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @param <P> The class of puzzle object to be serialized by an implementing class
 */
public abstract class PuzzleInputStream<P> extends InputStream {
	
	private P puzzle = null;
	
	private byte[] image = null;
	
	private int position = 0;
	
	/**
	 * Returns the puzzle to be serialized 
	 * @return The puzzle to be serialized
	 */
	public P getPuzzle()
	{
		return puzzle;
	}
	
	/**
	 * Sets the puzzle to be serialized
	 * @param puzzle The puzzle to be serialized
	 */
	public void setPuzzle( P puzzle )
	{
		this.puzzle = puzzle;
	}
	
	/**
	 * Constructs a new PuzzleInputStream
	 */
	public PuzzleInputStream()
	{
		;
	}
	
	/**
	 * Constructs a new puzzle serializer for a given puzle
	 * @param puzzle The puzzle to be serialized
	 */
	public PuzzleInputStream( P puzzle )
	{
		this.puzzle = puzzle;
	}
	
	/**
	 * Returns the next byte from the serialized puzzle input stream. When first invoked,
	 * the puzzle is serialized by calling the {@link #toByteArray()} method.
	 */
	@Override
	public int read() throws IOException
	{
		if ( image == null )
		{
			image = toByteArray();
		}
		
		return position < image.length ? image[ position++ ] : -1;
	}
	
	/**
	 * Serializes the puzzle associated with this class.
	 * @return A serialized byte-array representation of the puzzle 
	 * @throws IOException if an error occurs during the serialization process
	 */
	public abstract byte[] toByteArray() throws IOException;
	
}
