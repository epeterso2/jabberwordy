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

	public XPFPuzzleInputStream setCompact(boolean compact) {
		this.compact = compact;
		return this;
	}

	public boolean isCompact() {
		return compact;
	}

	public XPFPuzzleInputStream setEncoding( String encoding )
	{
		this.encoding = encoding;
		return this;
	}

	public XPFPuzzleInputStream( XPFPuzzleCollection collection )
	{
		super( collection );
	}

	/**
	 * Serializes the puzzle associated with this class into an XPF image.
	 */
	@Override
	public byte[] toByteArray() throws IOException
	{
		// Make sure we're ready to serialize
		validate();

		// Write the output
		return new XMLOutputter( ( isCompact() ? Format.getCompactFormat() : Format.getPrettyFormat() ).setEncoding( encoding ) ).outputString( buildDocument( getPuzzle() ) ).getBytes();
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

	private Element buildCluesElement( XPFPuzzle puzzle )
	{
		Element cluesElement = null;

		if ( puzzle.getClues() != null )
		{
			List<XPFClue> clues = allCluesAreLocated( puzzle ) ? puzzle.getClues() : locateClues( puzzle );

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

	private XPFCellStyle getStyle( XPFPuzzle puzzle, int x, int y )
	{
		return puzzle.getCellStyles().containsKey( x, y ) ? puzzle.getCellStyles().get( x, y ) : blockCellStyle;
	}

	private List<XPFClue> locateClues( XPFPuzzle puzzle )
	{
		// Make a copy that we can destroy
		List<XPFClue> clues = new ArrayList<XPFClue>();
		clues.addAll( puzzle.getClues() );

		List<XPFClue> locatedClues = new ArrayList<XPFClue>();
		int clueNumber = 0;

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			boolean acrossEntry = false;
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

				// Can this be the start of an across entry?
				if ( styleLeft.isBlock() && ! styleRight.isBlock() && ( ! styleUp.isBlock() || ! styleDown.isBlock() ) )
				{
					acrossEntry = true;
				}

				// Can this be the start of a down entry?
				if ( styleUp.isBlock() && ! styleDown.isBlock() && ( ! styleLeft.isBlock() || ! styleRight.isBlock() ) )
				{
					downEntry = true;
				}

				// Can this be the unchecked start of an across entry?
				if ( playDirs == 1 && ! styleRight.isBlock() )
				{
					acrossEntry = true;
				}

				// Can this be the unchecked start of a down entry?
				if ( playDirs == 1 && ! styleDown.isBlock() )
				{
					downEntry = true;
				}

				if ( acrossEntry || downEntry )
				{
					clueNumber++;

					if ( acrossEntry )
					{
						XPFClue clue = buildNextClue( clues, coord, Integer.valueOf( clueNumber ).toString() );
						clue.setDirection( "Across" );
						locatedClues.add( clue );
					}

					if ( downEntry )
					{
						XPFClue clue = buildNextClue( clues, coord, Integer.valueOf( clueNumber ).toString() );
						clue.setDirection( "Down" );
						locatedClues.add( clue );
					}
				}
			}
		}

		return locatedClues;
	}

	private XPFClue buildNextClue( List<XPFClue> clues, Coordinate coord, String clueNumber )
	{
		XPFClue newClue = new XPFClue();
		XPFClue clue = clues.remove( 0 );

		newClue.setAnswer( clue.getAnswer() );
		newClue.setText( clue.getText() );
		newClue.setCoordinate( coord );
		newClue.setNumber( clueNumber );

		return newClue;
	}

	private static boolean allCluesAreLocated( XPFPuzzle puzzle )
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
			if ( puzzle.getCellStyles().get( coord ).getShade() != null )
			{
				if ( shadesElement == null )
				{
					shadesElement = new Element( "Shades" );
				}

				Element shadeElement = new Element( "Shade" );
				shadeElement.setAttribute( "Row", Integer.valueOf( coord.getY() ).toString() );
				shadeElement.setAttribute( "Col", Integer.valueOf( coord.getX() ).toString() );
				shadeElement.setText( puzzle.getCellStyles().get( coord ).getShade() );

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

	public void validate() throws IOException
	{
		validate( getPuzzle() );
	}

	public static void validate( XPFPuzzleCollection collection ) throws IOException
	{
		if ( collection != null )
		{
			for ( XPFPuzzle puzzle : collection )
			{
				validate( puzzle );
			}
		}
	}

	public static void validate( XPFPuzzle puzzle ) throws IOException
	{
		if ( puzzle == null )
		{
			throw new IOException( new NullPointerException( "Null puzzle cannot be serialized" ) );
		}

		if ( ! allCluesAreLocated( puzzle ) && ! allCluesNotLocated( puzzle ) )
		{
			throw new IOException( "Either all clues must have locations or all clues must not have locations" );
		}

		// TODO More validation
	}

	public static void main( String[] arg ) throws IOException
	{
		XPFPuzzle p1 = new XPFPuzzle( 3, 3 );
		p1.setTitle( "A" );
		char letter = 'A';
		for ( Coordinate coord : p1.getCoordinates() )
		{
			p1.getSolutions().get( coord ).setLetter( letter );
			letter = (char) ( ( letter == 'Z' ) ? 'A' : ( letter + 1 ));
		}
		
		p1.getCellStyles().get( 1, 1 ).setBlock( true );
		p1.getCellStyles().get( 2, 2 ).setBlock( true );
		
		List<XPFClue> clues = new ArrayList<XPFClue>();
		clues.add( new XPFClue( "1A", "1a" ) );
		clues.add( new XPFClue( "1D", "1d" ) );
		clues.add( new XPFClue( "2D", "2d" ) );
		clues.add( new XPFClue( "3D", "3d" ) );
		clues.add( new XPFClue( "4A", "4a" ) );
		clues.add( new XPFClue( "5A", "5a" ) );
		p1.setClues( clues );

		XPFPuzzleCollection collection = new XPFPuzzleCollection();

		collection.add( p1 );

		System.out.println( new String( new XPFPuzzleInputStream( collection ).toByteArray() ) );
	}

}
