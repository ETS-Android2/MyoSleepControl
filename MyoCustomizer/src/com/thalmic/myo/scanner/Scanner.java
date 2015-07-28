/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.util.Log
 */
package com.thalmic.myo.scanner;

import android.os.Handler;
import android.util.Log;
import com.thalmic.myo.Myo;
import com.thalmic.myo.internal.ble.Address;
import com.thalmic.myo.internal.ble.BleManager;
import java.util.ArrayList;
import java.util.UUID;

public class Scanner {
    private static UUID sAdvertisedUuid;
    private BleManager mBleManager;
    private Handler mHandler;
    private boolean mScanning;
    private final Runnable mStopRunnable;
    private long mRestartInterval;
    private final Runnable mRestartRunnable;
    private ArrayList<OnScanningStartedListener> mScanningStartedListeners;
    private OnMyoScannedListener mMyoScannedListener;
    private BleManager.BleScanCallback mBleScanCallback;

    public Scanner(BleManager bleManager, OnMyoScannedListener scannedListener) {
        this.mStopRunnable = new Runnable(){

            @Override
            public void run() {
                Scanner.this.stopScanning();
            }
        };
        this.mRestartRunnable = new Runnable(){

            @Override
            public void run() {
                Scanner.this.restartScanning();
                if (Scanner.this.mRestartInterval > 0) {
                    Scanner.this.mHandler.postDelayed(Scanner.this.mRestartRunnable, Scanner.this.mRestartInterval);
                }
            }
        };
        this.mScanningStartedListeners = new ArrayList();
        this.mBleScanCallback = new ScanCallback();
        this.mBleManager = bleManager;
        this.mMyoScannedListener = scannedListener;
        this.mHandler = new Handler();
    }

    public void setBleManager(BleManager bleManager) {
        this.mBleManager = bleManager;
    }

    public void startScanning(long scanPeriod, long restartInterval) {
        if (this.mScanning) {
            Log.w((String)"Scanner", (String)"Scan is already in progress. Ignoring call to startScanning.");
            return;
        }
        this.mHandler.removeCallbacks(this.mStopRunnable);
        if (scanPeriod > 0) {
            this.mHandler.postDelayed(this.mStopRunnable, scanPeriod);
        }
        this.mHandler.removeCallbacks(this.mRestartRunnable);
        if (restartInterval > 0) {
            this.mHandler.postDelayed(this.mRestartRunnable, restartInterval);
        }
        this.mRestartInterval = restartInterval;
        boolean started = this.mBleManager.startBleScan(this.mBleScanCallback);
        if (started) {
            this.mScanning = true;
            for (OnScanningStartedListener listener : this.mScanningStartedListeners) {
                listener.onScanningStarted();
            }
        }
    }

    public void stopScanning() {
        this.mScanning = false;
        this.mHandler.removeCallbacks(this.mStopRunnable);
        this.mHandler.removeCallbacks(this.mRestartRunnable);
        this.mBleManager.stopBleScan(this.mBleScanCallback);
        for (OnScanningStartedListener listener : this.mScanningStartedListeners) {
            listener.onScanningStopped();
        }
    }

    private void restartScanning() {
        this.mBleManager.stopBleScan(this.mBleScanCallback);
        boolean started = this.mBleManager.startBleScan(this.mBleScanCallback);
        if (!started) {
            for (OnScanningStartedListener listener : this.mScanningStartedListeners) {
                listener.onScanningStopped();
            }
        }
    }

    public boolean isScanning() {
        return this.mScanning;
    }

    public void addOnScanningStartedListener(OnScanningStartedListener listener) {
        this.mScanningStartedListeners.add(listener);
        if (this.mScanning) {
            listener.onScanningStarted();
        }
    }



    private static boolean isMyo(UUID serviceUuid) {
        if (sAdvertisedUuid == null) {
            sAdvertisedUuid = UUID.fromString("4248124a-7f2c-4847-b9de-04a9010006d5");
        }
        return sAdvertisedUuid.equals(serviceUuid);
    }

    public static interface OnMyoScannedListener {
        public Myo onMyoScanned(Address var1, String var2, int var3);
    }

    public static interface OnScanningStartedListener {
        public void onScanningStarted();

        public void onScanningStopped();
    }

    private class ScanCallback
    implements BleManager.BleScanCallback {
        private ScanCallback() {
        }

        @Override
        public void onBleScan(final Address address, final String name, final int rssi, final UUID serviceUuid) {
            Scanner.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    if (Scanner.isMyo(serviceUuid)) {
                        Scanner.this.mMyoScannedListener.onMyoScanned(address, name, rssi);
                    }
                }
            });
        }

    }

}

