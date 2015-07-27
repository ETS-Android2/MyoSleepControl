/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;

public abstract class AbstractDeviceListener
implements DeviceListener {
    @Override
    public void onAttach(Myo myo, long timestamp) {
    }

    @Override
    public void onDetach(Myo myo, long timestamp) {
    }

    @Override
    public void onConnect(Myo myo, long timestamp) {
    }

    @Override
    public void onDisconnect(Myo myo, long timestamp) {
    }

    @Override
    public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection, float rotation, WarmupState warmupState) {
    }

    @Override
    public void onArmUnsync(Myo myo, long timestamp) {
    }

    @Override
    public void onUnlock(Myo myo, long timestamp) {
    }

    @Override
    public void onLock(Myo myo, long timestamp) {
    }

    @Override
    public void onPose(Myo myo, long timestamp, Pose pose) {
    }

    @Override
    public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
    }

    @Override
    public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
    }

    @Override
    public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
    }

    @Override
    public void onRssi(Myo myo, long timestamp, int rssi) {
    }
    
    @Override
    public void onWarmupComplete(Myo myo, long timestamp, WarmupResult warmupResult) {
    }
}

