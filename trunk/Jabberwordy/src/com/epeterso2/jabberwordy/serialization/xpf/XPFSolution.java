/**
 * Copyright (c) 2010 Eric Peterson, www.epeterso2.com
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
 * Contains the complete solution for a single cell in a PUZ puzzle.
 * <p>
 * An XPF puzzle has two possible solutions for each cell in the grid: a single letter answer as well as a rebus (multi-letter
 * or symbol) answer. The single letter answers must be in the range of 'A' through 'Z' - these are stored in the solution
 * section of the image. The rebus answers can be one or more characters long, and the answers may be either individual letters
 * or some punctuation symbols. Either solution is considered valid when checking the solution to the puzzle. But if a puzzle
 * is encrypted, then only the single-letter answers are used in the unlock code verification.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFSolution {
	
	private char letter = 0;
	
	private String rebus = null;
	
	/**
	 * Constructs a new solution
	 */
	public XPFSolution()
	{
		;
	}
	
	/**
	 * Constructs a new solution and sets the single-letter answer
	 * @param letter The single-letter answer
	 */
	public XPFSolution( char letter )
	{
		this.setLetter(letter);
	}
	
	/**
	 * Constructs a new solution and sets the rebus answer
	 * @param rebus The rebus answer
	 */
	public XPFSolution( String rebus )
	{
		this.setRebus(rebus);
	}
	
	/**
	 * Constructs a new solution and sets both the single-letter and rebus answers
	 * @param letter The single-letter answer
	 * @param rebus The rebus answer
	 */
	public XPFSolution( char letter, String rebus )
	{
		this.setLetter(letter);
		this.setRebus(rebus);
	}

	/**
	 * Sets the single-letter answer
	 * @param letter The single-letter answer
	 */
	public XPFSolution setLetter(char letter) {
		this.letter = letter;
		return this;
	}

	/**
	 * Retrieves the single-letter answer
	 * @return The single-letter answer
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * Sets the rebus answer
	 * @param rebus The rebus answer
	 */
	public XPFSolution setRebus(String rebus) {
		this.rebus = rebus;
		return this;
	}

	/**
	 * Retrieves the rebus answer
	 * @return The rebus answer
	 */
	public String getRebus() {
		return rebus;
	}
	
	/**
	 * Returns the solution as a single letter. If the single letter is in the range of 'A' through 'Z' inclusive,
	 * then the single letter is returned. Otherwise, if the rebus is non-null and has at least one character and
	 * the first character is in the range 'A' through 'Z' inclusive, then the first character of the rebus is returned.
	 * @return The single-letter representation of this solution, or 0 if there is no representation 
	 */
	public char getSingleLetter()
	{
		char single = 0;
	
		if ( letter >= 'A' && letter <= 'Z' )
		{
			single = letter;
		}
		
		else if ( rebus != null && rebus.length() > 0 && rebus.charAt( 0 ) >= 'A' && rebus.charAt( 0 ) <= 'Z' )
		{
			single = rebus.charAt( 0 );
		}
		
		return single;
	}
	
	/**
	 * Returns a printable representation of the single-letter and rebus answers in "[letter:rebus]" format.
	 */
	public String toString()
	{
		return new StringBuilder().append( "[" ).append( letter ).append( ":" ).append( rebus ).append( "]" ).toString();
	}

}
