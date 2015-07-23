package de.nachregenkommtsonne.myoarengine.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsEditor {
	 private static final String MYOMAC = "MYO_MAC";

	  private SharedPreferences _sharedPref;

	  public SettingsEditor()
	  {
	  }

	  public void onCreate(Context context)
	  {
	    String packageName = context.getPackageName();
	    _sharedPref = context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
	  }

	  public void saveMac(String mac)
	  {
	    Editor edit = _sharedPref.edit();
	    edit.putString(MYOMAC, mac);
	    edit.apply();
	  }

	  public void deleteMac()
	  {
	    Editor edit = _sharedPref.edit();
	    edit.remove(MYOMAC);
	    edit.apply();
	  }

	  public String getMac()
	  {
	    return _sharedPref.getString(MYOMAC, null);
	  }
}
