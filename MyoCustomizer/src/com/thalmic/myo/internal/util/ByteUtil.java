/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo.internal.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ByteUtil {

    public static UUID getUuidFromBytes(byte[] array, int index) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        long msb = buffer.getLong(index);
        long lsb = buffer.getLong(index + 8);
        return new UUID(msb, lsb);
    }

    public static String getString(byte[] array, int offset) {
        if (offset > array.length) {
            return null;
        }
        byte[] strBytes = new byte[array.length - offset];
        for (int i = 0; i != array.length - offset; ++i) {
            strBytes[i] = array[offset + i];
        }
        return new String(strBytes);
    }
}

