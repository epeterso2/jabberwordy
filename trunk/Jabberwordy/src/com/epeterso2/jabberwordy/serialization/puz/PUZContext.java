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

package com.epeterso2.jabberwordy.serialization.puz;

import java.util.List;

/**
 * A convenience context for reading elements of a PUZ image. An instance of this class is constructed with a PUZ byte-array image.
 * This image is read by the getter methods of this class using the methods of the {@link PUZUtil} class. 
 * <p>
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public class PUZContext {
	
	private byte[] image = null;
	
	/**
	 * Build a new PUZ context from a PUZ file image
	 * @param image The PUZ image
	 */
	public PUZContext( byte[] image )
	{
		this.image = image;
	}

	/**
	 * Getter for the PUZ image
	 * @return The PUZ image for this context
	 */
	public byte[] getImage()
	{
		return image;
	}
	
	/**
	 * Get the width of the puzzle grid in the PUZ image
	 * @return The width of the grid
	 */
	public int getWidth()
	{
		return PUZUtil.getWidth( image );
	}
	
	/**
	 * Get the height of the puzzle grid in the PUZ image
	 * @return The height of the grid
	 */
	public int getHeight()
	{
		return PUZUtil.getHeight( image );
	}
	
	/**
	 * Get the number of clues in the PUZ image
	 * @return The number of clues
	 */
	public int getNumberOfClues()
	{
		return PUZUtil.getNumberOfClues( image );
	}
	
	/**
	 * Get the type of puzzle in the PUZ image
	 * @return The value of the puzzle type field
	 */
	public int getPuzzleType()
	{
		return PUZUtil.getPuzzleType( image );
	}
	
	/**
	 * Get the type of solution in the PUZ image
	 * @return The value of the solution type field
	 */
	public int getSolutionType()
	{
		return PUZUtil.getSolutionType( image );
	}
	
	/**
	 * Get the solution for a specific cell in the PUZ image. Any rebus solutions for the cell will be ignored. 
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the solution cell
	 */
	public byte getSolutionCell( int col, int row )
	{
		return PUZUtil.getSolutionCell( image, col, row );
	}
	
	/**
	 * Get the player state for a specific cell in the PUZ image. Any rebus state for the cell will be ignored. 
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the player state cell
	 */
	public byte getPlayerStateCell( int col, int row )
	{
		return PUZUtil.getPlayerStateCell( image, col, row );
	}
	
	/**
	 * Get the title of the puzzle in the PUZ image
	 * @return The puzzle title
	 */
	public String getTitle()
	{
		return PUZUtil.getTitle( image );
	}
	
	/**
	 * Get the author of the puzzle in the PUZ image
	 * @return The author
	 */
	public String getAuthor()
	{
		return PUZUtil.getAuthor( image );
	}
	
	/**
	 * Get the copyright notice of the puzzle in the PUZ image
	 * @return The copyright notice
	 */
	public String getCopyright()
	{
		return PUZUtil.getCopyright( image );
	}
	
	/**
	 * Get a specific clue by clue number in the PUZ image
	 * @param clueNumber The number of the clue to obtain
	 * @return The clue string
	 */
	public String getClue( int clueNumber )
	{
		return PUZUtil.getClue( image, clueNumber );
	}
	
	/**
	 * Get a {@link List} of all clues in the PUZ image. The index of each clue matches the index used in the {@link #getClue(int)} method.
	 * @return The list of clues
	 */
	public List<String> getClues()
	{
		return PUZUtil.getClues( image );
	}
	
	/**
	 * Get the notes string from the PUZ image
	 * @return The notes string
	 */
	public String getNotes()
	{
		return PUZUtil.getNotes( image );
	}
	
	/**
	 * Determine if the PUZ image passes its validity tests or not
	 * @return True if valid, false if not
	 */
	public boolean isValidImage()
	{
		return PUZUtil.isValidImage( image );
	}

	/**
	 * Determine if the PUZ image is of that of a diagramless puzzle or not
	 * @return True if diagramless, false if normal
	 */
	public boolean isDiagramless()
	{
		return PUZUtil.isDiagramless( image );
	}

	/**
	 * Get the number of elapsed seconds on the timer in the PUZ image
	 * @return The number of elapsed seconds
	 */
	public int getElapsedSeconds()
	{
		return PUZUtil.getElapsedSeconds( image );
	}

	/**
	 * Determine if the timer in the PUZ image is running or not
	 * @return True if running, false if not
	 */
	public boolean isTimerRunning()
	{
		return PUZUtil.isTimerRunning( image );
	}

	/**
	 * Determine if the solution to the PUZ image is scrambled or not
	 * @return True if scrambled, false if not
	 */
	public boolean isSolutionEncrypted()
	{
		return PUZUtil.isSolutionEncrypted( image );
	}

	/**
	 * Get the solution for a specific cell in the PUZ image. Any rebus solutions for the cell take precedence over the single-character solution. 
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the solution cell
	 */
	public PUZSolution getSolution( int col, int row )
	{
		return PUZUtil.getSolution( image, col, row );
	}

	/**
	 * Get the player state for a specific cell in the PUZ image. Any rebus states for the cell take precedence over the single-character state. 
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the solution cell
	 */
	public String getPlayerState( int col, int row )
	{
		return PUZUtil.getPlayerState( image, col, row );
	}
	
	/**
	 * Determine if the cell at a specific location in the PUZ image is a block (black square) or not
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is a block, false otherwise
	 */
	public boolean isBlock( int col, int row )
	{
		return PUZUtil.isBlock( image, col, row );
	}
	
	/**
	 * Determine if the cell at a specific location in the PUZ image is flagged as previously marked incorrect or not
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is flagged as previously marked incorrect, false otherwise
	 */
	public boolean isPreviouslyMarkedIncorrect( int col, int row )
	{
		return PUZUtil.isPreviouslyMarkedIncorrect( image, col, row );
	}

	/**
	 * Determine if the cell at a specific location in the PUZ image is flagged as currently marked incorrect or not
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is flagged as currently marked incorrect, false otherwise
	 */
	public boolean isCurrentlyMarkedIncorrect( int col, int row )
	{
		return PUZUtil.isCurrentlyMarkedIncorrect( image, col, row );
	}

	/**
	 * Determine if the cell at a specific location in the PUZ image is flagged as revealed or not
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is flagged as revealed, false otherwise
	 */
	public boolean isRevealed( int col, int row )
	{
		return PUZUtil.isRevealed( image, col, row );
	}

	/**
	 * Determine if the cell at a specific location in the PUZ image is circled or not
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is circled, false otherwise
	 */
	public boolean isCircled( int col, int row )
	{
		return PUZUtil.isCircled( image, col, row );
	}

	/**
	 * Find the unlock code for the scrambled solution in the PUZ image. If the puzzle is not scrambled, return null.
	 * See {@link PUZEncryption} for details on the encryption scheme.
	 * @return The unlock code
	 */
	public String getUnlockCode()
	{
		return PUZUtil.getUnlockCode( image );
	}
	
	/**
	 * Unlock (unscramble) the solution in the PUZ image using the given unlock code.
	 * See {@link PUZEncryption} for details on the encryption scheme.
	 * @param string The unlock code to use.
	 */
	public void unlockSolution( String string )
	{
		PUZUtil.unlockSolution( image, string );
	}
	
}
