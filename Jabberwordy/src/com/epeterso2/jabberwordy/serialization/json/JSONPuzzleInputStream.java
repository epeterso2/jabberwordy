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

import java.io.IOException;

import com.epeterso2.jabberwordy.serialization.PuzzleInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializes a {@link JSONPuzzle} object into the JSON file format.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/JSON">Crossword puzzle data in JSON format</a>
 * @see <a href="http://www.json.org">Introducing JSON</a>
 */
public class JSONPuzzleInputStream extends PuzzleInputStream<JSONPuzzle> {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public byte[] toByteArray() throws IOException
	{
		if ( getPuzzle() == null )
		{
			throw new IOException( new NullPointerException( "Null puzzle" ) );
		}
		
		return gson.toJson( getPuzzle() ).getBytes();
	}

	/**
	 * Determines if the {@link JSONPuzzle} associated with this input stream can be successfully serialized.
	 * @throws IOException The puzzle cannot be serialized
	 */
	@Override
	public void testForSerializability() throws IOException
	{
		testPuzzleForSerializability( getPuzzle() );
	}
	
	/**
	 * Determines if the given {@link JSONPuzzle} can be successfully serialized.
	 * @throws IOException The puzzle cannot be serialized
	 */
	public static void testForSerializability( JSONPuzzle puzzle ) throws IOException
	{
		testPuzzleForSerializability( puzzle );
	}

	private static void testPuzzleForSerializability( JSONPuzzle puzzle )
	{
		;
	}
	
}
