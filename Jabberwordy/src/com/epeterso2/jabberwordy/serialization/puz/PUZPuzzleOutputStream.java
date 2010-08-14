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
import java.io.OutputStream;

import com.epeterso2.jabberwordy.serialization.PuzzleOutputStream;
import com.epeterso2.jabberwordy.util.Coordinate;

/**
 * Provides an {@link OutputStream} for deserializing a PUZ image into a {@link PUZPuzzle} object.
 * <p>
 * The <tt>strict</tt> property of this class controls how strictly the checksums in the PUZ file image are checked
 * prior to deserialization. If the <tt>strict</tt> property is <tt>true</tt> (the default value), then checksums for
 * the PUZ image are computed and compared against the checksums stored in the image; if the checksums do not match,
 * an {@link IOException} is thrown. If the <tt>strict</tt> property is <tt>false</tt>, the checksums are not validated
 * prior to deserialization.
 * <p>
 * Two individual puzzles produced by this class will be equal as long as they are logically equivalent. If the puzzles have
 * an equal grid, solution, player state, clues, and other descriptive properties, then the puzzles will be equal even if
 * some details of their file representations vary (such as the order of extra sections or the numbering of rebus entries).
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see PuzzleOutputStream
 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat">The PUZ Project</a>
 */
public class PUZPuzzleOutputStream extends PuzzleOutputStream<PUZPuzzle> {

	private boolean strict = true;

	/**
	 * Returns the status of checksum verification
	 * @return <tt>true</tt> if the PUZ file image checksums will be validated prior to deserialization, <tt>false</tt> otherwise
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * Constructs a new output stream
	 */
	public PUZPuzzleOutputStream()
	{
		super();
	}

	/**
	 * Constructs a new PUZ output stream with a strictness setting
	 * @param strict If <tt>true</tt>, the PUZ file image checksums will be validated prior to deserialization.
	 * If <tt>false</tt>, the checksums will not be validated prior to deserialization.
	 */
	public PUZPuzzleOutputStream( boolean strict )
	{
		this();
		this.strict = strict;
	}

	/**
	 * Constructs a new PUZ deserializing output stream using an input stream.
	 * The entire input stream will be read then written to this output stream.
	 * The input stream is not closed after it has been read.
	 * @param inputStream The inputStream containing a PUZ file image
	 */
	public PUZPuzzleOutputStream( InputStream inputStream ) throws IOException
	{
		super( inputStream );
	}
	
	/**
	 * Constructs a new PUZ deserializing output stream using an input stream with a strictness setting.
	 * The entire input stream will be read then written to this output stream.
	 * @param inputStream The inputStream containing a PUZ file image
	 * @param strict If <tt>true</tt>, the PUZ file image checksums will be validated prior to deserialization.
	 * If <tt>false</tt>, the checksums will not be validated prior to deserialization.
	 */
	public PUZPuzzleOutputStream( InputStream inputStream, boolean strict ) throws IOException
	{
		this( inputStream );
		this.strict = strict;
	}

	/**
	 * Deserializes the PUZ image written into this input stream into a {@link PUZPuzzle} object.
	 * If the <tt>strict</tt> property of this class is <tt>true</tt>,
	 * then checksum validation is performed. If the solution in the PUZ image is encrypted, the unlock code is found
	 * and the solution is decrypted automatically.
	 * @return The deserialized {@link PUZPuzzle} object
	 * @throws IOException An exception occurred during deserialization.
	 * This can happen if an inconsistency in the PUZ image is detected or if the <tt>strict</tt> property is <tt>true</tt>
	 * and the computed checksums of the PUZ image do not match the checksums stored in the PUZ image.
	 */
	@Override
	public PUZPuzzle toPuzzle() throws IOException
	{
		// Build a new context from the data in this output stream
		PUZContext context = new PUZContext( toByteArray() );

		// Validate the image if we're not lenient
		if ( ! isStrict() && ! context.isValidImage() )
		{
			throw new IOException();
		}
		
		// Build a new puzzle object
		PUZPuzzle puzzle = new PUZPuzzle( context.getWidth(), context.getHeight() );

		// Establish a fault barrier for deserialization. At this point, the image is assumed to be valid (non-corrupted).
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

		// Fault barrier - wrap all caught exceptions as an IOException. This could include a variety of
		// null pointer, array out of bounds, and other exceptions if the PUZ file image is corrupted.
		catch ( Exception e )
		{
			throw new IOException( e );
		}

		// Return the deserialized puzzle to the caller
		return puzzle;
	}

}
