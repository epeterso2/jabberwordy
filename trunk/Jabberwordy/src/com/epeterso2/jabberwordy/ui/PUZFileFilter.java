package com.epeterso2.jabberwordy.ui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PUZFileFilter extends FileFilter {

	@Override
	public boolean accept( File f )
	{
		return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith( ".puz" );
	}

	@Override
	public String getDescription()
	{
		return "PUZ Files";
	}

}
