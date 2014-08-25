package net.turrem.tvf;

import net.turrem.tvf.color.EnumDynamicColorMode;
import net.turrem.tvf.layer.TVFLayer;

public interface ITVFRenderInterface
{
	public void setDynamicColor(EnumDynamicColorMode mode, Object color);
	
	public void clearDynamicColor();
	
	public void setShader(String shader, Object uniforms);
	
	public void clearShader();
	
	public void renderLayer(int layerindex);
	
	public void renderLayer(int layerindex, Object visiblePar);
	
	public void loadLayer(int layerindex, TVFLayer layer);
}