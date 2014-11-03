package net.turrem.tvf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFVertex
{
	public final float[] pos = new float[3];
	public final float[] norm = new float[3];
	public final float[] uv = new float[2];
	
	public static TVFVertex read(DataInputStream data) throws IOException
	{
		TVFVertex v = new TVFVertex();
		v.pos[0] = data.readFloat();
		v.pos[1] = data.readFloat();
		v.pos[2] = data.readFloat();
		v.norm[0] = data.readFloat();
		v.norm[1] = data.readFloat();
		v.norm[2] = data.readFloat();
		v.uv[0] = data.readFloat();
		v.uv[1] = data.readFloat();
		return v;
	}
	
	public void write(DataOutputStream data) throws IOException
	{
		data.writeFloat(this.pos[0]);
		data.writeFloat(this.pos[1]);
		data.writeFloat(this.pos[2]);
		data.writeFloat(this.norm[0]);
		data.writeFloat(this.norm[1]);
		data.writeFloat(this.norm[2]);
		data.writeFloat(this.uv[0]);
		data.writeFloat(this.uv[1]);
	}
}
