/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Myo;
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

}

