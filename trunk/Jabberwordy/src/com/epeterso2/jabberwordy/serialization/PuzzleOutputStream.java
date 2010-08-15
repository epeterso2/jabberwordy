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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This abstract class is the superclass of all classes that implement puzzle deserializers, which convert
 * puzzle images into puzzle objects.
 * <p>
 * This class and its subclasses are used to convert a sequence of bytes of a puzzle image that represents a particular puzzle into an
 * object representation of the same puzzle. To convert the puzzle image to a puzzle object, an application
 * must:
 * <ol>
 * <li>Create a new instance of a {@link PuzzleOutputStream} subclass that can deserialize the puzzle image,</li>
 * <li>Use the {@link OutputStream#write(int)} method (or other related write methods) of that instance to
 * write the puzzle image, and</li>
 * <li>Call the {@link #toPuzzle()} method of that instance, which will build a puzzle object from the written puzzle image.
 * </ol>
 * A subclass of this class will return a puzzle object of a type specific to the subclass. This object is returned
 * by the implementation of the abstract method {@link #toPuzzle()}. 
 * <p>
 * If an instance of this class is constructed with an {@link InputStream} parameter, then the entire contents of the input stream are read
 * and immediately written to this {@link OutputStream}. This provides an easy way to implement the reading of
 * puzzle images from files, URLs, and other input streams, like so:
 * <p>
 * <tt>MyPuzzle puzzle = new MyPuzzleOutputStream( new FileInputStream( new File( "MyPuzzle.puz" ) ) ).toPuzzle();</tt>
 * <p>
 * or:
 * <p>
 * <tt>MyPuzzle puzzle = new MyPuzzleOutputStream( new URL( "http://www.puzzles.com/puzzle123.puz" ).openStream() ).toPuzzle();</tt>
 * <p>
 * @param P The class of puzzle object to be returned by the {@link #toPuzzle()} method 
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see OutputStream#write(byte[])
 * @see OutputStream#write(byte[], int, int)
 */
public abstract class PuzzleOutputStream<P> extends OutputStream {
	
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	/**
	 * Constructs a new puzzle image output stream.
	 */
	public PuzzleOutputStream()
	{
		;
	}

	/**
	 * Constructs a new puzzle image output stream using the data read from an input stream.
	 * This method reads all of the data from the given {@link InputStream} and writes
	 * it to its own output stream. If the given input stream contains an entire puzzle
	 * image, then the puzzle may be deserialized immediately with the {@link #toPuzzle()}
	 * method. 
	 * @param inputStream The input stream with the puzzle data
	 * @throws IOException An error occurred during reading the input stream or writing the output stream
	 */
	public PuzzleOutputStream( InputStream inputStream ) throws IOException
	{
		for ( int i; ( i = inputStream.read() ) != -1; )
		{
			outputStream.write( i );
		}
	}
	
	/**
	 * Writes the given byte to the puzzle output stream.
	 */
	@Override
	public void write( int b ) throws IOException
	{
		outputStream.write( b );
	}
	
	/**
	 * Returns the byte[] representation of the serialized puzzle image. The array contains all of the data
	 * that has been written to this output stream so far.
	 * @return The puzzle image
	 */
	public byte[] toByteArray()
	{
		return outputStream.toByteArray();
	}

	/**
	 * Converts the serialized puzzle image into a puzzle object. Implementations of this method
	 * may call the {@link #toByteArray()} method to retrieve the serialized puzzle image.
	 * @return The deserialized puzzle object
	 * @throws IOException An error occurred during deserialization
	 */
	public abstract P toPuzzle() throws IOException;

}
