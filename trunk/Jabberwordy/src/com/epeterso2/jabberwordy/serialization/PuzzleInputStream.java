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
 * This abstract class is the superclass of all classes that implement puzzle serializers, which convert
 * puzzle objects into puzzle images.
 * <p>
 * This class and its subclasses are used to convert a puzzle object into a sequence of bytes that
 * represents the same puzzle. To convert the puzzle object to a puzzle image, an application
 * must:
 * <ol>
 * <li>Create a new instance of a {@link PuzzleInputStream} subclass,</li>
 * <li>Associate a puzzle object with the input stream via the constructor or the {@link #setPuzzle(Object)} method</li>
 * <li>Use the {@link InputStream#read()} method (or other related read methods) of that instance to
 * read the serialized puzzle image.</li>
 * </ol>
 * A subclass of this class will serialize the puzzle object in a manner specific to the subclass.
 * This serialized image is returned by the implementation of the abstract method {@link #toByteArray()}. 
 * That method is called to serialize the puzzle the first time the {@link #read()} method of this class is called.
 * Subsequent calls to the {@link #read()} method will return the subsequent bytes of the serialized image.
 * @param P The class of puzzle object to be serialized by the {@link #toByteArray()} method 
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see InputStream#read(byte[])
 * @see InputStream#read(byte[], int, int)
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
	public PuzzleInputStream<P> setPuzzle( P puzzle )
	{
		this.puzzle = puzzle;
		return this;
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
	
	/**
	 * Determines if the puzzle associated with this input stream can be successfully serialized.
	 * This is useful for checking the ability to serialize an object prior to the potentially
	 * expensive act of serialization.
	 * @throws IOException The puzzle object cannot be serialized without error
	 */
	public abstract void testForSerializability() throws IOException;
	
}
