/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.thalmic.myo;

import com.thalmic.myo.GattConstants;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.internal.ble.BleManager;
import java.util.UUID;

class MyoGatt
{
  private Hub mHub;
  private BleManager mBleManager;

  public MyoGatt(Hub hub)
  {
    this.mHub = hub;
  }

  public void setBleManager(BleManager bleManager)
  {
    this.mBleManager = bleManager;
  }

  public boolean connect(String address)
  {
    return this.connect(address, false);
  }

  private boolean connect(String address, boolean autoConnect)
  {
    boolean connecting = this.mBleManager.connect(address, autoConnect);
    if (connecting)
    {
      Myo myo = this.mHub.getDevice(address);
      myo.setConnectionState(Myo.ConnectionState.CONNECTING);
    }
    return connecting;
  }

  public void disconnect(String address)
  {
    this.mBleManager.disconnect(address);
    Myo myo = this.mHub.getDevice(address);
    if (myo.getConnectionState() == Myo.ConnectionState.CONNECTING)
    {
      myo.setConnectionState(Myo.ConnectionState.DISCONNECTED);
    }
    myo.setAttached(false);
  }

  public void turnOffForTransport(String address)
  {
    byte[] command1 = new byte[2];
    command1[0] = 4;
    command1[1] = 0;
    byte[] command = command1;
    this.writeControlCommand(address, command);
  }

  public void setLightsColors(String address, int red, int green, int blue, int red2, int green2, int blue2)
  {
    byte[] command1 = new byte[8];
    command1[0] = 6;
    command1[1] = 6;
    command1[2] = (byte) red;
    command1[3] = (byte) green;
    command1[4] = (byte) blue;
    command1[5] = (byte) red2;
    command1[6] = (byte) green2;
    command1[7] = (byte) blue2;
    byte[] command = command1;
    this.writeControlCommand(address, command);
  }

  public void setSleepMode(String address, SleepMode i)
  {
    byte[] command1 = new byte[3];
    command1[0] = 9;
    command1[1] = 1;
    command1[2] = (byte) (i == SleepMode.NORMAL ? 0 : 1);
    byte[] command = command1;
    this.writeControlCommand(address, command);
  }

  private void writeControlCommand(String address, byte[] controlCommand)
  {
    UUID serviceUuid = GattConstants.CONTROL_SERVICE_UUID;
    UUID charUuid = GattConstants.COMMAND_CHAR_UUID;
    this.mBleManager.getBleGatt().writeCharacteristic(address, serviceUuid, charUuid, controlCommand);
  }
}
