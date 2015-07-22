package de.nachregenkommtsonne.myoarengine;

public class C
{
  static {
    System.loadLibrary("MyoArEngine");
  }

  public static native void onSurfaceCreated(String script, int texIDAscii, int texIDRasen, int texIDSky);
  public static native void onSurfaceChanged(int width, int height);
  public static native void onDrawFrame();
}
