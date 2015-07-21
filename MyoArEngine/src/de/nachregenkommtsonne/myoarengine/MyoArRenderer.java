package de.nachregenkommtsonne.myoarengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import de.nachregenkommtsonne.myoarengine.utility.MovementCalculator;
import de.nachregenkommtsonne.myoarengine.utility.Vector;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

public class MyoArRenderer implements Renderer
{
	private DummyWorldRenderer dummyWorldRenderer = new DummyWorldRenderer();
	private int _width = 0;
	private int _height = 0;
	private float[] _rotationMatrix = new float[16];
	private Vector _position;
	private Vector _displayVector;
	private Context _context;

	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;

	public MyoArRenderer(VectorAverager gravitationalVector,
			VectorAverager magneticVector, Context context)
	{
		_gravitationalVector = gravitationalVector;
		_magneticVector = magneticVector;
		_position = new Vector();
		_displayVector = new Vector(0.0f, 0.0f, 0.0f);
		_context = context;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glEnable(GL10.GL_TEXTURE_2D);

		final Bitmap bitmap = BitmapFactory.decodeResource(_context.getResources(),	R.drawable.font);

		int[] textur = new int[1];
		gl.glGenTextures(1, textur, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textur[0]);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

		bitmap.recycle();

//		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		new C().onSurfaceCreated();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		_width = width;
		_height = height;

		gl.glViewport(0, 0, width, height);

		gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);

		new C().onSurfaceChanged(width, height);

	}

	public void onDrawFrame(GL10 gl)
	{
		if (_width == 0 || _height == 0)
			return;

		Vector gavitationalVector = _gravitationalVector.getAverage();
		gavitationalVector.normalize();

		Vector magneticVector = _magneticVector.getAverage();
		magneticVector.normalize();

		SensorManager.getRotationMatrix(_rotationMatrix, null,
				gavitationalVector.getValues(), magneticVector.getValues());

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 90.0f, (float) _width / _height, 0.1f, 200.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLoadMatrixf(_rotationMatrix, 0);

		Vector xVector = new Vector(_rotationMatrix[0], _rotationMatrix[4],
				_rotationMatrix[8]);
		Vector yVector = new Vector(_rotationMatrix[1], _rotationMatrix[5],
				_rotationMatrix[9]);
		Vector zVector = new Vector(_rotationMatrix[2], _rotationMatrix[6],
				_rotationMatrix[10]);

		Vector delta = new MovementCalculator().getMovementDelta(_displayVector,
				xVector, yVector, zVector);

		if (delta.isValid())
			_position = _position.add(delta);

		gl.glTranslatef(_position.getX(), _position.getY(), _position.getZ());

		dummyWorldRenderer.render(gl, _matrix);
		dummyWorldRenderer.render2(gl, _matrix);

		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0.0f, 800.0f, 0.0f, 600.0f, -1.0f, 1.0f); // {0,0} ist unten
																													// links

		gl.glMatrixMode(GL10.GL_MODELVIEW);

		new C().onDrawFrame();
	}

	public void setMovementVector(Vector vector)
	{
		_displayVector = vector;
	}

	float[] _matrix;

	public void orientationData(float[] matrix)
	{
		_matrix = matrix;
	}
}