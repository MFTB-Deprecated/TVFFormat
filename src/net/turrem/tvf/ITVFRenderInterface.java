package net.turrem.tvf;

import net.turrem.tvf.color.EnumDynamicColorMode;

public interface ITVFRenderInterface
{
	public void setDynamicColor(EnumDynamicColorMode mode, Object color);
	
	public void clearDynamicColor();
	
	public void setShader(String shader, Object uniforms);
	
	public void clearShader();
	
	public void renderLayer(int layerindex);
}
