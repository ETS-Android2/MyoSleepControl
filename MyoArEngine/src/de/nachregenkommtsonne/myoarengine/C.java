package de.nachregenkommtsonne.myoarengine;

public class C
{
  static {
    System.loadLibrary("MyoArEngine");
  }

  public native void onSurfaceCreated();
  public native void onSurfaceChanged(int width, int height);
  public native void onDrawFrame();
}
