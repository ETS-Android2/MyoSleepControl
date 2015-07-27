/*
 * Decompiled with CFR 0_101.
 */
package com.thalmic.myo.internal.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtil {
    public int postJsonToUrl(String jsonString, String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("charset", "utf-8");
        urlConnection.connect();
        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
        dos.writeBytes(jsonString);
        dos.flush();
        dos.close();
        return urlConnection.getResponseCode();
    }
}

