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

import java.io.IOException;
import java.io.InputStream;

import com.epeterso2.jabberwordy.serialization.PuzzleDeserializationException;
import com.epeterso2.jabberwordy.serialization.PuzzleOutputStream;
import com.epeterso2.jabberwordy.util.Coordinate;

/**
 * Provides an {@link InputStream} for deserializing a PUZ file image into a {@link PUZPuzzle} object.
 * Once all of the PUZ file image data have been written to this input stream, the {@link #toPuzzle()} method
 * may be invoked to return the deserialized puzzle.
 * If the {@code lenient} property of this class is set,
 * then checksums in the PUZ image are not validated prior to deserialization.
 * If the {@code lenient} property is not set, then
 * a {@link PuzzleDeserializationException} is thrown if any of the PUZ checksums does not match its computed value for the image.  
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public class PUZPuzzleOutputStream extends PuzzleOutputStream<PUZPuzzle> {

	private boolean lenient = false;

	/**
	 * Get the lenient flag value.
	 * @return True for lenient deserialization, false for strict deserialization
	 */
	public boolean isLenient() {
		return lenient;
	}

	/**
	 * Constructs a new PUZ deserializing output stream
	 */
	public PUZPuzzleOutputStream()
	{
		super();
	}

	/**
	 * Constructs a new PUZ deserializing output stream with a leniency setting
	 * @param lenient If true, the deserializer will ignore bad checksums in the PUZ image
	 */
	public PUZPuzzleOutputStream( boolean lenient )
	{
		this();
		this.lenient = lenient;
	}

	/**
	 * Constructs a new PUZ deserializing output stream using an input stream.
	 * The entire input stream will be read then written to this output stream.
	 * @param inputStream The inputStream containing a PUZ file image
	 */
	public PUZPuzzleOutputStream( InputStream inputStream ) throws IOException
	{
		super( inputStream );
	}
	
	/**
	 * Constructs a new PUZ deserializing output stream using an input stream with a leniency setting.
	 * The entire input stream will be read then written to this output stream.
	 * @param inputStream The inputStream containing a PUZ file image
	 */
	public PUZPuzzleOutputStream( InputStream inputStream, boolean lenient ) throws IOException
	{
		this( inputStream );
		this.lenient = lenient;
	}

	/**
	 * Deserializes the PUZ image written into this input stream into a {@link PUZPuzzle} object.
	 * If the {@code lenient} property of this class is true,
	 * then checksum validation is not performed. If the solution in the PUZ image is scrambled, the unlock code is found
	 * and the solution is descrambled automatically.
	 * @return The deserialized puzzle object
	 * @throws PuzzleDeserializationException An inconsistency in the PUZ image was detected that prevents successful deserialization.
	 */
	@Override
	public PUZPuzzle toPuzzle() throws PuzzleDeserializationException
	{
		// Build a new context from the data in this output stream
		PUZContext context = new PUZContext( toByteArray() );

		// Validate the image if we're not lenient
		if ( ! isLenient() && ! context.isValidImage() )
		{
			throw new PuzzleDeserializationException();
		}
		
		// Build a new puzzle object
		PUZPuzzle puzzle = new PUZPuzzle( context.getWidth(), context.getHeight() );

		try
		{
			// Find the unlock code
			puzzle.setUnlockCode( context.getUnlockCode() );
			
			// Unlock the puzzle if it's encrypted
			if ( puzzle.isSolutionEncrypted() )
			{
				context.unlockSolution( puzzle.getUnlockCode() );
			}

			// Build the basic strings
			puzzle.setAuthor( context.getAuthor() );
			puzzle.setTitle( context.getTitle() );
			puzzle.setCopyright( context.getCopyright() );
			puzzle.setNotes( context.getNotes() );
			
			// Mark as diagramless
			puzzle.setDiagramless( context.isDiagramless() );
			
			// Adjust the timer settings
			puzzle.setElapsedSeconds( context.getElapsedSeconds() );
			puzzle.setTimerRunning( context.isTimerRunning() );

			// Construct the grid
			for ( Coordinate coord : puzzle.getCoordinates() )
			{
				// Convert the PUZ image coordinates to PUZPuzzle object coordinates
				int imageCol = coord.getX() - 1;
				int imageRow = coord.getY() - 1;
				
				// Set the solution and player state for this location
				puzzle.getSolutions().put( coord, context.getSolution( imageCol, imageRow ) );
				puzzle.getPlayerState().put( coord, context.getPlayerState( imageCol, imageRow ) );

				// Build the cell style for this location
				PUZCellStyle cellStyle = puzzle.getCellStyles().get( coord );
				cellStyle.setPreviouslyMarkedIncorrect( context.isPreviouslyMarkedIncorrect( imageCol, imageRow ) );
				cellStyle.setCurrentlyMarkedIncorrect( context.isCurrentlyMarkedIncorrect( imageCol, imageRow ) );
				cellStyle.setRevealed( context.isRevealed( imageCol, imageRow ) );
				cellStyle.setCircled( context.isCircled( imageCol, imageRow ) );
				cellStyle.setBlock( context.isBlock( imageCol, imageRow ) );
			}

			// Number the grid
			puzzle.assignClueNumbers();
			
			// Populate the clues - requires the grid to be numbered first
			puzzle.assignClues( context.getClues() );
		}

		catch ( Exception e )
		{
			throw new PuzzleDeserializationException( e );
		}

		return puzzle;
	}

}
