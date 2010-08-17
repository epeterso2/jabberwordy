package com.epeterso2.jabberwordy.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.epeterso2.jabberwordy.util.Coordinate;
import com.epeterso2.jabberwordy.util.CoordinateMap;
import com.epeterso2.jabberwordy.util.GridCoordinateSet;

public class PuzzleModel {
	
	private String title = null;
	
	private String author = null;
	
	private String editor = null;
	
	private String publisher = null;
	
	private Date date = null;
	
	private String copyright = null;
	
	private String notes = null;
	
	private int width = 0;
	
	private int height = 0;
	
	private CoordinateMap<PuzzleModelCellStyle> cellStyles = new CoordinateMap<PuzzleModelCellStyle>();
	
	private CoordinateMap<PuzzleModelSolution> solutions = new CoordinateMap<PuzzleModelSolution>();

	private CoordinateMap<PuzzleModelClue> clues = new CoordinateMap<PuzzleModelClue>();
	
	private Set<PuzzleModelListener> listeners = new HashSet<PuzzleModelListener>();
	
	public PuzzleModel( int width, int height )
	{
		this.width = width;
		this.height = height;
		
		for ( Coordinate coord : new GridCoordinateSet( width, height ) )
		{
			coord.getX();
		}
	}
	
	public void addPuzzleModelListener( PuzzleModelListener listener )
	{
		listeners.add( listener );
	}
	
	public void removePuzzleModelListener( PuzzleModelListener listener )
	{
		listeners.remove( listener );
	}
	
	public void updateListeners( PuzzleModelEvent event )
	{
		for ( PuzzleModelListener listener : listeners )
		{
			listener.puzzleChanged( event );
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
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

	public void setCellStyles(CoordinateMap<PuzzleModelCellStyle> cellStyles) {
		this.cellStyles = cellStyles;
	}

	public CoordinateMap<PuzzleModelCellStyle> getCellStyles() {
		return cellStyles;
	}

	public void setSolutions(CoordinateMap<PuzzleModelSolution> solutions) {
		this.solutions = solutions;
	}

	public CoordinateMap<PuzzleModelSolution> getSolutions() {
		return solutions;
	}

	public void setClues(CoordinateMap<PuzzleModelClue> clues) {
		this.clues = clues;
	}

	public CoordinateMap<PuzzleModelClue> getClues() {
		return clues;
	}

}
