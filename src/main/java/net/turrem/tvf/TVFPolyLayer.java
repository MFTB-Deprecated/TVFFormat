package net.turrem.tvf;

import java.util.ArrayList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.turrem.blueutils.img.ByteImage;

public class TVFPolyLayer
{
	public ArrayList<TVFVertex> verts = new ArrayList<TVFVertex>();
	public ArrayList<TVFPolyFace> faces = new ArrayList<TVFPolyFace>();
	public ByteImage texture;
	
	public static TVFPolyLayer read(DataInputStream data) throws IOException
	{
		TVFPolyLayer poly = new TVFPolyLayer();
		poly.texture = new ByteImage(data.readInt(), data.readInt(), data);
		int size = data.readInt();
		poly.verts.ensureCapacity(size);
		for (int i = 0; i < size; i++)
		{
			poly.verts.add(TVFVertex.read(data));
		}
		size = data.readInt();
		poly.faces.ensureCapacity(size);
		for (int i = 0; i < size; i++)
		{
			poly.faces.add(TVFPolyFace.read(data));
		}
		return poly;
	}
	
	public void write(DataOutputStream data) throws IOException
	{
		data.writeInt(this.texture.width);
		data.writeInt(this.texture.height);
		this.texture.write(data);
		data.write(this.verts.size());
		for (TVFVertex vert : this.verts)
		{
			vert.write(data);
		}
		data.write(this.faces.size());
		for (TVFPolyFace face : this.faces)
		{
			face.write(data);
		}
	}
}
