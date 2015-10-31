/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.Pair
 */
package com.thalmic.myo;

import android.util.Log;
import android.util.Pair;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.GattConstants;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.MyoGatt;
import com.thalmic.myo.internal.ble.Address;
import com.thalmic.myo.internal.ble.BleGatt;
import com.thalmic.myo.internal.ble.BleGattCallback;
import com.thalmic.myo.internal.util.ByteUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.UUID;

class GattCallback
extends BleGattCallback {
    private Hub mHub;
    private BleGatt mBleGatt;
    private MyoGatt mMyoGatt;
    private UpdateParser mParser;
    private HashMap<Address, LinkedHashMap<UUID, InitReadChar>> mInitializingMyos = new HashMap();

    public GattCallback(Hub hub) {
        this.mHub = hub;
    }

    void setBleGatt(BleGatt bleGatt) {
        this.mBleGatt = bleGatt;
    }

    void setMyoGatt(MyoGatt myoGatt) {
        this.mMyoGatt = myoGatt;
    }

    void setUpdateParser(UpdateParser updateParser) {
        this.mParser = updateParser;
    }

    @Override
    public void onDeviceConnectionFailed(Address address) {
        this.onDeviceDisconnected(address);
    }

    @Override
    public void onDeviceConnected(Address address) {
        if (!Hub.allowedToConnectToMyo(this.mHub, address.toString())) {
            this.onMyoInitializationFailed(address);
            return;
        }
        this.mInitializingMyos.put(address, new LinkedHashMap());
        this.mBleGatt.discoverServices(address.toString());
    }

    @Override
    public void onDeviceDisconnected(Address address) {
        if (!this.mInitializingMyos.containsKey(address)) {
            Myo myo = this.getMyoDevice(address);
            this.mParser.onMyoDisconnected(myo);
        } else {
            this.mInitializingMyos.remove(address);
        }
    }

    @Override
    public void onServicesDiscovered(Address address, boolean success) {
        if (!success) {
            this.onMyoInitializationFailed(address);
            return;
        }
        this.readNecessaryCharacteristics(address);
    }

    @Override
    public void onCharacteristicRead(Address address, UUID uuid, byte[] value, boolean success) {
        Myo myo = this.getMyoDevice(address);
        if (!success) {
            if (myo.getConnectionState() == Myo.ConnectionState.CONNECTING) {
                this.onMyoInitializationFailed(address);
            }
            return;
        }
        if (myo.getConnectionState() == Myo.ConnectionState.CONNECTING && this.mInitializingMyos.get(address).remove(uuid) != null) {
            if (GattConstants.DEVICE_NAME_CHAR_UUID.equals(uuid)) {
                myo.setName(ByteUtil.getString(value, 0));
            } else if (GattConstants.FIRMWARE_VERSION_CHAR_UUID.equals(uuid)) {
                boolean firmwareSupported = this.onFirmwareVersionRead(myo, value);
                if (!firmwareSupported) {
                    this.onMyoInitializationFailed(address);
                    return;
                }
            } else if (GattConstants.FIRMWARE_INFO_CHAR_UUID.equals(uuid)) {
                this.onFirmwareInfoRead(myo, value);
            }
            if (!this.readNextInitializationCharacteristic(address)) {
                this.mInitializingMyos.remove(address);
                this.onMyoInitializationSucceeded(address);
            }
        }
    }


    private Myo getMyoDevice(Address address) {
        Myo device = this.mHub.getDevice(address.toString());
        if (device == null) {
            device = this.mHub.addKnownDevice(address);
        }
        return device;
    }

    private void readNecessaryCharacteristics(Address address) {
        this.mInitializingMyos.get(address).put(GattConstants.FIRMWARE_VERSION_CHAR_UUID, new InitReadChar(GattConstants.CONTROL_SERVICE_UUID, GattConstants.FIRMWARE_VERSION_CHAR_UUID));
        this.mInitializingMyos.get(address).put(GattConstants.FIRMWARE_INFO_CHAR_UUID, new InitReadChar(GattConstants.CONTROL_SERVICE_UUID, GattConstants.FIRMWARE_INFO_CHAR_UUID));
        this.mInitializingMyos.get(address).put(GattConstants.DEVICE_NAME_CHAR_UUID, new InitReadChar(GattConstants.GAP_SERVICE_UUID, GattConstants.DEVICE_NAME_CHAR_UUID));
        this.readNextInitializationCharacteristic(address);
    }

    private boolean readNextInitializationCharacteristic(Address address) {
        Iterator<InitReadChar> iterator = this.mInitializingMyos.get(address).values().iterator();
        if (!iterator.hasNext()) {
            return false;
        }
        InitReadChar readChar = iterator.next();
        this.mBleGatt.readCharacteristic(address.toString(), readChar.getService(), readChar.getCharacteristic());
        return true;
    }

    private boolean onFirmwareVersionRead(Myo myo, byte[] value) {
        FirmwareVersion fwVersion;
        try {
            fwVersion = new FirmwareVersion(value);
        }
        catch (IllegalArgumentException e) {
            Log.e((String)"GattCallback", (String)"Problem reading FirmwareVersion.", (Throwable)e);
            fwVersion = new FirmwareVersion();
        }
        myo.setFirmwareVersion(fwVersion);
        if (!myo.isFirmwareVersionSupported()) {
            String format = "Myo (address=%s) firmware version (%s) is not supported. The SDK requires firmware version %d.x.x, minimum %d.%d.0.";
            Log.e((String)"GattCallback", (String)String.format(format, myo.getMacAddress(), fwVersion.toDisplayString(), 1, 1, 1));
            return false;
        }
        return true;
    }

    private void onFirmwareInfoRead(Myo myo, byte[] value) {

    }

    private void onMyoInitializationSucceeded(Address address) {
        Myo myo = this.getMyoDevice(address);
        String addressString = address.toString();
        this.mBleGatt.setCharacteristicNotification(addressString, GattConstants.EMG_SERVICE_UUID, GattConstants.EMG0_DATA_CHAR_UUID, true, false);
        this.mBleGatt.setCharacteristicNotification(addressString, GattConstants.FV_SERVICE_UUID, GattConstants.FV_DATA_CHAR_UUID, true, false);
        this.mBleGatt.setCharacteristicNotification(addressString, GattConstants.IMU_SERVICE_UUID, GattConstants.IMU_DATA_CHAR_UUID, true, false);
        this.mBleGatt.setCharacteristicNotification(addressString, GattConstants.CLASSIFIER_SERVICE_UUID, GattConstants.CLASSIFIER_EVENT_CHAR_UUID, true, true);
        this.mParser.onMyoConnected(myo);
    }

    private void onMyoInitializationFailed(Address address) {
        Log.e((String)"GattCallback", (String)("Failure in initialization of Myo. Disconnecting from Myo with address=" + address));
        this.mMyoGatt.disconnect(address.toString());
    }

    static interface UpdateParser
     {
        public void onMyoConnected(Myo var1);

        public void onMyoDisconnected(Myo var1);

    }



    private static class InitReadChar extends Pair<UUID, UUID>
    {
        public InitReadChar(UUID serviceUuid, UUID characteristicUuid)
        {
            super(serviceUuid, characteristicUuid);
        }

        public UUID getService() {
            return (UUID)this.first;
        }

        public UUID getCharacteristic() {
            return (UUID)this.second;
        }
    }

}

