package de.nachregenkommtsonne.myoarengine;

import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;

public class MyoArRenderView extends GLSurfaceView
{
	private Renderer _myoArRenderer;
	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;
	private SensorEventListener _gravitationalEventListener;
	private SensorEventListener _magneticEventListener;

	public MyoArRenderView(Context context)
	{
		super(context);

		_gravitationalVector = new VectorAverager(25);
		_magneticVector = new VectorAverager(25);

		_myoArRenderer = new MyoArRenderer(_gravitationalVector, _magneticVector);
	}

	public void initialize()
	{
		setRenderer(_myoArRenderer);

		SensorManager sensorService = (SensorManager) getContext()
				.getSystemService(Context.SENSOR_SERVICE);

		Sensor accelerometerSensor = sensorService
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		_gravitationalEventListener = new AveragingSensorEventListener(
				_gravitationalVector);
		sensorService.registerListener(_gravitationalEventListener,
				accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

		Sensor magneticFieldSensor = sensorService
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		_magneticEventListener = new AveragingSensorEventListener(_magneticVector);
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
