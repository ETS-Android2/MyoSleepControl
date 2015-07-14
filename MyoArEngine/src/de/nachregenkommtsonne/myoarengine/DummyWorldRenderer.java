package de.nachregenkommtsonne.myoarengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import de.nachregenkommtsonne.myoarengine.utility.Vector;

public class DummyWorldRenderer
{
  int _numVerticesBase = 2 * 201 * 2 * 2;
  FloatBuffer _verticesBase;
  // FloatBuffer _colorBuffer;
  ShortBuffer _indexBufferBase;

  FloatBuffer _verticesArm;
  ShortBuffer _indexBufferArm;

  public DummyWorldRenderer()
  {
    ByteBuffer vbb = ByteBuffer.allocateDirect(_numVerticesBase * 3 * 4);
    vbb.order(ByteOrder.nativeOrder());
    _verticesBase = vbb.asFloatBuffer();

    // ByteBuffer cbb = ByteBuffer.allocateDirect(_numVertices * 4 * 4);
    // cbb.order(ByteOrder.nativeOrder());
    // _colorBuffer = cbb.asFloatBuffer();

    ByteBuffer ibb = ByteBuffer.allocateDirect(_numVerticesBase * 2);
    ibb.order(ByteOrder.nativeOrder());
    _indexBufferBase = ibb.asShortBuffer();

    float ebene = -9.0f;
    short num = 0;
    for (int i = -100; i <= 100; i++)
    {
      _verticesBase.put(new Vector(-100.0f, i, ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(100.0f, i, ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(i, -100.0f, ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(i, 100.0f, ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(-100.0f, i, -ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(100.0f, i, -ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(i, -100.0f, -ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);

      _verticesBase.put(new Vector(i, 100.0f, -ebene).getValues());
      // _colorBuffer.put(new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
      _indexBufferBase.put(num++);
    }

    _verticesBase.position(0);
    // _colorBuffer.position(0);
    _indexBufferBase.position(0);

    ByteBuffer vbb2 = ByteBuffer.allocateDirect(8 * 3 * 4);
    vbb2.order(ByteOrder.nativeOrder());
    _verticesArm = vbb2.asFloatBuffer();

    ByteBuffer ibb2 = ByteBuffer.allocateDirect(24 * 2);
    ibb2.order(ByteOrder.nativeOrder());
    _indexBufferArm = ibb2.asShortBuffer();

    _verticesArm.put(new Vector(-1.0f, -1.0f, -1.0f).getValues());
    _verticesArm.put(new Vector(-1.0f, 1.0f, -1.0f).getValues());
    _verticesArm.put(new Vector(1.0f, 1.0f, -1.0f).getValues());
    _verticesArm.put(new Vector(1.0f, -1.0f, -1.0f).getValues());

    _verticesArm.put(new Vector(-1.0f, -1.0f, 1.0f).getValues());
    _verticesArm.put(new Vector(-1.0f, 1.0f, 1.0f).getValues());
    _verticesArm.put(new Vector(1.0f, 1.0f, 1.0f).getValues());
    _verticesArm.put(new Vector(1.0f, -1.0f, 1.0f).getValues());

    _indexBufferArm.put((short) 0);
    _indexBufferArm.put((short) 1);

    _indexBufferArm.put((short) 1);
    _indexBufferArm.put((short) 2);

    _indexBufferArm.put((short) 2);
    _indexBufferArm.put((short) 3);

    _indexBufferArm.put((short) 3);
    _indexBufferArm.put((short) 0);

    _indexBufferArm.put((short) 4);
    _indexBufferArm.put((short) 5);

    _indexBufferArm.put((short) 5);
    _indexBufferArm.put((short) 6);

    _indexBufferArm.put((short) 6);
    _indexBufferArm.put((short) 7);

    _indexBufferArm.put((short) 7);
    _indexBufferArm.put((short) 4);

    _indexBufferArm.put((short) 0);
    _indexBufferArm.put((short) 4);

    _indexBufferArm.put((short) 1);
    _indexBufferArm.put((short) 5);

    _indexBufferArm.put((short) 2);
    _indexBufferArm.put((short) 6);

    _indexBufferArm.put((short) 3);
    _indexBufferArm.put((short) 7);

    _verticesArm.position(0);
    _indexBufferArm.position(0);
  }

  void render(GL10 gl, float[] _matrix)
  {
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    // gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _verticesBase);
    // gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
    gl.glDrawElements(GL10.GL_LINES, _numVerticesBase, GL10.GL_UNSIGNED_SHORT, _indexBufferBase);
  }

  void render2(GL10 gl, float[] _matrix)
  {
    if (_matrix != null)
    {
      gl.glPushMatrix();
      
     
      
      gl.glMultMatrixf(_matrix, 0);

      // gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
      // gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, _verticesArm);
      // gl.glColorPointer(4, GL10.GL_FLOAT, 0, _colorBuffer);
      gl.glDrawElements(GL10.GL_LINES, 24, GL10.GL_UNSIGNED_SHORT, _indexBufferArm);
      gl.glPopMatrix();
    }
  }
}