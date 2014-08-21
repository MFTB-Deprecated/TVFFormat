package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TVFPalette
{
	public static TVFPalette make(int type)
	{
		switch (type)
		{
			case 1:
				return new TVFPaletteColor();
			case 2:
				return new TVFPaletteDynamic();
			default:
				return null;
		}
	}
	
	public abstract int getType();
	
	public abstract void readPalette(DataInputStream data) throws IOException;

	public abstract void writePalette(DataOutputStream data) throws IOException;
}
