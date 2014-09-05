package net.turrem.tvf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.turrem.tvf.layer.TVFLayer;
import net.turrem.tvf.layer.TVFLayerFaces;
import net.turrem.tvf.rendermodel.TVFRenderModel;

public class TVFFile
{
	/**
	 * TVF file magic number
	 */
	public final byte[] magic = { 'T', 'V', 'F', 0x00 };
	/**
	 * Major file format version
	 */
	public final short majorVersion = 1;
	/**
	 * Minor file format version
	 */
	public final short minorVersion = 2;

	/**
	 * The total width (x) of the 3D model
	 */
	public short width;
	/**
	 * The total height (y) of the 3D model
	 */
	public short height;
	/**
	 * The total length (z) of the 3D model
	 */
	public short length;

	/**
	 * The layers that comprise this model
	 */
	public ArrayList<TVFLayer> layers = new ArrayList<TVFLayer>();

	public TVFFile()
	{

	}

	/**
	 * Makes the function calls necessary to render the model in the dynamic state defined by the given parameters to the given interface 
	 * @param render The interface to call to
	 * @param pars The array of parameters that controls the dynamic elements of the model
	 */
	public void renderLayers(ITVFRenderInterface render, Object[] pars)
	{
		if (render != null)
		{
			for (int i = 0; i < this.layers.size(); i++)
			{
				TVFLayer layer = this.layers.get(i);
				if (layer != null)
				{
					Object vis = pars[layer.visibleChannel & 0xFF];
					if (vis instanceof Boolean && (boolean) vis)
					{
						layer.render(render, pars, i);
					}
					else
					{
						render.renderLayer(i, vis);
					}
				}
			}
		}
	}
	
	/**
	 * Gets a class to provide necessary render calls equivalent to those provided by {@link #renderLayers(ITVFRenderInterface, Object[]) renderLayers} without storing most of the file's data
	 * @return A class to provide render calls
	 */
	public TVFRenderModel getRender()
	{
		return new TVFRenderModel(this);
	}

	private void write(DataOutputStream data) throws IOException
	{
		data.write(this.magic);
		data.writeShort(this.majorVersion);
		data.writeShort(this.minorVersion);

		data.writeShort(this.width);
		data.writeShort(this.height);
		data.writeShort(this.length);

		int layernum = Math.min(this.layers.size(), Short.MAX_VALUE);
		data.writeShort(layernum);
		for (int i = 0; i < layernum; i++)
		{
			this.writeLayer(data, this.layers.get(i));
		}
	}

	private void read(DataInputStream data) throws IOException
	{
		byte[] readMagic = new byte[3];
		data.read(magic);
		if (!Arrays.equals(magic, readMagic))
		{
			throw new IOException("Incorrect Magic Number");
		}

		short readVersionMajor = data.readShort();
		short readVersionMinor = data.readShort();

		if ((readVersionMajor != this.majorVersion) || (readVersionMinor != this.minorVersion))
		{
			throw new IOException("Unsupported File Version: " + readVersionMajor + "." + readVersionMinor);
		}

		this.width = data.readShort();
		this.height = data.readShort();
		this.length = data.readShort();

		short layernum = data.readShort();
		this.layers.ensureCapacity(layernum);
		for (int i = 0; i < layernum; i++)
		{
			this.layers.add(this.readLayer(data));
		}
		this.layers.trimToSize();
	}

	private void writeLayer(DataOutputStream data, TVFLayer layer) throws IOException
	{
		if (layer == null)
		{
			data.writeByte(0);
		}
		else
		{
			data.writeByte(0xFF);
			layer.writeLayer(data);
		}
	}

	private TVFLayer readLayer(DataInputStream data) throws IOException
	{
		if (data.readByte() == 0xFF)
		{
			TVFLayer layer = new TVFLayerFaces();
			layer.readLayer(data);
			return layer;
		}
		return null;
	}

	/**
	 * Reads a new TVFFile
	 * @param file The file to read from
	 * @return The new TVFFile
	 * @throws IOException If something goes wrong
	 */
	public static TVFFile read(File file) throws IOException
	{
		DataInputStream input = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		TVFFile tvf = new TVFFile();
		tvf.read(input);
		input.close();
		return tvf;
	}

	/**
	 * Writes a TVFFile
	 * @param file The file to write to
	 * @param tvf The TVFFile to write
	 * @throws IOException If something goes wrong
	 */
	public static void write(File file, TVFFile tvf) throws IOException
	{
		DataOutputStream output = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		tvf.write(output);
		output.close();
	}
}
