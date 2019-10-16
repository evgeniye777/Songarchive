package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import databases.DATABasestwo;

public class Fragment3 extends Fragment {
    //фрагмент для отображения содержимого одной песни
    private static final String Key = "index";
    private infoOneSong mSong;
    private TextView Slova;
    private TextView Akords;
    private Button Ton,Save;
    String akkordu;
    private static final String VT = "vton";
    private static final int vt = 0;
    private static final String PO = "pobnov";
    private static final int po = 1;
    private static final String VA = "value";
    private static final int va = 2;
    private DATABasestwo basa2;
    private SQLiteDatabase mdb2;

    String Tonorg, Tonnew;
    String namepo,slovapo;
    static infoOneSong sInfoOneSong = new infoOneSong();
    MenuItem Mitem2;
    MenuItem Mitem3;
    int izbran=0;
    static int color;
    boolean vievA;
    int str = 0;
    int[] sit = new int[20];
    String masT[] = {"A", "A#", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "Am", "Am#", "Hm", "Cm", "Cm#", "Dm", "Dm#", "Em", "Fm", "Fm#", "Gm", "Gm#"};

    public static Fragment3 newInstance(infoOneSong song,int color0) {
        Bundle args = new Bundle();
        color = color0;
        sInfoOneSong = song;
        Fragment3 fragment = new Fragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSong = sInfoOneSong;
        if (savedInstanceState != null) {String vs=savedInstanceState.getString(Key,"");
            String[] mas = new String[6];
            int i0=0,n=0;
            for (int i=0;i<vs.length();i++) {if (vs.charAt(i)=='¶') {mas[n]=vs.substring(i0,i);i0=i+1;n++;}}
            mas[5] =vs.substring(i0);
            mSong.setName(mas[0]);
            mSong.setText(mas[1]);
            mSong.setTon(mas[2]);
            mSong.setAkords(mas[3]);
            mSong.setnsb(mas[4]);
            mSong.setidn(mas[5]);

        }
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        basa2 = new DATABasestwo(getActivity());
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
        // 8ой параметр настроек отвечает за оценку приложения, как только поьзователь попытался оценить его, этот параметр меняется на еденицу, и тогда больше не будет всплывать уведомление с просьбой оценить
        if (sit[8]==0) {
        FragmentManager manager = getFragmentManager();
        value dialog = value.newInstance(color,mdb2);
        dialog.setTargetFragment(Fragment3.this, va);
        dialog.show(manager, VA);}
        if (sit[3]==0) {vievA=true;} // третий параметр настроек отвечает за наличие аккордов, переменная viewA решает отображать аккорды или нет
        else {vievA=false;}
    }
//получение данных с таблицы где хранятся параметры настроек
    public void info() {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + "t8", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            sit[i] = Integer.parseInt(cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
        cursor.close();

        Cursor cursor2 = mdb2.rawQuery("SELECT * FROM " + "t6", null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            if (cursor2.getString(1).equals(mSong.getnsb()+";"+mSong.getidn())) {izbran=1;break;}
            cursor2.moveToNext();
        }
        cursor2.close();
    }
//описание пунктов меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.panel3,menu);
        MenuItem Mitem = menu.findItem(R.id.del);
        Mitem2 = menu.findItem(R.id.izbrannoe);
        Mitem3 = menu.findItem(R.id.noizbrannoe);
        if (izbran==0) {Mitem2.setVisible(false);}
        else {Mitem3.setVisible(false);}
        if (!mSong.getnsb().equals("t5")&&!mSong.getnsb().equals("t6")) {
            Mitem.setVisible(false);}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.obnov:
                FragmentManager manager = getFragmentManager();
                PlusObnov dialog = PlusObnov.newInstance(color);
                dialog.most(akkordu,namepo,slovapo);
                dialog.setTargetFragment(Fragment3.this, po);
                dialog.show(manager, PO);
                return true;
            case R.id.del:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),1);
                obnovTable("t7",mSong.getnsb()+";"+mSong.getidn(),1);
                ArhiveSong.get(getActivity()).delCrime(mSong);
                getActivity().finish();  //закрытие активности изх фрагмента
                return true;
            case R.id.orientation:
                ContentValues values = new ContentValues();
                int orN = (sit[1]+1)%3;
                values.put("sitings", orN);
                mdb2.update("t8", values, "_id" + " = ?", new String[]{""+2});
                sit[1]=orN;
                orientation();
                return true;
            case R.id.izbrannoe:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),1);
                return true;
            case R.id.noizbrannoe:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),0);
                return true;
            case R.id.export:
                FragmentManager manager2 = getFragmentManager();
                goinclass dialog2 = goinclass.newInstance(color,""+Slova.getText(),""+Akords.getText(),namepo);
                dialog2.setTargetFragment(Fragment3.this, va);
                dialog2.show(manager2, VA);
                return true;
            default: return super.onOptionsItemSelected(item) ;
        }
    }
    void orientation() {if (sit[1]==2) {Slova.setGravity(Gravity.RIGHT);}
        if (sit[1]==1) {Slova.setGravity(Gravity.LEFT);}
        if (sit[1]==0) {Slova.setGravity(Gravity.CENTER);}}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forfragment3, container, false);
        //текстовые метки слов и аккордов
        Slova = (TextView) v.findViewById(R.id.slova);
        Akords = (TextView) v.findViewById(R.id.akkords);
        Boltactiv();Italikactiv();Allactiv();
        Slova.setTextSize((float)sit[0]);
        Slova.setText(mSong.getText());
        orientation();
        Slova.setMovementMethod(new ScrollingMovementMethod());
        akkordu = "" + mSong.getAkords();
        slovapo = mSong.getText();
        namepo = mSong.getName();

        Tonorg = ""+mSong.getTon();
        Tonnew = Tonorg;
        Akords.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
        Akords.setTextSize(sit[0]);
        Akords.setText(akkordu.replace(";","; ").replace("  "," "));
        //кнопка сменить тональность
        Ton = (Button) v.findViewById(R.id.Ton);
        Ton.setText(" Тональность " + Tonorg + " ");
        Ton.setTextColor(Akords.getTextColors());
        Ton.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
        Ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                VuborTonaClass dialog = VuborTonaClass.newInstance(color);
                if (Tonnew ==null) {Tonnew = Tonorg;}
                dialog.most("" + Tonnew,""+Tonorg);
                dialog.setTargetFragment(Fragment3.this, vt);
                dialog.show(manager, VT);
            }
        });
        //кнопка сохранить
        Save = (Button) v.findViewById(R.id.Save);
        Save.setVisibility(View.GONE);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSong.setAkords(akkordu);
                mSong.setTon(Tonnew);
                mSong.setText(slovapo);
                mSong.setName(namepo);
                Tonorg = Tonnew;
                ArhiveSong.get(getActivity()).updateCrime(mSong);
                vivod("Сохранено");
                Ton.setText(" Тональность " + Tonorg + " ");
                Ton.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
                Save.setVisibility(View.GONE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requesCode, int resultCode, Intent data) {
        if (requesCode == vt) {
            str = (int) data.getSerializableExtra(VuborTonaClass.EXTRA_TON);
            Tonnew = (String) data.getSerializableExtra(VuborTonaClass.EXTRA_TONn);
            akkordu = perevod(mSong.getAkords()).replace(";","; ").replace("  "," ");
            Akords.setText(akkordu);
            Akords.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
            Save.setVisibility(!Tonnew.equals(Tonorg) ? View.VISIBLE : View.GONE);
        }
        if (requesCode == po) {
            akkordu = "";
            akkordu = (String) data.getSerializableExtra(PlusObnov.EXTRA_POA);
            namepo = (String) data.getSerializableExtra(PlusObnov.EXTRA_PON);
            slovapo = (String) data.getSerializableExtra(PlusObnov.EXTRA_POS);
            Tonnew = "";
            OpredTon(akkordu);
            Slova.setText(slovapo);
            Akords.setText(akkordu);
            Akords.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
            Save.setVisibility(View.VISIBLE);
        }
    }

    public boolean nalAkk(String s) {
        return s != "" &&s!=null;
    }

    public void vivod(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }
//этот метод я больше всего продумывал. Он считывает строку с аккордами и меняет каждый аккорд в зависимости от того на сколько изменили тональность
    public String perevod(String akkordslast) {String sum=""; int x=0,x1=0;
        String akkordsnew = akkordslast+"      ";
        for (int i = 0; i < akkordslast.length(); i++) {
            for (int j = 0; j < 24; j++) { String oneakk = akkordsnew.substring(i, i + masT[j].length()); char sa =akkordsnew.charAt(i+masT[j].length());
                if (oneakk.indexOf(masT[j]) !=-1 && sa!='m' && sa !='#') { x=i;sum += akkordsnew.substring(x1, x);
                    String twoakk = masT[(j + str) % 12+12*(j/12)];i+=oneakk.length();x1=i;
                    sum += twoakk;
                }
            }
        }
        sum+=akkordsnew.substring(x1,akkordslast.length());
        return sum;
    }
//этот метод используется если создавать новую песню: пользователь вводит строку с аккордами и программа считывает первый аккорд
    public void OpredTon(String akkordslast) {String sum=""; int x=0,x1=0,i=0; boolean b= true;
        String akkordsnew = akkordslast+"      ";
        while (b && i< akkordslast.length()) {
            for (int j = 0; j < 24; j++) { String oneakk = akkordsnew.substring(i, i + masT[j].length()); char sa =akkordsnew.charAt(i+masT[j].length());
                if (oneakk.indexOf(masT[j]) !=-1 && sa!='m' && sa !='#') {Tonnew = masT[j];b=false;
                }
            }
            i++;
        }
    }

    void obnovTable(String tab,String adreses,int i) {
        Cursor cursor = mdb2.rawQuery("SELECT * FROM " + tab, null);
        cursor.moveToFirst();
        String product0="",product="";
        while (!cursor.isAfterLast()) {
            product0 = cursor.getString(0);
            product = cursor.getString(1);
            if (adreses.equals(product)) {
                mdb2.delete(tab, "_id = ?", new String[]{product0});
            }
            cursor.moveToNext();
        }
        Mitem2.setVisible(false);
        Mitem3.setVisible(true);
        if (i==0) {
            ContentValues values = new ContentValues();
            values.put("adreses", adreses);
            mdb2.insert(tab,null,values);
            Mitem2.setVisible(true);
            Mitem3.setVisible(false);}
        cursor.close();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Key,mSong.getName()+"¶"+mSong.getText()+"¶"+mSong.getTon()+"¶"+
                mSong.getAkords()+"¶"+mSong.getnsb() +"¶"+
                mSong.getidn());
    };

    //эти три следующих метода задают параметры текста (жирный курсив заглавный)
    public void Boltactiv() {
        if (sit[5]==0&&sit[4]==0) {Slova.setTypeface(null, Typeface.NORMAL);
            Akords.setTypeface(null, Typeface.NORMAL);
        }
        else if (sit[5]==0&&sit[4]==1){Slova.setTypeface(null, Typeface.BOLD);
            Akords.setTypeface(null, Typeface.BOLD);}
        else if (sit[5]==1&&sit[4]==0){Slova.setTypeface(null, Typeface.ITALIC);
            Akords.setTypeface(null, Typeface.ITALIC);}
        else if (sit[5]==1&&sit[4]==1){Slova.setTypeface(null, Typeface.BOLD_ITALIC);
            Akords.setTypeface(null, Typeface.BOLD_ITALIC);}
    }
    public void Italikactiv() {
        if (sit[4]==0&&sit[5]==0) {Slova.setTypeface(null, Typeface.NORMAL);
            Akords.setTypeface(null, Typeface.NORMAL);}
        else if (sit[4]==0&&sit[5]==1){Slova.setTypeface(null, Typeface.ITALIC);
            Akords.setTypeface(null, Typeface.ITALIC);}
        else if (sit[4]==1&&sit[5]==0){Slova.setTypeface(null, Typeface.BOLD);
            Akords.setTypeface(null, Typeface.BOLD);}
        else if (sit[4]==1&&sit[5]==1){Slova.setTypeface(null, Typeface.BOLD_ITALIC);
            Akords.setTypeface(null, Typeface.BOLD_ITALIC);
        }}
    public void Allactiv() {
        if (sit[6]==0) {Slova.setAllCaps(false);
            Akords.setAllCaps(false);}
        else{Slova.setAllCaps(true);
            Akords.setAllCaps(true);}
    }
}