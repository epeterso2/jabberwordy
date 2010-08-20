package com.epeterso2.jabberwordy.modelconversion;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.epeterso2.jabberwordy.model.PuzzleModel;
import com.epeterso2.jabberwordy.serialization.json.JSONPuzzle;
import com.epeterso2.jabberwordy.serialization.puz.PUZPuzzle;
import com.epeterso2.jabberwordy.serialization.xpf.XPFPuzzle;

public abstract class PuzzleModelFactory {
	
	private static Map<Class<?>, PuzzleToModelConverter> puzzleConverters = new HashMap<Class<?>, PuzzleToModelConverter>();
	
	private static Set<FileToModelConverter> fileConverters = new HashSet<FileToModelConverter>();
	
	static
	{
		puzzleConverters.put( PUZPuzzle.class, new PUZPuzzleToModelConverter() );
		puzzleConverters.put( XPFPuzzle.class, new XPFPuzzleToModelConverter() );
		puzzleConverters.put( JSONPuzzle.class, new JSONPuzzleToModelConverter() );
		
		fileConverters.add( new PUZFileToModelConverter() );
		fileConverters.add( new XPFFileToModelConverter() );
	}
	
	public static PuzzleModel buildPuzzleModel( Object puzzle ) throws PuzzleModelConversionException
	{
		if ( puzzleConverters.containsKey( puzzle.getClass() ) )
		{
			return puzzleConverters.get( puzzle.getClass() ).convert( puzzle );
		}
		
		else
		{
			throw new PuzzleModelConversionException( "Unrecognized puzzle object" );
		}
	}
	
	public static PuzzleModel buildPuzzleModel( File file ) throws PuzzleModelConversionException
	{
		for ( FileToModelConverter converter : fileConverters )
		{
			try
			{
				return converter.convert( file );
			}
			
			catch ( PuzzleModelConversionException e )
			{
				// Nothing to see ... move along
			}
		}
		
		throw new PuzzleModelConversionException( "Unrecognized file type" );
	}
	
}
