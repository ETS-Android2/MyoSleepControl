package de.nachregenkommtsonne.myoarengine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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
	}

	private void makeWindowFixedFullscreen()
	{
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Window window = getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}