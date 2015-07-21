package de.nachregenkommtsonne.myoarengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import de.nachregenkommtsonne.myoarengine.utility.Vector;

public class DummyWorldRenderer
{
  FloatBuffer _verticesBase;
  ShortBuffer _indexBufferBase;

  FloatBuffer _verticesArm;
  ShortBuffer _indexBufferArm;

  public DummyWorldRenderer()
  {
  }

  void render(GL10 gl, float[] _matrix)
  {
  	DrawQuad(gl, 
  			new Vector(100.0f, 100.0f, -9.0f),
  			new Vector(100.0f, -100.0f, -9.0f),
  			new Vector(-100.0f, -100.0f, -9.0f),
  			new Vector(-100.0f, 100.0f, -9.0f));

  	DrawQuad(gl, 
  			new Vector(100.0f, 100.0f, 9.0f),
  			new Vector(100.0f, -100.0f, 9.0f),
  			new Vector(-100.0f, -100.0f, 9.0f),
  			new Vector(-100.0f, 100.0f, 9.0f));

  }

	private void DrawQuad(GL10 gl, Vector vector1, Vector vector2, Vector vector3,
			Vector vector4)
	{
		ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(4*3 *4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = vertexByteBuffer.asFloatBuffer();

    ByteBuffer textureByteBuffer = ByteBuffer.allocateDirect(4*2 *4);
    textureByteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer textures = textureByteBuffer.asFloatBuffer();

    ByteBuffer indexByteBuffer = ByteBuffer.allocateDirect(6 * 2);
    indexByteBuffer.order(ByteOrder.nativeOrder());
    ShortBuffer indices = indexByteBuffer.asShortBuffer();
    
    vertices.put(vector1.getValues());
    vertices.put(vector2.getValues());
    vertices.put(vector3.getValues());
    vertices.put(vector4.getValues());


    textures.put(0.f);
    textures.put(0.f);

    textures.put(25.f);
    textures.put(0.f);

    textures.put(25.f);
    textures.put(25.f);

    textures.put(0.f);
    textures.put(25.f);

    indices.put((short)0);
    indices.put((short)1);
    indices.put((short)2);
    indices.put((short)2);
    indices.put((short)3);
    indices.put((short)0);
    
    vertices.position(0);
    textures.position(0);
    indices.position(0);
    
    gl.glEnable(GL10.GL_TEXTURE_2D);
    gl.glBindTexture(GL10.GL_TEXTURE_2D, 2);

    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
    
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textures); 
		
    gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);

    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}