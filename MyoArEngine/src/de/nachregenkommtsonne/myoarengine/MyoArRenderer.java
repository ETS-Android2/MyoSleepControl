package de.nachregenkommtsonne.myoarengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.hardware.SensorManager;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import de.nachregenkommtsonne.myoarengine.utility.Vector;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

final class MyoArRenderer implements Renderer
{
	final DummyWorldRenderer dummyWorldRenderer = new DummyWorldRenderer();
	int _width = 0;
	int _height = 0;
	static final int matrix_size = 16;
	float[] _rotationMatrix = new float[matrix_size];

	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;

	public MyoArRenderer(VectorAverager gravitationalVector,
			VectorAverager magneticVector)
	{
		super();
		_gravitationalVector = gravitationalVector;
		_magneticVector = magneticVector;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		_width = width;
		_height = height;

		gl.glViewport(0, 0, width, height);

		gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
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


		dummyWorldRenderer.render(gl);
	}
}