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

package com.epeterso2.jabberwordy.serialization.xpf;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.epeterso2.jabberwordy.serialization.Puzzle;
import com.epeterso2.jabberwordy.util.Coordinate;
import com.epeterso2.jabberwordy.util.CoordinateMap;
import com.epeterso2.jabberwordy.util.GridCoordinateSet;

/**
 * Represents the deserialized form of a puzzle in XPF format.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzle implements Puzzle {
	
	private String type = null;
	
	private String title = null;
	
	private String author = null;
	
	private String editor = null;
	
	private String copyright = null;
	
	private String publisher = null;
	
	private String notepad = null;
	
	private Date date = null;
	
	private int rows = 0;
	
	private int cols = 0;
	
	private CoordinateMap<XPFCellStyle> cellStyles = new CoordinateMap<XPFCellStyle>();
	
	private CoordinateMap<XPFSolution> solutions = new CoordinateMap<XPFSolution>();
	
	private CoordinateMap<String> playerState = new CoordinateMap<String>();
	
	private Set<XPFClue> clues = new TreeSet<XPFClue>();
	
	/**
	 * Constructs a new XPF puzzle with a grid of the given dimensions in columns and rows.
	 * @param cols The number of columns in the grid
	 * @param rows The number of rows in the grid
	 */
	public XPFPuzzle( int cols, int rows )
	{
		this.cols = cols;
		this.rows = rows;
		
		for ( Coordinate coord : new GridCoordinateSet( cols, rows ) )
		{
			cellStyles.put( coord, new XPFCellStyle() );
			solutions.put( coord, new XPFSolution() );
			playerState.put( coord, "" );
		}
	}
	
	/**
	 * Returns the type of XPF puzzle. Values can include "normal", "diagramless", etc.
	 * @return The type of puzzle
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of XPF puzzle. Values can include "normal", "diagramless", etc.
	 * @param type The type of puzzle
	 * @return This object
	 */
	public XPFPuzzle setType(String type) {
		this.type = type;
		return this;
	}

	/**
	 * Returns the title of the puzzle.
	 * @return The title of the puzzle
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the puzzle.
	 * @param title The title of the puzzle
	 * @return This object
	 */
	public XPFPuzzle setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Returns the name of the author of the puzzle.
	 * @return The author of the puzzle
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the name of the author of the puzzle
	 * @param author The author of the puzzle
	 * @return This object
	 */
	public XPFPuzzle setAuthor(String author) {
		this.author = author;
		return this;
	}

	/**
	 * Returns the name of the editor of the puzzle
	 * @return The editor of the puzzle
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * Sets the name of the editor of the puzzle
	 * @param editor The editor of the puzzle
	 * @return This object
	 */
	public XPFPuzzle setEditor(String editor) {
		this.editor = editor;
		return this;
	}

	/**
	 * Returns the copyright notice of the puzzle
	 * @return The copyright notice of the puzzle
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Sets the copyright notice of the puzzle
	 * @param copyright The copyright notice of the puzzle
	 * @return This object
	 */
	public XPFPuzzle setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	/**
	 * Returns the name of the publisher of the puzzle
	 * @return The publisher of the puzzle
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Sets the name of the publisher of the puzzle
	 * @param publisher The publisher of the puzzle
	 * @return This object
	 */
	public XPFPuzzle setPublisher(String publisher) {
		this.publisher = publisher;
		return this;
	}

	/**
	 * Returns the date of publication of this puzzle
	 * @return The date of publication
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date of publication of this puzzle
	 * @param date The date of publication
	 * @return This object
	 */
	public XPFPuzzle setDate(Date date) {
		this.date = date;
		return this;
	}

	/**
	 * Returns the number of rows in the grid
	 * @return The number of rows in the grid
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Returns the number of columns in the grid
	 * @return The number of columns in the grid
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Returns the mapping of {@link Coordinate} objects into {@link XPFCellStyle} objects.
	 * @return The cell style mapping
	 */
	public CoordinateMap<XPFCellStyle> getCellStyles() {
		return cellStyles;
	}

	/**
	 * Returns the mapping of {@link Coordinate} objects into {@link XPFSolution} objects
	 * @return The solution mapping
	 */
	public CoordinateMap<XPFSolution> getSolutions() {
		return solutions;
	}

	/**
	 * Returns the mapping of {@link Coordinate} objects into the player state (answers entered by the solver)
	 * @return The coordinate mapping of the player state
	 */
	public CoordinateMap<String> getPlayerState() {
		return playerState;
	}

	/**
	 * Sets the list of clues for this puzzle  
	 * @param clues The list of clues
	 * @return This object
	 */
	public XPFPuzzle setClues(Set<XPFClue> clues) {
		this.clues = clues;
		return this;
	}

	/**
	 * Returns the list of clues for this puzzle
	 * @return The list of clues
	 */
	public Set<XPFClue> getClues() {
		return clues;
	}

	/**
	 * Sets the contents of the notepad for this puzzle
	 * @param notepad The contents of the notepad
	 * @return This object
	 */
	public XPFPuzzle setNotepad(String notepad) {
		this.notepad = notepad;
		return this;
	}

	/**
	 * Returns the contents of the notepad for this puzzle
	 * @return The contents of the notepad
	 */
	public String getNotepad() {
		return notepad;
	}
	
	/**
	 * Returns the list of coordinates of all cells in this puzzle's grid
	 * @return The list of coordinates
	 */
	public Set<Coordinate> getCoordinates()
	{
		return getCellStyles().keySet();
	}
	
	/**
	 * Returns a simple string representation of the puzzle (author, title, grid size)
	 */
	@Override
	public String toString()
	{
		return new StringBuilder().append( getTitle() ).append( " by " ).append( getAuthor() )
			.append( " [" ).append( getCols() ).append( "x" ).append( getRows() ).append( "]" ).toString();
	}
	
	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode()
	{
		int hash = 1;
		
		hash = hash + 37 * hashOrOne( getAuthor() );
		hash = hash + 37 * hashOrOne( getCellStyles() );
		hash = hash + 37 * hashOrOne( getClues() );
		hash = hash + 37 * getCols();
		hash = hash + 37 * hashOrOne( getCopyright() );
		hash = hash + 37 * hashOrOne( getDate() );
		hash = hash + 37 * hashOrOne( getEditor() );
		hash = hash + 37 * hashOrOne( getNotepad() );
		hash = hash + 37 * hashOrOne( getPlayerState() );
		hash = hash + 37 * hashOrOne( getPublisher() );
		hash = hash + 37 * getRows();
		hash = hash + 37 * hashOrOne( getSolutions() );
		hash = hash + 37 * hashOrOne( getTitle() );
		hash = hash + 37 * hashOrOne( getType() );
		
		return hash;
	}
	
	private int hashOrOne( Object object )
	{
		return object == null ? 1 : object.hashCode();
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object == null || ! ( object instanceof XPFPuzzle ) )
		{
			return false;
		}
		
		else
		{
			XPFPuzzle that = (XPFPuzzle) object;
			
			return
				equal( this.getAuthor(), that.getAuthor() ) &&
				equal( this.getCellStyles(), that.getCellStyles() ) &&
				equal( this.getClues(), that.getClues() ) &&
				equal( this.getCols(), that.getCols() ) &&
				equal( this.getCopyright(), that.getCopyright() ) &&
				equal( this.getDate(), that.getDate() ) &&
				equal( this.getEditor(), that.getEditor() ) &&
				equal( this.getNotepad(), that.getNotepad() ) &&
				equal( this.getPlayerState(), that.getPlayerState() ) &&
				equal( this.getPublisher(), that.getPublisher() ) &&
				equal( this.getRows(), that.getRows() ) &&
				equal( this.getSolutions(), that.getSolutions() ) &&
				equal( this.getTitle(), that.getTitle() ) &&
				equal( this.getType(), that.getType() );
		}
		
	}
	
	private boolean equal( Object one, Object two )
	{
		return one == null ? two == null : one.equals( two );
	}
	
}
