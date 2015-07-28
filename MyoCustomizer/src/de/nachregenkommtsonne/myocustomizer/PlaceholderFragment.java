package de.nachregenkommtsonne.myocustomizer;

import java.lang.reflect.Field;
import java.util.UUID;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.internal.ble.BleGatt;
import com.thalmic.myo.internal.ble.BleManager;
import com.thalmic.myo.scanner.ScanActivity;

import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment;
import afzkl.development.colorpickerview.preference.ColorPreference;
import afzkl.development.colorpickerview.preference.ColorPreference.OnShowDialogListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class PlaceholderFragment extends PreferenceFragment
{
  private Myo _myo;
  private BleGatt _bleGatt;
  private Preference _connection_connect_dialog;
  private Preference _connection_disconnect;
  private Preference _options;
  private Preference _options_set_color;
  private Preference _options_transport;
  private Preference _options_autosleep;
  private ColorPreference _options_pick_color_status;
  private ColorPreference _options_pick_color_connection;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    initMyo();

    addPreferencesFromResource(R.xml.preferences);

    _connection_connect_dialog = findPreference(getString(R.string.pref_key_connection_connect_dialog));
    _connection_connect_dialog.setOnPreferenceClickListener(new OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference preference)
      {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, ScanActivity.class);
        activity.startActivity(intent);
        return true;
      }
    });

    _connection_disconnect = findPreference(getString(R.string.pref_key_connection_disconnect));
    _connection_disconnect.setOnPreferenceClickListener(new OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference preference)
      {
        disconnectAll();

        return true;
      }
    });

    _options_set_color = findPreference(getString(R.string.pref_key_options_set_color));
    _options_set_color.setOnPreferenceClickListener(new OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference preference)
      {
        byte[] command = new byte[8];
        command[0] = 6;
        command[1] = 6;

        int statusColor = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getString(R.string.pref_key_options_pick_color_status), 0xFF0000);

        command[2] = (byte) (statusColor >> 16);
        command[3] = (byte) ((statusColor >> 8) & 0xFF);
        command[4] = (byte) (statusColor & 0xFF);

        int linkColor = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getString(R.string.pref_key_options_pick_color_connection), 0xFF0000);

        command[5] = (byte) (linkColor >> 16);
        command[6] = (byte) ((linkColor >> 8) & 0xFF);
        command[7] = (byte) (linkColor & 0xFF);

        _bleGatt.writeCharacteristic(
            _myo.getMacAddress(),
            UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"),
            UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"),
            command);

        return true;
      }
    });

    _options_transport = findPreference(getString(R.string.pref_key_options_transport));
    _options_transport.setOnPreferenceClickListener(new OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference preference)
      {
        byte[] command = new byte[2];
        command[0] = 4;
        command[1] = 0;

        _bleGatt.writeCharacteristic(
            _myo.getMacAddress(),
            UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"),
            UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"),
            command);

        Hub hub = Hub.getInstance();
        hub.getScanner().stopScanning();

        hub.detach(_myo.getMacAddress());
        
        _myo = null;
        updateEnabledState();
        disableAutoSleep();
        
        return true;
      }
    });

    _options_autosleep = findPreference(getString(R.string.pref_key_options_autosleep));
    _options_autosleep.setOnPreferenceChangeListener(new OnPreferenceChangeListener()
    {
      public boolean onPreferenceChange(Preference preference, Object newValue)
      {
        boolean dontSleep = (Boolean) newValue;

        byte[] command = new byte[3];
        command[0] = 9;
        command[1] = 1;
        command[2] = (byte) (dontSleep ? 1 : 0);

        _bleGatt.writeCharacteristic(
            _myo.getMacAddress(),
            UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"),
            UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"),
            command);

        return true;
      }
    });

    _options_pick_color_status = (ColorPreference) findPreference(getString(R.string.pref_key_options_pick_color_status));
    _options_pick_color_status.setOnShowDialogListener(new OnShowDialogListener()
    {
      @Override
      public void onShowColorPickerDialog(String arg0, int arg1)
      {
        int statusColor = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getString(R.string.pref_key_options_pick_color_status), 0xFFFF0000);
        ColorPickerDialogFragment f = ColorPickerDialogFragment.newInstance(0, "Select color for status light", null, statusColor, false);
        f.show(getFragmentManager(), "d");
      }
    });

    _options_pick_color_connection = (ColorPreference) findPreference(getString(R.string.pref_key_options_pick_color_connection));
    _options_pick_color_connection.setOnShowDialogListener(new OnShowDialogListener()
    {
      @Override
      public void onShowColorPickerDialog(String arg0, int arg1)
      {
        int linkColor = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(getString(R.string.pref_key_options_pick_color_connection), 0xFFFF0000);
        ColorPickerDialogFragment f = ColorPickerDialogFragment
            .newInstance(1, "Select color for connection light", null, linkColor, false);
        f.show(getFragmentManager(), "d");
      }
    });

    _options = findPreference(getString(R.string.pref_key_options));

    updateEnabledState();
    disableAutoSleep();
 }

  private void disableAutoSleep()
  {
    ((CheckBoxPreference)_options_autosleep).setChecked(false);
  }
  
  private void disconnectAll()
  {
    Hub hub = Hub.getInstance();
    hub.getScanner().stopScanning();

    for (Myo myo : hub.getConnectedDevices())
    {
      hub.detach(myo.getMacAddress());
    }
    
    disableAutoSleep();
  }
  
  @Override
  public void onPause()
  {
    super.onPause();
    
    disconnectAll();
  }
  
  @Override
  public void onResume()
  {
    super.onResume();
    
    updateEnabledState();
    disableAutoSleep();
 }
  
  private void updateEnabledState()
  {
    boolean state = _myo != null;
    _connection_disconnect.setEnabled(state);
    _options.setEnabled(state);
  }

  private void initMyo()
  {
    Hub hub = Hub.getInstance();

    hub.init(this.getActivity());

    try
    {
      Field mBleManagerField = Hub.class.getDeclaredField("mBleManager");
      mBleManagerField.setAccessible(true);
      BleManager bleManager = (BleManager) mBleManagerField.get(hub);
      _bleGatt = bleManager.getBleGatt();
    }
    catch (Exception e)
    {
      Log.e("err", e.toString());
    }

    hub.setSendUsageData(false);

    hub.addListener(new AbstractDeviceListener()
    {
      @Override
      public void onConnect(Myo myo, long timestamp)
      {
        super.onConnect(myo, timestamp);
        
        _myo = myo;
        updateEnabledState();
      }

      @Override
      public void onDisconnect(Myo myo, long timestamp)
      {
        super.onDisconnect(myo, timestamp);
        
        _myo = null;
        updateEnabledState();
      }
    });
  }

  public void setColorStatus(int color)
  {
    _options_pick_color_status.saveValue(color);
  }

  public void setColorLink(int color)
  {
    _options_pick_color_connection.saveValue(color);
  }
}
