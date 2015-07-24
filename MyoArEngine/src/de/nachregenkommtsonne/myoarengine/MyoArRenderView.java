package de.nachregenkommtsonne.myoarengine;

import de.nachregenkommtsonne.myoarengine.utility.Vector3D;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

import com.thalmic.myo.Quaternion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyoArRenderView extends GLSurfaceView {
	private MyoArRenderer _myoArRenderer;
	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;
	private SensorEventListener _gravitationalEventListener;
	private SensorEventListener _magneticEventListener;

	public MyoArRenderView(Context context) {
		super(context);

		_gravitationalVector = new VectorAverager(10);
		_magneticVector = new VectorAverager(10);

		_myoArRenderer = new MyoArRenderer(_gravitationalVector, _magneticVector, this.getContext());

		setEGLConfigChooser(8, 8, 8, 8, 16, 8);

		setKeepScreenOn(true);
	}

	public void initialize() {
		setRenderer(_myoArRenderer);

		SensorManager sensorService = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

		Sensor accelerometerSensor = sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		_gravitationalEventListener = new AveragingSensorEventListener(_gravitationalVector);
		sensorService.registerListener(_gravitationalEventListener, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);

		Sensor magneticFieldSensor = sensorService.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		_magneticEventListener = new AveragingSensorEventListener(_magneticVector);
		sensorService.registerListener(_magneticEventListener, magneticFieldSensor, SensorManager.SENSOR_DELAY_GAME);
	}

	public void onPause() {
		super.onPause();

		SensorManager sensorService = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
		sensorService.unregisterListener(_gravitationalEventListener);
		sensorService.unregisterListener(_magneticEventListener);
	}

	float initialX = 0.0f;
	float initialY = 0.0f;

	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			initialX = event.getX(0);
			initialY = event.getY(0);

			return true;

		case MotionEvent.ACTION_MOVE:
			float currentX = event.getX(0);
			float currentY = event.getY(0);

			float relativeX = initialX - currentX;
			float relativeY = initialY - currentY;

			_myoArRenderer.setMovementVector(new Vector3D(relativeX / 1000.0f, relativeY / -1000.0f, 0.0f));

			return true;

		case MotionEvent.ACTION_UP:

			_myoArRenderer.setMovementVector(new Vector3D());

			return true;
		}

		return super.onTouchEvent(event);
	}

	public void orientationData(Quaternion q, boolean inverse) {
		_myoArRenderer.orientationData(q, inverse);
	}
}