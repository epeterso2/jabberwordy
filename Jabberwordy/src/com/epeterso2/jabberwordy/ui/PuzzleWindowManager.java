package com.epeterso2.jabberwordy.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.epeterso2.jabberwordy.model.PuzzleModel;

public abstract class PuzzleWindowManager {
	
	private static Map<File, PuzzleSolvingWindow> windows = new HashMap<File, PuzzleSolvingWindow>();
	
	public synchronized static void launchPuzzleSolvingWindow( PuzzleModel model )
	{
		if ( ! windows.containsKey( model.getFile() ) )
		{
			windows.put( model.getFile(), new PuzzleSolvingWindow( model ) );
		}
		
		windows.get( model.getFile() ).setVisible( true );
	}
	
	public synchronized static void deregisterPuzzleSolvingWindow( PuzzleSolvingWindow window )
	{
		File key = null;
		
		for ( Map.Entry<File, PuzzleSolvingWindow> entry : windows.entrySet() )
		{
			if ( entry.getValue() == window )
			{
				key = entry.getKey();
				break;
			}
		}
		
		if ( key != null )
		{
			windows.remove( key );
		}
	}

}
