package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class Activ3 extends AppCompatActivity {
    //активность для работы с одной песней
    private static final String Key = "index";
    static infoOneSong mSong2 = new infoOneSong();
    private static int sitc;
    private static String nam;
private int color; // Диалоговые окна не окрашиваются той темой которая выбрана в настройках и поэтому приходится этим окнам передавать значение этого цвета, чтобы вручную подстроить его под данную тему
    public  static Intent newIntent(Context packageContext, infoOneSong song,int sit){
        mSong2 = song;
        nam = song.getName();
        sitc = sit;
        Intent intent = new Intent(packageContext,Activ3.class);
        return intent;
    };

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        //в случае если приложение было свернуто и при последующем его открытии программа не выдала ошибку, необходимо сохранить параметры слов аккордов и другого
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            String[] mas = new String[9];
            int i0=0,n=0;
            for (int i=0;i<vs.length();i++) {if (vs.charAt(i)=='¶') {mas[n]=vs.substring(i0,i);i0=i+1;n++;}}
            mas[8] =vs.substring(i0);
            mSong2.setName(mas[0]);
            mSong2.setText(mas[1]);
            mSong2.setTon(mas[2]);
            mSong2.setAkords(mas[3]);
            mSong2.izbran(mas[4]);
            mSong2.setnsb(mas[5]);
            mSong2.setidn(mas[6]);
            nam = mas[7];
            sitc = Integer.parseInt(mas[8]);

        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme;
        if (sitc==0) { theme = sp.getInt("THEME", R.style.AppTheme);color = R.color.white;}
        else if (sitc==1) { theme = sp.getInt("THEME", R.style.AppTheme2);color = R.color.blacckS; }
        else if (sitc==2) { theme = sp.getInt("THEME", R.style.AppTheme3); color = R.color.greenwin;}
        else if (sitc==3) { theme = sp.getInt("THEME", R.style.AppTheme4);color = R.color.Yellowwin; }
        else if (sitc==4) { theme = sp.getInt("THEME", R.style.AppTheme5);color = R.color.Pinkwin;}
        else if (sitc==5) { theme = sp.getInt("THEME", R.style.AppTheme6);color = R.color.blywin;}
        else if (sitc==6) { theme = sp.getInt("THEME", R.style.AppTheme7);color = R.color.Parplewin;}
        else if (sitc==7) { theme = sp.getInt("THEME", R.style.AppTheme8);color = R.color.blydarkwin;}
        else if (sitc==8) { theme = sp.getInt("THEME", R.style.AppTheme9);color = R.color.brownwin;}
        else { theme = sp.getInt("THEME", R.style.AppTheme10);color = R.color.orangewin;}
        setTheme(theme);
        setTitle(nam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractiv3);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.forActive3);
        if (fragment == null) {
            fragment = Fragment3.newInstance(mSong2,color);
            fm.beginTransaction().add(R.id.forActive3, fragment).commit();
        }
    }
    //этот метод вызывается при сворачивании приложения, как раз здесь происходит сохранение параметров открытого контента, а в onCreat() возвращение этих данных
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,mSong2.getName()+"¶"+mSong2.getText()+"¶"+mSong2.getTon()+"¶"+
                mSong2.getAkords()+"¶"+mSong2.getizbran()+"¶"+mSong2.getnsb() +"¶"+
                mSong2.getidn()+"¶"+nam+"¶"+sitc);
    };
}
