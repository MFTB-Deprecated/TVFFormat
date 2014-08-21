package net.turrem.tvf.layer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.turrem.tvf.color.TVFPalette;
import net.turrem.tvf.face.TVFFace;

public class TVFLayerFaces extends TVFLayer
{
	public TVFPalette palette;
	public ArrayList<TVFFace> faces;
	
	public TVFLayerFaces()
	{
		this.faces = new ArrayList<TVFFace>();
	}
	
	@Override
	protected void readData(DataInputStream data) throws IOException
	{
	}

	@Override
	protected void writeData(DataOutputStream data) throws IOException
	{
	}
}
