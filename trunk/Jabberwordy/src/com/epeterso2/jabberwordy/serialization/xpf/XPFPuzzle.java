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
import java.util.HashSet;
import java.util.Set;

import com.epeterso2.jabberwordy.util.Coordinate;
import com.epeterso2.jabberwordy.util.CoordinateMap;
import com.epeterso2.jabberwordy.util.GridCoordinateSet;

/**
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzle {
	
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
	
	private Set<XPFClue> clues = new HashSet<XPFClue>();
	
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public CoordinateMap<XPFCellStyle> getCellStyles() {
		return cellStyles;
	}

	public CoordinateMap<XPFSolution> getSolutions() {
		return solutions;
	}

	public CoordinateMap<String> getPlayerState() {
		return playerState;
	}

	public void setClues(Set<XPFClue> clues) {
		this.clues = clues;
	}

	public Set<XPFClue> getClues() {
		return clues;
	}

	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	public String getNotepad() {
		return notepad;
	}
	
	public Set<Coordinate> getCoordinates()
	{
		return getCellStyles().keySet();
	}
	
}
