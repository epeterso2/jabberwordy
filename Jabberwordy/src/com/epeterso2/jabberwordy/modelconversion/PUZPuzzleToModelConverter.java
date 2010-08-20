package com.epeterso2.jabberwordy.modelconversion;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.puz.PUZPuzzle;

public class PUZPuzzleToModelConverter implements PuzzleToModelConverter {

	@Override
	public PuzzleModel convert( Object puzzle ) throws PuzzleModelConversionException
	{
		if ( ! ( puzzle instanceof PUZPuzzle ) )
		{
			throw new PuzzleModelConversionException( "Not a PUZ puzzle" );
		}
		
		PUZPuzzle puz = (PUZPuzzle) puzzle;
		PuzzleModel model = new PuzzleModel( puz.getWidth(), puz.getHeight() );
		model.setTitle( puz.getTitle() );
		model.setAuthor( puz.getAuthor() );
		
		return model;
	}

}
