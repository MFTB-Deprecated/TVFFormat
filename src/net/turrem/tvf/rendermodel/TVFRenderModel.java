package net.turrem.tvf.rendermodel;

import net.turrem.tvf.ITVFRenderInterface;
import net.turrem.tvf.TVFFile;
import net.turrem.tvf.color.TVFPalette;
import net.turrem.tvf.layer.TVFLayer;
import net.turrem.tvf.layer.TVFLayerFaces;

/**
 * A class to provide necessary render calls equivalent to those provided by
 * {@link net.turrem.tvf.TVFFile#renderLayers(ITVFRenderInterface, Object[])
 * renderLayers} without storing most of the file's data
 */
public class TVFRenderModel
{
	protected static class TVFRenderModelLayer
	{
		protected final TVFPalette palette;

		protected TVFRenderModelLayer(TVFPalette palette)
		{
			this.palette = palette;
		}

		protected TVFRenderModelLayer(TVFLayer layer)
		{
			if (layer instanceof TVFLayerFaces)
			{
				this.palette = ((TVFLayerFaces) layer).palette;
			}
			else
			{
				this.palette = null;
			}
		}

		public void render(ITVFRenderInterface render, Object[] pars, int index)
		{
			if (this.palette != null)
			{
				this.palette.startRender(render, pars);
				render.renderLayer(index);
				this.palette.clearRender(render);
			}
			else
			{
				render.renderLayer(index);
			}
		}
	}

	protected final TVFRenderModelLayer[] layers;

	public TVFRenderModel(TVFFile tvf)
	{
		this.layers = new TVFRenderModelLayer[256];
		for (int i = 0; i < tvf.layers.size(); i++)
		{
			if (tvf.layers.get(i) != null)
			{
				this.layers[i] = new TVFRenderModelLayer(tvf.layers.get(i));
			}
		}
	}

	/**
	 * Makes the function calls necessary to render the model in the dynamic
	 * state defined by the given parameters to the given interface
	 * 
	 * @param render The interface to call to
	 * @param pars The array of parameters that controls the dynamic elements of
	 *            the model
	 */
	public void render(ITVFRenderInterface render, Object[] pars)
	{
		if (render != null)
		{
			for (int i = 0; i < this.layers.length; i++)
			{
				TVFRenderModelLayer layer = this.layers[i];
				if (layer != null)
				{
					layer.render(render, pars, i);
				}
			}
		}
	}
}
