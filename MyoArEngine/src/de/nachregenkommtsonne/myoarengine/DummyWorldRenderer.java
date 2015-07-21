package de.nachregenkommtsonne.myoarengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import de.nachregenkommtsonne.myoarengine.utility.Vector2D;
import de.nachregenkommtsonne.myoarengine.utility.Vector3D;

public class DummyWorldRenderer
{
  FloatBuffer _verticesBase;
  ShortBuffer _indexBufferBase;

  FloatBuffer _verticesArm;
  ShortBuffer _indexBufferArm;

  public DummyWorldRenderer()
  {
  }

  void renderSkyBox(GL10 gl)
  {
  	//sky
  	DrawQuad(gl, 3,
  			new Vector3D(100.0f, 100.0f, 49.0f), new Vector2D(.25f, .25f),
  			new Vector3D(100.0f, -100.0f, 49.0f), new Vector2D(.25f, .75f),
  			new Vector3D(-100.0f, -100.0f, 49.0f), new Vector2D(.75f, .75f),
  			new Vector3D(-100.0f, 100.0f, 49.0f), new Vector2D(.75f, .25f));

  	//4 sky sides
  	// x = 100
  	DrawQuad(gl, 3,
  			new Vector3D(100.0f, 100.0f, 49.0f), new Vector2D(.25f, .25f),
  			new Vector3D(100.0f, -100.0f, 49.0f), new Vector2D(.25f, .75f),
  			new Vector3D(100.0f, -100.0f, -1.0f), new Vector2D(.0f, .75f),
  			new Vector3D(100.0f, 100.0f, -1.0f), new Vector2D(.0f, .25f));

  	// y = -100
  	DrawQuad(gl, 3,
  			new Vector3D(100.0f, -100.0f, -1.0f), new Vector2D(.25f, 1.f),
  			new Vector3D(100.0f, -100.0f, 49.0f), new Vector2D(.25f, .75f),
  			new Vector3D(-100.0f, -100.0f, 49.0f), new Vector2D(.75f, .75f),
  			new Vector3D(-100.0f, -100.0f, -1.0f), new Vector2D(.75f, 1.f));

  	//x = -100
  	DrawQuad(gl, 3,
  			new Vector3D(-100.0f, 100.0f, -1.0f), new Vector2D(1.f, .25f),
  			new Vector3D(-100.0f, -100.0f, -1.0f), new Vector2D(1.f, .75f),
  			new Vector3D(-100.0f, -100.0f, 49.0f), new Vector2D(.75f, .75f),
  			new Vector3D(-100.0f, 100.0f, 49.0f), new Vector2D(.75f, .25f));

  	//y = 100
  	DrawQuad(gl, 3,
  			new Vector3D(100.0f, 100.0f, 49.0f), new Vector2D(.25f, .25f),
  			new Vector3D(100.0f, 100.0f, -1.0f), new Vector2D(.25f, .0f),
  			new Vector3D(-100.0f, 100.0f, -1.0f), new Vector2D(.75f, .0f),
  			new Vector3D(-100.0f, 100.0f, 49.0f), new Vector2D(.75f, .25f));
  }

	private void DrawQuad(
			GL10 gl, int texID, 
			Vector3D vector1, Vector2D texVector1,
			Vector3D vector2, Vector2D texVector2,
			Vector3D vector3, Vector2D texVector3,
			Vector3D vector4, Vector2D texVector4)
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

    textures.put(texVector1.getValues());
    textures.put(texVector2.getValues());
    textures.put(texVector3.getValues());
    textures.put(texVector4.getValues());

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
    gl.glBindTexture(GL10.GL_TEXTURE_2D, texID);

    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertices);
    
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textures); 
		
    gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indices);

    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void renderWorld(GL10 gl, float[] _matrix)
	{
  	//ground
  	DrawQuad(gl, 2,
  			new Vector3D(10000.0f, 10000.0f, -1.0f), new Vector2D(0.f, 0.f),
  			new Vector3D(10000.0f, -10000.0f, -1.0f), new Vector2D(2500.f, 0.f),
  			new Vector3D(-10000.0f, -10000.0f, -1.0f), new Vector2D(2500.f, 2500.f),
  			new Vector3D(-10000.0f, 10000.0f, -1.0f), new Vector2D(0.f, 2500.f));
	}
}