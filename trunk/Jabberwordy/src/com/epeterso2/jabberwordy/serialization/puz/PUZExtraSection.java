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

/**
 * Represents an extra section of a PUZ image. An extra section has four parts:
 * <ul>
 * <li>Title</li> - A 4-character string that identifies the type of section
 * <li>Checksum</li> - A Checksum of the data
 * <li>Data</li> - The data itself
 * </ul>
 * Extra sections in a PUZ image are null-terminated. The null is not considered part of the data for the purposes of checksumming, and the
 * null is not included in the end of the data in this object representation.
 * <p>
 * Currently, five types of extra sections are known:
 * <ul>
 * <li><b>GEXT</b> - Contains cell style information. See {@link PUZCellStyle} for details.</li> 
 * <li><b>LTIM</b> - Contains timer information</li> 
 * <li><b>GRBS</b> - Contains a grid map of solution rebus entries</li> 
 * <li><b>RTBL</b> - Contains the index of solution rebus values</li> 
 * <li><b>RUSR</b> - Contains a map of rebus player state (answers)</li> 
 * </ul>
 * This class is used by the {@link PUZUtil}, {@link PUZPuzzleOutputStream}, and {@link PUZPuzzleInputStream} classes for manipulating PUZ images.
 * It is not referenced by the {@link PUZPuzzle} class.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat">The PUZ Project</a>
 */
public class PUZExtraSection {
	
	private String title = null;
	
	private int checksum = 0;
	
	private byte[] data = null;

	/**
	 * Returns the title of the section
	 * @return The section title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the section
	 * @param title The section title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the length of the data in this section
	 * @return The length of the data
	 */
	public int getLength() {
		return getData().length;
	}

	/**
	 * Returns the checksum of the data region of this section
	 * @return The checksum
	 */
	public int getChecksum() {
		return checksum;
	}

	/**
	 * Sets the checksum of the data region of this section
	 * @param checksum
	 */
	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	/**
	 * Returns the data of this section
	 * @return The data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data of this section
	 * @param data The data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}
	
	/**
	 * Returns a hash code value for the object.
	 */
	@Override
	public int hashCode()
	{
		return 0;
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 */
	@Override
	public boolean equals( Object object )
	{
		if ( object == null || ! ( object instanceof PUZExtraSection ) )
		{
			return false;
		}

		else
		{
			PUZExtraSection that = (PUZExtraSection) object;
			
			return
				equal( this.getTitle(), that.getTitle() ) &&
				this.getChecksum() == that.getChecksum() &&
				equalByteArrays( this.getData(), that.getData() ) &&
				true;
		}
	}
	
	private boolean equalByteArrays( byte[] one, byte[] two )
	{
		if ( one == null )
		{
			return two == null;
		}
		
		else if ( two == null || one.length != two.length )
		{
			return false;
		}
		
		else
		{
			for ( int i = 0; i < one.length; ++i )
			{
				if ( one[ i ] != two[ i ] )
				{
					return false;
				}
			}
		}
		
		return true;
	}

	private boolean equal( Object one, Object two )
	{
		return one == null ? two == null : one.equals( two );
	}

}
