/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.Log
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.thalmic.myo;

import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Myo;
import com.thalmic.myo.internal.util.NetworkUtil;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Reporter {
    private static final String TAG = "Reporter";
    public static final String EVENT_NAME_ATTACHED_MYO = "AttachedMyo";
    public static final String EVENT_NAME_DETACHED_MYO = "DetachedMyo";
    public static final String EVENT_NAME_SYNCED_MYO = "SyncedMyo";
    public static final String EVENT_NAME_UNSYNCED_MYO = "UnsyncedMyo";
    private static final String EVENT_URL = "http://devices.thalmic.com/event";
    private static final String MYOSDK_PLATFORM = "Android_" + Build.VERSION.RELEASE;
    private static final String MYOSDK_VERSION = "0.10.0";
    private static final String REPORTING_MAC_ADDRESS_KEY = "macAddress";
    private static final String REPORTING_PLATFORM_KEY = "softwarePlatform";
    private static final String REPORTING_SDK_VERSION_KEY = "softwareVersion";
    private static final String REPORTING_APP_ID_KEY = "appId";
    private static final String REPORTING_UUID_KEY = "uuid";
    private static final String REPORTING_DATA_KEY = "data";
    private static final String REPORTING_EVENT_NAME_KEY = "eventName";
    private static final String REPORTING_TIMESTAMP_KEY = "timestamp";
    private static final String REPORTING_FIRMWARE_VERSION_KEY = "firmwareVersion";
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private NetworkUtil mUtil;
    private boolean mSendUsageData = true;

    public Reporter() {
        this(new NetworkUtil());
    }

    public Reporter(NetworkUtil util) {
        this.mUtil = util;
    }

    public void setSendUsageData(boolean sendUsageData) {
        this.mSendUsageData = sendUsageData;
    }

    public boolean isSendingUsageData() {
        return this.mSendUsageData;
    }

    public void sendMyoEvent(final String appId, final String uuid, final String eventName, final Myo myo) {
        if (!this.mSendUsageData) {
            return;
        }
        if (myo == null || TextUtils.isEmpty((CharSequence)myo.getMacAddress())) {
            Log.e((String)"Reporter", (String)"Could not send Myo event. Invalid Myo.");
            return;
        }
        final long timestamp = System.currentTimeMillis() * 1000;
        new AsyncTask<Void, Void, Boolean>(){

            protected /* varargs */ Boolean doInBackground(Void ... params) {
                try {
                    JSONObject jo = Reporter.buildEventJsonObject(appId, uuid, eventName, myo, timestamp);
                    return Reporter.this.sendJsonPostRequest(jo.toString(), "http://devices.thalmic.com/event");
                }
                catch (Exception e) {
                    Log.e((String)"Reporter", (String)("Exception in sending event:" + e.toString()));
                    return false;
                }
            }
        }.executeOnExecutor((Executor)this.mExecutor);
    }

    private boolean sendJsonPostRequest(String jsonString, String urlString) throws IOException {
        boolean success;
        int responseCode = this.mUtil.postJsonToUrl(jsonString, urlString);
        boolean bl = success = responseCode == 200;
        if (!success) {
            Log.e((String)"Reporter", (String)("Unsuccessful sending post request to " + urlString + ". Received non-200 " + "(" + responseCode + ") response code from server."));
        }
        return success;
    }

    private static JSONObject buildEventJsonObject(String appId, String uuid, String eventName, Myo myo, long timestamp) throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("appId", (Object)appId);
        jo.put("softwarePlatform", (Object)MYOSDK_PLATFORM);
        jo.put("softwareVersion", (Object)"0.10.0");
        jo.put("uuid", (Object)uuid);
        JSONArray eventArray = new JSONArray();
        jo.put("data", (Object)eventArray);
        JSONObject event = new JSONObject();
        event.put("eventName", (Object)eventName);
        event.put("timestamp", timestamp);
        eventArray.put((Object)event);
        JSONObject eventData = new JSONObject();
        eventData.put("macAddress", (Object)Reporter.macAddressForReporting(myo.getMacAddress()));
        eventData.put("firmwareVersion", (Object)Reporter.firmwareVersionForReporting(myo.getFirmwareVersion()));
        event.put("data", (Object)eventData);
        return jo;
    }

    private static String macAddressForReporting(String address) {
        return address.toLowerCase().replace(':', '-');
    }

    private static String firmwareVersionForReporting(FirmwareVersion v) {
        return "" + v.major + "." + v.minor + "." + v.patch + "." + v.hardwareRev;
    }

}

