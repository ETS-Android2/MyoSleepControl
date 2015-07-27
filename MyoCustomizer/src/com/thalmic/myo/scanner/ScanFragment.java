/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.bluetooth.BluetoothAdapter
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  com.thalmic.myo.R
 *  com.thalmic.myo.R$id
 *  com.thalmic.myo.R$layout
 *  com.thalmic.myo.R$menu
 *  com.thalmic.myo.R$string
 */
package com.thalmic.myo.scanner;

import android.app.Activity;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import de.nachregenkommtsonne.myocustomizer.R;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.scanner.MyoDeviceListAdapter;
import com.thalmic.myo.scanner.Scanner;

public class ScanFragment
extends Fragment
implements Scanner.OnScanningStartedListener {
    private Scanner mScanner;
    private ListView mListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        this.mScanner = Hub.getInstance().getScanner();
        this.mScanner.addOnScanningStartedListener(this);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                this.startActivityForResult(enableBtIntent, 1);
            } else {
                this.mScanner.startScanning();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mScanner.removeOnScanningStartedListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myosdk__fragment_scan, container, false);
        this.mListView = (ListView)view.findViewById(R.id.myosdk__scan_result_view);
        final MyoDeviceListAdapter adapter = this.mScanner.getAdapter();
        this.mListView.setAdapter((ListAdapter)adapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                    Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                    ScanFragment.this.startActivityForResult(enableBtIntent, 1);
                    return;
                }
                Scanner.OnMyoClickedListener listener = ScanFragment.this.mScanner.getOnMyoClickedListener();
                if (listener != null) {
                    Myo myo = adapter.getMyo(position);
                    listener.onMyoClicked(myo);
                }
            }
        });
        return view;
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mListView.setAdapter(null);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.myosdk__fragment_scan, menu);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem scanItem = menu.findItem(R.id.myosdk__action_scan);
        scanItem.setTitle(this.mScanner.isScanning() ? R.string.myosdk__action_stop_scan : R.string.myosdk__action_scan);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (R.id.myosdk__action_scan == id) {
            this.toggleScanning();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == -1) {
                this.mScanner.startScanning();
            } else {
                this.onBluetoothNotEnabled();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onScanningStarted() {
        this.getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onScanningStopped() {
        this.getActivity().invalidateOptionsMenu();
    }

    protected void onBluetoothNotEnabled() {
        this.getActivity().finish();
    }

    private void toggleScanning() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
            this.startActivityForResult(enableBtIntent, 1);
            return;
        }
        if (this.mScanner.isScanning()) {
            this.mScanner.stopScanning();
        } else {
            this.mScanner.startScanning();
        }
    }

}

