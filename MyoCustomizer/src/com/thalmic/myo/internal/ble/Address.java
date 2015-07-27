/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 */
package com.thalmic.myo.internal.ble;

import android.bluetooth.BluetoothAdapter;

public class Address {
    private String mAddress;

    public Address(String address) {
        if (!BluetoothAdapter.checkBluetoothAddress((String)address)) {
            throw new IllegalArgumentException("Cannot create Address for invalid address " + address);
        }
        this.mAddress = address;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address)o;
        return this.mAddress.equals(address.mAddress);
    }

    public int hashCode() {
        return this.mAddress.hashCode();
    }

    public String toString() {
        return this.mAddress;
    }
}

