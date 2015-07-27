/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCallback
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.content.Context
 *  android.os.Handler
 *  android.util.Log
 */
package com.thalmic.myo.internal.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.thalmic.myo.internal.ble.Address;
import com.thalmic.myo.internal.ble.BleGatt;
import com.thalmic.myo.internal.ble.BleGattCallback;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@TargetApi(value=18)
class JBBluetoothLeController
implements BleGatt {
    private final class GattCallback extends BluetoothGattCallback {
		public void onConnectionStateChange(final BluetoothGatt gatt, final int status, final int newState) {
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		            Address address = GattCallback.this.addressOf(gatt);
		            if (status != 0) {
		                Log.e((String)"JBBluetoothLeController", (String)("Received error status=" + status + " for onConnectionStateChange on address=" + address));
		                if (newState == 0) {
		                    JBBluetoothLeController.this.mExternalCallback.onDeviceConnectionFailed(address);
		                }
		            } else if (newState == 2) {
		                JBBluetoothLeController.this.mExternalCallback.onDeviceConnected(address);
		            } else if (newState == 0) {
		                JBBluetoothLeController.this.mExternalCallback.onDeviceDisconnected(address);
		            }
		        }
		    });
		}

		public void onServicesDiscovered(final BluetoothGatt gatt, final int status) {
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		            boolean success;
		            Address address = GattCallback.this.addressOf(gatt);
		            boolean bl = success = status == 0;
		            if (!success) {
		                Log.e((String)"JBBluetoothLeController", (String)("Received error status=" + status + " for onServicesDiscovered on address=" + address));
		            }
		            JBBluetoothLeController.this.mExternalCallback.onServicesDiscovered(address, success);
		        }
		    });
		}

		public void onCharacteristicChanged(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		        	byte[] data = characteristic.getValue();
		        	if (data.length < 20){
		        		final StringBuilder stringBuilder = new StringBuilder(data.length);
		                for(byte byteChar : data)
		                    stringBuilder.append(String.format("%02X ", byteChar));

		                
		                Log.e("character", "onCharacteristicChanged: " + characteristic.getValue().length + " " +  stringBuilder.toString());
		        	}
		            JBBluetoothLeController.this.mExternalCallback.onCharacteristicChanged(GattCallback.this.addressOf(gatt), characteristic.getUuid(), characteristic.getValue());
		        }
		    });
		}

		
		
		public void onCharacteristicRead(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, final int status) {
		    this.onOperationFinished();
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		            boolean success;
		            Address address = GattCallback.this.addressOf(gatt);
		            boolean bl = success = status == 0;
		            if (!success) {
		                Log.e((String)"JBBluetoothLeController", (String)("Received error status=" + status + " for onCharacteristicRead of " + characteristic.getUuid() + " on address=" + address));
		            }
		            byte[] data = characteristic.getValue();
		        	if (data.length < 20){
		        		final StringBuilder stringBuilder = new StringBuilder(data.length);
		                for(byte byteChar : data)
		                    stringBuilder.append(String.format("%02X ", byteChar));

		                
		                Log.e("character", "onCharacteristicChanged: " + characteristic.getValue().length + " " +  stringBuilder.toString());
		        	}
		            JBBluetoothLeController.this.mExternalCallback.onCharacteristicRead(address, characteristic.getUuid(), characteristic.getValue(), success);
		        }
		    });
		}

		public void onCharacteristicWrite(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, final int status) {
		    this.onOperationFinished();
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		            boolean success;
		            Address address = GattCallback.this.addressOf(gatt);
		            boolean bl = success = status == 0;
		            if (!success) {
		                Log.e((String)"JBBluetoothLeController", (String)("Received error status=" + status + " for onCharacteristicWrite of " + characteristic.getUuid() + " on address=" + address));
		            }
		            JBBluetoothLeController.this.mExternalCallback.onCharacteristicWrite(address, characteristic.getUuid(), success);
		        }
		    });
		}

		public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
		    Log.e("desc", "descriptor:" + descriptor.getValue().length);
			this.onOperationFinished();
		}

		public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
		    this.onOperationFinished();
		}

		public void onReadRemoteRssi(final BluetoothGatt gatt, final int rssi, final int status) {
		    this.onOperationFinished();
		    JBBluetoothLeController.this.mCallbackHandler.post(new Runnable(){

		        @Override
		        public void run() {
		            boolean success;
		            Address address = GattCallback.this.addressOf(gatt);
		            boolean bl = success = status == 0;
		            if (!success) {
		                Log.e((String)"JBBluetoothLeController", (String)("Received error status=" + status + " for onReadRemoteRssi on address=" + address));
		            }
		            JBBluetoothLeController.this.mExternalCallback.onReadRemoteRssi(address, rssi, success);
		        }
		    });
		}

		private void onOperationFinished() {
		    JBBluetoothLeController.this.mOperationPending = false;
		}

		private Address addressOf(BluetoothGatt gatt) {
		    return new Address(gatt.getDevice().getAddress());
		}
	}

    private static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private HashMap<String, BluetoothGatt> mGattConnections = new HashMap();
    private ExecutorService mOperationExecutor;
    private boolean mOperationPending;
    private Handler mCallbackHandler;
    private BleGattCallback mExternalCallback;
    private final BluetoothGattCallback mGattCallback;

    JBBluetoothLeController(Context context) {
        this.mGattCallback = new GattCallback();
        this.mContext = context;
        this.mOperationExecutor = Executors.newSingleThreadExecutor();
        this.mCallbackHandler = new Handler();
        BluetoothManager bluetoothManager = (BluetoothManager)this.mContext.getSystemService("bluetooth");
        this.mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public void setBleGattCallback(BleGattCallback callback) {
        this.mExternalCallback = callback;
    }

    public void close() {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                HashSet<String> keySet = new HashSet<String>(JBBluetoothLeController.this.mGattConnections.keySet());
                for (String address : keySet) {
                    BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.remove(address);
                    bluetoothGatt.close();
                }
            }
        });
        this.mOperationExecutor.shutdown();
    }

    public boolean connect(final String address, final boolean autoConnect) {
        if (this.mOperationExecutor.isShutdown()) {
            Log.w((String)"JBBluetoothLeController", (String)("Could not connect to address " + address + ". Executor shutdown."));
            return false;
        }
        Future result = this.mOperationExecutor.submit(new Callable<Boolean>(){

            @Override
            public Boolean call() throws Exception {
                BluetoothDevice device;
                if (JBBluetoothLeController.this.mBluetoothAdapter == null || address == null) {
                    Log.w((String)"JBBluetoothLeController", (String)"BluetoothAdapter not initialized or unspecified address.");
                    return false;
                }
                BluetoothGatt existingGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                if (existingGatt != null) {
                    existingGatt.close();
                    device = existingGatt.getDevice();
                } else {
                    device = JBBluetoothLeController.this.mBluetoothAdapter.getRemoteDevice(address);
                }
                BluetoothGatt bluetoothGatt = device.connectGatt(JBBluetoothLeController.this.mContext, autoConnect, JBBluetoothLeController.this.mGattCallback);
                JBBluetoothLeController.this.mGattConnections.put(address, bluetoothGatt);
                return bluetoothGatt != null;
            }
        });
        try {
            return (Boolean)result.get();
        }
        catch (InterruptedException e) {
            Log.w((String)"JBBluetoothLeController", (String)("GATT connect interrupted for address: " + address), (Throwable)e);
            return false;
        }
        catch (ExecutionException e) {
            Log.e((String)"JBBluetoothLeController", (String)("Problem during GATT connect for address: " + address), (Throwable)e);
            return false;
        }
    }

    public void disconnect(final String address) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                if (JBBluetoothLeController.this.mBluetoothAdapter == null || bluetoothGatt == null) {
                    Log.w((String)"JBBluetoothLeController", (String)"BluetoothAdapter not initialized");
                    return;
                }
                bluetoothGatt.disconnect();
            }
        });
    }

    @Override
    public void discoverServices(final String address) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                bluetoothGatt.discoverServices();
            }
        });
    }

    @Override
    public void readCharacteristic(final String address, final UUID serviceUuid, final UUID charUuid) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                BluetoothGattService service = bluetoothGatt.getService(serviceUuid);
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(charUuid);
                JBBluetoothLeController.this.readCharacteristic(bluetoothGatt, characteristic);
            }
        });
    }

    @Override
    public void writeCharacteristic(final String address, final UUID serviceUuid, final UUID charUuid, final byte[] value) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                BluetoothGattService service = bluetoothGatt.getService(serviceUuid);
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(charUuid);
                JBBluetoothLeController.this.writeCharacteristic(bluetoothGatt, characteristic, value);
            }
        });
    }

    @Override
    public void setCharacteristicNotification(final String address, final UUID serviceUuid, final UUID charUuid, final boolean enable, final boolean indicate) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt bluetoothGatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                BluetoothGattService service = bluetoothGatt.getService(serviceUuid);
                BluetoothGattCharacteristic characteristic = service.getCharacteristic(charUuid);
                JBBluetoothLeController.this.setCharacteristicNotification(bluetoothGatt, characteristic, enable, indicate);
            }
        });
    }

    @Override
    public void readRemoteRssi(final String address) {
        this.submitTask(new Runnable(){

            @Override
            public void run() {
                BluetoothGatt gatt = (BluetoothGatt)JBBluetoothLeController.this.mGattConnections.get(address);
                if (gatt.readRemoteRssi()) {
                    JBBluetoothLeController.this.mOperationPending = true;
                    JBBluetoothLeController.this.waitForOperationCompletion();
                } else {
                    Log.e((String)"JBBluetoothLeController", (String)"Failed reading remote rssi");
                }
            }
        });
    }

    private Future<?> submitTask(Runnable task) {
        if (this.mOperationExecutor.isShutdown()) {
            Log.w((String)"JBBluetoothLeController", (String)"Could not submit task. Executor shutdown.");
            return null;
        }
        return this.mOperationExecutor.submit(task);
    }

    private void readCharacteristic(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        if (gatt.readCharacteristic(characteristic)) {
            this.mOperationPending = true;
            this.waitForOperationCompletion();
        } else {
            Log.e((String)"JBBluetoothLeController", (String)("Failed reading characteristic " + characteristic.getUuid()));
        }
    }

    private void writeCharacteristic(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] value) {
        characteristic.setValue(value);
        if (gatt.writeCharacteristic(characteristic)) {
            this.mOperationPending = true;
            this.waitForOperationCompletion();
        } else {
            Log.e((String)"JBBluetoothLeController", (String)("Failed writing characteristic " + characteristic.getUuid()));
        }
    }

    private void setCharacteristicNotification(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, boolean enable, boolean indicate) {
        if (!gatt.setCharacteristicNotification(characteristic, enable)) {
            Log.e((String)"JBBluetoothLeController", (String)("Failed setting characteristic notification " + characteristic.getUuid()));
            return;
        }
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG);
        byte[] value = enable ? (indicate ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
        clientConfig.setValue(value);
        if (gatt.writeDescriptor(clientConfig)) {
            this.mOperationPending = true;
            this.waitForOperationCompletion();
        } else {
            Log.e((String)"JBBluetoothLeController", (String)("Failed writing descriptor " + clientConfig.getUuid()));
        }
    }

    private void waitForOperationCompletion() {
        long t;
        long timeout = 1000;
        long interval = 10;
        for (t = 1000; t > 0 && this.mOperationPending; t-=10) {
            try {
                Thread.sleep(10);
                continue;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (t == 0) {
            Log.w((String)"JBBluetoothLeController", (String)"Wait for operation completion timed out.");
        }
    }
}

