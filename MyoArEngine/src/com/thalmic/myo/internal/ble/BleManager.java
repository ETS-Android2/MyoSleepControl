/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo.internal.ble;

import com.thalmic.myo.internal.ble.Address;
import com.thalmic.myo.internal.ble.BleGatt;
import java.util.UUID;

public interface BleManager {
    public boolean isBluetoothSupported();

    public BleGatt getBleGatt();

    public boolean startBleScan(BleScanCallback var1);

    public void stopBleScan(BleScanCallback var1);

    public boolean connect(String var1, boolean var2);

    public void disconnect(String var1);

    public void dispose();

    public static interface BleScanCallback {
        public void onBleScan(Address var1, String var2, int var3, UUID var4);
    }

}

