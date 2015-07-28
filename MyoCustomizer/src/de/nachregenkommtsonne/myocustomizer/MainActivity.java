package de.nachregenkommtsonne.myocustomizer;

import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment.ColorPickerDialogListener;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements ColorPickerDialogListener
{
  private PlaceholderFragment _placeholderFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null)
    {
      _placeholderFragment = new PlaceholderFragment();
      getFragmentManager().beginTransaction().add(R.id.container, _placeholderFragment).commit();
    }
    
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    
    return false;
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

  public enum SleepMode
  {
    NORMAL, NEVER_SLEEP
  }

  @Override
  public void onColorSelected(int dialogId, int color)
  {
    switch (dialogId) {
    case 0:
      _placeholderFragment.setColorStatus(color);
      break;
    case 1:
      _placeholderFragment.setColorLink(color);
      break;
    }
  }

  @Override
  public void onDialogDismissed(int arg0)
  {
  }
}
