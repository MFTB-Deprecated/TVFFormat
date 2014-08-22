package net.turrem.tvf.face;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFFace
{
	protected void readData(DataInputStream data) throws IOException
	{
		
	}

	protected void writeData(DataOutputStream data) throws IOException
	{
		
	}
	
	public static TVFFace read(DataInputStream data) throws IOException
	{
		TVFFace face = new TVFFace();
		face.readData(data);
		return face;
	}

	public static void write(DataOutputStream data, TVFFace face) throws IOException
	{
		face.writeData(data);
	}
}
