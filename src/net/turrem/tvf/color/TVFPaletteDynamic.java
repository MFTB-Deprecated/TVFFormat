package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFPaletteDynamic extends TVFPaletteColor
{
	@Override
	public int getType()
	{
		return 2;
	}
	
	@Override
	public void readPalette(DataInputStream data) throws IOException
	{
		super.readPalette(data);
	}

	@Override
	public void writePalette(DataOutputStream data) throws IOException
	{
		super.writePalette(data);
	}
}
