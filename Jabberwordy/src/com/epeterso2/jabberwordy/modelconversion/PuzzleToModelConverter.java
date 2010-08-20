package com.epeterso2.jabberwordy.modelconversion;

import com.epeterso2.jabberwordy.model.PuzzleModel;

public interface PuzzleToModelConverter {
	
	public PuzzleModel convert( Object puzzle ) throws PuzzleModelConversionException;

}
