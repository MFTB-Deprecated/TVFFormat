package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFPaletteDynamic extends TVFPaletteColor
{
	public static enum EnumDynamicColorMode
	{
		MIX,
		MULTIPLY,
		ADD,
		SET;
	}
	
	public EnumDynamicColorMode mode;
	public int colorChannel;
	
	@Override
	public int getType()
	{
		return 2;
	}
	
	@Override
	public void readPalette(DataInputStream data) throws IOException
	{
		super.readPalette(data);
		this.mode = EnumDynamicColorMode.values()[data.readByte() & 0xFF];
		this.colorChannel = data.readByte() & 0xFF;
	}

	@Override
	public void writePalette(DataOutputStream data) throws IOException
	{
		super.writePalette(data);
		data.writeByte(this.mode.ordinal());
		data.writeByte(this.colorChannel);
	}
}
