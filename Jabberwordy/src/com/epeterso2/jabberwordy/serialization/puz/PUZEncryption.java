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
 * Provides methods to encrypt and decrypt PUZ solutions
 * <p>
 * The encryption algorithm used by these methods is a variant of a CRC-16 algorithm, described in some detail
 * in the wiki for the <a href="">PUZ</a> project. The algorithm operates only letters A through Z only; it does
 * not work for non-alphabetic characters.
 * <p>
 * There are three basic operations performed by this algorithm:
 * <ul>
 * <li><b>Scramble.</b> The first and last halves of the sequence of letters are interleaved.</li>
 * <li><b>Shift.</b> One end of the sequence of letters is removed and appended to the opposite end.</li>
 * <li><b>Rotate.</b> The individual letters are Caesar-shifted.</li>
 * </ul>
 * The amounts by which the sequence is shifted and the letters are rotated is controlled by a 4-digit key.
 * Each digit in the key must be in the range of 1 to 9, which implies that there are 6,561 unique keys.
 * <p>
 * 
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://code.google.com/p/puz/wiki/FileFormat">The PUZ Project</a> 
 */
public abstract class PUZEncryption {
	
	private static byte[] scrambleString( byte[] in )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			int index;
			
			if ( i < in.length / 2 )
			{
				index = 2 * i + 1;
			}
			
			else
			{
				index = ( i - in.length / 2 ) * 2;
			}
			
			out[ index ] = in[ i ];
		}
		
		return out;
	}
	
	private static byte[] unscrambleString( byte[] in )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			int index;
			
			if ( i % 2 == 0 )
			{
				index = in.length / 2 + i / 2;
			}
			
			else
			{
				index = i / 2;
			}
			
			out[ index ] = in[ i ];
		}
		
		return out;
	}
	
	private static byte[] shiftString( byte[] in, int size )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			int index;
			
			if ( i < size )
			{
				index = in.length - size + i;
			}
			
			else
			{
				index = i - size; 
			}
			
			out[ index ] = in[ i ];
		}
		
		return out;
	}
	
	private static byte[] unshiftString( byte[] in, int size )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			int index;
			
			if ( i < in.length - size )
			{
				index = i + size;
			}
			
			else
			{
				index = i - in.length + size;
			}
			
			out[ index ] = in[ i ];
		}
		
		return out;
	}

	/**
	 * Encrypt a sequence of letters using the PUZ algorithm and the given key. This algorithm is nondestructive to the input byte array.
	 * @param in The sequence of bytes to encrypt. The values in the array must be between 65 ('A') and 90 ('Z') inclusive.
	 * @param key An int array containing the 4 digits of the encryption key.
	 * @return The encrypted sequence of bytes.
	 */
	public static byte[] encrypt( byte[] in, int[] key )
	{
		byte[] out = clone( in );
		
		for ( int keyNumPos = 0; keyNumPos < key.length; ++keyNumPos )
		{
			for ( int i = 0; i < in.length; ++i )
			{
				out[ i ] = (byte) ( out[ i ] + key[ i % key.length ] );
				
				if ( out[ i ] > 90 )
				{
					out[ i ] -= 26;
				}
			}
			
			out = scrambleString( shiftString( out, key[ keyNumPos ] ) );
		}
		
		return out;
	}
	
	/**
	 * Decrypt a sequence of letters using the PUZ algorithm and the given key. This algorithm is nondestructive to the input byte array.
	 * @param in The sequence of bytes to decrypt. The values in the array must be between 65 ('A') and 90 ('Z') inclusive.
	 * @param key An int array containing the 4 digits of the decryption key.
	 * @return The decrypted sequence of bytes.
	 */
	public static byte[] decrypt( byte[] in, int[] key )
	{
		byte[] out = clone( in );
		
		for ( int keyNumPos = key.length - 1; keyNumPos >= 0; --keyNumPos )
		{
			out = unshiftString( unscrambleString( out ), key[ keyNumPos ] );
			
			for ( int i = 0; i < in.length; ++i )
			{
				out[ i ] = (byte) ( out[ i ] - key[ i % key.length ] );
				
				if ( out[ i ] < 65 )
				{
					out[ i ] += 26;
				}
			}
		}
		
		return out;
	}
	
	/**
	 * Encrypt a string of letters using the PUZ algorithm and the given key. This algorithm is nondestructive to the input string.
	 * @param in The ciphertext string to encrypt. The letters in the string must be between 'A' and 'Z' inclusive.
	 * @param key An int array containing the 4 digits of the encryption key.
	 * @return The encrypted string.
	 */
	public static String encrypt( String in, int[] key )
	{
		return new String( encrypt( in.getBytes(), key ) );
	}
	
	/**
	 * Decrypt a string of letters using the PUZ algorithm and the given key. This algorithm is nondestructive to the input string.
	 * @param in The ciphertext string to decrypt. The letters in the string must be between 'A' and 'Z' inclusive.
	 * @param key An int array containing the 4 digits of the decryption key.
	 * @return The decrypted string.
	 */
	public static String decrypt( String in, int[] key )
	{
		return new String( decrypt( in.getBytes(), key ) );
	}

	private static byte[] clone( byte[] in )
	{
		byte[] out = new byte[ in.length ];
		
		for ( int i = 0; i < in.length; ++i )
		{
			out[ i ] = in[ i ];
		}
		
		return out;
	}
	
}
