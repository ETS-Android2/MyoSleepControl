/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class FirmwareInfo {
    static final int EXPECTED_BYTE_LENGTH = 8;
   

    FirmwareInfo(byte[] array) {
        if (array.length < 8) {
            throw new IllegalArgumentException("Unexpected length=" + array.length + " of array. Expecting length of " + 8);
        }
        ByteBuffer buffer = ByteBuffer.wrap(array);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
        
    }

    FirmwareInfo() {


    }

}

