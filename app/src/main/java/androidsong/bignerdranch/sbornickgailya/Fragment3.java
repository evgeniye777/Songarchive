package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
import java.util.List;
import java.util.UUID;

import databases.DATABases;

public class Fragment3 extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String ARG_SONG_ID = "song_id";
    private infoOneSong mSong;
    private List<infoOneSong> mCrimes;
    private TextView Slova;
    private TextView Akords;
    private Button Ton,Save;
    String akkordu;
    private static final String VT = "vton";
    private static final int vt = 0;
    private static String nam;
    private DATABases basa;
    private SQLiteDatabase mdb;
    private static final String PO = "pobnov";
    private static final int po = 1;
    String Tonorg, Tonnew;
    String namepo,slovapo;
    MenuItem Mitem2;
    MenuItem Mitem3;
    boolean vievA;
    int str = 0;
    int[] sit = new int[4];
    String masT[] = {"A", "A#", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "Am", "Am#", "Hm", "Cm", "Cm#", "Dm", "Dm#", "Em", "Fm", "Fm#", "Gm", "Gm#"};

    public static Fragment3 newInstance(UUID songId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SONG_ID, songId);
        Fragment3 fragment = new Fragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID songId = (UUID) getArguments().getSerializable(ARG_SONG_ID);
        mSong = ArhiveSong.get(getActivity()).getinfo(songId);
        mCrimes = ArhiveSong.get(getActivity()).getInfoOne();

        basa = new DATABases(getActivity());
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
        if (sit[3]==0) {vievA=true;}
        else {vievA=false;}
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



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.panel3,menu);
        MenuItem Mitem = menu.findItem(R.id.del);
         Mitem2 = menu.findItem(R.id.izbrannoe);
         Mitem3 = menu.findItem(R.id.noizbrannoe);
         if (mSong.getizbran()==null) {Mitem2.setVisible(false);}
         else {Mitem3.setVisible(false);}
        if (!mSong.getnsb().equals("t5")&&!mSong.getnsb().equals("t6")) {
        Mitem.setVisible(false);}
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.obnov:
                FragmentManager manager = getFragmentManager();
                PlusObnov dialog = PlusObnov.newInstance();
                dialog.most(akkordu,namepo,slovapo);
                dialog.setTargetFragment(Fragment3.this, po);
                dialog.show(manager, PO);
                return true;
            case R.id.del:
obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),"1");
                obnovTable("t7",mSong.getnsb()+";"+mSong.getidn(),"1");
                ArhiveSong.get(getActivity()).delCrime(mSong);
                getActivity().finish();  //закрытие активности изх фрагмента
                return true;
            case R.id.orientation:
                ContentValues values = new ContentValues();
                int orN = (sit[1]+1)%3;
                values.put("sitings", orN);
                mdb.update("t8", values, "_id" + " = ?", new String[]{""+2});
                sit[1]=orN;
                orientation();
                return true;
            case R.id.izbrannoe:
obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),mSong.getizbran());
                return true;
            case R.id.noizbrannoe:
                obnovTable("t6",mSong.getnsb()+";"+mSong.getidn(),mSong.getizbran());
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
        nam = mSong.getName();
        Slova = (TextView) v.findViewById(R.id.slova);
        Slova.setTextSize((float)sit[0]);
        Slova.setText(mSong.getText());
        orientation();
        Slova.setMovementMethod(new ScrollingMovementMethod());
        akkordu = "" + mSong.getAkords();
        slovapo = mSong.getText();
        namepo = mSong.getName();

        Tonorg = ""+mSong.getTon();
        Tonnew = Tonorg;
        Akords = (TextView) v.findViewById(R.id.akkords);
        Akords.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
        Akords.setTextSize(sit[0]);
        if (sit[2]==1) {Akords.setTextColor(Color.WHITE);}
        Akords.setText(akkordu.replace(";","; ").replace("  "," "));
        Ton = (Button) v.findViewById(R.id.Ton);

        Ton.setText(" Тональность " + Tonorg + " ");
        Ton.setVisibility(nalAkk(Tonnew)&&vievA ? View.VISIBLE : View.GONE);
        Ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                VuborTonaClass dialog = VuborTonaClass.newInstance("");
                if (Tonnew ==null) {Tonnew = Tonorg;}
                dialog.most("" + Tonnew,""+Tonorg);
                dialog.setTargetFragment(Fragment3.this, vt);
                dialog.show(manager, VT);
            }
        });
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
        if (sit[2]==1) {
            Akords.setTextColor(Color.WHITE);
            Slova.setTextColor(Color.WHITE);
            Ton.setTextColor(Color.WHITE);
            Save.setTextColor(Color.WHITE);}
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

    void obnovTable(String tab,String adreses,String i) {
        Cursor cursor = mdb.rawQuery("SELECT * FROM " + tab, null);
        cursor.moveToFirst();
        String product0="",product="";
        while (!cursor.isAfterLast()) {
            product0 = cursor.getString(0);
            product = cursor.getString(1);
            if (adreses.equals(product)) {
                mdb.delete(tab, "_id = ?", new String[]{product0});
            }
            cursor.moveToNext();
        }
        mSong.izbran(null);
        Mitem2.setVisible(false);
        Mitem3.setVisible(true);
        if (i==null) {
        ContentValues values = new ContentValues();
        values.put("adreses", adreses);
        mdb.insert(tab,null,values);
            mSong.izbran("1");
        Mitem2.setVisible(true);
            Mitem3.setVisible(false);}
        cursor.close();
        ArhiveSong.get(getActivity()).updateCrime(mSong);
    }
}