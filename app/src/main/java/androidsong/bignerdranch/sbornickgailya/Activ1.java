package androidsong.bignerdranch.sbornickgailya;
import android.bignerdranch.sbornickgailya.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

import databases.DATABases;

public class Activ1 extends SingleClass {
    private DATABases basa;
    private SQLiteDatabase mdb;
    int sit,sitc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        basa = new DATABases(this);
        try {
            basa.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb = basa.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        info();
        sitc=sit;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = sp.getInt("THEME", R.style.AppTheme);
        if (sitc==1) {
        theme = sp.getInt("THEME", R.style.AppTheme2);}
        setTheme(theme);
        setTitle("ВНебоПесни");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);}
    @Override
    protected Fragment createFragment() {Fragment1 fr = new Fragment1();
    fr.Thema(sitc);
    return  fr;}

    public void info() {
        Cursor cursor = mdb.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        cursor.moveToPosition(2);
        sit = cursor.getInt(1);
        cursor.close();
    }

    @Override
    public  void onStart() {super.onStart();
      info();
      if (sitc!=sit) {
          Intent i = new Intent( this , this.getClass() );
          finish();
          this.startActivity(i);
      }
    }
}
