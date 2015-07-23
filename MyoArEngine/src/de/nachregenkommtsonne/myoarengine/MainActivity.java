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
import de.nachregenkommtsonne.myoarengine.utility.SettingsEditor;

public class MainActivity extends Activity
{
	private MyoArRenderView _myoArRenderView;
	private SettingsEditor _settingsEditor;

	public MainActivity() {
		_settingsEditor = new SettingsEditor();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_settingsEditor.onCreate(this);

		_myoArRenderView = new MyoArRenderView(this);

		makeWindowFixedFullscreen();

		_myoArRenderView.initialize();
		setContentView(_myoArRenderView);

		initMyo();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			_myoArRenderView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}

	}

	private void makeWindowFixedFullscreen() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	private void initMyo() {
		Hub hub = Hub.getInstance();
		if (!hub.init(this, this.getPackageName())) {
			throw new RuntimeException("Error initializing Myo hub");
		}

		hub.setSendUsageData(false);
		hub.addListener(new DeviceListener() {
			public void onUnlock(Myo arg0, long arg1) {
				//Toast.makeText(MainActivity.this, "onUnlock", Toast.LENGTH_SHORT).show();
			}

			public void onRssi(Myo arg0, long arg1, int arg2) {
			}

			public void onPose(Myo arg0, long arg1, Pose arg2) {
				//Toast.makeText(MainActivity.this, "onPose", Toast.LENGTH_SHORT).show();
			}

			public void onOrientationData(Myo arg0, long arg1, Quaternion q) {
				_myoArRenderView.orientationData(q);
			}

			public void onLock(Myo arg0, long arg1) {
				//Toast.makeText(MainActivity.this, "onLock", Toast.LENGTH_SHORT).show();
			}

			public void onGyroscopeData(Myo arg0, long arg1, Vector3 arg2) {
			}

			public void onDisconnect(Myo arg0, long arg1) {
			}

			public void onDetach(Myo arg0, long arg1) {
			}

			public void onConnect(Myo arg0, long arg1) {
				_settingsEditor.saveMac(arg0.getMacAddress());
			}

			public void onAttach(Myo arg0, long arg1) {
				//Toast.makeText(MainActivity.this, "onAttach", Toast.LENGTH_SHORT).show();
			}

			public void onArmUnsync(Myo arg0, long arg1) {
				//Toast.makeText(MainActivity.this, "onArmUnsync", Toast.LENGTH_SHORT).show();
			}

			public void onArmSync(Myo arg0, long arg1, Arm arg2, XDirection arg3) {
				//Toast.makeText(MainActivity.this, "onArmSync", Toast.LENGTH_SHORT).show();
			}

			public void onAccelerometerData(Myo arg0, long arg1, Vector3 arg2) {
			}
		});

		String macAddress = _settingsEditor.getMac();
		if (macAddress != null) {
			hub.attachByMacAddress(macAddress);
		} else {
			hub.attachToAdjacentMyo();
		}
	}
}