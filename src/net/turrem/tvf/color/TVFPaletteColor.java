package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFPaletteColor extends TVFPalette
{
	public TVFColor[] palette = new TVFColor[256];

	@Override
	public int getType()
	{
		return 1;
	}

	public void readPalette(DataInputStream data) throws IOException
	{
		for (int i = 0; i < 256; i++)
		{
			this.palette[i] = TVFColor.read(data);
		}
	}

	public void writePalette(DataOutputStream data) throws IOException
	{
		for (int i = 0; i < 256; i++)
		{
			TVFColor.write(data, this.palette[i]);
		}
	}
}
