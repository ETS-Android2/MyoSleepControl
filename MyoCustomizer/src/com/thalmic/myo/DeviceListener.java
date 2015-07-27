/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import com.thalmic.myo.Myo;

public interface DeviceListener {

    public void onConnect(Myo var1, long var2);

    public void onDisconnect(Myo var1, long var2);
}

