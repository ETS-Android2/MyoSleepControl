/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.thalmic.myo.internal.ble;

import android.content.Context;
import android.util.Log;
import com.thalmic.myo.internal.ble.BleManager;
import com.thalmic.myo.internal.ble.JBBleManager;

public abstract class BleFactory {

    private BleFactory() {
    }

    public static BleManager createBleManager(Context context) {
        if ("jb".equals("ss1")) {
            try {
                Class manager = Class.forName("com.thalmic.myo.internal.ble.SS1BleManager");
                return (BleManager)manager.newInstance();
            }
            catch (Exception e) {
                Log.e((String)"BleFactory", (String)"Failed creating SS1BleManager", (Throwable)e);
                return null;
            }
        }
        return new JBBleManager(context);
    }
}

