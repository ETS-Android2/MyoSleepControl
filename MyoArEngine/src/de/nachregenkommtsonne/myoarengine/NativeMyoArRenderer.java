package de.nachregenkommtsonne.myoarengine;

public class NativeMyoArRenderer {
	static {
		System.loadLibrary("MyoArRenderer");
	}

	public native void onSurfaceCreated(String script, int texIDAscii, int texIDRasen, int texIDSky);

	public native void onSurfaceChanged(int width, int height);

	public native void draw(float x, float y, float z, float _rotationMatrix1, float _rotationMatrix2, float _rotationMatrix3, float _rotationMatrix4, float _rotationMatrix5, float _rotationMatrix6, float _rotationMatrix7, float _rotationMatrix8, float _rotationMatrix9, float _rotationMatrix10, float _rotationMatrix11, float _rotationMatrix12, float _rotationMatrix13, float _rotationMatrix14, float _rotationMatrix15, float _rotationMatrix16);
}
