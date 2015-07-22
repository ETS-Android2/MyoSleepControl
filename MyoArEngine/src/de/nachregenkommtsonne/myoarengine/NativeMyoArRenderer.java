package de.nachregenkommtsonne.myoarengine;

public class NativeMyoArRenderer {
	static {
		System.loadLibrary("MyoArRenderer");
	}

	public native void onSurfaceCreated(String script, int texIDAscii, int texIDRasen, int texIDSky);

	public native void onSurfaceChanged(int width, int height);

	public native void onDrawHud();

	public native void drawSkyBox();

	public native void drawWorld();
}
