package net.turrem.tvf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TVFFile
{
	public final byte[] magic = {'T', 'V', 'F', 0x00};
	public final short majorVersion = 1;
	public final short minorVersion = 0;
	
	public short width;
	public short height;
	public short length;
	
	public short layerCount;
	
	public TVFFile()
	{
		
	}
	
	private void write(DataOutputStream data) throws IOException
	{
		data.write(this.magic);
		data.writeShort(this.majorVersion);
		data.writeShort(this.minorVersion);
	}
	
	private void read(DataInputStream data) throws IOException
	{
		byte[] readMagic = new byte[3];
		data.read(magic);
		if (!Arrays.equals(magic, readMagic))
		{
			throw new IOException("Incorrect Magic Number");
		}
	}
	
	public TVFFile read(File file) throws IOException
	{
		DataInputStream input = new DataInputStream(new GZIPInputStream( new FileInputStream(file)));
		TVFFile tvf = new TVFFile();
		tvf.read(input);
		input.close();
		return tvf;
	}
	
	public void write(File file, TVFFile tvf) throws IOException
	{
		DataOutputStream output = new DataOutputStream(new GZIPOutputStream( new FileOutputStream(file)));
		tvf.write(output);
		output.close();
	}
}
