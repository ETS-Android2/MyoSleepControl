package de.nachregenkommtsonne.myoarengine;

import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private MyoArRenderView _myoArRenderView;

	public MainActivity()
	{
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_myoArRenderView = new MyoArRenderView(this);

		makeWindowFixedFullscreen();
		
		_myoArRenderView.initialize();
		setContentView(_myoArRenderView);
		
    initMyo();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			_myoArRenderView.setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}

	}
	
	private void makeWindowFixedFullscreen()
	{
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

  private void initMyo()
  {
    Hub hub = Hub.getInstance();
    if (!hub.init(this, this.getPackageName()))
    {
      throw new RuntimeException("Error initializing Myo hub");
    }

    hub.setSendUsageData(false);
    hub.addListener(new DeviceListener()
    {
      public void onUnlock(Myo arg0, long arg1)
      {
        Toast.makeText(MainActivity.this, "onUnlock", Toast.LENGTH_SHORT).show();
      }
      public void onRssi(Myo arg0, long arg1, int arg2)
      {
      }
      public void onPose(Myo arg0, long arg1, Pose arg2)
      {
        Toast.makeText(MainActivity.this, "onPose", Toast.LENGTH_SHORT).show();
      }
      public void onOrientationData(Myo arg0, long arg1, Quaternion q)
      {
        //double pitch = Quaternion.pitch(quat);
        //double roll = Quaternion.roll(quat);
        //double yaw = Quaternion.yaw(quat);
        
        float[] matrix = new float[16];


        
        matrix[0] = (float) (1 - 2 * (q.y() * q.y() + q.z() * q.z()));
        matrix[1] = (float) (2 * (q.x() * q.y() + q.z() * q.w()));
        matrix[2] = (float) (2 * (q.x() * q.z() - q.y() * q.w()));
        matrix[3] = 0;

        // Second Column
        matrix[4] = (float) (2 * (q.x() * q.y() - q.z() * q.w()));
        matrix[5] = (float) (1 - 2 * (q.x() * q.x() + q.z() * q.z()));
        matrix[6] = (float) (2 * (q.z() * q.y() + q.x() * q.w()));
        matrix[7] = 0;

        // Third Column
        matrix[8] = (float) (2 * (q.x() * q.z() + q.y() * q.w()));
        matrix[9] = (float) (2 * (q.y() * q.z() - q.x() * q.w()));
        matrix[10] = (float) (1 - 2 * (q.x() * q.x() + q.y() * q.y()));
        matrix[11] = 0;

        // Fourth Column
        matrix[12] = 0;
        matrix[13] = 0;
        matrix[14] = 0;
        matrix[15] = 1;
        
        _myoArRenderView.orientationData(matrix);
      }
      public void onLock(Myo arg0, long arg1)
      {
        Toast.makeText(MainActivity.this, "onLock", Toast.LENGTH_SHORT).show();
      }
      public void onGyroscopeData(Myo arg0, long arg1, Vector3 arg2)
      {
      }
      public void onDisconnect(Myo arg0, long arg1)
      {
      }
      public void onDetach(Myo arg0, long arg1)
      {
      }
      public void onConnect(Myo arg0, long arg1)
      {
        Toast.makeText(MainActivity.this, "onConnect", Toast.LENGTH_SHORT).show();
      }
      public void onAttach(Myo arg0, long arg1)
      {
        Toast.makeText(MainActivity.this, "onAttach", Toast.LENGTH_SHORT).show();
      }
      public void onArmUnsync(Myo arg0, long arg1)
      {
        Toast.makeText(MainActivity.this, "onArmUnsync", Toast.LENGTH_SHORT).show();
      }
      public void onArmSync(Myo arg0, long arg1, Arm arg2, XDirection arg3)
      {
        Toast.makeText(MainActivity.this, "onArmSync", Toast.LENGTH_SHORT).show();
      }
      public void onAccelerometerData(Myo arg0, long arg1, Vector3 arg2)
      {
      }
    });
    hub.attachToAdjacentMyo();
  }
}