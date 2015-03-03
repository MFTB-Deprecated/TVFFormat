package net.turrem.voxel.tvf;

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
	/**
	 * TVF file magic number
	 */
	public final byte[] magic = { 'T', 'V', 'O', 'X' };
	/**
	 * Major file format version
	 */
	public final short majorVersion = 3;
	/**
	 * Minor file format version
	 */
	public final short minorVersion = 0;
	
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
		byte[] readMagic = new byte[4];
		data.read(this.magic);
		if (!Arrays.equals(this.magic, readMagic))
		{
			throw new IOException("Incorrect Magic Number");
		}
		
		short readVersionMajor = data.readShort();
		short readVersionMinor = data.readShort();
		
		if ((readVersionMajor != this.majorVersion) || (readVersionMinor != this.minorVersion))
		{
			throw new IOException("Unsupported File Version: " + readVersionMajor + "." + readVersionMinor);
		}
	}
	
	/**
	 * Reads a new TVFFile
	 * 
	 * @param file The file to read from
	 * @return The new TVFFile
	 * @throws IOException If something goes wrong
	 */
	public static TVFFile read(File file) throws IOException
	{
		DataInputStream input;
		try
		{
			input = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		}
		catch (IOException e)
		{
			input = new DataInputStream(new FileInputStream(file));
		}
		TVFFile tvf = new TVFFile();
		tvf.read(input);
		input.close();
		return tvf;
	}
	
	/**
	 * Writes a TVFFile
	 * 
	 * @param file The file to write to
	 * @param tvf The TVFFile to write
	 * @throws IOException If something goes wrong
	 */
	public static void write(File file, TVFFile tvf) throws IOException
	{
		write(file, tvf, true);
	}
	
	/**
	 * Writes a TVFFile
	 * 
	 * @param file The file to write to
	 * @param tvf The TVFFile to write
	 * @param compress Should the file be compressed
	 * @throws IOException If something goes wrong
	 */
	public static void write(File file, TVFFile tvf, boolean compress) throws IOException
	{
		DataOutputStream output;
		if (compress)
		{
			output = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		}
		else
		{
			output = new DataOutputStream(new FileOutputStream(file));
		}
		tvf.write(output);
		output.close();
	}
}
