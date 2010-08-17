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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.epeterso2.jabberwordy.serialization.PuzzleOutputStream;

/**
 * Provides an {@link OutputStream} for deserializing an XPF image into an {@link XPFPuzzleCollection} object.
 * The order in which the puzzles appear in the {@link XPFPuzzleCollection} is identical to the order in which they
 * appear in the deserialized XPF image.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzleOutputStream extends PuzzleOutputStream<XPFPuzzleCollection> {
	
	public XPFPuzzleOutputStream( InputStream inputStream ) throws IOException
	{
		super( inputStream );
	}

	/**
	 * Converts a serialized XPF image into an {@link XPFPuzzleCollection}.
	 */
	@Override
	public XPFPuzzleCollection toPuzzle() throws IOException
	{
		return buildCollection( getDOM() );
	}
	
	private Document getDOM() throws IOException
	{
		try
		{
			return new SAXBuilder().build( new ByteArrayInputStream( toByteArray() ) );
		}
		
		catch (JDOMException e)
		{
			throw new IOException( e );
		}
	}

	private XPFPuzzleCollection buildCollection( Document document ) throws IOException
	{
		testForDeserializability( document );
		
		XPFPuzzleCollection collection = new XPFPuzzleCollection();
		
		for ( Object puzzleElement : document.getRootElement().getChildren( "Puzzle" ) )
		{
			if ( puzzleElement instanceof Element )
			{
				collection.add( buildPuzzle( (Element) puzzleElement ) );
			}
			
			else
			{
				throw new IOException( "Unexpected child object found: " + puzzleElement );
			}
		}
		
		return collection;
	}

	private static SimpleDateFormat format = new SimpleDateFormat( "M/d/yyyy" );
	
	private XPFPuzzle buildPuzzle( Element puzzleElement ) throws IOException
	{
		XPFPuzzle puzzle = new XPFPuzzle( getWidth( puzzleElement ), getHeight( puzzleElement ) );
		
		puzzle.setTitle( getElementValue( puzzleElement, "Title" ) );
		puzzle.setAuthor( getElementValue( puzzleElement, "Author" ) );
		puzzle.setCopyright( getElementValue( puzzleElement, "Copyright" ) );
		puzzle.setEditor( getElementValue( puzzleElement, "Editor" ) );
		puzzle.setPublisher( getElementValue( puzzleElement, "Publisher" ) );
		puzzle.setNotepad( getElementValue( puzzleElement, "Notepad" ) );
		puzzle.setDate( buildDate( puzzleElement ) );

		buildShades( puzzle, puzzleElement );
		buildCircles( puzzle, puzzleElement );
		buildGrid( puzzle, puzzleElement );
		buildClues( puzzle, puzzleElement );
		
		return puzzle;
	}
	
	private void buildShades( XPFPuzzle puzzle, Element puzzleElement )
	{
		if ( puzzleElement.getChild( "Shades" ) != null )
		{
			for ( Object shadeObject : puzzleElement.getChildren ( "Shade" ) )
			{
				if ( shadeObject instanceof Element )
				{
					Element shadeElement = (Element) shadeObject;
					int row = Integer.valueOf( shadeElement.getAttributeValue( "Row" ) );
					int col = Integer.valueOf( shadeElement.getAttributeValue( "Col" ) );
					puzzle.getCellStyles().get( col, row ).setShade( shadeElement.getText() );
				}
			}
		}
	}

	private void buildCircles( XPFPuzzle puzzle, Element puzzleElement )
	{
		if ( puzzleElement.getChild( "Circles" ) != null )
		{
			for ( Object shadeObject : puzzleElement.getChildren ( "Circle" ) )
			{
				if ( shadeObject instanceof Element )
				{
					Element shadeElement = (Element) shadeObject;
					int row = Integer.valueOf( shadeElement.getAttributeValue( "Row" ) );
					int col = Integer.valueOf( shadeElement.getAttributeValue( "Col" ) );
					puzzle.getCellStyles().get( col, row ).setCircled( true );
				}
			}
		}
	}

	private void buildGrid( XPFPuzzle puzzle, Element puzzleElement )
	{
		if ( puzzleElement.getChild( "Grid" ) != null )
		{
			int row = 0;
			
			for ( Object rowObject : puzzleElement.getChild( "Grid" ).getChildren( "Row" ) )
			{
				if ( rowObject instanceof Element )
				{
					row++;
					Element rowElement = (Element) rowObject;
					
					for ( int col = 0; col < rowElement.getText().length(); ++col )
					{
						puzzle.getSolutions().get( col + 1, row ).setLetter( rowElement.getText().charAt( col ) );
					}
				}
			}
		}
	}

	private void buildClues( XPFPuzzle puzzle, Element puzzleElement )
	{
		;
	}

	private Date buildDate( Element puzzleElement ) throws IOException
	{
		try
		{
			return puzzleElement.getChild( "Date" ) != null ? format.parse( puzzleElement.getChildText( "Date" ) ) : null;
		}
		
		catch ( ParseException e )
		{
			throw new IOException( e );
		}
	}

	private String getElementValue( Element puzzleElement, String elementName )
	{
		return puzzleElement.getChild( elementName ) == null ? "" : puzzleElement.getChildText( elementName );
	}

	public void testForDeserializability( Document document ) throws IOException
	{
		confirm( document.hasRootElement(), "No root element" );
		confirm( document.getRootElement() != null, "Root element is null " );
		confirm( document.getRootElement().getName().equals( "Puzzles" ), "No 'Puzzles' element found at the root" );
		
		for ( Object puzzleElement : document.getRootElement().getChildren( "Puzzle" ) )
		{
			confirm( puzzleElement instanceof Element, "Non-element object with name 'Puzzle' found" );
			confirmPuzzleElement( (Element) puzzleElement );
		}
	}
	
	private void confirmPuzzleElement( Element puzzleElement ) throws IOException
	{
		confirm( puzzleElement.getChildren( "Size" ).size() == 1, "Puzzle must contain exactly one 'Size' element" );
		confirm( puzzleElement.getChild( "Size" ).getChildren( "Rows" ).size() == 1, "Puzzle 'Size' element must contain exactly one 'Rows' element" );
		confirm( puzzleElement.getChild( "Size" ).getChildren( "Cols" ).size() == 1, "Puzzle 'Size' element must contain exactly one 'Cols' element" );
		
		confirm( puzzleElement.getChildren( "Grid" ).size() == 1, "Puzzle must contain exactly one 'Size' element" );
		confirm( puzzleElement.getChild( "Grid" ).getChildren( "Row" ).size() ==
			Integer.valueOf( puzzleElement.getChild( "Size" ).getChildText( "Rows" ) ),
			"Number of rows in 'Grid' element is not equal to the number of rows in 'Size' element" );
		
		confirmDate( puzzleElement );
		
		for ( Object rowElement : puzzleElement.getChild( "Grid" ).getChildren( "Row" ) )
		{
			if ( rowElement instanceof Element )
			{
				confirm( ( (Element) rowElement ).getText().length() == getWidth( puzzleElement ), "" );
			}
		}
		
		confirmShades( puzzleElement );
		confirmCircles( puzzleElement );
	}
	
	private void confirmShades( Element puzzleElement ) throws IOException
	{
		;
	}

	private void confirmCircles( Element puzzleElement ) throws IOException
	{
		;
	}

	private int getWidth( Element puzzleElement )
	{
		if ( puzzleElement.getChild( "Size" ) != null && puzzleElement.getChild( "Size" ).getChild( "Cols" ) != null )
		{
			return Integer.valueOf( puzzleElement.getChild( "Size" ).getChildText( "Cols" ) );
		}
		
		else
		{
			return -1;
		}
	}

	private int getHeight( Element puzzleElement )
	{
		if ( puzzleElement.getChild( "Size" ) != null && puzzleElement.getChild( "Size" ).getChild( "Rows" ) != null )
		{
			return Integer.valueOf( puzzleElement.getChild( "Size" ).getChildText( "Rows" ) );
		}
		
		else
		{
			return -1;
		}
	}

	private void confirmDate( Element puzzleElement ) throws IOException
	{
		if ( puzzleElement.getChild( "Date" ) != null )
		{
			try
			{
				format.parse( puzzleElement.getChildText( "Date" ) );
			}
			
			catch (ParseException e)
			{
				throw new IOException( e );
			}
		}
	}

	private void confirm( boolean assertion, String message ) throws IOException
	{
		if ( ! assertion )
		{
			throw new IOException( message );
		}
	}

}
