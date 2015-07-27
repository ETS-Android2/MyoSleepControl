/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.thalmic.myo;

import android.os.Build;
import com.thalmic.myo.ControlCommand;
import com.thalmic.myo.ControlCommand.SleepMode;
import com.thalmic.myo.GattConstants;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.internal.ble.BleManager;
import java.util.UUID;

class MyoGatt {
    private Hub mHub;
    private BleManager mBleManager;

    public MyoGatt(Hub hub) {
        this.mHub = hub;
    }

    public void setBleManager(BleManager bleManager) {
        this.mBleManager = bleManager;
    }

    public boolean connect(String address) {
        return this.connect(address, false);
    }

    public boolean connect(String address, boolean autoConnect) {
        boolean connecting = this.mBleManager.connect(address, autoConnect);
        if (connecting) {
            Myo myo = this.mHub.getDevice(address);
            myo.setConnectionState(Myo.ConnectionState.CONNECTING);
        }
        return connecting;
    }

    public void disconnect(String address) {
        this.mBleManager.disconnect(address);
        Myo myo = this.mHub.getDevice(address);
        if (myo.getConnectionState() == Myo.ConnectionState.CONNECTING) {
            myo.setConnectionState(Myo.ConnectionState.DISCONNECTED);
            this.mHub.getScanner().getScanListAdapter().notifyDeviceChanged();
        }
        myo.setAttached(false);
    }





    
	public void turnOffForTransport(String address) {
        byte[] command = ControlCommand.createForTurnOffForTransport();
        this.writeControlCommand(address, command);
	}

	public void setLightsColors(String address, int red, int green, int blue, int red2, int green2, int blue2) {
        byte[] command = ControlCommand.createForSetLightsColors(red, green, blue, red2, green2, blue2);
        this.writeControlCommand(address, command);
	}

	public void setSleepMode(String address, SleepMode i) {
        byte[] command = ControlCommand.createForSetSleepMode(i);
        this.writeControlCommand(address, command);
	}

	private void writeControlCommand(String address, byte[] controlCommand) {
        UUID serviceUuid = GattConstants.CONTROL_SERVICE_UUID;
        UUID charUuid = GattConstants.COMMAND_CHAR_UUID;
        this.mBleManager.getBleGatt().writeCharacteristic(address, serviceUuid, charUuid, controlCommand);
    }
}
