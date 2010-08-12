package com.epeterso2.jabberwordy.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {
	
	public static FileImage read( File file ) throws IOException
	{
		FileInputStream in = new FileInputStream( file );
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int data = -1;
		while ( ( data = in.read() ) != -1 )
		{
			out.write( data );
		}
		
		in.close();
		out.close();
		
		return new FileImage( file, out.toByteArray() );
	}
	
}
