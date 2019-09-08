package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import databases.DATABases;

public class Activesitings extends AppCompatActivity {
    SeekBar textSize;
    TextView text;
    Button mCheckBox;
    Switch swi;
    private DATABases basa;
    private SQLiteDatabase mdb;
    float r0=20;
    int[] sit = new int[4];
    int i=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Настройки");
        String nameT;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = sp.getInt("THEME", R.style.AppTheme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);


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
        if (sit[2]==0) {super.setTheme(theme); nameT = "Тёмная тема";}
        else {theme = sp.getInt("THEME", R.style.AppTheme2);
            super.setTheme(theme);nameT="Светлая тема";}

        setContentView(R.layout.foractivesitings);
        textSize = (SeekBar) findViewById(R.id.seekBar2);
        text = (TextView) findViewById(R.id.text);
        mCheckBox = (Button) findViewById(R.id.checkBox);
        mCheckBox.setText(nameT);
                textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                i = progressValue;
                float r;
                if (i>50) {r = r0+(i-50)/4;}
                else {r = r0+(i-50)/5;}
                text.setTextSize(r);
                ContentValues values = new ContentValues();
                values.put("sitings", (int)r);
                mdb.update("t8", values, "_id" + " = ?", new String[]{""+1});
            }

            // никак не реагируем на начало движения индикатора прогресса
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            // когда пользователь отпустил индикатор изменения прогресса
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int i=0;
        if (sit[0]>20) {i=(sit[0]-20)*4+50;}
        else {i = (sit[0]-20)*5+50;}
        textSize.setProgress(i);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("sitings", (int)(sit[2]+1)%2);
                mdb.update("t8", values, "_id" + " = ?", new String[]{""+3});
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        swi = (Switch) findViewById(R.id.switch1);
        if (sit[3]==0) {
        swi.setChecked(true);}
        else {swi.setChecked(false);}
        swi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                ContentValues values2 = new ContentValues();
if (isChecked) {
    values2.put("sitings", 0);
}
else {values2.put("sitings", 1);}
                mdb.update("t8", values2, "_id" + " = ?", new String[]{""+4});
            }
        });
    }


    public void info() {
        Cursor cursor = mdb.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            sit[i] = Integer.parseInt(cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
        cursor.close();
    }


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Activesitings.class);
        return intent;
    }

    public void vivod(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
