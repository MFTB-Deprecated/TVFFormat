package net.turrem.tvf.face;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.turrem.tvf.layer.TVFLayerFaces;

public class TVFFace
{
	public byte x;
	public byte y;
	public byte z;
	public byte direction;
	public byte color;
	public byte[] lighting;

	public TVFFace(EnumLightingType prelightingType)
	{
		switch (prelightingType)
		{
			case NONE:
				this.lighting = new byte[0];
				break;
			case SOLID:
				this.lighting = new byte[1];
				break;
			case SMOOTH:
				this.lighting = new byte[4];
				break;
		}
	}

	protected void readData(DataInputStream data) throws IOException
	{
		this.x = data.readByte();
		this.y = data.readByte();
		this.z = data.readByte();
		this.color = data.readByte();
		data.read(this.lighting);
	}

	protected void writeData(DataOutputStream data) throws IOException
	{
		data.writeByte(this.x);
		data.writeByte(this.y);
		data.writeByte(this.z);
		data.writeByte(this.color);
		data.write(this.lighting);
	}

	public static TVFFace read(DataInputStream data, TVFLayerFaces layer) throws IOException
	{
		TVFFace face = new TVFFace(layer.prelightType);
		face.readData(data);
		return face;
	}

	public static void write(DataOutputStream data, TVFFace face, TVFLayerFaces layer) throws IOException
	{
		face.writeData(data);
	}

	private final static int[][] vertDir = { new int[] { 2, 1, 3, 0 }, new int[] { 2, 3, 1, 0 }, new int[] { 2, 3, 1, 0 }, new int[] { 2, 1, 3, 0 }, new int[] { 2, 1, 3, 0 }, new int[] { 2, 3, 1, 0 } };

	/**
	 * Gets the position of a vertex in this face's vertex array given the
	 * relative location of that vertex in the current voxel.
	 * 
	 * @param x X offset (0 or 1)
	 * @param y Y offset (0 or 1)
	 * @param z Z offset (0 or 1)
	 * @return The position in this face's vertex array.
	 */
	private int getVert(int x, int y, int z)
	{
		int d = (this.direction & 0xFF) / 2;
		int k = 0;
		if (d != 0)
		{
			k <<= 1;
			k |= x;
		}
		if (d != 1)
		{
			k <<= 1;
			k |= y;
		}
		if (d != 2)
		{
			k <<= 1;
			k |= z;
		}
		return vertDir[this.direction & 0xFF][k];
	}

	/**
	 * Gets the indices in this face's vertex array of every vertex that matches
	 * the given parameters.
	 * 
	 * @param x X offset (-1 or 1, 0 matches both)
	 * @param y Y offset (-1 or 1, 0 matches both)
	 * @param z Z offset (-1 or 1, 0 matches both)
	 * @return A list of indices in this face's vertex array
	 */
	private int[] getVerts(int x, int y, int z)
	{
		int dir = (this.direction & 0xFF) / 2;
		int i0 = x == 1 ? 1 : 0;
		int j0 = y == 1 ? 1 : 0;
		int k0 = z == 1 ? 1 : 0;
		int i1 = i0 + (x == 0 && dir != 0 ? 2 : 1);
		int j1 = j0 + (y == 0 && dir != 1 ? 2 : 1);
		int k1 = k0 + (z == 0 && dir != 2 ? 2 : 1);
		int[] vrt = new int[(i1 - i0) * (j1 - j0) * (k1 - k0)];
		int v = 0;
		for (int i = i0; i < i1; i++)
		{
			for (int j = j0; j < j1; j++)
			{
				for (int k = k0; k < k1; k++)
				{
					vrt[v++] = this.getVert(i, j, k);
				}
			}
		}
		return vrt;
	}

	/**
	 * Multiplies the light values of every vertex that matches the given
	 * parameters.
	 * 
	 * @param x X offset (-1 or 1, 0 matches both)
	 * @param y Y offset (-1 or 1, 0 matches both)
	 * @param z Z offset (-1 or 1, 0 matches both)
	 * @param value The value to multiply the matching vertices' light levels
	 *            by.
	 */
	public void multiplyLight(int x, int y, int z, float value)
	{
		int[] verts = this.getVerts(x, y, z);
		if (this.lighting.length == 4)
		{
			for (int v : verts)
			{
				this.lighting[v] = (byte) ((this.lighting[v] & 0xFF) * value);
			}
		}
		else if (this.lighting.length == 1)
		{
			float av = verts.length / 4.0F;
			value = (1.0F - av) + (value * av);
			this.lighting[0] = (byte) ((this.lighting[0] & 0xFF) * value);
		}
	}

	/**
	 * Adds to the light values of every vertex that matches the given
	 * parameters.
	 * 
	 * @param x X offset (-1 or 1, 0 matches both)
	 * @param y Y offset (-1 or 1, 0 matches both)
	 * @param z Z offset (-1 or 1, 0 matches both)
	 * @param value The value to add to the matching vertices' light levels.
	 */
	public void addLight(int x, int y, int z, float value)
	{
		int[] verts = this.getVerts(x, y, z);
		if (this.lighting.length == 4)
		{
			for (int v : verts)
			{
				this.lighting[v] = (byte) ((this.lighting[v] & 0xFF) + value);
			}
		}
		else if (this.lighting.length == 1)
		{
			float av = verts.length / 4.0F;
			this.lighting[0] = (byte) ((this.lighting[0] & 0xFF) + (value * av));
		}
	}

	/**
	 * Sets the light values of every vertex that matches the given parameters.
	 * 
	 * @param x X offset (-1 or 1, 0 matches both)
	 * @param y Y offset (-1 or 1, 0 matches both)
	 * @param z Z offset (-1 or 1, 0 matches both)
	 * @param value The value to set the matching vertices' light levels to.
	 */
	public void setLight(int x, int y, int z, float value)
	{
		int[] verts = this.getVerts(x, y, z);
		if (this.lighting.length == 4)
		{
			for (int v : verts)
			{
				this.lighting[v] = (byte) (value * 0xFF);
			}
		}
		else if (this.lighting.length == 1)
		{
			float av = verts.length / 4.0F;
			this.lighting[0] = (byte) (((this.lighting[0] & 0xFF) * (1.0F - av)) + (value * av));
		}
	}
}
