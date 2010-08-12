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
 * This abstract class is the superclass of all classes that implement puzzle deserializers,
 * one which provides an {@link OutputStream} implementation.
 * <p>
 * Subclasses must implement the {@link #toPuzzle()} method, which is responsible for converting
 * the raw data into a puzzle object. To deserialize a puzzle, an application must write the
 * serialized puzzle data to this output stream.
 * The raw data must be a serialized representation of the generic type parameter of the
 * implementing subclass.
 * When all of the data has been written, the application must invoke the {@link #toPuzzle()}
 * method to return the deserialized puzzle object.
 * <p>
 * Subclasses of this class can access the serialized puzzle image by calling the
 * {@link #toByteArray()} method from their {@link #toPuzzle()}
 * methods.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @param <P> The class of puzzle object to be serialized by an implementing class
 */
public abstract class PuzzleOutputStream<P> extends OutputStream {
	
	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	public PuzzleOutputStream()
	{
		;
	}

	/**
	 * Constructs a new output stream using the data read from an input stream.
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
	 * Returns the byte[] representation of the serialized puzzle image.
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
	 * @throws PuzzleDeserializationException An error occurred during deserialization
	 */
	public abstract P toPuzzle() throws PuzzleDeserializationException;

}
