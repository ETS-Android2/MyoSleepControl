/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  com.thalmic.myo.R
 *  com.thalmic.myo.R$drawable
 *  com.thalmic.myo.R$id
 *  com.thalmic.myo.R$layout
 *  com.thalmic.myo.R$string
 */
package com.thalmic.myo.scanner;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import de.nachregenkommtsonne.myocustomizer.R;

import com.thalmic.myo.Myo;
import com.thalmic.myo.scanner.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class MyoDeviceListAdapter
extends BaseAdapter
implements Scanner.ScanListAdapter {
    private ArrayList<Item> mItems = new ArrayList();
    private RssiComparator mComparator = new RssiComparator();

    @Override
    public void addDevice(Myo myo, int rssi) {
        if (myo == null) {
            throw new IllegalArgumentException("Myo cannot be null.");
        }
        Item item = this.getItem(myo);
        if (item != null) {
            item.rssi = rssi;
        } else {
            item = new Item(myo, rssi);
            this.mItems.add(item);
        }
        this.notifyDataSetChanged();
    }

    public Myo getMyo(int position) {
        return this.mItems.get((int)position).myo;
    }

    public void clear() {
        this.mItems.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDeviceChanged() {
        this.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        this.sortByRssi();
        super.notifyDataSetChanged();
    }

    public int getCount() {
        return this.mItems.size();
    }

    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        String version;
        String deviceName;
        ViewHolder viewHolder;
        Context context = viewGroup.getContext();
        if (view == null) {
            view = View.inflate((Context)context, (int)R.layout.myosdk__device_list_item, (ViewGroup)null);
            viewHolder = new ViewHolder();
            viewHolder.deviceName = (TextView)view.findViewById(16908308);
            viewHolder.deviceVersion = (TextView)view.findViewById(16908309);
            viewHolder.requiredDeviceVersion = (TextView)view.findViewById(R.id.myosdk__required_firmware_version_text);
            viewHolder.progressBar = (ProgressBar)view.findViewById(R.id.myosdk__progress);
            viewHolder.connectionStateDot = view.findViewById(R.id.myosdk__connection_state_dot);
            view.setTag((Object)viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Myo myo = this.mItems.get((int)i).myo;
        viewHolder.deviceVersion.setText((CharSequence)"");
        viewHolder.requiredDeviceVersion.setText((CharSequence)"");
        viewHolder.deviceVersion.setVisibility(8);
        viewHolder.requiredDeviceVersion.setVisibility(8);
        viewHolder.connectionStateDot.setVisibility(8);
        viewHolder.progressBar.setVisibility(8);
        if (myo.getConnectionState() == Myo.ConnectionState.DISCONNECTED) {
            if (!myo.isFirmwareVersionSupported()) {
                viewHolder.deviceVersion.setVisibility(0);
                viewHolder.requiredDeviceVersion.setVisibility(0);
                viewHolder.connectionStateDot.setVisibility(0);
                viewHolder.connectionStateDot.setBackgroundResource(R.drawable.myosdk__firmware_incompatible_dot);
                version = myo.getFirmwareVersion().toDisplayString();
                String requiredVersion = "1.1.0";
                String versionString = String.format(context.getString(R.string.myosdk__firmware_version_format), version);
                String requiredVersionString = String.format(context.getString(R.string.myosdk__firmware_required_format), requiredVersion);
                viewHolder.deviceVersion.setText((CharSequence)versionString);
                viewHolder.requiredDeviceVersion.setText((CharSequence)requiredVersionString);
            }
        } else if (myo.getConnectionState() == Myo.ConnectionState.CONNECTING) {
            viewHolder.progressBar.setVisibility(0);
        } else if (myo.getConnectionState() == Myo.ConnectionState.CONNECTED) {
            viewHolder.deviceVersion.setVisibility(0);
            viewHolder.connectionStateDot.setVisibility(0);
            viewHolder.connectionStateDot.setBackgroundResource(R.drawable.myosdk__connected_dot);
            version = myo.getFirmwareVersion().toDisplayString();
            String versionString = String.format(context.getString(R.string.myosdk__firmware_version_format), version);
            viewHolder.deviceVersion.setText((CharSequence)versionString);
        }
        if (TextUtils.isEmpty((CharSequence)(deviceName = myo.getName()))) {
            deviceName = context.getString(R.string.myosdk__unknown_myo);
        }
        viewHolder.deviceName.setText((CharSequence)deviceName);
        return view;
    }

    private Item getItem(Myo myo) {
        int size = this.mItems.size();
        for (int i = 0; i < size; ++i) {
            if (!this.mItems.get((int)i).myo.equals(myo)) continue;
            return this.mItems.get(i);
        }
        return null;
    }

    private void sortByRssi() {
        Collections.sort(this.mItems, this.mComparator);
    }

    private static class RssiComparator
    implements Comparator<Item> {
        private RssiComparator() {
        }

        @Override
        public int compare(Item lhs, Item rhs) {
            if (lhs.myo.equals(rhs.myo)) {
                return 0;
            }
            return rhs.rssi - lhs.rssi;
        }

        @Override
        public boolean equals(Object object) {
            return super.equals(object);
        }
    }

    private static class ViewHolder {
        TextView deviceName;
        TextView deviceVersion;
        TextView requiredDeviceVersion;
        ProgressBar progressBar;
        View connectionStateDot;

        private ViewHolder() {
        }
    }

    private class Item {
        final Myo myo;
        int rssi;

        public Item(Myo myo, int rssi) {
            this.myo = myo;
            this.rssi = rssi;
        }
    }

}

