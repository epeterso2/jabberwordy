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

package com.epeterso2.jabberwordy.util;

import java.util.EnumMap;

/**
 * Builds a numbered crossword grid based upon conventional grid-numbering rules.
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a> 
 */
public class StandardClueNumberCalculator {
	
	private enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}
	
	private int rows = 0;
	
	private int cols = 0;
	
	private CoordinateMap<Boolean> blocks = new CoordinateMap<Boolean>();

	/**
	 * Constructs a new clue number calculator.
	 * @param cols The number of columns in the grid
	 * @param rows The number of rows in the grid
	 * @param blocks A {@link CoordinateMap} of {@link Boolean} values. A cell at a given coordinate
	 * will be treated as a block (black square) if it has an entry in this map and if the entry is true.
	 */
	public StandardClueNumberCalculator( int cols, int rows, CoordinateMap<Boolean> blocks )
	{
		this.cols = cols;
		this.rows = rows;
		this.blocks = blocks;
	}

	/**
	 * Builds a numbered grid according to conventional crossword puzzle rules using the given grid size and block (black square) pattern.
	 * <p>
	 * A cell will be assigned a number only if it can be the start of either an across or down clue.
	 * A cell can be the start of an across clue if the cell to the left is a block and the cell to the right is not a block.
	 * A cell can be the start of a down clue if the cell above it is a block and the cell below it is not a block.
	 * (Virtual cells outside the borders of the grid are considered blocks.)
	 * <p>
	 * Numbers are assigned in left-to-right, top-to-bottom order, starting with the number 1. Entries in the map will also have
	 * properties set that indicate if they can be the start of an across clue or a down clue (or both).
	 * @return A {@link CoordinateMap} of {@link StandardClueNumberResult} objects which contains the calculated numbering for the grid given
	 * in the constructor.
	 */
	public CoordinateMap<StandardClueNumberResult> getNumberedGrid()
	{
		CoordinateMap<StandardClueNumberResult> grid = new CoordinateMap<StandardClueNumberResult>();
		
		int clueNumber = 0;
		
		for ( int row = 1; row <= rows; ++row )
		{
			for ( int col = 1; col <= cols; ++col )
			{
				StandardClueNumberResult clueNumberResult = new StandardClueNumberResult();
				grid.put( col, row, clueNumberResult );
				
				boolean isBlock = blocks.containsKey( col, row ) && blocks.get( col, row );
				boolean isStartOfAcrossEntry = isStartOfAcrossEntry( col, row );
				boolean isStartOfDownEntry = isStartOfDownEntry( col, row );
				
				if ( ! isBlock && ( isStartOfAcrossEntry || isStartOfDownEntry ) )
				{
					clueNumberResult.setNumber( ++clueNumber );
					clueNumberResult.setStartOfAcrossClue( isStartOfAcrossEntry );
					clueNumberResult.setStartOfDownClue( isStartOfDownEntry );
				}
			}
		}
		
		return grid;
	}

	private boolean isStartOfAcrossEntry( int col, int row )
	{
		EnumMap<Direction, Boolean> isAdjacentCellABlock = getAdjacentBlockMap( col, row );

		return isAdjacentCellABlock.get( Direction.LEFT ) && ! isAdjacentCellABlock.get( Direction.RIGHT ) ? true : false;
	}

	private boolean isStartOfDownEntry( int col, int row ) 
	{
		EnumMap<Direction, Boolean> isAdjacentCellABlock = getAdjacentBlockMap( col, row );

		return isAdjacentCellABlock.get( Direction.UP ) && ! isAdjacentCellABlock.get( Direction.DOWN ) ? true : false;
	}
	
	private EnumMap<Direction, Boolean> getAdjacentBlockMap( int col, int row )
	{
		EnumMap<Direction, Boolean> map = new EnumMap<Direction, Boolean>( Direction.class );
		
		map.put( Direction.UP,    row == 1    || blocks.containsKey( col, row - 1 ) && blocks.get( col, row - 1 ) );
		map.put( Direction.DOWN,  row == rows || blocks.containsKey( col, row + 1 ) && blocks.get( col, row + 1 ) );
		map.put( Direction.LEFT,  col == 1    || blocks.containsKey( col - 1, row ) && blocks.get( col - 1, row ) );
		map.put( Direction.RIGHT, col == cols || blocks.containsKey( col + 1, row ) && blocks.get( col + 1, row ) );
		
		return map;
	}
	
}
