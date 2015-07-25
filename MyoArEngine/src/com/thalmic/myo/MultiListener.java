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
import java.util.ArrayList;

class MultiListener
implements DeviceListener {
    private ArrayList<DeviceListener> mListeners = new ArrayList();

    MultiListener() {
    }

    public void add(DeviceListener listener) {
        this.mListeners.add(listener);
    }

    public void remove(DeviceListener listener) {
        this.mListeners.remove(listener);
    }

    public boolean contains(DeviceListener listener) {
        return this.mListeners.contains(listener);
    }

    public void clear() {
        this.mListeners.clear();
    }

    @Override
    public void onAttach(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onAttach(myo, timestamp);
        }
    }

    @Override
    public void onDetach(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onDetach(myo, timestamp);
        }
    }

    @Override
    public void onConnect(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onConnect(myo, timestamp);
        }
    }

    @Override
    public void onDisconnect(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onDisconnect(myo, timestamp);
        }
    }

    @Override
    public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onArmSync(myo, timestamp, arm, xDirection);
        }
    }

    @Override
    public void onArmUnsync(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onArmUnsync(myo, timestamp);
        }
    }

    @Override
    public void onUnlock(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onUnlock(myo, timestamp);
        }
    }

    @Override
    public void onLock(Myo myo, long timestamp) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onLock(myo, timestamp);
        }
    }

    @Override
    public void onPose(Myo myo, long timestamp, Pose pose) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onPose(myo, timestamp, pose);
        }
    }

    @Override
    public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onOrientationData(myo, timestamp, rotation);
        }
    }

    @Override
    public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onAccelerometerData(myo, timestamp, accel);
        }
    }

    @Override
    public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onGyroscopeData(myo, timestamp, gyro);
        }
    }

    @Override
    public void onRssi(Myo myo, long timestamp, int rssi) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            this.mListeners.get(i).onRssi(myo, timestamp, rssi);
        }
    }
}

