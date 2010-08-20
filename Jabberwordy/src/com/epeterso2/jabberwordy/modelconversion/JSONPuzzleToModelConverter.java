package com.epeterso2.jabberwordy.modelconversion;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.json.JSONPuzzle;

public class JSONPuzzleToModelConverter implements PuzzleToModelConverter {

	@Override
	public PuzzleModel convert( Object puzzle ) throws PuzzleModelConversionException
	{
		JSONPuzzle json = (JSONPuzzle) puzzle;
		PuzzleModel model = new PuzzleModel( json.getSize().getCols(), json.getSize().getRows() );
		
		return model;
	}

}
