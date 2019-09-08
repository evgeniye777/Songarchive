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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class Activ3 extends AppCompatActivity {
    private static final String EXTRA_SONGONE_ID = "com.bignerdranch.android.songoneintent.song_id";
    private static final String Key = "index";
private ViewPager mViewPager;
private List<infoOneSong> mSong;
static infoOneSong mSong2;
private static int sitc;
private static String nam;

    public  static Intent newIntent(Context packageContext, infoOneSong song,int sit){
        mSong2 = song;
        nam = song.getName();
        sitc = sit;
        Intent intent = new Intent(packageContext,Activ3.class);
        intent.putExtra(EXTRA_SONGONE_ID,song.getId());
        return intent;
    };

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            finish();
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = sp.getInt("THEME", R.style.AppTheme);
        if (sitc==1) {
            theme = sp.getInt("THEME", R.style.AppTheme2);}
        setTheme(theme);
        setTitle(nam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractiv3);
        UUID songId = (UUID) getIntent().getSerializableExtra(EXTRA_SONGONE_ID);
        mViewPager = (ViewPager) findViewById(R.id.song_view_pager);
        mSong = ArhiveSong.get(this).getInfoOne();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                infoOneSong songg = mSong.get(i);

                return Fragment3.newInstance(songg.getId());
            }

            @Override
            public int getCount() {
                return mSong.size();
            }
        });
        for (int i=0;i<mSong.size();i++) {if (mSong.get(i).getId().equals(songId)) {
            //mSong2 = mSong.get(i);
            mViewPager.setCurrentItem(i);
            break;
        }}


    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,nam);
    };
    /*@Override
    public void onStop() {
        super.onStop();
        finish();
    }*/
}
