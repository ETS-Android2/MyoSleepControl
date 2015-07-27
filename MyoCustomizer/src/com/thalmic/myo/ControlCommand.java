/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

public abstract class ControlCommand
{

  public static byte[] createForTurnOffForTransport()
  {
    byte[] command = new byte[2];
    command[0] = 4;
    command[1] = 0;
    return command;
  }

  public static byte[] createForSetLightsColors(int red, int green, int blue, int red2, int green2, int blue2)
  {
    byte[] command = new byte[8];
    command[0] = 6;
    command[1] = 6;
    command[2] = (byte) red;
    command[3] = (byte) green;
    command[4] = (byte) blue;
    command[5] = (byte) red2;
    command[6] = (byte) green2;
    command[7] = (byte) blue2;
    return command;
  }

  public static byte[] createForSetSleepMode(SleepMode i)
  {
    byte[] command = new byte[3];
    command[0] = 9;
    command[1] = 1;
    command[2] = (byte) (i == SleepMode.NORMAL ? 0 : 1);
    return command;
  }



  public static enum SleepMode
  {
    NORMAL, NEVER_SLEEP
  }
}
