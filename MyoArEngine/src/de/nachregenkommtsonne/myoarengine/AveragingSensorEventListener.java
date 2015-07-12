package de.nachregenkommtsonne.myoarengine;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import de.nachregenkommtsonne.myoarengine.utility.Vector;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

public final class AveragingSensorEventListener implements
		SensorEventListener
{
	VectorAverager _vectorAverager;
	
	public AveragingSensorEventListener(VectorAverager vectorAverager)
	{
		_vectorAverager = vectorAverager;
	}

	public void onSensorChanged(SensorEvent event)
	{
		_vectorAverager.add(new Vector(event.values));
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy){}
}