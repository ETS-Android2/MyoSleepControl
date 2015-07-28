/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.thalmic.myo;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.GattCallback;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

class MyoUpdateParser
implements GattCallback.UpdateParser {
    private Hub mHub;
    private DeviceListener mListener;

    MyoUpdateParser(Hub hub, DeviceListener listener) {
        this.mHub = hub;
        this.mListener = listener;
    }

    @Override
    public void onMyoConnected(Myo myo) {
        long now = this.mHub.now();
        if (!myo.isAttached()) {
            myo.setAttached(true);
        }
        this.setMyoConnectionState(myo, Myo.ConnectionState.CONNECTED);
        this.mListener.onConnect(myo, now);
    }

    @Override
    public void onMyoDisconnected(Myo myo) {
        long now = this.mHub.now();
        this.setMyoConnectionState(myo, Myo.ConnectionState.DISCONNECTED);
        
        this.mListener.onDisconnect(myo, now);
    }

    private void setMyoConnectionState(Myo myo, Myo.ConnectionState connectionState) {
        myo.setConnectionState(connectionState);
    }
}

