/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo.internal.ble;

import com.thalmic.myo.internal.ble.BleGattCallback;
import java.util.UUID;

public interface BleGatt {
    public void setBleGattCallback(BleGattCallback var1);

    public void discoverServices(String var1);

    public void readCharacteristic(String var1, UUID var2, UUID var3);

    public void writeCharacteristic(String var1, UUID var2, UUID var3, byte[] var4);

    public void setCharacteristicNotification(String var1, UUID var2, UUID var3, boolean var4, boolean var5);
}

