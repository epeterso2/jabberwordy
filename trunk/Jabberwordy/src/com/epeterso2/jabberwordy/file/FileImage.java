package com.epeterso2.jabberwordy.file;

import java.io.File;

public class FileImage {

	private File file = null;
	
	private byte[] data = new byte[0];
	
	public FileImage()
	{
		;
	}

	public FileImage( File file, byte[] data )
	{
		this.file = file;
		this.data = data;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}
