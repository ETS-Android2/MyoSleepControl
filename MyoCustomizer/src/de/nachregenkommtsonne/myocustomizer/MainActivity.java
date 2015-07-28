package de.nachregenkommtsonne.myocustomizer;

import afzkl.development.colorpickerview.dialog.ColorPickerDialogFragment.ColorPickerDialogListener;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

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
