package androidsong.bignerdranch.sbornickgailya;
import android.bignerdranch.sbornickgailya.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;


import java.io.IOException;
import databases.DATABasestwo;

public class Activ1 extends SingleClass {
    //активность для работы со сборниками
    private DATABasestwo basa2;
    private SQLiteDatabase mdb2;
    //далее идут переданные параметры настрое; в связи с тем, что эта активность самая первая, то при изменении настроик о
    // на не всегда будет обновляться, поэтому приходится на каждый параметр хранить прошлое и обновленное значение
    // и в методе onStart() при их различии обновлять это окно
    int sit,sitc,SB,SBc,SI,SIc,SA,SAc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // читаем базу данных
        basa2 = new DATABasestwo(this);
        try {
            basa2.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb2 = basa2.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        info();
        sitc=sit;
        SBc = SB;
        SIc = SI;
        SAc = SA;
        //Установка темы
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme;
        if (sitc==0) {   theme = sp.getInt("THEME", R.style.AppTheme);}
        else if (sitc==1) { theme = sp.getInt("THEME", R.style.AppTheme2); }
        else if (sitc==2) { theme = sp.getInt("THEME", R.style.AppTheme3); }
        else if (sitc==3) { theme = sp.getInt("THEME", R.style.AppTheme4); }
        else if (sitc==4) { theme = sp.getInt("THEME", R.style.AppTheme5);}
        else if (sitc==5) { theme = sp.getInt("THEME", R.style.AppTheme6);}
        else if (sitc==6) { theme = sp.getInt("THEME", R.style.AppTheme7);}
        else if (sitc==7) { theme = sp.getInt("THEME", R.style.AppTheme8);}
        else if (sitc==8) { theme = sp.getInt("THEME", R.style.AppTheme9);}
        else { theme = sp.getInt("THEME", R.style.AppTheme10);}
        super.setTheme(theme);
        setTheme(theme);
        //Установка картинки заголовка
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.zagalovok);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //Запрет на смену ориентации
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);}

    @Override
    protected Fragment createFragment() {Fragment1 fr = new Fragment1();
    return  fr;}
// считывание данных с базы данных "localdata.db" о параметрах настроек
    public void info() {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        cursor.moveToPosition(2);
        sit = cursor.getInt(1);
        cursor.moveToPosition(4);
        SB = cursor.getInt(1);
        cursor.moveToPosition(5);
        SI = cursor.getInt(1);
        cursor.moveToPosition(6);
        SA = cursor.getInt(1);
        cursor.close();
    }
// в случае если настройки изменены то активность нужно перезагрузить (всё что заканчивается на букву "с" прошлые данные, остальные обновленные)
    @Override
    public  void onStart() {super.onStart();
      info();
      if (sitc!=sit||SAc!=SA||SBc!=SB||SIc!=SI) {
          Intent i = new Intent( this , this.getClass() );
          finish();
          this.startActivity(i);
      }
    }
}
