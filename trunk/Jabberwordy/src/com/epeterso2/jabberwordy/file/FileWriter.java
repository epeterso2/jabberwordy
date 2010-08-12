package com.epeterso2.jabberwordy.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {
	
	public static void write( byte[] image, File file ) throws IOException
	{
		FileOutputStream out = new FileOutputStream( file );
		out.write( image );
		out.close();
	}

}
