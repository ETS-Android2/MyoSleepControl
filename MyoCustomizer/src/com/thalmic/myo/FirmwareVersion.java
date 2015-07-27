/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FirmwareVersion {
    final int major;
    final int minor;
    private final int patch;
    private final int hardwareRev;

    FirmwareVersion(byte[] array) {
        if (array.length < 8) {
            throw new IllegalArgumentException("Unexpected length=" + array.length + " of array. Expecting length of " + 8);
        }
        ByteBuffer buffer = ByteBuffer.wrap(array);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.major = buffer.getShort();
        this.minor = buffer.getShort();
        this.patch = buffer.getShort();
        this.hardwareRev = buffer.getShort();
    }


    FirmwareVersion() {
        this.major = 0;
        this.minor = 0;
        this.patch = 0;
        this.hardwareRev = 0;
    }

    public boolean isNotSet() {
        return this.major == 0 && this.minor == 0 && this.patch == 0 && this.hardwareRev == 0;
    }

    public String toDisplayString() {
        return "" + this.major + "." + this.minor + "." + this.patch;
    }

    public String toString() {
        return "{ major : " + this.major + ", " + "minor : " + this.minor + ", " + "patch : " + this.patch + ", " + "hardwareRev : " + this.hardwareRev + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FirmwareVersion that = (FirmwareVersion)o;
        if (this.hardwareRev != that.hardwareRev) {
            return false;
        }
        if (this.major != that.major) {
            return false;
        }
        if (this.minor != that.minor) {
            return false;
        }
        if (this.patch != that.patch) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = this.major;
        result = 31 * result + this.minor;
        result = 31 * result + this.patch;
        result = 31 * result + this.hardwareRev;
        return result;
    }
}

