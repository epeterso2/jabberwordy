package com.epeterso2.jabberwordy.modelconversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.xpf.XPFPuzzleCollection;
import com.epeterso2.jabberwordy.serialization.xpf.XPFPuzzleOutputStream;

public class XPFFileToModelConverter implements FileToModelConverter {

	@Override
	public PuzzleModel convert( File file ) throws PuzzleModelConversionException
	{
		XPFPuzzleCollection collection;
		
		try
		{
			collection = new XPFPuzzleOutputStream( new FileInputStream( file ) ).toPuzzle();
		}
		
		catch ( IOException e )
		{
			throw new PuzzleModelConversionException( e );
		}
		
		if ( collection.size() != 1 )
		{
			throw new PuzzleModelConversionException( "Cannot convert XPF file with multiple puzzles" );
		}
		
		return new XPFPuzzleToModelConverter().convert( collection.get( 0 ) );
	}

}
