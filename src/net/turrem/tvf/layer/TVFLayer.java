package net.turrem.tvf.layer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TVFLayer
{	
	public short xOffset;
	public short yOffset;
	public short zOffset;

	public void readLayer(DataInputStream data) throws IOException
	{
		this.xOffset = data.readShort();
		this.yOffset = data.readShort();
		this.zOffset = data.readShort();
	}
	
	protected abstract void readData(DataInputStream data) throws IOException;
	
	public void writeLayer(DataOutputStream data) throws IOException
	{
		data.writeShort(this.xOffset);
		data.writeShort(this.yOffset);
		data.writeShort(this.zOffset);
	}

	protected abstract void writeData(DataOutputStream data) throws IOException;
}
