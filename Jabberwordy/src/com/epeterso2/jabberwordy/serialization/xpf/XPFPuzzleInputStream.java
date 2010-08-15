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

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.epeterso2.jabberwordy.serialization.PuzzleInputStream;
import com.epeterso2.jabberwordy.util.Coordinate;

/**
 * Serializes an {@link XPFPuzzleCollection} object into an XPF image as an {@link InputStream}. This serializer generates puzzles
 * that conform to version 1.0 of the XPF specification. The order in which the puzzles appear in the serialized XPF image is identical
 * to the order in which they appear in the {@link XPFPuzzleCollection}.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzleInputStream extends PuzzleInputStream<XPFPuzzleCollection> {

	private String encoding = "UTF-8";

	private boolean compact = false;

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat( "M/d/yyyy" );

	/**
	 * Sets the compact mode for this serializer.
	 * If set to <tt>true</tt>, the output will be optimized to minimize the size of the serialized data.
	 * If set to <tt>false</tt> (default), the output will be formatted for human-readability. 
	 * @param compact The compact mode setting
	 * @return This input stream
	 */
	public XPFPuzzleInputStream setCompact(boolean compact) {
		this.compact = compact;
		return this;
	}

	/**
	 * Returns the compact mode setting for this serializer.
	 * If set to <tt>true</tt>, the output will be optimized to minimize the size of the serialized data.
	 * If set to <tt>false</tt> (default), the output will be formatted for human-readability. 
	 * @return The compact mode setting
	 */
	public boolean isCompact() {
		return compact;
	}

	/**
	 * Sets the encoding method used in the XML output. The default value is "UTF-8".
	 * @param encoding The desired encoding method
	 * @return This input stream
	 */
	public XPFPuzzleInputStream setEncoding( String encoding )
	{
		this.encoding = encoding;
		return this;
	}

	/**
	 * Constructs a new input stream for serializing an {@link XPFPuzzleCollection}.
	 * @param collection The puzzle collection to serialize
	 */
	public XPFPuzzleInputStream( XPFPuzzleCollection collection )
	{
		super( collection );
	}

	/**
	 * Constructs a new input stream for serializing a single puzzle.
	 * The input stream will be initialized with a new
	 * {@link XPFPuzzleCollection} will be created that contains only the given puzzle.
	 * @param puzzle The puzzle to serialize
	 */
	public XPFPuzzleInputStream( XPFPuzzle puzzle )
	{
		super( new XPFPuzzleCollection( puzzle ) );
	}

	/**
	 * Serializes the {@link XPFPuzzleCollection} associated with this class into an XPF image.
	 */
	@Override
	public byte[] toByteArray() throws IOException
	{
		// Make sure we're ready to serialize
		validate();

		// Write the output
		return new XMLOutputter( ( isCompact() ? Format.getCompactFormat() : Format.getPrettyFormat() )
				.setEncoding( encoding ) ).outputString( buildDocument( getPuzzle() ) ).getBytes();
	}

	private Document buildDocument( XPFPuzzleCollection puzzleCollection )
	{
		Document document = new Document();

		Element rootElement = new Element( "Puzzles" );
		rootElement.setAttribute( "Version", "1.0" );

		for ( XPFPuzzle puzzle : puzzleCollection )
		{
			rootElement.addContent( buildPuzzle( puzzle ) );
		}

		document.setRootElement( rootElement );

		return document;
	}

	private Element buildPuzzle( XPFPuzzle puzzle )
	{
		Element puzzleElement = new Element( "Puzzle" );

		addElementIfNonNull( puzzleElement, buildSimpleElement( "Type", puzzle.getType() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Title", puzzle.getTitle() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Author", puzzle.getAuthor() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Editor", puzzle.getEditor() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Copyright", puzzle.getCopyright() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Publisher", puzzle.getPublisher() ) );
		addElementIfNonNull( puzzleElement, buildSimpleElement( "Date", puzzle.getDate() == null ? null : dateFormatter.format( puzzle.getDate() ) ) );
		addElementIfNonNull( puzzleElement, buildSizeElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildGridElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildCirclesElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildRebusEntriesElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildShadesElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildCluesElement( puzzle ) );
		addElementIfNonNull( puzzleElement, buildNotepadElement( puzzle ) );

		return puzzleElement;
	}

	private Element buildNotepadElement( XPFPuzzle puzzle )
	{
		Element element = null;

		if ( puzzle.getNotepad() != null )
		{
			element = new Element( "Notepad" );
			element.addContent( new CDATA( puzzle.getNotepad() ) );
		}

		return element;
	}

	private List<XPFClue> buildLocatedClueList( XPFPuzzle puzzle )
	{
		if ( allCluesLocated( puzzle ) )
		{
			return puzzle.getClues();
		}

		else
		{
			List<XPFClue> clues = locateClues( puzzle );

			for ( int i = 0; i < clues.size(); ++i )
			{
				XPFClue loc = clues.get( i );
				XPFClue puz = puzzle.getClues().get( i );

				loc.setAnswer( puz.getAnswer() );
				loc.setText( puz.getText() );
			}

			return clues;
		}
	}

	private Element buildCluesElement( XPFPuzzle puzzle )
	{
		Element cluesElement = null;

		if ( puzzle.getClues() != null )
		{
			List<XPFClue> clues = buildLocatedClueList( puzzle );

			for ( XPFClue clue : clues )
			{
				if ( cluesElement == null )
				{
					cluesElement = new Element( "Clues" );
				}

				Element clueElement = new Element( "Clue" );
				clueElement.setAttribute( "Row", Integer.valueOf( clue.getCoordinate().getY() ).toString() );
				clueElement.setAttribute( "Col", Integer.valueOf( clue.getCoordinate().getX() ).toString() );
				clueElement.setAttribute( "Num", clue.getNumber() );
				clueElement.setAttribute( "Dir", clue.getDirection() );
				clueElement.setAttribute( "Ans", clue.getAnswer() );
				clueElement.setText( clue.getText() );

				cluesElement.addContent( clueElement );
			}
		}

		return cluesElement;
	}

	private static XPFCellStyle blockCellStyle = new XPFCellStyle();

	static
	{
		blockCellStyle.setBlock( true );
	}

	private static XPFCellStyle getStyle( XPFPuzzle puzzle, int x, int y )
	{
		return puzzle.getCellStyles().containsKey( x, y ) ? puzzle.getCellStyles().get( x, y ) : blockCellStyle;
	}

	private static boolean isAcrossEntry( XPFPuzzle puzzle, Coordinate coord )
	{
		boolean acrossEntry = false;

		// If the cell is not a block, see if it needs to be numbered
		if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
		{
			// Grab the other cells in the vicinity, including virtual ones that might be off the grid
			XPFCellStyle styleUp = getStyle( puzzle, coord.getX(), coord.getY() - 1 );
			XPFCellStyle styleDown  = getStyle( puzzle, coord.getX(), coord.getY() + 1 );
			XPFCellStyle styleLeft = getStyle( puzzle, coord.getX() - 1, coord.getY() );
			XPFCellStyle styleRight = getStyle( puzzle, coord.getX() + 1, coord.getY() );

			// Compute the number of enterable directions
			int playDirs = 0;
			playDirs += styleUp.isBlock()    ? 0 : 1;
			playDirs += styleDown.isBlock()  ? 0 : 1;
			playDirs += styleLeft.isBlock()  ? 0 : 1;
			playDirs += styleRight.isBlock() ? 0 : 1;

			// Can this be the start of an across entry?
			if ( styleLeft.isBlock() && ! styleRight.isBlock() && ( ! styleUp.isBlock() || ! styleDown.isBlock() ) )
			{
				acrossEntry = true;
			}

			// Can this be the unchecked start of an across entry?
			if ( playDirs == 1 && ! styleRight.isBlock() )
			{
				acrossEntry = true;
			}
		}

		return acrossEntry;
	}

	private static boolean isDownEntry( XPFPuzzle puzzle, Coordinate coord )
	{
		boolean downEntry = false;

		// If the cell is not a block, see if it needs to be numbered
		if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
		{
			// Grab the other cells in the vicinity, including virtual ones that might be off the grid
			XPFCellStyle styleUp = getStyle( puzzle, coord.getX(), coord.getY() - 1 );
			XPFCellStyle styleDown  = getStyle( puzzle, coord.getX(), coord.getY() + 1 );
			XPFCellStyle styleLeft = getStyle( puzzle, coord.getX() - 1, coord.getY() );
			XPFCellStyle styleRight = getStyle( puzzle, coord.getX() + 1, coord.getY() );

			// Compute the number of enterable directions
			int playDirs = 0;
			playDirs += styleUp.isBlock()    ? 0 : 1;
			playDirs += styleDown.isBlock()  ? 0 : 1;
			playDirs += styleLeft.isBlock()  ? 0 : 1;
			playDirs += styleRight.isBlock() ? 0 : 1;

			// Can this be the start of a down entry?
			if ( styleUp.isBlock() && ! styleDown.isBlock() && ( ! styleLeft.isBlock() || ! styleRight.isBlock() ) )
			{
				downEntry = true;
			}

			// Can this be the unchecked start of a down entry?
			if ( playDirs == 1 && ! styleDown.isBlock() )
			{
				downEntry = true;
			}
		}

		return downEntry;
	}

	private static List<XPFClue> locateClues( XPFPuzzle puzzle )
	{
		List<XPFClue> locatedClues = new ArrayList<XPFClue>();
		int clueNumber = 0;

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			boolean acrossEntry = isAcrossEntry( puzzle, coord );
			boolean downEntry = isDownEntry( puzzle, coord );

			if ( acrossEntry || downEntry )
			{
				clueNumber++;

				if ( acrossEntry )
				{
					XPFClue clue = new XPFClue();
					clue.setCoordinate( coord );
					clue.setNumber( Integer.valueOf( clueNumber ).toString() );
					clue.setDirection( "Across" );

					locatedClues.add( clue );
				}

				if ( downEntry )
				{
					XPFClue clue = new XPFClue();
					clue.setCoordinate( coord );
					clue.setNumber( Integer.valueOf( clueNumber ).toString() );
					clue.setDirection( "Down" );

					locatedClues.add( clue );
				}
			}
		}

		return locatedClues;
	}

	private static boolean allCluesLocated( XPFPuzzle puzzle )
	{
		for ( XPFClue clue : puzzle.getClues() )
		{
			if ( ! clue.isLocated() )
			{
				return false;
			}
		}

		return true;
	}

	private static boolean allCluesNotLocated( XPFPuzzle puzzle )
	{
		for ( XPFClue clue : puzzle.getClues() )
		{
			if ( clue.isLocated() )
			{
				return false;
			}
		}

		return true;
	}

	private Element buildShadesElement( XPFPuzzle puzzle )
	{
		Element shadesElement = null;

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			String shade = puzzle.getCellStyles().get( coord ).getShade();
			
			if ( shade != null && shade.length() > 0 )
			{
				if ( shadesElement == null )
				{
					shadesElement = new Element( "Shades" );
				}

				Element shadeElement = new Element( "Shade" );
				shadeElement.setAttribute( "Row", Integer.valueOf( coord.getY() ).toString() );
				shadeElement.setAttribute( "Col", Integer.valueOf( coord.getX() ).toString() );
				shadeElement.setText( shade.toLowerCase() );

				shadesElement.addContent( shadeElement );
			}
		}

		return shadesElement;
	}

	private Element buildRebusEntriesElement( XPFPuzzle puzzle )
	{
		Element rebusEntriesElement = null;

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			XPFSolution solution = puzzle.getSolutions().get( coord );

			if ( solution.getRebus() != null && solution.getRebus().length() > 0 )
			{
				if ( rebusEntriesElement == null )
				{
					rebusEntriesElement = new Element( "RebusEntries" );
				}

				Element rebusEntry = new Element( "Rebus" );
				rebusEntry.setAttribute( "Row", Integer.valueOf( coord.getY() ).toString() );
				rebusEntry.setAttribute( "Col", Integer.valueOf( coord.getX() ).toString() );
				rebusEntry.setAttribute( "Short", Character.valueOf( solution.getLetter() ).toString() );
				rebusEntry.setText( solution.getRebus() );

				rebusEntriesElement.addContent( rebusEntry );
			}
		}

		return rebusEntriesElement;
	}

	private Element buildCirclesElement( XPFPuzzle puzzle )
	{
		Element circlesElement = null;

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			if ( puzzle.getCellStyles().get( coord ).isCircled() )
			{
				if ( circlesElement == null )
				{
					circlesElement = new Element( "Circles" );
				}

				Element circleElement = new Element( "Circle" );
				circleElement.setAttribute( "Row", Integer.valueOf( coord.getY() ).toString() );
				circleElement.setAttribute( "Col", Integer.valueOf( coord.getX() ).toString() );

				circlesElement.addContent( circleElement );
			}
		}

		return circlesElement;
	}

	private Element buildSimpleElement( String name, String text )
	{
		Element element = null;

		if ( text != null )
		{
			element = new Element( name );
			element.setText( text );
		}

		return element;
	}

	private Element buildGridElement( XPFPuzzle puzzle )
	{
		Element gridElement = new Element( "Grid" );

		for ( int row = 1; row <= puzzle.getRows(); ++row )
		{
			gridElement.addContent( buildRowElement( puzzle, row ) );
		}

		return gridElement;
	}

	private Element buildRowElement( XPFPuzzle puzzle, int row )
	{
		Element rowElement = new Element( "Row" );
		StringBuilder builder = new StringBuilder();

		for ( int col = 1; col <= puzzle.getCols(); ++col )
		{
			if ( puzzle.getCellStyles().get( col, row ).isBlock() )
			{
				builder.append( "." );
			}

			else if ( puzzle.getCellStyles().get( col, row ).isBorderless() )
			{
				builder.append( "~" );
			}

			else
			{
				builder.append( puzzle.getSolutions().get( col, row ).getLetter() );
			}
		}

		rowElement.setText( builder.toString() );

		return rowElement;
	}

	private Element buildSizeElement( XPFPuzzle puzzle )
	{
		Element sizeElement = new Element( "Size" );

		Element rowsElement = new Element( "Rows" );
		rowsElement.setText( Integer.valueOf( puzzle.getRows() ).toString() );
		sizeElement.addContent( rowsElement );

		Element colsElement = new Element( "Cols" );
		colsElement.setText( Integer.valueOf( puzzle.getCols() ).toString() );
		sizeElement.addContent( colsElement );

		return sizeElement;
	}

	private void addElementIfNonNull( Element rootElement, Content childContent )
	{
		if ( childContent != null )
		{
			rootElement.addContent( childContent );
		}
	}

	/**
	 * Ensure that the {@link XPFPuzzleCollection} associated with this input stream can be
	 * successfully serialized.
	 * @throws IOException The puzzle cannot be serialized 
	 */
	@Override
	public void validate() throws IOException
	{
		validate( getPuzzle() );
	}
	
	/**
	 * Ensure that the given {@link XPFPuzzle} can be successfully serialized.
	 * A new {@link XPFPuzzleCollection} will be created that contains only the given puzzle.
	 * @throws IOException The puzzle cannot be serialized 
	 */
	public static void validate( XPFPuzzle puzzle ) throws IOException
	{
		validate( new XPFPuzzleCollection( puzzle ) );
	}

	/**
	 * Ensure that the given {@link XPFPuzzleCollection}can be successfully serialized.
	 * @throws IOException The puzzle cannot be serialized 
	 */
	public static void validate( XPFPuzzleCollection collection ) throws IOException
	{
		if ( collection != null )
		{
			for ( XPFPuzzle puzzle : collection )
			{
				validatePuzzle( puzzle );
			}
		}
	}

	private static void validatePuzzle( XPFPuzzle puzzle ) throws IOException
	{
		confirm( puzzle != null, "Cannot serialize null puzzle" );
		confirm( puzzle.getRows() != 0, "Puzzle has 0 rows" );
		confirm( puzzle.getCols() != 0, "Puzzle has 0 columns" );
		confirm( allCluesLocated( puzzle ) || allCluesNotLocated( puzzle ), "Some clues are located and some clues are not located" );
		confirmShadesCoorect( puzzle );

		if ( allCluesNotLocated( puzzle ) )
		{
			confirm( locateClues( puzzle ).size() == puzzle.getClues().size(), "Number of clue locations does not match number of clued entries in the puzzle" );
		}
	}

	private static void confirmShadesCoorect( XPFPuzzle puzzle ) throws IOException
	{
		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			String shade = puzzle.getCellStyles().get( coord ).getShade();
			
			if ( shade != null && shade.length() > 0 )
			{
				confirm( shade.matches( "^(gray)|(#[0-9a-fA-F]{6})$"), "Invalid shade at " + coord + ": " + shade );
			}
		}
	}

	private static void confirm( boolean assertion, String message ) throws IOException
	{
		if ( ! assertion )
		{
			throw new IOException( message );
		}
	}

}
