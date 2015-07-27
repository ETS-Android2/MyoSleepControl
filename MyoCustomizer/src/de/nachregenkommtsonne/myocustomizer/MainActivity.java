package de.nachregenkommtsonne.myocustomizer;

import android.app.Activity;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.ControlCommand.SleepMode;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Hub.LockingPolicy;
import com.thalmic.myo.Myo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null)
    {
      getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();
    if (id == R.id.action_settings)
    {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public class PlaceholderFragment extends Fragment
  {
    Myo _myo;

    public PlaceholderFragment()
    {
    }

    @Override
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
          
          _myo.setLightsColors(iRed, iGreen, iBlue, iRed2, iGreen2, iBlue2);
        }
      });
      
      btTurnOff.setOnClickListener(new OnClickListener()
      {
        public void onClick(View v)
        {
          _myo.turnOffForTransport();
        }
      });
      
      btStayAwake.setOnClickListener(new OnClickListener()
      {
        public void onClick(View v)
        {
          _myo.setSleepMode(SleepMode.NEVER_SLEEP);
        }
      });
      
      btDontStayAwake.setOnClickListener(new OnClickListener()
      {
        public void onClick(View v)
        {
          _myo.setSleepMode(SleepMode.NORMAL);
        }
      });
      
      Hub hub = Hub.getInstance();
      
      hub.init(this.getActivity());
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
    }
  }
}
