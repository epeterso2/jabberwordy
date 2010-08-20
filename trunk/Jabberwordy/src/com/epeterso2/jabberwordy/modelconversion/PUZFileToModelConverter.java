package com.epeterso2.jabberwordy.modelconversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.puz.PUZPuzzleOutputStream;

public class PUZFileToModelConverter implements FileToModelConverter {

	@Override
	public PuzzleModel convert( File file ) throws PuzzleModelConversionException
	{
		try
		{
			return PuzzleModelFactory.buildPuzzleModel( new PUZPuzzleOutputStream( new FileInputStream( file ) ).toPuzzle() );
		}
		
		catch ( IOException e )
		{
			throw new PuzzleModelConversionException( e );
		}
	}

}
