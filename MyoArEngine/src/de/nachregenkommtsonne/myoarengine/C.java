package de.nachregenkommtsonne.myoarengine;

public class C
{

  static {
    System.loadLibrary("MyoArEngine");
  }
  

  public native String getMessage(short[] samples);

  
}
