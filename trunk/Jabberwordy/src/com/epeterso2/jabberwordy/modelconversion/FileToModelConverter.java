package com.epeterso2.jabberwordy.modelconversion;

import java.io.File;

import com.epeterso2.jabberwordy.model.PuzzleModel;

public interface FileToModelConverter {
	
	public PuzzleModel convert( File file ) throws PuzzleModelConversionException;

}
