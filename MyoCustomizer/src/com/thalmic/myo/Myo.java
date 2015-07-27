/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import com.thalmic.myo.ControlCommand.SleepMode;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Hub;
import com.thalmic.myo.MyoGatt;
import com.thalmic.myo.internal.ble.Address;

public class Myo {
    private final MyoGatt mMyoGatt;
    private String mName;
    private final String mAddress;
    private boolean mAttached;
    private ConnectionState mConnState = ConnectionState.DISCONNECTED;
    private FirmwareVersion mFirmwareVersion;
   private boolean mUnlocked;
 
    Myo(Hub hub, Address address) {
        this.mMyoGatt = hub.getMyoGatt();
        this.mName = "";
        this.mAddress = address.toString();
    }

    public String getName() {
        return this.mName;
    }

    public String getMacAddress() {
        return this.mAddress;
    }

    public FirmwareVersion getFirmwareVersion() {
        return this.mFirmwareVersion;
    }

    public boolean isUnlocked() {
        return this.mUnlocked;
    }


    
    public void turnOffForTransport() {
    	this.mMyoGatt.turnOffForTransport(this.mAddress);
    }
    
    public void setLightsColors(int red, int green, int blue, int red2, int green2, int blue2){
    	this.mMyoGatt.setLightsColors(this.mAddress, red, green, blue, red2, green2, blue2);
    }
    
    public void setSleepMode(SleepMode sleepMode){
    	this.mMyoGatt.setSleepMode(this.mAddress, sleepMode);
    }
    
    public boolean isFirmwareVersionSupported() {
        if (this.mFirmwareVersion == null) {
            return true;
        }
        if (this.mFirmwareVersion.isNotSet()) {
            return false;
        }
        if (this.mFirmwareVersion.major == 1 && this.mFirmwareVersion.minor >= 1) {
            return true;
        }
        return false;
    }

    public boolean isConnected() {
        return this.getConnectionState() == ConnectionState.CONNECTED;
    }

    public ConnectionState getConnectionState() {
        return this.mConnState;
    }

    void setAttached(boolean attached) {
        this.mAttached = attached;
    }

    boolean isAttached() {
        return this.mAttached;
    }

    void setName(String name) {
        this.mName = name;
    }

    void setConnectionState(ConnectionState state) {
        this.mConnState = state;
    }




    void setUnlocked(boolean unlocked) {
        this.mUnlocked = unlocked;
    }

 


    void setFirmwareVersion(FirmwareVersion firmwareVersion) {
        this.mFirmwareVersion = firmwareVersion;
    }

    public static enum ConnectionState {
        CONNECTED,
        CONNECTING,
        DISCONNECTED
    }

    public static enum UnlockType {
        TIMED,
        HOLD
    }

    public static enum VibrationType {
        SHORT,
        MEDIUM,
        LONG
    }
}

