/**
 * Copyright (c) 2010 Eric Peterson, www.epeterso2.com
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection of utility methods and constants for reading and writing PUZ images in byte[] format
 * <p>
 * These routines are used by the {@link PUZPuzzleInputStream} and {@link PUZPuzzleOutputStream} classes for their heavy lifting.
 * If all you need to do is to convert a PUZ file to or from a {@link PUZPuzzle} object, use those classes instead. 
 * <p>
 * This class provides stateless static methods that operate on PUZ images stored in byte[] format. The methods are stateless and nondestructive,
 * which means they are simple and accurate at the expense of efficiency.
 * <p>
 * Two-byte numeric values stored in a PUZ image in little-endian format. All values are unsigned. Methods of this class that return unsigned byte
 * and unsigned short values do so by storing them in {@code int} objects.
 * This class contains convenience methods to convert between int, unsigned byte, and unsigned short values.
 * <p>
 * The solution and player state cells in a PUZ image are arranged as bytes in row-first order.
 * The routines of this class assume that the leftmost column index and the topmost row index are 0.
 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat">The PUZ File Format</a>
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 */
public abstract class PUZUtil {

	/**
	 * Offset of the overall checksum
	 */
	public static int POS_OVERALL_CHECKSUM = 0x00;
	
	/**
	 * Offset of the magic string ACROSS&DOWN\0
	 */
	public static int POS_FILE_MAGIC = 0x02;
	
	/**
	 * Offset of the CIB checksum
	 */
	public static int POS_CIB_CHECKSUM = 0x0E;
	
	/**
	 * Offset of the masked low checksums
	 */
	public static int POS_MASKED_LOW_CHECKSUMS = 0x10;
	
	/**
	 * Offset of the masked high checksums
	 */
	public static int POS_MASKED_HIGH_CHECKSUMS = 0x14;
	
	/**
	 * Offset of the version string
	 */
	public static int POS_VERSION = 0x18;
	
	/**
	 * Offset of the reserved memory at 0x1C
	 */
	public static int POS_RESERVED_0X1C = 0x1C;
	
	/**
	 * Offset of the decrypted solution checksum
	 */
	public static int POS_DECRYPTED_SOLUTION_CHECKSUM = 0x1E;
	
	/**
	 * Offset of the reserved memory at 0x20
	 */
	public static int POS_RESERVED_0X20 = 0x20;
	
	/**
	 * Offset of the start of the CIB
	 */
	public static int POS_CIB = 0x2C;
	
	/**
	 * Offset of the width of the grid
	 */
	public static int POS_WIDTH = 0x2C;
	
	/**
	 * Offset of the height of the grid
	 */
	public static int POS_HEIGHT = 0x2D;
	
	/**
	 * Offset of the number of clues in the grid
	 */
	public static int POS_NUMBER_OF_CLUES = 0x2E;
	
	/**
	 * Offset of the puzzle type
	 */
	public static int POS_PUZZLE_TYPE = 0x30;
	
	/**
	 * Offset of the solution type
	 */
	public static int POS_SOLUTION_TYPE = 0x32;
	
	/**
	 * Offset of the start of the solution
	 */
	public static int POS_SOLUTION = 0x34;

	/**
	 * Length of the version string
	 */
	public static int VERSION_LENGTH = 4;
	
	/**
	 * Number of bytes in the masked checksums
	 */
	public static int MASKED_CHECKSUM_LENGTH = 4;
	
	/**
	 * Size of the reserved region of memory at 0x1c
	 */
	public static int RESERVED_0X1C_LENGTH = 2;
	
	/**
	 * Size of the reserved region of memory at 0x20
	 */
	public static int RESERVED_0X20_LENGTH = 0xC;

	/**
	 * String index of the title
	 */
	public static int STRING_TITLE = 0;
	
	/**
	 * String index of the author
	 */
	public static int STRING_AUTHOR = 1;
	
	/**
	 * String index of the copyright
	 */
	public static int STRING_COPYRIGHT = 2;
	
	/**
	 * String index of the first clue
	 */
	public static int STRING_FIRST_CLUE = 3;

	/**
	 * String name of the GEXT section
	 */
	public static String GEXT_SECTION_NAME = "GEXT";
	
	/**
	 * String name of the LTIM section
	 */
	public static String LTIM_SECTION_NAME = "LTIM";
	
	/**
	 * String name of the GRBS section
	 */
	public static String GRBS_SECTION_NAME = "GRBS";
	
	/**
	 * String name of the RTBL section
	 */
	public static String RTBL_SECTION_NAME = "RTBL";
	
	/**
	 * String name of the RUSR section
	 */
	public static String RUSR_SECTION_NAME = "RUSR";

	/**
	 * Flag that indiciates a cell was previously marked wrong
	 */
	public static int PREVIOUSLY_WRONG_FLAG = 0x10;
	
	/**
	 * Flag that indicates a cell is currently marked wrong
	 */
	public static int CURRENTLY_WRONG_FLAG = 0x20;
	
	/**
	 * Flag that indicates the solution to a cell has been revealed
	 */
	public static int REVEALED_FLAG = 0x40;
	
	/**
	 * Flag that indicates that a particular cell is to be drawn as circled
	 */
	public static int CIRCLED_FLAG = 0x80;

	/**
	 * The offset of the title within an extra section
	 */
	public static int SECTION_TITLE_OFFSET    = 0x00;
	
	/**
	 * The offset of the length within an extra section
	 */
	public static int SECTION_LENGTH_OFFSET   = 0x04;
	
	/**
	 * The offset of the checksum within an extra section
	 */
	public static int SECTION_CHECKSUM_OFFSET = 0x06;
	
	/**
	 * The offset of the data within an extra section
	 */
	public static int SECTION_DATA_OFFSET     = 0x08;
	
	/**
	 * The length of the title of an extra section
	 */
	public static int SECTION_TITLE_LENGTH    = 4;

	/**
	 * Value at {@link #POS_PUZZLE_TYPE} that indicates that this is a normal puzzle
	 */
	public static int PUZZLE_TYPE_NORMAL = 0x0001;
	
	/**
	 * Value at {@link #POS_PUZZLE_TYPE} that indiciates a diagramless puzzle
	 */
	public static int PUZZLE_TYPE_DIAGRAMLESS = 0x0401;
	
	/**
	 * Flag at {@link #POS_PUZZLE_TYPE} that indicates a diagramless puzzle
	 */
	public static int PUZZLE_TYPE_DIAGRAMLESS_FLAG = 0x0400;

	/**
	 * Value at {@link #POS_SOLUTION_TYPE} that indicates a normal solution
	 */
	public static int SOLUTION_NORMAL = 0x0000;
	
	/**
	 * Value at {@link #POS_SOLUTION_TYPE} that indicates that the solution is encrypted
	 */
	public static int SOLUTION_ENCRYPTED = 0x0004;
	
	/**
	 * Value of the magic string "ACROSS&DOWN\0" found at {@link #POS_FILE_MAGIC} 
	 */
	public static String FILE_MAGIC = "ACROSS&DOWN\0";
	
	/**
	 * Length of the magic string {@link #FILE_MAGIC}
	 */
	public static int FILE_MAGIC_LENGTH = FILE_MAGIC.length();

	/**
	 * Calculate the checksum for a given region of an image using 0 as the initial checksum value.
	 * @param image The byte[] image
	 * @param start The starting position of the region
	 * @param length The length of the region
	 * @return The computed checksum for the region 
	 */
	public static int computeChecksum( byte[] image, int start, int length )
	{
		return computeChecksum( image, start, length, 0 );
	}

	/**
	 * Calculate the checksum for a given region of an image using the given initial checksum value.
	 * The checksum is a variant of <a href="http://en.wikipedia.org/wiki/Cyclic_redundancy_check">CRC-16</a>.
	 * @param image The byte[] image
	 * @param start The starting position of the region
	 * @param length The length of the region
	 * @param checksum The initial value of the checksum
	 * @return The computed checksum for the region
	 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat#Checksums">PUZ file format checksums</a>
	 */
	public static int computeChecksum( byte[] image, int start, int length, int checksum )
	{
		for ( int i = 0; i < length; ++i )
		{
			checksum = checksum & 0xffff;

			if ( ( checksum & 0x0001 ) == 0x0001 )
			{
				checksum = ( checksum >> 1 ) | 0x8000;
			}

			else
			{
				checksum = checksum >> 1;
			}

			checksum += byteToUbyte( image[ start + i ] );
		}

		return checksum & 0xffff;
	}

	/**
	 * Return an unsigned byte value as an int
	 * @param b The byte value
	 * @return The unsigned value of the byte as an int
	 */
	public static int byteToUbyte( byte b )
	{
		return (int) ( b & 0xFF );
	}
	
	/**
	 * Return an unsigned short value in an int as a 2-element array of unsigned bytes in little-endian format
	 * @param i The integer to convert
	 * @return A byte[] of unsigned byte values (byte 0 is the least significant, byte 1 is most significant) 
	 */
	public static byte[] intToUshortBytes( int i )
	{
		byte[] out = new byte[ 2 ];
		
		out[ 0 ] = (byte) ( i & 0xFF );
		out[ 1 ] = (byte) ( ( i >> 8 ) & 0xFF );
		
		return out;
	}

	/**
	 * Return a 2-byte region of an image in little-endian format as an unsigned short value contained in an int
	 * @param image The image containing the region
	 * @param offset The offset of the 2-byte region in the image
	 * @return The unsigned short value represented by the 2-byte region 
	 */
	public static int getUshort( byte[] image, int offset )
	{
		return byteToUbyte( image[ offset + 1 ] ) * 256 + byteToUbyte( image[ offset ] );
	}

	/**
	 * Return a new byte[] image of a given region within an image
	 * @param image The original image
	 * @param offset The offset of the region within the image
	 * @param length The length of the region
	 * @return A new byte[] object with the same length as the original region and whose values equal those in the original region
	 */
	public static byte[] getBytes( byte[] image, int offset, int length )
	{
		byte[] bytes = new byte[ length ];

		for ( int i = 0; i < length; ++i )
		{
			bytes[ i ] = image[ i + offset ];
		}

		return bytes;
	}

	/**
	 * Return the overall file checksum stored in a PUZ image
	 * @param image The PUZ image
	 * @return The unsigned short value of the overall checksum read from the image
	 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat#Checksums">PUZ file format checksums</a>
	 */
	public static int getOverallFileChecksum( byte[] image )
	{
		return getUshort( image, POS_OVERALL_CHECKSUM );
	}

	/**
	 * Return the file magic string stored in a PUZ image
	 * @param image The PUZ image
	 * @return The value of the file magic string read from the image
	 */
	public static String getFileMagic( byte[] image )
	{
		StringBuilder builder = new StringBuilder();

		for ( int i = 0; i < FILE_MAGIC_LENGTH; ++i )
		{
			builder.append( (char) image[ POS_FILE_MAGIC + i ] );
		}

		return builder.toString();
	}

	/**
	 * Return the CIB checksum stored in a PUZ image
	 * @param image The PUZ image
	 * @return The unsigned short value of the CIB checksum read from the image
	 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat#Checksums">PUZ file format checksums</a>
	 */
	public static int getCIBChecksum( byte[] image )
	{
		return getUshort( image, POS_CIB_CHECKSUM );
	}

	/**
	 * Return the masked low checksums stored in a PUZ image
	 * @param image The PUZ image
	 * @return An array of 4 bytes of the masked low checksums read from the image 
	 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat#Checksums">PUZ file format checksums</a>
	 */
	public static byte[] getMaskedLowChecksums( byte[] image )
	{
		return getBytes( image, POS_MASKED_LOW_CHECKSUMS, MASKED_CHECKSUM_LENGTH );
	}

	/**
	 * Return the masked high checksums stored in a PUZ image
	 * @param image The PUZ image
	 * @return An array of 4 bytes of the masked high checksums read from the image 
	 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat#Checksums">PUZ file format checksums</a>
	 */
	public static byte[] getMaskedHighChecksums( byte[] image )
	{
		return getBytes( image, POS_MASKED_HIGH_CHECKSUMS, MASKED_CHECKSUM_LENGTH );
	}

	/**
	 * Return the version string stored in a PUZ image
	 * @param image The PUZ image
	 * @return A string of length 4 representing the version string read from the image
	 */
	public static String getVersionString( byte[] image )
	{
		StringBuilder builder = new StringBuilder();

		for ( int i = 0; i < VERSION_LENGTH; ++i )
		{
			builder.append( (char) image[ POS_VERSION + i ] );
		}

		return builder.toString();
	}

	/**
	 * Return the value of the puzzle type stored in a PUZ image
	 * @param image The PUZ image
	 * @return The unsigned short value of the puzzle type stored in the image
	 */
	public static int getPuzzleType( byte[] image )
	{
		return getUshort( image, POS_PUZZLE_TYPE );
	}

	/**
	 * Return the value of the solution type stored in a PUZ image
	 * @param image The PUZ image
	 * @return The unsigned short value of the solution type stored in the image
	 */
	public static int getSolutionType( byte[] image )
	{
		return getUshort( image, POS_SOLUTION_TYPE );
	}

	/**
	 * Return the value of a specific solution cell stored in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column number (starting at 0) of the selected cell
	 * @param row The row number (starting at 0) of the selected cell
	 * @return The value of the solution cell at the given coordinates
	 */
	public static byte getSolutionCell( byte[] image, int col, int row )
	{
		return image[ POS_SOLUTION + row * getWidth( image ) + col ];
	}

	/**
	 * Return the value of a specific player state cell stored in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column number (starting at 0) of the selected cell
	 * @param row The row number (starting at 0) of the selected cell
	 * @return The value of the player state cell at the given coordinates
	 */
	public static byte getPlayerStateCell( byte[] image, int col, int row )
	{
		return image[ POS_SOLUTION + PUZUtil.getNumberOfCells( image ) + row * getHeight( image ) + col ];
	}

	/**
	 * Return the value of the reserved region of memory at offset 0x1C in a PUZ image.
	 * @param image The PUZ image
	 * @return The byte[] value of the reserved region of memory
	 */
	public static byte[] getReserved0x1c( byte[] image )
	{
		return getBytes( image, POS_RESERVED_0X1C, RESERVED_0X1C_LENGTH );
	}

	/**
	 * Return the value of the decrypted solution checksum stored in a PUZ image. This checksum is used to verify that a
	 * given solution to a locked puzzle is correct.
	 * @param image The PUZ image
	 * @return The unsigned short value of the decrypted solution checksum
	 */
	public static int getDecryptedSolutionChecksum( byte[] image )
	{
		return getUshort( image, POS_DECRYPTED_SOLUTION_CHECKSUM );
	}

	/**
	 * Return the value of the reserved region of memory at offset 0x20 in a PUZ image.
	 * @param image The PUZ image
	 * @return The byte[] value of the reserved region of memory
	 */
	public static byte[] getReserved0x20( byte[] image )
	{
		return getBytes( image, POS_RESERVED_0X20, RESERVED_0X20_LENGTH );
	}

	/**
	 * Return the string stored in a PUZ image that corresponds to the given index. The strings are stored beginning
	 * immediately after the solution and player state sections, and each string is null terminated. Some strings have
	 * fixed indices (such as {@link #STRING_AUTHOR}, {@link #STRING_COPYRIGHT}, {@link #STRING_TITLE}, and
	 * {@link #STRING_FIRST_CLUE}). After the last clue string is the Notepad string.
	 * @param image The PUZ image
	 * @param index The index number of the string to retrieve
	 * @return The string at the given index
	 */
	public static String getString( byte[] image, int index )
	{
		StringBuilder builder = new StringBuilder();
		int start = getStringStartPosition( image, index );
		
		for ( int i = 0; start + i < image.length && image[ start + i ] != 0; ++i )
		{
			builder.append( (char) byteToUbyte( image[ start + i ] ) );
		}
		
		return builder.toString();
	}

	/**
	 * Return the starting position of a string stored within a PUZ image
	 * @param image The PUZ image
	 * @param index The index of the string whose start position will be found
	 * @return The start position of the indexed string
	 */
	public static int getStringStartPosition( byte[] image, int index )
	{
		int startPos = getStringSectionStartPosition( image );
		int stringsFound = 0;
		while ( stringsFound < index && startPos < image.length )
		{
			if ( image[ startPos ] == 0 )
			{
				stringsFound++;
			}

			++startPos;
		}

		return startPos < image.length ? startPos : -1;
	}

	/**
	 * Return the offset of the beginning of the string section within a PUZ image
	 * @param image The PUZ image
	 * @return The offset of the start of the string section
	 */
	public static int getStringSectionStartPosition( byte[] image )
	{
		return POS_SOLUTION + getNumberOfCells( image ) * 2;
	}

	/**
	 * Return the total number of cells in the grid stored within a PUZ image. This is equal to the product of the width and height stored in the image.
	 * @param image The PUZ image
	 * @return The number of cells in the image
	 */
	public static int getNumberOfCells( byte[] image )
	{
		return getWidth( image ) * getHeight( image );
	}

	/**
	 * Return the width of the grid stored in a PUZ image
	 * @param image The PUZ image
	 * @return The width of the grid in number of cells
	 */
	public static int getWidth( byte[] image )
	{
		return (int) ( image[ POS_WIDTH ] );
	}

	/**
	 * Return the height of the grid stored in a PUZ image
	 * @param image The PUZ image
	 * @return The height of the grid in number of cells
	 */
	public static int getHeight( byte[] image )
	{
		return (int) ( image[ POS_HEIGHT ] );
	}

	/**
	 * Return the title string stored in a PUZ image
	 * @param image The PUZ image
	 * @return The title of the puzzle read from the image
	 */
	public static String getTitle( byte[] image )
	{
		return getString( image, STRING_TITLE );
	}

	/**
	 * Return the name of the author stored in a PUZ image
	 * @param image The PUZ image
	 * @return The name of the author of the puzzle read from the image
	 */
	public static String getAuthor( byte[] image )
	{
		return getString( image, STRING_AUTHOR );
	}

	/**
	 * Return the copyright notice stored in a PUZ image
	 * @param image The PUZ image
	 * @return The copyright notice read from the image
	 */
	public static String getCopyright( byte[] image )
	{
		return getString( image, STRING_COPYRIGHT );
	}

	/**
	 * Return the notepad string stored in a PUZ image
	 * @param image The PUZ image
	 * @return The notepad string read from the image
	 */
	public static String getNotes( byte[] image )
	{
		return getString( image, STRING_FIRST_CLUE + getNumberOfClues( image ) );
	}

	/**
	 * Return a {@link PUZExtraSection} with a given title stored in a PUZ image. If no section with that title is found in the image,
	 * null is returned.
	 * @param image The PUZ image
	 * @param title The title of the extra section to find
	 * @return The extra section with the given title, or null if not found
	 */
	public static PUZExtraSection getExtraSection( byte[] image, String title )
	{
		return getExtraSections( image ).get( title );
	}

	/**
	 * Return a {@link Map} of the names of all extra sections stored in a PUZ image to {@link PUZExtraSection} objects that correspond to those sections.
	 * @param image The PUZ image
	 * @return A {@link Map} of {@link String} into {@link PUZExtraSection} for all extra sections found in the image
	 */
	public static Map<String, PUZExtraSection> getExtraSections( byte[] image )
	{
		Map<String, PUZExtraSection> sections = new HashMap<String, PUZExtraSection>();
		
		for ( int position = getExtraSectionStartPosition( image ); position >= 0 && position < image.length; )
		{
			PUZExtraSection section = getExtraSection( image, position );
			sections.put( section.getTitle(), section );
			position += SECTION_DATA_OFFSET + section.getLength() + 1;
		}

		return sections;
	}

	/**
	 * Return the starting position of the extra sections in a PUZ image
	 * @param image The PUZ image
	 * @return The start of the first extra section stored in the image
	 */
	public static int getExtraSectionStartPosition( byte[] image )
	{
		return getStringStartPosition( image, STRING_FIRST_CLUE + getNumberOfClues( image ) + 1 );
	}

	private static PUZExtraSection getExtraSection( byte[] image, int position )
	{
		StringBuilder builder = new StringBuilder();

		for ( int i = 0; i < SECTION_TITLE_LENGTH; ++i )
		{
			builder.append( (char) image[ position + i ] );
		}

		PUZExtraSection section = new PUZExtraSection();
		section.setTitle( builder.toString() );
		section.setChecksum( getUshort( image, position + SECTION_CHECKSUM_OFFSET ) );
		section.setData( getBytes( image, position + SECTION_DATA_OFFSET, getUshort( image, position + SECTION_LENGTH_OFFSET ) ) );

		return section;
	}

	/**
	 * Return the number of clues in a PUZ image
	 * @param image The PUZ image
	 * @return The number of clue strings stored in the image
	 */
	public static int getNumberOfClues( byte[] image )
	{
		return getUshort( image, POS_NUMBER_OF_CLUES );
	}

	/**
	 * Return a specific clue string stored in a PUZ image 
	 * @param image The PUZ image
	 * @param clueNumber The number of the clue. Clues are numbered incrementally starting at 0.
	 * @return The clue string read from the image
	 */
	public static String getClue( byte[] image, int clueNumber )
	{
		return getString( image, STRING_FIRST_CLUE + clueNumber );
	}

	/**
	 * Return a {@link List} of all clues stored within a PUZ image
	 * @param image The PUZ image
	 * @return A {@link List} of clues stored within the image. The index of each clue in the list corresponds to the index
	 * number used by the {@link #getClue(byte[], int)} method.
	 */
	public static List<String> getClues( byte[] image )
	{
		List<String> clues = new ArrayList<String>();

		for ( int clueNumber = 0; clueNumber < getNumberOfClues( image ); ++clueNumber )
		{
			clues.add( getClue( image, clueNumber ) );
		}

		return clues;
	}

	/**
	 * Determine if a given image is a valid PUZ file image. The {@link #FILE_MAGIC} string, overall file checksum, CIB checksum,
	 * masked checksums, and extra section checksums are all computed and compared to the values found in the PUZ image.
	 * @param image The PUZ image
	 * @return True if all tests pass and checksums match, false otherwise
	 */
	public static boolean isValidImage( byte[] image )
	{
		return
		isFileMagicValid( image ) &&
		isOverallFileChecksumValid( image ) &&
		isCIBChecksumValid( image ) &&
		isMaskedChecksumValid( image ) &&
		isExtraSectionChecksumValid( image );
	}

	private static boolean isFileMagicValid( byte[] image )
	{
		return FILE_MAGIC.equals( getFileMagic( image ) );
	}

	private static boolean isOverallFileChecksumValid( byte[] image )
	{
		return getUshort( image, POS_OVERALL_CHECKSUM ) == computeOverallFileChecksum( image );
	}

	private static int computeOverallFileChecksum( byte[] image )
	{
		int checksum = computeCIBChecksum( image );
		checksum = computeSolutionChecksum( image, checksum );
		checksum = computeGridChecksum( image, checksum );
		checksum = computePartialBoardChecksum( image, checksum );
		return checksum;
	}

	private static boolean isCIBChecksumValid( byte[] image )
	{
		return getUshort( image, POS_CIB_CHECKSUM ) == computeCIBChecksum( image );
	}

	private static int computeCIBChecksum( byte[] image )
	{
		return computeChecksum( image, POS_CIB, 8, 0 );
	}

	private static boolean isMaskedChecksumValid( byte[] image )
	{
		byte[] lowChecksums = getMaskedLowChecksums( image );
		byte[] highChecksums = getMaskedHighChecksums( image );
		byte[] lowComputedChecksums = computeMaskedLowChecksums( image );
		byte[] highComputedChecksums = computeMaskedHighChecksums( image );
		
		for ( int i = 0; i < 4; ++i )
		{
			if ( lowChecksums[ i ] != lowComputedChecksums[ i ] || highChecksums[ i ] != highComputedChecksums[ i ] )
			{
				return false;
			}
		}
		
		return true;
	}

	private static byte[] computeMaskedLowChecksums( byte[] image )
	{
		byte[] checksums = new byte[ 4 ];
		
		int cibChecksum = computeCIBChecksum( image );
		int solutionChecksum = computeSolutionChecksum( image );
		int gridChecksum = computeGridChecksum( image );
		int partialBoardChecksum = computePartialBoardChecksum( image );
		
		checksums[ 0 ] = (byte) ('I' ^ ( cibChecksum & 0xFF ));
		checksums[ 1 ] = (byte) ('C' ^ ( solutionChecksum & 0xFF ));
		checksums[ 2 ] = (byte) ('H' ^ ( gridChecksum & 0xFF ));
		checksums[ 3 ] = (byte) ('E' ^ ( partialBoardChecksum & 0xFF ));
		
		return checksums;
	}

	private static byte[] computeMaskedHighChecksums( byte[] image )
	{
		byte[] checksums = new byte[ 4 ];

		int cibChecksum = computeCIBChecksum( image );
		int solutionChecksum = computeSolutionChecksum( image );
		int gridChecksum = computeGridChecksum( image );
		int partialBoardChecksum = computePartialBoardChecksum( image );
		
		checksums[ 0 ] = (byte) ('A' ^ ( ( cibChecksum & 0xFF00 ) ) >> 8 );
		checksums[ 1 ] = (byte) ('T' ^ ( ( solutionChecksum & 0xFF00 ) ) >> 8 );
		checksums[ 2 ] = (byte) ('E' ^ ( ( gridChecksum & 0xFF00 ) ) >> 8 );
		checksums[ 3 ] = (byte) ('D' ^ ( ( partialBoardChecksum & 0xFF00 ) ) >> 8 );
		
		return checksums;
	}
	
	private static int computePartialBoardChecksum( byte[] image )
	{
		return computePartialBoardChecksum( image, 0 );
	}
	
	private static int computePartialBoardChecksum( byte[] image, int checksum )
	{
		if ( getTitle( image ).length() > 0 )
		{
			checksum = computeChecksum( image, getStringStartPosition( image, STRING_TITLE ), getTitle( image ).length() + 1, checksum );
		}
		
		if ( getAuthor( image ).length() > 0 )
		{
			checksum = computeChecksum( image, getStringStartPosition( image, STRING_AUTHOR ), getAuthor( image ).length() + 1, checksum );
		}
		
		if ( getCopyright( image ).length() > 0 )
		{
			checksum = computeChecksum( image, getStringStartPosition( image, STRING_COPYRIGHT ), getCopyright( image ).length() + 1, checksum );
		}
		
		for ( int i = 0; i < getNumberOfClues( image ); ++i )
		{
			checksum = computeChecksum( image, getStringStartPosition( image, STRING_FIRST_CLUE + i ), getClue( image, i ).length(), checksum );
		}
		
		if ( getNotes( image ).length() > 0 )
		{
			checksum = computeChecksum( image, getStringStartPosition( image, STRING_FIRST_CLUE + getNumberOfClues( image ) ), getNotes( image ).length() + 1, checksum );
		}
		
		return checksum;
	}
	
	private static int computeSolutionChecksum( byte[] image )
	{
		return computeSolutionChecksum( image, 0 );
	}

	private static int computeSolutionChecksum( byte[] image, int checksum )
	{
		return computeChecksum( image, POS_SOLUTION, getNumberOfCells( image ), checksum );
	}

	private static int computeGridChecksum( byte[] image )
	{
		return computeGridChecksum( image, 0 );
	}
	
	private static int computeGridChecksum( byte[] image, int checksum )
	{
		return computeChecksum( image, POS_SOLUTION + getNumberOfCells( image ), getNumberOfCells( image ), checksum );
	}

	private static boolean isExtraSectionChecksumValid( byte[] image )
	{
		for ( PUZExtraSection section : getExtraSections( image ).values() )
		{
			if ( section.getChecksum() != computeChecksum( section.getData(), 0, section.getLength(), 0 ) )
			{
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Determine if a PUZ image is for a diagramless puzzle or not.
	 * @param image The PUZ image
	 * @return True if the image is for a diagramless puzzle, false if for a normal puzzle
	 */
	public static boolean isDiagramless( byte[] image )
	{
		return ( getPuzzleType( image ) & PUZZLE_TYPE_DIAGRAMLESS_FLAG ) == PUZZLE_TYPE_DIAGRAMLESS_FLAG;
	}

	/**
	 * Return the number of elapsed seconds on the timer read from a PUZ image
	 * @param image The PUZ image
	 * @return The number of seconds, or -1 if the value was not found in the PUZ image
	 */
	public static int getElapsedSeconds( byte[] image )
	{
		int elapsedSeconds = -1;

		PUZExtraSection section = getExtraSection( image, LTIM_SECTION_NAME );

		if ( section != null )
		{
			elapsedSeconds = Integer.valueOf( buildString( section.getData(), 0 ).split( "," )[ 0 ] );
		}

		return elapsedSeconds;
	}

	/**
	 * Determine if the timer is running for a PUZ image
	 * @param image The PUZ image
	 * @return True if the timer is running, false if not or if not timer data was found in the image
	 */
	public static boolean isTimerRunning( byte[] image )
	{
		boolean timerRunning = false;

		PUZExtraSection section = getExtraSection( image, LTIM_SECTION_NAME );

		if ( section != null )
		{
			timerRunning = Integer.valueOf( buildString( section.getData(), 0 ).split( "," )[ 1 ] ) == 0;
		}

		return timerRunning;
	}

	private static String buildString( byte[] image, int start, int length )
	{
		StringBuilder builder = new StringBuilder();

		for ( int i = 0; start + i < image.length && i < length; ++i )
		{
			builder.append( (char) byteToUbyte( image[ start + i ] ) );
		}

		return builder.toString();
	}

	private static String buildString( byte[] image, int start )
	{
		StringBuilder builder = new StringBuilder();

		for ( int i = 0; start + i < image.length && image[ start + i ] != 0; i++ )
		{
			builder.append( (char) byteToUbyte( image[ start + i ] ) );
		}

		return builder.toString();
	}

	/**
	 * Determine if the solution for a PUZ image is encrypted or not. An encrypted image can be unlocked only with the correct unlock code.
	 * @param image The PUZ image
	 * @return True if the puzzle solution is encrypted, false if not
	 */
	public static boolean isSolutionEncrypted( byte[] image )
	{
		return getUshort( image, POS_SOLUTION_TYPE ) == SOLUTION_ENCRYPTED;
	}

	/**
	 * Return the solution for a given cell in a PUZ image. If there is a rebus entry for the cell in the GRBS/RTBL extra sections,
	 * then the rebus value is returned. Otherwise, the value of the normal solution cell is returned. 
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the solution for the desired cell
	 */
	public static PUZSolution getSolution( byte[] image, int col, int row )
	{
		PUZSolution solution = new PUZSolution();

		if ( ! isBlock( image, col, row ) )
		{
			solution.setLetter( (char) getSolutionCell( image, col, row ) );
			
			PUZExtraSection grbs = getExtraSection( image, GRBS_SECTION_NAME );
			PUZExtraSection rtbl = getExtraSection( image, RTBL_SECTION_NAME );

			if ( grbs != null && rtbl != null )
			{
				int grbsIndex = grbs.getData()[ getGridOffset( image, col, row ) ];

				if ( grbsIndex != 0 )
				{
					solution.setRebus( getRTBLValue( rtbl, grbsIndex - 1 ) );
				}
			}
		}

		return solution;
	}

	private static String getRTBLValue( PUZExtraSection rtbl, int index )
	{
		String value = null;

		for ( String rtblEntry : buildString( rtbl.getData(), 0 ).split( ";" ) )
		{
			String[] entry = rtblEntry.trim().split( ":" );

			if ( entry.length == 2 && Integer.valueOf( entry[ 0 ] ) == index )
			{
				value = entry[ 1 ];
				break;
			}
		}

		return value;
	}
	
	private static Set<Byte> blockValues = new HashSet<Byte>();
	
	static
	{
		blockValues.add( (byte) '.' );
		blockValues.add( (byte) ':' );
	}
	
	/**
	 * Determines if a specific cell in a PUZ image is playable by the solver, meaning that the solver can enter a value.
	 * A nonplayable cell is a black square or block.
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the cell is playable by the solver, false if it is a block.
	 */
	public static boolean isBlock( byte[] image, int col, int row )
	{
		return blockValues.contains( image[ POS_SOLUTION + getGridOffset( image, col, row ) ] );
	}

	private static int getGridOffset( byte[] image, int col, int row )
	{
		return row * getWidth( image ) + col;
	}

	/**
	 * Return the player state for a given cell in a PUZ image. If there is a rebus entry for the cell in the RUSR extra section,
	 * then the rebus value is returned. Otherwise, the value of the normal player state cell is returned. 
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return The value of the player state for the desired cell
	 */
	public static String getPlayerState( byte[] image, int col, int row )
	{
		String playerState = "";

		if ( ! isBlock( image, col, row ) )
		{
			playerState = Character.toString( (char) image[ POS_SOLUTION + getNumberOfCells( image ) + getGridOffset( image, col, row ) ] );
			String rusrString = getRUSRString( image, getGridOffset( image, col, row ) );

			if ( rusrString != null )
			{
				playerState = rusrString;
			}

			if ( "-".equals( playerState ) )
			{
				playerState = "";
			}
		}

		return playerState;
	}

	private static String getRUSRString( byte[] image, int gridOffset )
	{
		String string = null;
		PUZExtraSection rusr = getExtraSection( image, RUSR_SECTION_NAME );

		if ( rusr != null )
		{
			String[] strings = buildString( rusr.getData(), 0, rusr.getData().length ).split( "\0" );
			string = gridOffset < strings.length ? strings[ gridOffset ] : null;
		}

		return string;
	}

	/**
	 * Determine if the previously marked incorrect flag is set for a given solution cell in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the previously marked incorrect flag is set, false otherwise
	 */
	public static boolean isPreviouslyMarkedIncorrect( byte[] image, int col, int row )
	{
		return isGEXTFlagSet( image, col, row, PREVIOUSLY_WRONG_FLAG );
	}

	/**
	 * Determine if the currently marked incorrect flag is set for a given solution cell in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the currently marked incorrect flag is set, false otherwise
	 */
	public static boolean isCurrentlyMarkedIncorrect( byte[] image, int col, int row )
	{
		return isGEXTFlagSet( image, col, row, CURRENTLY_WRONG_FLAG );
	}

	/**
	 * Determine if the revealed flag is set for a given solution cell in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the revealed flag is set, false otherwise
	 */
	public static boolean isRevealed( byte[] image, int col, int row )
	{
		return isGEXTFlagSet( image, col, row, REVEALED_FLAG );
	}

	/**
	 * Determine if the circled flag is set for a given solution cell in a PUZ image.
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @return True if the circled flag is set, false otherwise
	 */
	public static boolean isCircled( byte[] image, int col, int row )
	{
		return isGEXTFlagSet( image, col, row, CIRCLED_FLAG );
	}

	private static boolean isGEXTFlagSet( byte[] image, int col, int row, int flag )
	{
		PUZExtraSection section = getExtraSection( image, GEXT_SECTION_NAME );

		return section != null && ( section.getData()[ getGridOffset( image, col, row ) ] & flag ) == flag ;
	}

	/**
	 * Determines the solution unlock code for a PUZ image if the solution is encrypted. The solution is found
	 * by brute-force searching of all 6,561 possible unlock codes until a working one is found by comparing
	 * the checksum of the unlocked solution to the decrypted solution checksum stored in the image. If one does exist,
	 * it will be a 4-digit number that does not use zero (0) as a digit. This method will only find the code,
	 * it will not unlock the solution. The {@link PUZEncryption} class is used to perform the unlocking.
	 * @param image The PUZ image
	 * @return The unlock code, or null if the code cannot be determined or if there is no unlock code
	 */
	public static String getUnlockCode( byte[] image )
	{
		return isSolutionEncrypted( image ) ? findUnlockCode( image ) : null;
	}

	private static String findUnlockCode(byte[] image)
	{
		byte[] encryptedSolution = getSolution( image );
		int decryptedSolutionChecksum = getDecryptedSolutionChecksum( image );
		
		for ( int a = 1; a < 10; ++a )
		{
			for ( int b = 1; b < 10; ++b )
			{
				for ( int c = 1; c < 10; ++c )
				{
					for ( int d = 1; d < 10; ++d )
					{
						int[] key = new int[] { a, b, c, d };
						byte[] decryptedSolution = PUZEncryption.decrypt( encryptedSolution, key );
						int decryptedChecksum = computeDecryptedChecksum( decryptedSolution );
						
						if ( decryptedSolutionChecksum == decryptedChecksum )
						{
							return buildKeyString( key );
						}
					}
				}
			}
		}
		
		return null;
	}

	private static int computeDecryptedChecksum( byte[] decryptedSolution )
	{
		return computeChecksum( decryptedSolution, 0, decryptedSolution.length );
	}
	
	private static String buildKeyString( int[] key )
	{
		StringBuilder builder = new StringBuilder();
		
		for ( int i = 0; i < key.length; ++i )
		{
			builder.append( key[ i ] );
		}
		
		return builder.toString();
	}
	
	private static int[] buildKey( String keyString )
	{
		int[] key = new int[ keyString.length() ];
		
		for ( int i = 0; i < keyString.length(); ++i )
		{
			key[ i ] = Integer.valueOf( keyString.substring( i, i + 1 ) );
		}
		
		return key;
	}
	
	private static byte[] getSolution( byte[] image )
	{
		List<Byte> bytes = new ArrayList<Byte>();
		
		for ( int col = 0; col < getWidth( image ); ++col)
		{
			for ( int row = 0; row < getHeight( image ); ++row )
			{
				if ( ! isBlock( image, col, row ) )
				{
					bytes.add( getSolutionCell( image, col, row ) );
				}
			}
		}
		
		byte[] out = new byte[ bytes.size() ];
		
		for ( int i = 0; i < bytes.size(); ++i )
		{
			out[ i ] = bytes.get( i );
		}
		
		return out;
	}
	
	/**
	 * Unlock the solution of a PUZ image using the given unlock code. The unlock code is not checked against the decrypted checksum
	 * stored elsewhere in the PUZ image; this method simply decrypts the
	 * solution using the given code. This method does perform destructive modification to the PUZ image - when it returns, the solution is unlocked
	 * and the cleartext is stored in the solution space of the image.
	 * @param image The PUZ image
	 * @param unlockCode The unlock code to use. This must be a 4-digit number whose digits do not include zero (0). 
	 */
	public static void unlockSolution( byte[] image, String unlockCode )
	{
		byte[] decryptedSolution = PUZEncryption.decrypt( getSolution( image ), buildKey( unlockCode ) );
		int unPos = 0;

		for ( int col = 0; col < getWidth( image ); ++col )
		{
			for ( int row = 0; row < getHeight( image ); ++row )
			{
				if ( ! isBlock( image, col, row ) )
				{
					setSolutionCell( image, col, row, decryptedSolution[ unPos++ ] );
				}
			}
		}
	}

	/**
	 * Lock the solution of a PUZ image using the given unlock code. The decrypted checksum stored elsewhere in the PUZ image is not computed
	 * nor modified; this method simply encrypts the solution using the given code.
	 * This method does perform destructive modification to the PUZ image - when it returns, the solution is locked
	 * and the encryption solution is stored in the solution space of the image.
	 * @param image The PUZ image
	 * @param unlockCode The unlock code to use. This must be a 4-digit number whose digits do not include zero (0). 
	 */
	public static void lockSolution( byte[] image, String unlockCode )
	{
		byte[] encryptedSolution = PUZEncryption.encrypt( getSolution( image ), buildKey( unlockCode ) );
		int unPos = 0;

		for ( int col = 0; col < getWidth( image ); ++col )
		{
			for ( int row = 0; row < getHeight( image ); ++row )
			{
				if ( ! isBlock( image, col, row ) )
				{
					setSolutionCell( image, col, row, encryptedSolution[ unPos++ ] );
				}
			}
		}
	}
	
	/**
	 * Set a specific solution cell in a PUZ image to a given value
	 * @param image The PUZ image
	 * @param col The column (starting at 0) of the desired cell
	 * @param row The row (starting at 0) of the desired cell
	 * @param b The value to which to set the solution cell
	 */
	public static void setSolutionCell( byte[] image, int col, int row, byte b )
	{
		image[ POS_SOLUTION + row * getWidth( image ) + col ] = b;
	}
	
	/**
	 * Copy a region of byte values from one byte array to another
	 * @param fromImage The source image
	 * @param fromOffset The offset of the start of the region in the source image
	 * @param toImage The destination image
	 * @param toOffset The offset of the start of the region in the destination image
	 * @param size The size of the region to copy
	 */
	public static void copyBytes( byte[] fromImage, int fromOffset, byte[] toImage, int toOffset, int size )
	{
		for ( int i = 0; i < size; ++i )
		{
			toImage[ toOffset + i ] = fromImage[ fromOffset + i ];
		}
	}
	
	/**
	 * Copy a region of byte values from one byte array to another. The regions begin at the start of both the source and destination images.
	 * @param fromImage The source image
	 * @param toImage The destination image
	 * @param size The size of the region to copy
	 */
	public static void copyBytes( byte[] fromImage, byte[] toImage, int size )
	{
		copyBytes( fromImage, 0, toImage, 0, size );
	}

	/**
	 * Compute the overall file checksum for a PUZ image and write it to the image
	 * @param image The PUZ image
	 */
	public static void addOverallFileChecksum( byte[] image )
	{
		copyBytes( intToUshortBytes( computeOverallFileChecksum( image ) ), 0, image, POS_OVERALL_CHECKSUM, 2 );
	}

	/**
	 * Compute the CIB checksum for a PUZ image and write it to the image
	 * @param image The PUZ image
	 */
	public static void addCIBChecksum( byte[] image )
	{
		copyBytes( intToUshortBytes( computeCIBChecksum( image ) ), 0, image, POS_CIB_CHECKSUM, 2 );
	}

	/**
	 * Compute the masked checksums for a PUZ image and write them to the image
	 * @param image The PUZ image
	 */
	public static void addMaskedChecksums( byte[] image )
	{
		copyBytes( computeMaskedLowChecksums( image ), 0, image, POS_MASKED_LOW_CHECKSUMS, 4 );
		copyBytes( computeMaskedHighChecksums( image ), 0, image, POS_MASKED_HIGH_CHECKSUMS, 4 );
	}

	/**
	 * Return a new byte[] image with the same values and length as a given byte[] image
	 * @param in The source image
	 * @return The copy of the source image
	 */
	public static byte[] clone( byte[] in )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			out[ i ] = in[ i ];
		}
		
		return out;
	}

}
