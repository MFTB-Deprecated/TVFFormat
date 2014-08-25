package net.turrem.tvf.color;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.turrem.tvf.ITVFRenderInterface;

public class TVFPaletteShader extends TVFPaletteColor
{
	public String shader;
	public byte uniformChannel;

	@Override
	public int getType()
	{
		return 3;
	}

	@Override
	public void readPalette(DataInputStream data) throws IOException
	{
		super.readPalette(data);
		this.shader = data.readUTF();
		this.uniformChannel = data.readByte();
	}

	@Override
	public void writePalette(DataOutputStream data) throws IOException
	{
		super.writePalette(data);
		data.writeUTF(this.shader);
		data.writeByte(this.uniformChannel);
	}
	
	@Override
	public void startRender(ITVFRenderInterface render, Object[] pars)
	{
		render.setShader(this.shader, pars[this.uniformChannel & 0xFF]);
	}

	@Override
	public void clearRender(ITVFRenderInterface render)
	{
		render.clearShader();
	}
}
