/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.app.ActionBar
 *  android.app.Activity
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.Window
 *  com.thalmic.myo.R
 *  com.thalmic.myo.R$dimen
 *  com.thalmic.myo.R$layout
 */
package com.thalmic.myo.scanner;

import android.app.Activity;
import android.os.Bundle;
import de.nachregenkommtsonne.myocustomizer.R;


public class ScanActivity
extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        int width = this.getResources().getDimensionPixelSize(R.dimen.myosdk__fragment_scan_window_width);
        int height = this.getResources().getDimensionPixelSize(R.dimen.myosdk__fragment_scan_window_height);
        if (width > 0 && height > 0) {
            this.getWindow().setLayout(width, height);
        }
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.myosdk__activity_scan);
        this.getActionBar().setDisplayOptions(0, 2);
    }
}

