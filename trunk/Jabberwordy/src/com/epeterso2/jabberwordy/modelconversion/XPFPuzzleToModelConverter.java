package com.epeterso2.jabberwordy.modelconversion;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.xpf.XPFPuzzle;

public class XPFPuzzleToModelConverter implements PuzzleToModelConverter {

	@Override
	public PuzzleModel convert( Object puzzle ) throws PuzzleModelConversionException
	{
		XPFPuzzle xpf = (XPFPuzzle) puzzle;
		PuzzleModel model = new PuzzleModel( xpf.getCols(), xpf.getRows() );
		
		model.setTitle( xpf.getTitle() );
		model.setAuthor( xpf.getAuthor() );
		
		return model;
	}

}
