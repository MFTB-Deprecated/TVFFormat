package net.turrem.tvf.layer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.turrem.tvf.color.TVFPalette;
import net.turrem.tvf.face.EnumLightingType;
import net.turrem.tvf.face.TVFFace;

public class TVFLayerFaces extends TVFLayer
{	
	public TVFPalette palette;
	public EnumLightingType prelightType;
	public ArrayList<TVFFace> faces;
	
	public TVFLayerFaces()
	{
		this.faces = new ArrayList<TVFFace>();
	}
	
	@Override
	protected void readData(DataInputStream data) throws IOException
	{
		this.palette = TVFPalette.read(data);
		int faceCount = data.readInt();
		this.faces.ensureCapacity(faceCount);
		for (int i = 0; i < faceCount; i++)
		{
			this.faces.add(TVFFace.read(data, this));
		}
		this.faces.trimToSize();
	}

	@Override
	protected void writeData(DataOutputStream data) throws IOException
	{
		TVFPalette.write(data, this.palette);
		data.writeInt(this.faces.size());
		for (TVFFace face : this.faces)
		{
			TVFFace.write(data, face, this);
		}
	}
}
