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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.epeterso2.jabberwordy.util.StandardClueNumberCalculator;
import com.epeterso2.jabberwordy.util.StandardClueNumberResult;
import com.epeterso2.jabberwordy.util.Coordinate;
import com.epeterso2.jabberwordy.util.CoordinateMap;
import com.epeterso2.jabberwordy.util.GridCoordinateSet;

/**
 * Representation of a crossword puzzle in the PUZ file format. This class can be serialized into a PUZ image with the {@link PUZPuzzleInputStream} or produced from a
 * PUZ image with the {@link PUZPuzzleOutputStream}. 
 * <p>
 * The cells are given coordinates starting with the top left cell in the grid. Numbering of both rows and columns begins with 1, so the top-left cell always
 * has the coordinate (1, 1). Coordinates are in (column, row) pairs, so the top row of cells is numbered (1, 1), (2, 1), (3, 1), ... and the second
 * row is numbered (1, 2), (2, 2), (3, 2) ... . Note that this differs slightly from the indexing scheme used in the {@link PUZUtil} and {@link PUZContext} classes,
 * where row and column numbering beings with 0. The 1-based numbering scheme used in this class more closely aligns with conventional use of crossword grid
 * coordinates. 
 * <p>
 * The solution stored in this class is always in cleartext form - encrypted solutions are not stored, and it is the responsibility of the deserializer to
 * decrypt the PUZ image. The unlock code associated with the solution is stored in the unlockCode property.
 * <p>
 * All solutions and player state entries are strings. Rebus entries in the solution and player state are packed and unpacked automatically during [de]serialization
 * and are not treated in any special way, as they are in the PUZ image.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat">The PUZ Project</a>
 */
public class PUZPuzzle implements Cloneable {
	
	private int width = 0;
	
	private int height = 0;
	
	private Map<Integer, String> acrossClues = new TreeMap<Integer, String>();
	
	private Map<Integer, String> downClues = new TreeMap<Integer, String>();
	
	private boolean diagramless = false;
	
	private CoordinateMap<PUZSolution> solutions = new CoordinateMap<PUZSolution>();
	
	private CoordinateMap<String> playerState = new CoordinateMap<String>();
	
	private CoordinateMap<PUZCellStyle> cellStyles = new CoordinateMap<PUZCellStyle>();
	
	private boolean timerRunning = false;
	
	private int elapsedSeconds = 0;
	
	private String author = null;
	
	private String copyright = null;
	
	private String notes = null;
	
	private String title = null;
	
	private String unlockCode = null;
	
	/**
	 * Construct a new puzzle object with the given width and height dimensions. The width and height properties are initialized,
	 * and empty grids for the solution, player state, and cell styles are also constructed. The width and height may only be set
	 * in the call to the constructor.
	 * @param width The width of the grid in cells
	 * @param height The height of the grid in cells
	 */
	public PUZPuzzle( int width, int height )
	{
		this.width = width;
		this.height = height;
		
		for ( Coordinate coordinate : new GridCoordinateSet( width, height ) )
		{
			getSolutions().put( coordinate, new PUZSolution() );
			getPlayerState().put( coordinate, "" );
			getCellStyles().put( coordinate, new PUZCellStyle() );
		}
	}
	
	/**
	 * Returns the {@link Set} of coordinates for all cells in the grid. The iterator for the cells in this set will 
	 * walk through the cells in row-first order beginning at the top-left cell:
	 * <p>
	 * (1, 1), (2, 1), (3, 1), ... (<i>width</i>, 1), (1, 2), (2, 2), ... (<i>width</i>, <i>height</i>)
	 * @return The set of coordinates in the grid
	 */
	public Set<Coordinate> getCoordinates()
	{
		return cellStyles.keySet();
	}
	
	/**
	 * Returns the width of the grid
	 * @return The width of the grid
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the grid
	 * @return The height of the grid
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns true if the puzzle is diagramless, false if it is normal
	 * @return The diagramless status
	 */
	public boolean isDiagramless() {
		return diagramless;
	}

	/**
	 * Sets the diagramless status to the given value: true if the puzzle is diagramless, false if it is normal.
	 * @param diagramless The diagramless status
	 */
	public PUZPuzzle setDiagramless(boolean diagramless) {
		this.diagramless = diagramless;
		return this;
	}

	/**
	 * Returns the mapping of coordinates into solution strings  
	 * @return The solution {@link CoordinateMap}
	 */
	public CoordinateMap<PUZSolution> getSolutions() {
		return solutions;
	}

	/**
	 * Returns the mapping of coordinates into player state (answers)
	 * @return The player state {@link CoordinateMap}
	 */
	public CoordinateMap<String> getPlayerState() {
		return playerState;
	}

	/**
	 * Returns the mapping of coordinates into cell styles. A cell style is a description of how the cell should appear to the solver.
	 * See the {@link PUZCellStyle} class for details on all of the attributes in a cell style.
	 * @return The cell style {@link CoordinateMap}
	 */
	public CoordinateMap<PUZCellStyle> getCellStyles() {
		return cellStyles;
	}

	/**
	 * Returns the timer running status
	 * @return True if the timer is running, false if the timer is stopped
	 */
	public boolean isTimerRunning() {
		return timerRunning;
	}

	/**
	 * Sets the timer running status
	 * @param timerRunning True if the timer is running, false if the timer is stopped
	 */
	public PUZPuzzle setTimerRunning(boolean timerRunning) {
		this.timerRunning = timerRunning;
		return this;
	}

	/**
	 * Returns the number of seconds that have elapsed since the timer has run since its last reset
	 * @return The number of elapsed seconds on the timer
	 */
	public int getElapsedSeconds() {
		return elapsedSeconds;
	}

	/**
	 * Sets the number of seconds that have elapsed since the timer has run since its last reset
	 * @param elapsedSeconds The number of elapsed seconds on the timer
	 */
	public PUZPuzzle setElapsedSeconds(int elapsedSeconds) {
		this.elapsedSeconds = elapsedSeconds;
		return this;
	}

	/**
	 * Returns the solution encryption status. The solution is not actually encrypted in this object; the status
	 * represents the encryption status in the corresponding PUZ image
	 * @return True if the solution is encrypted, false if the solution is not encrypted.
	 */
	public boolean isSolutionEncrypted() {
		return getUnlockCode() != null;
	}

	/**
	 * Returns the name of the puzzle author
	 * @return The puzzle author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the name of the puzzle author
	 * @param author The puzzle author
	 */
	public PUZPuzzle setAuthor(String author) {
		this.author = author;
		return this;
	}

	/**
	 * Returns the copyright notice
	 * @return The copyright notice
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Sets the copyright notice
	 * @param copyright The copyright notice
	 */
	public PUZPuzzle setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	/**
	 * Returns the notepad contents. The notepad contains additional information about the puzzle or that is necessary to solve the puzzle.
	 * @return The contents of the notepad 
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notepad contents. The notepad contains additional information about the puzzle or that is necessary to solve the puzzle.
	 * @param notes The contents of the notepad 
	 */
	public PUZPuzzle setNotes(String notes) {
		this.notes = notes;
		return this;
	}

	/**
	 * Returns the title of the puzzle
	 * @return The title of the puzzle
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the puzzle
	 * @param title The title of the puzzle
	 */
	public PUZPuzzle setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Sets the clue map for the across clues. The clues are keyed by cell number, not coordinate.
	 * @param acrossClues The map of cell number into across clue string
	 */
	public PUZPuzzle setAcrossClues(Map<Integer, String> acrossClues) {
		this.acrossClues = acrossClues;
		return this;
	}

	/**
	 * Returns the clue map for the across clues. The clues are keyed by cell number, not coordinate.
	 * @return The map of cell number into across clue string
	 */
	public Map<Integer, String> getAcrossClues() {
		return acrossClues;
	}

	/**
	 * Sets the clue map for the down clues. The clues are keyed by cell number, not coordinate.
	 * @param downClues The map of cell number into down clue string
	 */
	public PUZPuzzle setDownClues(Map<Integer, String> downClues) {
		this.downClues = downClues;
		return this;
	}

	/**
	 * Returns the clue map for the down clues. The clues are keyed by cell number, not coordinate.
	 * @return The map of cell number into down clue string
	 */
	public Map<Integer, String> getDownClues() {
		return downClues;
	}

	/**
	 * Sets the unlock code used to encrypt/decrypt the solution in the PUZ image. This must be a 4-digit string where all of the digits
	 * are in the range of 1 to 9.
	 * @param unlockCode The unlock code
	 */
	public PUZPuzzle setUnlockCode(String unlockCode) {
		this.unlockCode = unlockCode;
		return this;
	}

	/**
	 * Returns the unlock code used to encrypt/decrypt the solution in the PUZ image.
	 * @return The unlock code
	 */
	public String getUnlockCode() {
		return unlockCode;
	}

	/**
	 * Returns the total number of cells in the puzzle grid, including blocks. The value returned is the product of the width and height.
	 * @return The number of cells in the grid
	 */
	public int getNumberOfCells()
	{
		return getWidth() * getHeight();
	}

	/**
	 * Assigns clue numbers to the cells in the grid. The numbers are assigned in order starting at 1 for the left-most cell on the highest row
	 * without blocks. A cell will be assigned a clue number if it is the first cell in either an across or down entry.
	 * This method assumes that the status of the blocks and non-block squares in the grid have already been set. See the {@link #getCellStyles()} method
	 * and the {@link PUZCellStyle} class for more details.
	 * <p>
	 * A cell may be the start of an across entry if it is in the leftmost column of the grid or if there is a block in the cell to the left of it.
	 * There must not be a block in the cell to the right of it or it must not be on the rightmost column of the grid.
	 * Unchecked cells in the middle of an across entry cannot be individual down entries. 
	 * <p>
	 * A cell may be the start of a down entry if it is on the top row of the grid or if there is a block in the cell above it.
	 * There must not be a block in the cell below of it or it must not be on the bottom row of the grid.
	 * Unchecked cells in the middle of a down entry cannot be individual across entries. 
	 */
	public void assignClueNumbers()
	{
		int cellNumber = 0;

		// Walk through all cells
		for ( Coordinate coord : getCoordinates() )
		{
			boolean numberable = false;

			// If the cell is not a block, see if it needs to be numbered
			if ( ! getCellStyles().get( coord ).isBlock() )
			{
				// Grab the other cells in the vicinity, including virtual ones that might be off the grid
				PUZCellStyle styleUp = getStyle( coord.getX(), coord.getY() - 1 );
				PUZCellStyle styleDown  = getStyle( coord.getX(), coord.getY() + 1 );
				PUZCellStyle styleLeft = getStyle( coord.getX() - 1, coord.getY() );
				PUZCellStyle styleRight = getStyle( coord.getX() + 1, coord.getY() );

				// Compute the number of enterable directions
				int playDirs = 0;
				playDirs += styleUp.isBlock()    ? 0 : 1;
				playDirs += styleDown.isBlock()  ? 0 : 1;
				playDirs += styleLeft.isBlock()  ? 0 : 1;
				playDirs += styleRight.isBlock() ? 0 : 1;
				
				// Can this be the start of an across entry?
				if ( styleLeft.isBlock() && ! styleRight.isBlock() && ( ! styleUp.isBlock() || ! styleDown.isBlock() ) )
				{
					numberable = true;
				}
				
				// Can this be the start of a down entry?
				else if ( styleUp.isBlock() && ! styleDown.isBlock() && ( ! styleLeft.isBlock() || ! styleRight.isBlock() ) )
				{
					numberable = true;
				}
				
				// Can this be the unchecked start of an entry?
				else if ( playDirs == 1 && ( ! styleDown.isBlock() || ! styleRight.isBlock() ) )
				{
					numberable = true;
				}
			}
			
			// Set the cell number
			getCellStyles().get( coord ).setNumber( numberable ? ++cellNumber : 0 );
		}
	}
	
	private static PUZCellStyle blockCellStyle = new PUZCellStyle();
	
	static
	{
		blockCellStyle.setBlock( true );
	}

	private PUZCellStyle getStyle( int x, int y )
	{
		return getCellStyles().containsKey( x, y ) ? getCellStyles().get( x, y ) : blockCellStyle;
	}

	/**
	 * Assigns clues to entries. This method assumes that the grid is already numbered appropriately - see the {@link #assignClueNumbers()} method.
	 * <p>
	 * The list of clues is assumed to be in order of the numbered entry cells. If both an across and a down clue share the same numbered entry,
	 * then the across clue comes before the down clue. This system is how the across and down clues are arranged in a PUZ image.
	 * @param clues The {@link List} of clue strings to assign 
	 */
	public void assignClues( List<String> clues )
	{
		CoordinateMap<StandardClueNumberResult> result = new StandardClueNumberCalculator( width, height, buildBlockMap() ).getNumberedGrid();
		
		for ( Coordinate coord : result.keySet() )
		{
			if ( result.get( coord ).isStartOfAcrossClue() )
			{
				getAcrossClues().put( result.get( coord ).getNumber(), clues.remove( 0 ) );
			}

			if ( result.get( coord ).isStartOfDownClue() )
			{
				getDownClues().put( result.get( coord ).getNumber(), clues.remove( 0 ) );
			}
		}
	}

	private CoordinateMap<Boolean> buildBlockMap()
	{
		CoordinateMap<Boolean> map = new CoordinateMap<Boolean>();
		
		for ( Coordinate coord : getCoordinates() )
		{
			map.put( coord, getCellStyles().get( coord ).isBlock() );
		}
		
		return map;
	}

	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode()
	{
		int hashCode = 0;

		hashCode ^= getWidth() << 24;
		hashCode ^= getHeight() << 16;
		hashCode ^= hashCode( getTitle() ) << 8;
		hashCode ^= hashCode( getAuthor() ) << 0;
		hashCode ^= hashCode( getCopyright() ) << 24;
		hashCode ^= hashCode( getNotes() ) << 16;
		hashCode ^= hashCode( getAcrossClues() ) << 8;
		hashCode ^= hashCode( getDownClues() ) << 0;
		hashCode ^= hashCode( getCoordinates() ) << 24;
		hashCode ^= hashCode( getCellStyles() ) << 16;
		hashCode ^= hashCode( getSolutions() ) << 8;
		hashCode ^= hashCode( getPlayerState() ) << 0;
		hashCode ^= hashCode( Boolean.valueOf( isDiagramless() ) ) << 24;
		hashCode ^= hashCode( Boolean.valueOf( isTimerRunning() ) ) << 16;
		hashCode ^= getElapsedSeconds() << 8;
		hashCode ^= hashCode( Boolean.valueOf( isSolutionEncrypted() ) ) << 0;
		hashCode ^= hashCode( getUnlockCode() );
		
		return hashCode;
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two puzzles are equal only if all of their properties
	 * are equal (grid size, cell styles, clues, solution, player state, timer status, and descriptive strigs).
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object instanceof PUZPuzzle )
		{
			PUZPuzzle that = (PUZPuzzle) object;
			
			return
				this.getWidth() == that.getWidth() &&
				this.getHeight() == that.getHeight() &&
				equal( this.getTitle(), that.getTitle() ) &&
				equal( this.getAuthor(), that.getAuthor() ) &&
				equal( this.getCopyright(), that.getCopyright() ) &&
				equal( this.getNotes(), that.getNotes() ) &&
				equal( this.getAcrossClues(), that.getAcrossClues() ) &&
				equal( this.getDownClues(), that.getDownClues() ) &&
				equal( this.getCoordinates(), that.getCoordinates() ) &&
				equal( this.getCellStyles(), that.getCellStyles() ) &&
				equal( this.getSolutions(), that.getSolutions() ) &&
				equal( this.getPlayerState(), that.getPlayerState() ) &&
				this.isDiagramless() == that.isDiagramless() &&
				this.isTimerRunning() == that.isTimerRunning() &&
				this.getElapsedSeconds() == that.getElapsedSeconds() &&
				this.isSolutionEncrypted() == that.isSolutionEncrypted() &&
				equal( this.getUnlockCode(), that.getUnlockCode() ) &&
				true;
		}
		
		else
		{
			return false;
		}
	}
	
	private boolean equal( Object one, Object two )
	{
		return one == null ? two == null : one.equals( two );
	}
	
	private int hashCode( Object object )
	{
		return object == null ? 1 : object.hashCode();
	}
	
	/**
	 * Creates and returns a copy of this object.
	 */
	@Override
	public PUZPuzzle clone()
	{
		PUZPuzzle that = new PUZPuzzle( getWidth(), getHeight() );
		
		that.setTitle( getTitle() );
		that.setAuthor( getAuthor() );
		that.setCopyright( getCopyright() );
		that.setNotes( getNotes() );
		that.getAcrossClues().putAll( getAcrossClues() );
		that.getDownClues().putAll( getDownClues() );
		that.getSolutions().putAll( getSolutions() );
		that.getPlayerState().putAll( getPlayerState() );
		that.getCellStyles().putAll( getCellStyles() );
		that.setDiagramless( isDiagramless() );
		that.setElapsedSeconds( getElapsedSeconds() );
		that.setTimerRunning( isTimerRunning() );
		that.setUnlockCode( getUnlockCode() );
		
		return that;
	}

	/**
	 * Returns a string representation of this object. The string is of the form "TITLE by AUTHOR [WIDTHxHEIGHT]".
	 */
	@Override
	public String toString()
	{
		return new StringBuilder()
		.append( getTitle() ).append( " by " ).append( getAuthor() )
		.append( " [" ).append( getWidth() ).append( "x" ).append( getHeight() ).append( "]" )
		.toString();
	}

}
