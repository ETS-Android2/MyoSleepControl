/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package com.thalmic.myo.internal.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.thalmic.myo.internal.ble.Address;
import com.thalmic.myo.internal.ble.BleGatt;
import com.thalmic.myo.internal.ble.BleManager;
import com.thalmic.myo.internal.ble.JBBluetoothLeController;
import com.thalmic.myo.internal.util.ByteUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@TargetApi(value=18)
class JBBleManager
implements BleManager {
    private Context mContext;
    private BluetoothAdapter mAdapter;
    private JBBluetoothLeController mController;
    private HashMap<BleManager.BleScanCallback, BluetoothAdapter.LeScanCallback> mCallbacks = new HashMap();

    JBBleManager(Context context) {
        this.mContext = context.getApplicationContext();
        BluetoothManager bluetoothManager = (BluetoothManager)context.getSystemService("bluetooth");
        this.mAdapter = bluetoothManager.getAdapter();
        this.mController = new JBBluetoothLeController(context);
    }

    @Override
    public boolean isBluetoothSupported() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return false;
        }
        return this.mAdapter != null;
    }

    @Override
    public BleGatt getBleGatt() {
        return this.mController;
    }

    @Override
    public boolean startBleScan(BleManager.BleScanCallback callback) {
        BluetoothAdapter.LeScanCallback leScanCallback = this.mCallbacks.get(callback);
        if (leScanCallback == null) {
            leScanCallback = this.createCallback(callback);
            this.mCallbacks.put(callback, leScanCallback);
        }
        return this.mAdapter.startLeScan(leScanCallback);
    }

    @Override
    public void stopBleScan(BleManager.BleScanCallback callback) {
        BluetoothAdapter.LeScanCallback leScanCallback = this.mCallbacks.remove(callback);
        this.mAdapter.stopLeScan(leScanCallback);
    }

    @Override
    public boolean connect(String address, boolean autoConnect) {
        return this.mController.connect(address, autoConnect);
    }

    @Override
    public void disconnect(String address) {
        this.mController.disconnect(address);
    }

    @Override
    public void dispose() {
        this.mController.close();
    }

    private BluetoothAdapter.LeScanCallback createCallback(BleManager.BleScanCallback callback) {
        return new LeScanCallback(callback);
    }

    static List<UUID> parseServiceUuids(byte[] adv_data) {
        int len;
        ArrayList<UUID> uuids = new ArrayList<UUID>();
        block4 : for (int offset = 0; offset < adv_data.length - 2; offset+=len - 1) {
            if ((len = adv_data[offset++]) == 0) break;
            byte type = adv_data[offset++];
            switch (type) {
                case 2: 
                case 3: {
                    while (len > 1) {
                        int uuid16 = adv_data[offset++];
                        int n = offset++;
                        len-=2;
                        uuids.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", uuid16+=adv_data[n] << 8)));
                    }
                    continue block4;
                }
                case 6: 
                case 7: {
                    while (len > 15) {
                        UUID uuid = ByteUtil.getUuidFromBytes(adv_data, offset);
                        len-=16;
                        offset+=16;
                        uuids.add(uuid);
                    }
                    continue block4;
                }
            }
        }
        return uuids;
    }

    static class LeScanCallback
    implements BluetoothAdapter.LeScanCallback {
        private BleManager.BleScanCallback mCallback;

        LeScanCallback(BleManager.BleScanCallback callback) {
            this.mCallback = callback;
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Address address = new Address(device.getAddress());
            List<UUID> uuids = JBBleManager.parseServiceUuids(scanRecord);
            UUID serviceUuid = uuids.isEmpty() ? null : uuids.get(0);
            this.mCallback.onBleScan(address, device.getName(), rssi, serviceUuid);
        }
    }

}

