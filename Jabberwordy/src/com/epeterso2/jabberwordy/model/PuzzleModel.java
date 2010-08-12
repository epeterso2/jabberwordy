package com.epeterso2.jabberwordy.model;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	
	private File file = null;
	
	private long elapsedTime = 0;
	
	private boolean timerRunning = false;
	
	private Map<Integer, String> acrossClues = new TreeMap<Integer, String>();
	
	private Map<Integer, String> downClues = new TreeMap<Integer, String>();
	
	private CoordinateMap<String> solution = new CoordinateMap<String>();
	
	private CoordinateMap<String> answer = new CoordinateMap<String>();
	
	private CoordinateMap<CellAppearance> cellAppearances = new CoordinateMap<CellAppearance>();
	
	public CoordinateMap<CellAppearance> getCellAppearances() {
		return cellAppearances;
	}

	public void setCellAppearances(CoordinateMap<CellAppearance> cellAppearances) {
		this.cellAppearances = cellAppearances;
	}

	private boolean diagramless = false;
	
	private Set<PuzzleModelListener> listeners = new HashSet<PuzzleModelListener>();
	
	public PuzzleModel( int width, int height )
	{
		this.width = width;
		this.height = height;
		
		for ( Coordinate coord : new GridCoordinateSet( width, height ) )
		{
			solution.put( coord, "" );
			answer.put( coord, "" );
			cellAppearances.put( coord, new CellAppearance() );
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

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public void setAcrossClues(Map<Integer, String> acrossClues) {
		this.acrossClues = acrossClues;
	}

	public Map<Integer, String> getAcrossClues() {
		return acrossClues;
	}

	public void setDownClues(Map<Integer, String> downClues) {
		this.downClues = downClues;
	}

	public Map<Integer, String> getDownClues() {
		return downClues;
	}

	public void setSolution(CoordinateMap<String> solution) {
		this.solution = solution;
	}

	public CoordinateMap<String> getSolution() {
		return solution;
	}

	public void setAnswer(CoordinateMap<String> answer) {
		this.answer = answer;
	}

	public CoordinateMap<String> getAnswer() {
		return answer;
	}

	public void setTimerRunning(boolean timerRunning) {
		this.timerRunning = timerRunning;
	}

	public boolean isTimerRunning() {
		return timerRunning;
	}

	public void setDiagramless(boolean diagramless) {
		this.diagramless = diagramless;
	}

	public boolean isDiagramless() {
		return diagramless;
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

}
