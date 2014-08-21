package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TVFColor
{
	private byte red;
	private byte green;
	private byte blue;

	public TVFColor(float red, float green, float blue)
	{
		this.red = (byte) (red * 0xFF);
		this.green = (byte) (green * 0xFF);
		this.blue = (byte) (blue * 0xFF);
	}

	public TVFColor(int red, int green, int blue)
	{
		this.red = (byte) red;
		this.green = (byte) green;
		this.blue = (byte) blue;
	}

	public byte getRed()
	{
		return this.red;
	}

	public void setRed(int red)
	{
		this.red = (byte) red;
	}

	public byte getGreen()
	{
		return this.green;
	}

	public void setGreen(int green)
	{
		this.green = (byte) green;
	}

	public byte getBlue()
	{
		return this.blue;
	}

	public void setBlue(int blue)
	{
		this.blue = (byte) blue;
	}

	public int getRedInt()
	{
		return this.red & 0xFF;
	}

	public int getGreenInt()
	{
		return this.green & 0xFF;
	}

	public int getBlueInt()
	{
		return this.blue & 0xFF;
	}

	public void setRed(float red)
	{
		this.red = (byte) (red * 0xFF);
	}

	public void setGreen(float green)
	{
		this.green = (byte) (green * 0xFF);
	}

	public void setBlue(float blue)
	{
		this.blue = (byte) (blue * 0xFF);
	}

	public int getRGB()
	{
		return (this.getRedInt() << 0) | (this.getGreenInt() << 8) | (this.getBlueInt() << 16);
	}

	public static TVFColor read(DataInputStream data) throws IOException
	{
		int r = data.readByte() & 0xFF;
		int g = data.readByte() & 0xFF;
		int b = data.readByte() & 0xFF;
		if (r == 0 && g == 0 && b == 0)
		{
			return null;
		}
		else
		{
			return new TVFColor(r, g, b);
		}
	}

	public static void write(DataOutputStream data, TVFColor color) throws IOException
	{
		if (color == null)
		{
			data.writeByte(0);
			data.writeByte(0);
			data.writeByte(0);
		}
		else
		{
			data.writeByte(color.getRedInt());
			data.writeByte(color.getGreenInt());
			data.writeByte(color.getBlueInt());
		}
	}
}
