package net.turrem.tvf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFPolyFace
{
	public final int[] verts = new int[3];
	
	public static TVFPolyFace read(DataInputStream data) throws IOException
	{
		TVFPolyFace f = new TVFPolyFace();
		f.verts[0] = data.readInt();
		f.verts[1] = data.readInt();
		f.verts[2] = data.readInt();
		return f;
	}
	
	public void write(DataOutputStream data) throws IOException
	{
		data.write(this.verts[0]);
		data.write(this.verts[1]);
		data.write(this.verts[2]);
	}
}
