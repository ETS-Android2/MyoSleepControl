package de.nachregenkommtsonne.myocustomizer;

import java.lang.reflect.Field;
import java.util.UUID;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Hub.LockingPolicy;
import com.thalmic.myo.internal.ble.BleGatt;
import com.thalmic.myo.internal.ble.BleManager;
import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PlaceholderFragment extends PreferenceFragment
{
  Myo _myo;
  BleGatt _bleGatt;
  private Preference _connection_connect_adjacent;
  private Preference _connection_connect_dialog;
  private Preference _connection_disconnect;
  private Preference _options_set_color;
  private Preference _options_transport;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    initMyo();
    
    addPreferencesFromResource(R.xml.preferences);
    
    _connection_connect_adjacent = findPreference(getString(R.string.pref_key_connection_connect_adjacent));
    _connection_connect_adjacent.setOnPreferenceClickListener(new OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference preference)
      {
        Hub.getInstance().attachToAdjacentMyo();
        return true;
      }
    });
    
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
        Hub hub = Hub.getInstance();
        hub.getScanner().stopScanning();
        
        for (Myo myo : hub.getConnectedDevices())
        {
          hub.detach(myo.getMacAddress());
        }
        
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
        command[2] = (byte) 255;
        command[3] = (byte) 0;
        command[4] = (byte) 0;
        command[5] = (byte) 255;
        command[6] = (byte) 0;
        command[7] = (byte) 0;
        
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);
        
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
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);

        return true;
      }
    });
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
      }
      
      @Override
      public void onDisconnect(Myo myo, long timestamp)
      {
        super.onDisconnect(myo, timestamp);
        _myo = null;
      }
    });
  }
    
  /*@Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    
    Button btColor = (Button) rootView.findViewById(R.id.btColor);
    Button btTurnOff = (Button) rootView.findViewById(R.id.btTurnOff);
    Button btStayAwake = (Button) rootView.findViewById(R.id.btStayAwake);
    Button btDontStayAwake = (Button) rootView.findViewById(R.id.btDontStayAwake);
    
    final EditText red = (EditText) rootView.findViewById(R.id.red);
    final EditText green = (EditText) rootView.findViewById(R.id.green);
    final EditText blue = (EditText) rootView.findViewById(R.id.blue);
    final EditText red2 = (EditText) rootView.findViewById(R.id.red2);
    final EditText green2 = (EditText) rootView.findViewById(R.id.green2);
    final EditText blue2 = (EditText) rootView.findViewById(R.id.blue2);
    
    btColor.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        int iRed = Integer.parseInt(red.getText().toString());
        int iGreen = Integer.parseInt(green.getText().toString());
        int iBlue = Integer.parseInt(blue.getText().toString());
        int iRed2 = Integer.parseInt(red2.getText().toString());
        int iGreen2 = Integer.parseInt(green2.getText().toString());
        int iBlue2 = Integer.parseInt(blue2.getText().toString());
        byte[] command = new byte[8];
        command[0] = 6;
        command[1] = 6;
        command[2] = (byte) iRed;
        command[3] = (byte) iGreen;
        command[4] = (byte) iBlue;
        command[5] = (byte) iRed2;
        command[6] = (byte) iGreen2;
        command[7] = (byte) iBlue2;
        
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);
      }
    });
    
    btTurnOff.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        byte[] command = new byte[2];
        command[0] = 4;
        command[1] = 0;
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);
      }
    });
    
    btStayAwake.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        byte[] command = new byte[3];
        command[0] = 9;
        command[1] = 1;
        command[2] = (byte) 1;
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);
      }
    });
    
    btDontStayAwake.setOnClickListener(new OnClickListener()
    {
      public void onClick(View v)
      {
        byte[] command = new byte[3];
        command[0] = 9;
        command[1] = 1;
        command[2] = (byte) 0;
        _bleGatt.writeCharacteristic(_myo.getMacAddress(), UUID.fromString("d5060001-a904-deb9-4748-2c7f4a124842"), UUID.fromString("d5060401-a904-deb9-4748-2c7f4a124842"), command);
      }
    });
    
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
    hub.attachToAdjacentMyo();
    hub.setLockingPolicy(LockingPolicy.NONE);
    
    hub.addListener(new AbstractDeviceListener()
    {
      @Override
      public void onConnect(Myo myo, long timestamp)
      {
        super.onConnect(myo, timestamp);
        _myo = myo;
      }
    });
    
    return rootView;
  }*/
}
