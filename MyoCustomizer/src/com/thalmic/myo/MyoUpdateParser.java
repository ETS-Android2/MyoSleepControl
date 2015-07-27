/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.thalmic.myo;

import android.util.Log;
import com.thalmic.myo.Arm;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.GattCallback;
import com.thalmic.myo.GattConstants;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.MyoGatt;
import com.thalmic.myo.Reporter;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.scanner.Scanner;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

class MyoUpdateParser
implements GattCallback.UpdateParser {
    private static final String TAG = "MyoUpdateParser";
    static final int IMU_EXPECTED_BYTE_LENGTH = 20;
    private static final double ORIENTATION_CONVERSION_CONSTANT = 16384.0;
    private static final double ACCELERATION_CONVERSION_CONSTANT = 2048.0;
    private static final double GYRO_CONVERSION_CONSTANT = 16.0;
    private Hub mHub;
    private DeviceListener mListener;
    private Scanner mScanner;
    private Reporter mReporter;

    MyoUpdateParser(Hub hub, DeviceListener listener) {
        this.mHub = hub;
        this.mListener = listener;
    }

    void setListener(DeviceListener listener) {
        this.mListener = listener;
    }

    void setScanner(Scanner scanner) {
        this.mScanner = scanner;
    }

    void setReporter(Reporter reporter) {
        this.mReporter = reporter;
    }

    @Override
    public void onMyoConnected(Myo myo) {
        long now = this.mHub.now();
        if (!myo.isAttached()) {
            myo.setAttached(true);
            
            this.mReporter.sendMyoEvent(this.mHub.getApplicationIdentifier(), this.mHub.getInstallUuid(), "AttachedMyo", myo);
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
        if (this.mScanner != null) {
            this.mScanner.getScanListAdapter().notifyDeviceChanged();
        }
    }



    static short getShort(byte[] array, int offset) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getShort(offset);
    }

}

