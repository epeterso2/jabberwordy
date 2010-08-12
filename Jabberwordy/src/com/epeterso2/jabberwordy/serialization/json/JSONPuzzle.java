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

public class JSONPuzzle {
	
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
	
	private String jnotes = null;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public GridSize getSize() {
		return size;
	}

	public void setSize(GridSize size) {
		this.size = size;
	}

	public String[] getGrid() {
		return grid;
	}

	public void setGrid(String[] grid) {
		this.grid = grid;
	}

	public int[] getGridnums() {
		return gridnums;
	}

	public void setGridnums(int[] gridnums) {
		this.gridnums = gridnums;
	}

	public AcrossAndDownStrings getClues() {
		return clues;
	}

	public void setClues(AcrossAndDownStrings clues) {
		this.clues = clues;
	}

	public AcrossAndDownStrings getAnswers() {
		return answers;
	}

	public void setAnswers(AcrossAndDownStrings answers) {
		this.answers = answers;
	}

	public int[] getCircles() {
		return circles;
	}

	public void setCircles(int[] circles) {
		this.circles = circles;
	}

	public String getNotepad() {
		return notepad;
	}

	public void setNotepad(String notepad) {
		this.notepad = notepad;
	}

	public String getJnotes() {
		return jnotes;
	}

	public void setJnotes(String jnotes) {
		this.jnotes = jnotes;
	}
}
