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

/**
 * Thrown when an error occurs during deserialization of a puzzle image into a puzzle object. 
 * @author <a href="http://www.epeterso2.com/">Eric Peterson</a>
 */
@SuppressWarnings("serial")
public class PuzzleDeserializationException extends IOException {

	/**
	 * Constructs a new exception with a null cause and message.
	 */
	public PuzzleDeserializationException() {
		super();
	}

	/**
	 * Constructs a new exception with a null cause and the given message
	 * @param message The message to include in the exception
	 */
	public PuzzleDeserializationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with a null message and the given cause
	 * @param cause The cause to include in the exception
	 */
	public PuzzleDeserializationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new exception with the given cause and message
	 * @param message The message to include in the exception
	 * @param cause The cause to include in the exception
	 */
	public PuzzleDeserializationException(String message, Throwable cause) {
		super(message, cause);
	}

}
