package net.turrem.tvf.face;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.turrem.tvf.layer.TVFLayerFaces;

public class TVFFace
{
	public static enum EnumFaceDirection
	{
		XUP((byte) 0),
		XDOWN((byte) 1),
		YUP((byte) 2),
		YDOWN((byte) 3),
		ZUP((byte) 4),
		ZDOWN((byte) 5);
		
		public static final EnumFaceDirection[] dirs = new EnumFaceDirection[] {XUP, XDOWN, YUP, YDOWN, ZUP, ZDOWN};
		public final byte num;
		
		EnumFaceDirection(byte num)
		{
			this.num = num;
		}
	}
	
	public byte x;
	public byte y;
	public byte z;
	public byte direction;
	public byte color;
	public byte[] lighting;
	
	public TVFFace(EnumLightingType prelightingType)
	{
		switch (prelightingType)
		{
			case NONE:
				this.lighting = new byte[0];
				break;
			case SOLID:
				this.lighting = new byte[1];
				break;
			case SMOOTH:
				this.lighting = new byte[4];
				break;
		}
	}
	
	protected void readData(DataInputStream data) throws IOException
	{
		this.x = data.readByte();
		this.y = data.readByte();
		this.z = data.readByte();
		this.color = data.readByte();
		data.read(this.lighting);
	}

	protected void writeData(DataOutputStream data) throws IOException
	{
		data.writeByte(this.x);
		data.writeByte(this.y);
		data.writeByte(this.z);
		data.writeByte(this.color);
		data.write(this.lighting);
	}
	
	public static TVFFace read(DataInputStream data, TVFLayerFaces layer) throws IOException
	{
		TVFFace face = new TVFFace(layer.prelightType);
		face.readData(data);
		return face;
	}

	public static void write(DataOutputStream data, TVFFace face, TVFLayerFaces layer) throws IOException
	{
		face.writeData(data);
	}
}
