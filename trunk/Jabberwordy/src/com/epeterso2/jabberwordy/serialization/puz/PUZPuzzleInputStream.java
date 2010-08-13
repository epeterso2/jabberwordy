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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.epeterso2.jabberwordy.serialization.PuzzleInputStream;
import com.epeterso2.jabberwordy.util.Coordinate;
import com.epeterso2.jabberwordy.util.CoordinateMap;

/**
 * Serializes a {@link PUZPuzzle} object into a PUZ file image as an {@link OutputStream}.
 * <p>
 * This class is used both to verify that a given {@link PUZPuzzle} can be serialized and to perform the actual serialization.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public class PUZPuzzleInputStream extends PuzzleInputStream<PUZPuzzle> {

	/**
	 * The version string of puzzles produced by this serializer.
	 */
	private static String VERSION_STRING = "1.3\0";

	/**
	 * Serializes a {@link PUZPuzzle} object into a PUZ file image. PUZ file image extra sections will be created only if necessary.
	 * If the unlockCode property of the puzzle object is non-null, then the puzzle solution will be scrambled using the unlock code. 
	 * @return The byte[] of the serialized puzzle
	 * @throws PuzzleSerializationException An error occurred during serialization or the puzzle object contains inconsistencies that would preclude serialization
	 */
	public byte[] toByteArray() throws IOException
	{
		// Ensure the puzzle is ready to be serialized
		validate();

		PUZPuzzle puzzle = getPuzzle();

		/**
		 * Constructs a new PUZ deserializing output stream using an input stream.
		 * The entire input stream will be read then written to this output stream.
		 * @param inputStream The inputStream containing a PUZ file image
		 */
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Overall file checksum
		out.write( new byte[ 2 ] );

		// File magic
		out.write( PUZUtil.FILE_MAGIC.getBytes() );

		// CIB checksum
		out.write( new byte[ 2 ] );

		// Masked checksums
		out.write( new byte[ 4 ] );
		out.write( new byte[ 4 ] );

		// Version string
		out.write( VERSION_STRING.getBytes() );

		// Reserved 1C
		out.write( new byte[ 2 ] );

		// Scrambled checksum
		out.write( PUZUtil.intToUshortBytes( computeScrambledChecksum( puzzle ) ) );

		// Reserved 20
		out.write( new byte[ 0xC ] );

		// Width and height
		out.write( (byte) puzzle.getWidth() );
		out.write( (byte) puzzle.getHeight() );

		// Number of clues
		out.write( PUZUtil.intToUshortBytes( puzzle.getAcrossClues().size() + puzzle.getDownClues().size() ) );

		// Puzzle type
		out.write( buildPuzzleType( puzzle ) );

		// Scrambled status
		out.write( buildSolutionType( puzzle ) );

		// The solution
		out.write( buildSolution( puzzle ) );

		// The player state
		out.write( buildPlayerState( puzzle ) );

		// Title
		out.write( ( puzzle.getTitle() == null ? "" : puzzle.getTitle() ).getBytes() );
		out.write( 0 );

		// Author
		out.write( ( puzzle.getAuthor() == null ? "" : puzzle.getAuthor() ).getBytes() );
		out.write( 0 );

		// Copyright
		out.write( ( puzzle.getCopyright() == null ? "" : puzzle.getCopyright() ).getBytes() );
		out.write( 0 );

		// Clues
		out.write( buildClues( puzzle ) );

		// Notes
		out.write( ( puzzle.getNotes() == null ? "" : puzzle.getNotes() ).getBytes() );
		out.write( 0 );

		// Extra sections
		out.write( buildGRBSandRTBLSection( puzzle ) );
		out.write( buildLTIMSection( puzzle ) );
		out.write( buildGEXTSection( puzzle ) );
		out.write( buildRUSRSection( puzzle ) );

		// Get the image
		byte[] image = out.toByteArray();

		// Scramble the solution
		if ( puzzle.isSolutionEncrypted() )
		{
			PUZUtil.lockSolution( image, puzzle.getUnlockCode() );
		}

		// Add checksums
		addChecksums( image );

		// Validate the image
		if ( PUZUtil.isValidImage( image ) )
		{
			return image;
		}

		else
		{
			throw new IOException();
		}
	}

	private byte[] buildPuzzleType( PUZPuzzle puzzle )
	{
		int type = puzzle.isDiagramless() ? PUZUtil.PUZZLE_TYPE_DIAGRAMLESS : PUZUtil.PUZZLE_TYPE_NORMAL;

		return PUZUtil.intToUshortBytes( type );
	}

	private byte[] buildSolutionType( PUZPuzzle puzzle )
	{
		int type = puzzle.isSolutionEncrypted() ? PUZUtil.SOLUTION_ENCRYPTED : PUZUtil.SOLUTION_NORMAL;

		return PUZUtil.intToUshortBytes( type );
	}

	private byte[] addChecksums( byte[] image )
	{
		PUZUtil.addOverallFileChecksum( image );
		PUZUtil.addCIBChecksum( image );
		PUZUtil.addMaskedChecksums( image );

		return image;
	}

	private byte[] buildGRBSandRTBLSection( PUZPuzzle puzzle )
	{
		Map<Coordinate, Integer> grbsMap = new TreeMap<Coordinate, Integer>();
		Map<String, Integer> rtblMap = new TreeMap<String, Integer>();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			String solution = puzzle.getSolutions().get( coord ).getRebus();

			if ( solution != null && solution.length() > 0 )
			{
				if ( ! rtblMap.containsKey( solution ) )
				{
					rtblMap.put( solution, rtblMap.size() + 1 );
				}

				grbsMap.put( coord, rtblMap.get( solution ) + 1 );
			}

			else
			{
				grbsMap.put( coord, 0 );
			}
		}

		// No entries --> no generated section
		if ( grbsMap.size() == 0 )
		{
			return new byte[ 0 ];
		}

		// We have entries
		else
		{
			return buildGRBSandRTBLImage( puzzle, grbsMap, rtblMap );
		}
	}

	private byte[] buildGRBSandRTBLImage( PUZPuzzle puzzle, Map<Coordinate, Integer> grbsMap, Map<String, Integer> rtblMap )
	{
		byte[] grbsSection = buildExtraSection( PUZUtil.GRBS_SECTION_NAME, buildGRBSData( puzzle, grbsMap ) );
		byte[] rtblSection = buildExtraSection( PUZUtil.RTBL_SECTION_NAME, buildRTBLData( puzzle, invertRTBLMap( rtblMap ) ) );
		byte[] out = new byte[ grbsSection.length + rtblSection.length ];
		PUZUtil.copyBytes( grbsSection, out, grbsSection.length );
		PUZUtil.copyBytes( rtblSection, 0, out, grbsSection.length, rtblSection.length );

		return out;
	}

	private byte[] buildGRBSData( PUZPuzzle puzzle, Map<Coordinate, Integer> grbsMap )
	{
		byte[] out = new byte[ puzzle.getNumberOfCells() ];

		for ( Coordinate coord : grbsMap.keySet() )
		{
			out[ getGridOffset( puzzle, coord ) ] = (byte) ( (int) grbsMap.get( coord ) );
		}

		return out;
	}

	private byte[] buildRTBLData( PUZPuzzle puzzle, Map<Integer, String> rtblMap )
	{
		StringBuilder builder = new StringBuilder();

		for ( int index : rtblMap.keySet() )
		{
			if ( index < 10 )
			{
				builder.append( " " );
			}

			builder.append( index );
			builder.append( ':' );
			builder.append( rtblMap.get( index ) );
			builder.append( ';' );
		}

		return builder.toString().getBytes();
	}

	private Map<Integer, String> invertRTBLMap( Map<String, Integer> rtblMap )
	{
		Map<Integer, String> map = new TreeMap<Integer, String>();

		for ( String key : rtblMap.keySet() )
		{
			map.put( rtblMap.get( key ), key );
		}

		return map;
	}

	private byte[] buildLTIMSection( PUZPuzzle puzzle )
	{
		StringBuilder builder = new StringBuilder();
		builder.append( puzzle.getElapsedSeconds() );
		builder.append( ',' );
		builder.append( puzzle.isTimerRunning() ? 0 : 1 );

		return buildExtraSection( PUZUtil.LTIM_SECTION_NAME, builder.toString().getBytes() );
	}

	private byte[] buildGEXTSection( PUZPuzzle puzzle )
	{
		CoordinateMap<PUZCellStyle> styles = new CoordinateMap<PUZCellStyle>();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			PUZCellStyle style = puzzle.getCellStyles().get( coord );

			if ( style.isCircled() || style.isCurrentlyMarkedIncorrect() || style.isPreviouslyMarkedIncorrect() || style.isRevealed() )
			{
				styles.put( coord, style );
			}
		}

		// If there are no special flags, don't add this section
		if ( styles.size() == 0 )
		{
			return new byte[ 0 ];
		}

		// We have style flags, so add this section
		else
		{
			return buildExtraSection( PUZUtil.GEXT_SECTION_NAME, buildGEXTData( puzzle, styles ) );
		}
	}

	private byte[] buildGEXTData( PUZPuzzle puzzle, CoordinateMap<PUZCellStyle> styles )
	{
		byte[] out = new byte[ puzzle.getNumberOfCells() ];

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			out[ getGridOffset( puzzle, coord ) ] = 0;

			if ( styles.containsKey( coord ) )
			{
				PUZCellStyle style = styles.get( coord );

				out[ getGridOffset( puzzle, coord ) ] |= style.isCircled() ? PUZUtil.CIRCLED_FLAG : 0;
				out[ getGridOffset( puzzle, coord ) ] |= style.isCurrentlyMarkedIncorrect() ? PUZUtil.CURRENTLY_WRONG_FLAG : 0;
				out[ getGridOffset( puzzle, coord ) ] |= style.isPreviouslyMarkedIncorrect() ? PUZUtil.PREVIOUSLY_WRONG_FLAG : 0;
				out[ getGridOffset( puzzle, coord ) ] |= style.isRevealed() ? PUZUtil.REVEALED_FLAG : 0;
			}
		}

		return out;
	}

	private byte[] buildRUSRSection( PUZPuzzle puzzle )
	{
		CoordinateMap<String> answers = new CoordinateMap<String>();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			if ( puzzle.getPlayerState().get( coord ).length() > 1 )
			{
				answers.put( coord, puzzle.getPlayerState().get( coord ) );
			}
		}

		// No rebus answers? No section.
		if ( answers.size() == 0 )
		{
			return new byte[ 0 ];
		}

		// We have at least one rebus answer
		else
		{
			return buildExtraSection( PUZUtil.RUSR_SECTION_NAME, buildRUSRData( puzzle, answers ) );
		}
	}

	private byte[] buildRUSRData( PUZPuzzle puzzle, CoordinateMap<String> answers )
	{
		StringBuilder builder = new StringBuilder();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			if ( answers.containsKey( coord ) )
			{
				builder.append( answers.get( coord ) );
			}

			builder.append( (char) 0 );
		}

		return builder.toString().getBytes();
	}

	private byte[] buildExtraSection( String title, byte[] data )
	{
		byte[] section = new byte[ data.length + 9 ];

		for ( int i = 0; i < 4; ++i )
		{
			section[ i ] = (byte) title.charAt( i );
		}

		PUZUtil.copyBytes( title.getBytes(), 0, section, PUZUtil.SECTION_TITLE_OFFSET, PUZUtil.SECTION_TITLE_LENGTH );
		PUZUtil.copyBytes( PUZUtil.intToUshortBytes( data.length ), 0, section, PUZUtil.SECTION_LENGTH_OFFSET, 2 );
		PUZUtil.copyBytes( PUZUtil.intToUshortBytes( PUZUtil.computeChecksum( data, 0, data.length ) ), 0,
				section, PUZUtil.SECTION_CHECKSUM_OFFSET, 2 );
		PUZUtil.copyBytes( data, 0, section, PUZUtil.SECTION_DATA_OFFSET, data.length );
		section[ section.length - 1 ] = 0;

		return section;
	}

	private int computeScrambledChecksum( PUZPuzzle puzzle )
	{
		if ( puzzle.isSolutionEncrypted() )
		{
			StringBuilder builder = new StringBuilder();

			for ( int col = 1; col <= puzzle.getWidth(); ++col )
			{
				for ( int row = 1; row <= puzzle.getHeight(); ++row )
				{
					Coordinate coord = new Coordinate( col, row );

					if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
					{
						builder.append( puzzle.getSolutions().get( coord ).getLetter() );
					}
				}
			}

			byte[] image = builder.toString().getBytes();

			return PUZUtil.computeChecksum( image, 0, image.length );
		}

		else
		{
			return 0;
		}
	}

	private byte[] buildSolution( PUZPuzzle puzzle )
	{
		byte[] gridImage = new byte[ puzzle.getNumberOfCells() ];

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			byte value = '.';

			if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
			{
				value = (byte) puzzle.getSolutions().get( coord ).getLetter();
			}

			gridImage[ getGridOffset( puzzle, coord ) ] = value;
		}

		return gridImage;
	}

	private byte[] buildPlayerState( PUZPuzzle puzzle )
	{
		byte[] gridImage = new byte[ puzzle.getNumberOfCells() ];

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			byte value = '.';

			if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
			{
				String sol = puzzle.getPlayerState().get( coord );
				value = (byte) ( sol.length() == 0 ? '-' : sol.charAt( 0 ) );
			}

			gridImage[ getGridOffset( puzzle, coord ) ] = value;
		}

		return gridImage;
	}

	private byte[] buildClues( PUZPuzzle puzzle )
	{
		StringBuilder builder = new StringBuilder();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			int number = puzzle.getCellStyles().get( coord ).getNumber();

			if ( number > 0 )
			{
				if ( puzzle.getAcrossClues().containsKey( number ) )
				{
					builder.append( puzzle.getAcrossClues().get( number ) );
					builder.append( (char) 0 );
				}

				if ( puzzle.getDownClues().containsKey( number ) )
				{
					builder.append( puzzle.getDownClues().get( number ) );
					builder.append( (char) 0 );
				}
			}
		}

		return builder.toString().getBytes();
	}

	private static int getGridOffset( PUZPuzzle puzzle, Coordinate coord )
	{
		return ( coord.getX() - 1 ) + ( coord.getY() - 1 ) * puzzle.getWidth(); 
	}

	/**
	 * Analyzes the {@link PUZPuzzle} object associated with this output stream to determine if it can be serialized successfully or not.
	 * @throws PuzzleSerializationException if the puzzle cannot be serialized
	 */
	public void validate() throws IOException
	{
		PUZPuzzle puzzle = getPuzzle();

		// Dimensions
		confirm( puzzle.getWidth() > 0, "Width must be greater than zero" );
		confirm( puzzle.getHeight() > 0, "Height must be greater than zero" );

		// Cell styles
		confirm( puzzle.getCellStyles() != null, "Cell styles cannot be null" );
		confirm( puzzle.getNumberOfCells() == puzzle.getCellStyles().size(), "The number of cell styles must equal the number of cells" );
		confirmCellNumbering( puzzle );

		// Solution
		confirmSolution( puzzle );

		// Clues
		confirmClues( puzzle );

		// Scrambling
		if ( puzzle.isSolutionEncrypted() )
		{
			confirmUnlockCode( puzzle );
		}
	}

	private void confirmClues( PUZPuzzle puzzle ) throws IOException
	{
		ArrayList<String> clues = new ArrayList<String>();
		PUZPuzzle test = new PUZPuzzle( puzzle.getWidth(), puzzle.getHeight() );

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			test.getCellStyles().get( coord ).setBlock( puzzle.getCellStyles().get( coord ).isBlock() );
			int cellNumber = puzzle.getCellStyles().get( coord ).getNumber();

			if ( cellNumber > 0 )
			{
				if ( puzzle.getAcrossClues().containsKey( cellNumber ) )
				{
					confirm( puzzle.getAcrossClues().get( cellNumber ) != null, "Null clue found at " + cellNumber + "-Across" );
					clues.add( puzzle.getAcrossClues().get( cellNumber ) );
				}

				if ( puzzle.getDownClues().containsKey( cellNumber ) )
				{
					confirm( puzzle.getDownClues().get( cellNumber ) != null, "Null clue found at " + cellNumber + "-Down" );
					clues.add( puzzle.getDownClues().get( cellNumber ) );
				}
			}
		}

		test.assignClueNumbers();
		test.assignClues( clues );

		confirm( test.getAcrossClues().size() == puzzle.getAcrossClues().size(), "Incorrect number of across clues" );
		confirm( test.getDownClues().size() == puzzle.getDownClues().size(), "Incorrect number of down clues" );

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			int cellNumber = test.getCellStyles().get( coord ).getNumber();

			if ( cellNumber > 0 )
			{
				if ( test.getAcrossClues().containsKey( cellNumber ) )
				{
					confirm( test.getAcrossClues().get( cellNumber ).equals( puzzle.getAcrossClues().get( cellNumber ) ), "Error in cluing detected at " + cellNumber + "-Across" );
				}

				if ( test.getDownClues().containsKey( cellNumber ) )
				{
					confirm( test.getDownClues().get( cellNumber ).equals( puzzle.getDownClues().get( cellNumber ) ), "Error in cluing detected at " + cellNumber + "-Down" );			
				}
			}
		}
	}

	private void confirmUnlockCode( PUZPuzzle puzzle ) throws IOException
	{
		confirm( puzzle.getUnlockCode() != null, "Unlock code cannot be null if solution is scrambled" );
		confirm( puzzle.getUnlockCode().matches( "^[1-9]{4}$" ), "Unlock code must be a string of 4 digits in the range 1 to 9" );
	}

	private void confirmSolution( PUZPuzzle puzzle ) throws IOException
	{
		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			if ( ! puzzle.getCellStyles().get( coord ).isBlock() )
			{
				confirm( puzzle.getSolutions().get( coord ).getLetter() >= 'A' && puzzle.getSolutions().get( coord ).getLetter() <= 'Z', "Solution at " + coord + " is not a letter" );
			}
		}
	}

	private void confirmCellNumbering( PUZPuzzle puzzle ) throws IOException
	{
		PUZPuzzle test = new PUZPuzzle( puzzle.getWidth(), puzzle.getHeight() );

		// Build the test pattern
		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			test.getCellStyles().get( coord ).setBlock( puzzle.getCellStyles().get( coord ).isBlock() );
		}

		test.assignClueNumbers();

		for ( Coordinate coord : puzzle.getCoordinates() )
		{
			confirm( test.getCellStyles().get( coord ).getNumber() == puzzle.getCellStyles().get( coord ).getNumber(), "Invalid numbering at cell " + coord );
		}
	}

	private void confirm( boolean b, String string ) throws IOException
	{
		if ( ! b )
		{
			throw new IOException( string );
		}
	}

}
