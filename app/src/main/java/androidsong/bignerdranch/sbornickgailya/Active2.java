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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Active2 extends AppCompatActivity {
    //активность для работы с песнями
    private static final String TAG = "Active2";
    private static final String Key = "index";
    private EditText poisk;
    String sQ;
    // нехочу давать этим кнопкам длинные название, но они несут татарские символы
    Button b1,b2,b3,b4,b5,b6;
    int color;
   static int sitc;
   boolean ra=true,A23 = false;
    private static String namTa;
    Fragment2 fr = new Fragment2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            namTa = vs.substring(0,vs.indexOf("/"));
            sitc=Integer.parseInt(vs.substring(vs.indexOf("/")+1));
            Log.d(TAG,namTa+";"+sitc);
            ra=false;
            Intent intent = Active2.newIntent(this,namTa,sitc);
            finish();
            startActivity(intent);
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme;
        if (sitc==0) { theme = sp.getInt("THEME", R.style.AppTheme);color = R.color.white;}
        else if (sitc==1) { theme = sp.getInt("THEME", R.style.AppTheme2); color = R.color.blacckS;}
        else if (sitc==2) { theme = sp.getInt("THEME", R.style.AppTheme3);color = R.color.greenwin; }
        else if (sitc==3) { theme = sp.getInt("THEME", R.style.AppTheme4); color = R.color.Yellowwin;}
        else if (sitc==4) { theme = sp.getInt("THEME", R.style.AppTheme5);color = R.color.Pinkwin;}
        else if (sitc==5) { theme = sp.getInt("THEME", R.style.AppTheme6);color = R.color.blywin;}
        else if (sitc==6) { theme = sp.getInt("THEME", R.style.AppTheme7);color = R.color.Parplewin;}
        else if (sitc==7) { theme = sp.getInt("THEME", R.style.AppTheme8);color = R.color.blydarkwin;}
        else if (sitc==8) { theme = sp.getInt("THEME", R.style.AppTheme9);color = R.color.brownwin;}
        else { theme = sp.getInt("THEME", R.style.AppTheme10);color = R.color.orangewin;}
        setTheme(theme);
        setTitle(namZag(namTa));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractiv1two);
        poisk = (EditText) findViewById(R.id.poisk);
        LinearLayout dopbut = (LinearLayout) findViewById(R.id.dopbut);
        b1 =(Button) findViewById(R.id.but1);
        b2 =(Button) findViewById(R.id.but2);
        b3 =(Button) findViewById(R.id.but3);
        b4 =(Button) findViewById(R.id.but4);
        b5 =(Button) findViewById(R.id.but5);
        b6 =(Button) findViewById(R.id.but6);
        //t4 - название таблицы с татарскими песнями, и если сборник другой не татарский то кнопки эти не нужны
        if (!namTa.equals("t4")) {
            dopbut.setVisibility(View.GONE);
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b6.setVisibility(View.GONE);}
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.forActiv1);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.forActiv1, fragment).commit();
        }
        createFragment();
        poisk.addTextChangedListener(


                new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sQ=""+s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sQ=""+s;

            }

            @Override
            public void afterTextChanged(Editable s) {
                fr.Dpoisk(sQ);

            }

        });
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        poisk.setText(""+poisk.getText()+b1.getText());
        poisk.setSelection(poisk.getText().length());
    }
});
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisk.setText(""+poisk.getText()+b2.getText());
                poisk.setSelection(poisk.getText().length());
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisk.setText(""+poisk.getText()+b3.getText());
                poisk.setSelection(poisk.getText().length());
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisk.setText(""+poisk.getText()+b4.getText());
                poisk.setSelection(poisk.getText().length());
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisk.setText(""+poisk.getText()+b5.getText());
                poisk.setSelection(poisk.getText().length());
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poisk.setText(""+poisk.getText()+b6.getText());
                poisk.setSelection(poisk.getText().length());
            }
        });


    }

    public static Intent newIntent(Context packageContext, String namT,int sit) {
        namTa = namT;
        sitc=sit;
        Intent intent = new Intent(packageContext, Active2.class);
        return intent;
    }




    protected Fragment createFragment() {
       fr.Thema(sitc);
        fr.plusdata(namTa);
        fr.setcolor(color);
        return fr;
    }


    String namZag(String nam) {
        String s = "";
        if (nam.equals("t1")) {
            s = "Хвали Творца";
        } else if (nam.equals("t2")) {
            s = "Молодёжный сборник";
        } else if (nam.equals("t3")) {
            s = "Детский сборник";}
            else if (nam.equals("t4")) {
                s = "Татарский сборник";
        } else if (nam.equals("t5")) {
            s = "Мои песни";
        } else if (nam.equals("t6")) {
            s = "Избранное";
        } else if (nam.equals("t7")) {
            s = "История";
        }else {
            s = "Все песни";
        }
        return s;
    }

    public void vivod(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ra) {
            fr.Dpoisk("");}
    }
    @Override
    public void onStart() {
        super.onStart();
       A23 = false;
    }
    @Override
    public void onStop() {
        super.onStop();
        A23 = true;
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,namTa+"/"+sitc);
    };
}
