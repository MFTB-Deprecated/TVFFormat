package net.turrem.tvf.face;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.turrem.tvf.layer.TVFLayerFaces;

public class TVFFace
{
	public byte x;
	public byte y;
	public byte z;
	public byte direction;
	public byte color;
	public byte[] lighting = new byte[4];

	protected void readData(DataInputStream data) throws IOException
	{
		this.x = data.readByte();
		this.y = data.readByte();
		this.z = data.readByte();
		this.color = data.readByte();
		data.readFully(this.lighting);
	}

	protected void writeData(DataOutputStream data) throws IOException
	{
		data.writeByte(this.x);
		data.writeByte(this.y);
		data.writeByte(this.z);
		data.writeByte(this.color);
	}

	public static TVFFace read(DataInputStream data) throws IOException
	{
		TVFFace face = new TVFFace();
		face.readData(data);
		return face;
	}

	public static void write(DataOutputStream data, TVFFace face, TVFLayerFaces layer) throws IOException
	{
		face.writeData(data);
	}
}
