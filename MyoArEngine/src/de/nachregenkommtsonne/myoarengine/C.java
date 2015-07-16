package de.nachregenkommtsonne.myoarengine;

public class C
{

  static {
    System.loadLibrary("AirGap");
  }
  

  public native String getMessage(short[] samples);

  
}
