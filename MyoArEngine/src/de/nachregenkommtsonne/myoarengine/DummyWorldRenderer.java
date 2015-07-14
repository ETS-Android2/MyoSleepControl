package de.nachregenkommtsonne.myoarengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import de.nachregenkommtsonne.myoarengine.utility.Vector;

public class DummyWorldRenderer
{
	int _numVertices= 2 * 201 * 2 * 2;
	FloatBuffer _vertices;
	FloatBuffer _colorBuffer;
	ShortBuffer _indexBuffer;

	public DummyWorldRenderer()
	{
		ByteBuffer vbb = ByteBuffer.allocateDirect(_numVertices * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		_vertices = vbb.asFloatBuffer();

		ByteBuffer cbb = ByteBuffer.allocateDirect(_numVertices * 4 * 4);
		cbb.order(ByteOrder.nativeOrder());
		_colorBuffer = cbb.asFloatBuffer();

		ByteBuffer ibb = ByteBuffer.allocateDirect(_numVertices * 2);
		ibb.order(ByteOrder.nativeOrder());
		_indexBuffer = ibb.asShortBuffer();

		float ebene = -9.0f;
		short num = 0;
		for (int i = -100; i <= 100; i++)
		{
			_vertices.put(new Vector(-100.0f, i, ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(100.0f, i, ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(i, -100.0f, ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(i, 100.0f, ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(-100.0f, i, -ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(100.0f, i, -ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(i, -100.0f, -ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);

			_vertices.put(new Vector(i, 100.0f, -ebene).getValues());
			_colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
			_indexBuffer.put(num++);
		}

		_vertices.position(0);
		_indexBuffer.position(0);
		_colorBuffer.position(0);
	}
	
	void render(GL10 gl, Vector vector)
	{
    _vertices.put(new Vector().getValues());
    _vertices.put(vector.getValues());
	  _vertices.position(0);
	  
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _vertices);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
		gl.glDrawElements(GL10.GL_LINES, _numVertices, GL10.GL_UNSIGNED_SHORT,
				_indexBuffer);
	}
}