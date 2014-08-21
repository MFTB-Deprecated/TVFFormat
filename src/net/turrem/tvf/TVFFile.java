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

import net.turrem.tvf.layer.TVFLayer;
import net.turrem.tvf.layer.TVFLayerFaces;

public class TVFFile
{
	public final byte[] magic = { 'T', 'V', 'F', 0x00 };
	public final short majorVersion = 1;
	public final short minorVersion = 0;

	public short width;
	public short height;
	public short length;

	public TVFLayer[] layers = new TVFLayer[256];

	public TVFFile()
	{

	}

	private void write(DataOutputStream data) throws IOException
	{
		data.write(this.magic);
		data.writeShort(this.majorVersion);
		data.writeShort(this.minorVersion);

		data.writeShort(this.width);
		data.writeShort(this.height);
		data.writeShort(this.length);

		for (int i = 0; i < 256; i++)
		{
			this.writeLayer(data, this.layers[i]);
		}
	}

	private void read(DataInputStream data) throws IOException
	{
		byte[] readMagic = new byte[3];
		data.read(magic);
		if (!Arrays.equals(magic, readMagic))
		{
			throw new IOException("Incorrect Magic Number");
		}

		short readVersionMajor = data.readShort();
		short readVersionMinor = data.readShort();

		if ((readVersionMajor != this.majorVersion) || (readVersionMinor != this.minorVersion))
		{
			throw new IOException("Unsupported File Version: " + readVersionMajor + "." + readVersionMinor);
		}

		this.width = data.readShort();
		this.height = data.readShort();
		this.length = data.readShort();

		for (int i = 0; i < 256; i++)
		{
			this.layers[i] = this.readLayer(data);
		}
	}

	private void writeLayer(DataOutputStream data, TVFLayer layer) throws IOException
	{
		if (layer == null)
		{
			data.writeByte(0);
		}
		else
		{
			data.writeByte(0xFF);
			layer.writeLayer(data);
		}
	}

	private TVFLayer readLayer(DataInputStream data) throws IOException
	{
		if (data.readByte() == 0xFF)
		{
			TVFLayer layer = new TVFLayerFaces();
			layer.readLayer(data);
			return layer;
		}
		return null;
	}

	public TVFFile read(File file) throws IOException
	{
		DataInputStream input = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		TVFFile tvf = new TVFFile();
		tvf.read(input);
		input.close();
		return tvf;
	}

	public void write(File file, TVFFile tvf) throws IOException
	{
		DataOutputStream output = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		tvf.write(output);
		output.close();
	}
}
