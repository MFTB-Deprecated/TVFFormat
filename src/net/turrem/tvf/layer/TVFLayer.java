package net.turrem.tvf.layer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TVFLayer
{
	public static TVFLayer make(int type) throws IOException
	{
		switch (type)
		{
			case 0:
				return null;
			default:
				return null;
		}
	}

	public void readLayer(DataInputStream data) throws IOException
	{
		
	}
	
	protected abstract void readData(DataInputStream data) throws IOException;
	
	public void writeLayer(DataOutputStream data) throws IOException
	{
		data.writeByte(this.getType());
	}

	protected abstract void writeData(DataOutputStream data) throws IOException;
	
	public abstract int getType();
}
