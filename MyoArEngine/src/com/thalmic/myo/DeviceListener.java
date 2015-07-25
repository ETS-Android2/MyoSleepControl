/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import com.thalmic.myo.Arm;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.XDirection;

public interface DeviceListener {
    public void onAttach(Myo var1, long var2);

    public void onDetach(Myo var1, long var2);

    public void onConnect(Myo var1, long var2);

    public void onDisconnect(Myo var1, long var2);

    public void onArmSync(Myo var1, long var2, Arm var4, XDirection var5);

    public void onArmUnsync(Myo var1, long var2);

    public void onUnlock(Myo var1, long var2);

    public void onLock(Myo var1, long var2);

    public void onPose(Myo var1, long var2, Pose var4);

    public void onOrientationData(Myo var1, long var2, Quaternion var4);

    public void onAccelerometerData(Myo var1, long var2, Vector3 var4);

    public void onGyroscopeData(Myo var1, long var2, Vector3 var4);

    public void onRssi(Myo var1, long var2, int var4);
}

