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

import com.epeterso2.jabberwordy.serialization.Puzzle;

/**
 * Representation of a crossword puzzle in the JSON file format. This class can be serialized into a JSON image with the {@link JSONPuzzleInputStream} or produced from a
 * JSON image with the {@link JSONPuzzleOutputStream}.
 * <p>
 * The properties of this class and instances of classes it contains are named in such a way as to support easy serialization and
 * deserialization of JSON puzzles via third-party APIs. The names and data types of the properties of this class are equivalent to those in
 * the JSON format.    
 * <p>
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/JSON">Crossword puzzle data in JSON format</a>
 * @see <a href="http://www.json.org">Introducing JSON</a>
 */
public class JSONPuzzle implements Puzzle {
	
	private String title = null;
	
	private String author = null;
	
	private String editor = null;
	
	private String copyright = null;
	
	private String publisher = null;
	
	private String date = null;
	
	private GridSize size = null;
	
	private String[] grid = null;
	
	private int[] gridnums = null;
	
	private AcrossAndDownStrings clues = null;
	
	private AcrossAndDownStrings answers = null;
	
	private int[] circles = null;
	
	private String notepad = null;
	
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
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the author of the puzzle
	 * @return The author of the puzzle
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author of the puzzle
	 * @param author The author of the puzzle
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Returns the editor of the puzzle
	 * @return The editor of the puzzle
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * Sets the editor of the puzzle
	 * @param editor The editor of the puzzle
	 */
	public void setEditor(String editor) {
		this.editor = editor;
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
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * Returns the publisher of the puzzle
	 * @return The publisher of the puzzle
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Sets the publisher of the puzzle
	 * @param publisher The publisher of the puzzle
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Returns the date of publication of the puzzle
	 * @return The date of publication of the puzzle
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date of publication of the puzzle
	 * @param date The date of publication of the puzzle
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Returns the grid size of the puzzle
	 * @return The grid size of the puzzle
	 */
	public GridSize getSize() {
		return size;
	}

	/**
	 * Sets the grid size of the puzzle
	 * @param size The grid size of the puzzle
	 */
	public void setSize(GridSize size) {
		this.size = size;
	}

	/**
	 * Returns the contents of the puzzle grid
	 * For each entry, "." means there cell is a block, and non-"." means the solution to that cell is the given string.
	 * The strings may be rebus entries (have length > 1).
	 * @return The contents of the puzzle grid
	 */
	public String[] getGrid() {
		return grid;
	}

	/**
	 * Sets the contents of the puzzle grid
	 * For each entry, "." means there cell is a block, and non-"." means the solution to that cell is the given string.
	 * The strings may be rebus entries (have length > 1).
	 * @param grid The contents of the puzzle grid
	 */
	public void setGrid(String[] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the numbering of grid cells in the puzzle.
	 * For each entry, 0 means there is no number, nonzero means the number is the value at that entry.
	 * @return The numbering of grid cells in the puzzle
	 */
	public int[] getGridnums() {
		return gridnums;
	}

	/**
	 * Sets the numbering of grid cells in the puzzle.
	 * For each entry, 0 means there is no number, nonzero means the number is the value at that entry.
	 * @param gridnums The numbering of grid cells in the puzzle
	 */
	public void setGridnums(int[] gridnums) {
		this.gridnums = gridnums;
	}

	/**
	 * Returns the collection of clues in the puzzle
	 * @return The collection of clues in the puzzle
	 */
	public AcrossAndDownStrings getClues() {
		return clues;
	}

	/**
	 * Sets the collection of clues in the puzzle
	 * @param clues The collection of clues in the puzzle.
	 */
	public void setClues(AcrossAndDownStrings clues) {
		this.clues = clues;
	}

	/**
	 * Returns the collection of answers to the puzzle
	 * @return The collection of answers to the puzzle
	 */
	public AcrossAndDownStrings getAnswers() {
		return answers;
	}

	/**
	 * Sets the collection of answers to the puzzle
	 * @param answers The collection of answers to the puzzle
	 */
	public void setAnswers(AcrossAndDownStrings answers) {
		this.answers = answers;
	}

	/**
	 * Returns the array of circled cells in the puzzle.
	 * An entry of 0 means there is no circle, 1 means there is a circle.
	 * @return The array of circled cells in the puzzle
	 */
	public int[] getCircles() {
		return circles;
	}

	/**
	 * Sets the array of circled cells in the puzzle.
	 * An entry of 0 means there is no circle, 1 means there is a circle.
	 * @param circles The array of circled cells in the puzzle
	 */
	public void setCircles(int[] circles) {
		this.circles = circles;
	}

	/**
	 * Returns the contents of the notepad
	 * @return The contents of the notepad 
	 */
	public String getNotepad() {
		return notepad;
	}

	/**
	 * Sets the contents of the notepad
	 * @param notepad The contents of the notepad
	 */
	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

}
