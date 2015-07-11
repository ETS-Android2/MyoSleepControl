package de.nachregenkommtsonne.myoarengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MyoArRenderView extends GLSurfaceView
{
	private Renderer _myoArRenderer;
	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;
	private SensorEventListener _gravitationalEventListener;
	private SensorEventListener _magneticEventListener;

	int _width = 0;
	int _height = 0;

	static final int matrix_size = 16;
	float[] _rotationMatrix = new float[matrix_size];

	public MyoArRenderView(Context context)
	{
		super(context);

		_myoArRenderer = new Renderer()
		{
			public void onSurfaceCreated(GL10 gl, EGLConfig config){}

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

				Vector gavitationalVector = _gravitationalVector.getAvg();
				gavitationalVector.normalize();

				Vector magneticVector = _gravitationalVector.getAvg();
				magneticVector.normalize();

				SensorManager.getRotationMatrix(_rotationMatrix, null,
						gavitationalVector.getValues(), magneticVector.getValues());

				gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

				gl.glMatrixMode(GL10.GL_PROJECTION);
				gl.glLoadIdentity();
				GLU.gluPerspective(gl, 60.0f, (float) _width / _height, 0.1f,
						80.0f);

				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glLoadMatrixf(_rotationMatrix, 0);

				//TODO: Render something
			}
		};

		setRenderer(_myoArRenderer);

		_gravitationalEventListener = new SensorEventListener()
		{
			public void onSensorChanged(SensorEvent event)
			{
				_gravitationalVector.add(new Vector(event.values));
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}
		};

		_magneticEventListener = new SensorEventListener()
		{
			public void onSensorChanged(SensorEvent event)
			{
				_magneticVector.add(new Vector(event.values));
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}
		};
	}

	public void onResume()
	{
		super.onResume();

		SensorManager sensorService = (SensorManager) getContext()
				.getSystemService(Context.SENSOR_SERVICE);

		Sensor accelerometerSensor = sensorService
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorService.registerListener(_gravitationalEventListener,
				accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

		Sensor magneticFieldSensor = sensorService
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorService.registerListener(_magneticEventListener, magneticFieldSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	public void onPause()
	{
		super.onPause();

		SensorManager sensorService = (SensorManager) getContext()
				.getSystemService(Context.SENSOR_SERVICE);
		sensorService.unregisterListener(_gravitationalEventListener);
		sensorService.unregisterListener(_magneticEventListener);
	}
}
