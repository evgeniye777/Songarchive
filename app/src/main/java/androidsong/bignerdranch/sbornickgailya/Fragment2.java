package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databases.DATABases;
import databases.DATABasestwo;

public class Fragment2 extends Fragment {
    //фрагмент для отображения списка песен
    private RecyclerView mSongRec;

    private SongAdapter mAdapter;
    private String dpoisk="";
    int l=0;
    boolean ra=false;
    private DATABases basa;
    private SQLiteDatabase mdb;
    private DATABasestwo basa2;
    int color;
    private SQLiteDatabase mdb2;
    String masT[] = {"A", "A#", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "Am", "Am#", "Hm", "Cm", "Cm#", "Dm", "Dm#", "Em", "Fm", "Fm#", "Gm", "Gm#"};
    private String namTa;
    ArhiveSong arhiveSong = ArhiveSong.get(getActivity());
    List<infoOneSong> songi;//эта переменная хранит данные песен выбранного сборника
    List<infoOneSong> Arhivetwo = new ArrayList<> (); //та же самая переменная, но в ней хранятся выбранные песни, при введенном оисковом запросе
    int sitc;
    int[] sit = new int[20];
    int idI;
    private static final String PO = "pobnov";
    private static final int po = 0;
    String ton,akordspo,namepo,slovapo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        basa = new DATABases(getActivity());//в первой БД хранятся данные которые я буду впоследствии обновлять, меняя версию БД
        basa2 = new DATABasestwo(getActivity());//во второй хранятся данные которые должны сохранятся при обновлении приложения, что происходит если версию БД оставлять прежней
        try {
            basa.updateDataBase();
            basa2.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mdb = basa.getWritableDatabase();
            mdb2 = basa2.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        info();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forfragment2,container,false);
        mSongRec = (RecyclerView) view.findViewById(R.id.forfragment2);
        mSongRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (!namTa.equals("t6")&&!namTa.equals("t5")&&!namTa.equals("t7")) {updateUI();}
        return view;
    }

    private void updateUI() {
        arhiveSong.plusbasa(mdb,mdb2,namTa);
        arhiveSong.Obnovlenie();
        songi = arhiveSong.getInfoOne();
        if (mAdapter == null) {mAdapter = new SongAdapter(songi);
            mSongRec.setAdapter(mAdapter);}
        else {mAdapter.setSongs(songi);
            mAdapter.notifyDataSetChanged();}
    }

    public void update2() {Arhivetwo.clear();
        for (infoOneSong Song: songi) {
            if (Song.getName().toLowerCase().contains(dpoisk.toLowerCase())) {
                int x1 = Song.getName().toLowerCase().indexOf(dpoisk.toLowerCase());
                int x2 = x1 + dpoisk.length();
                Song.N1(x1);
                Song.N2(x2);
                Song.setnameintext("name");
                Arhivetwo.add(Song);
            }
            else if (ra&&Song.getText().replace("\n", " ").toLowerCase().replace(",","").contains(dpoisk.toLowerCase())) {
                int x1 = Song.getText().replace("\n", " ").toLowerCase().replace(",","").indexOf(dpoisk.toLowerCase());
                int x2 = x1 + dpoisk.length();
                Song.setnameintext("text");
                Song.N1(x1);
                Song.N2(x2);
                Arhivetwo.add(Song);
            }
        }
   if (!namTa.equals("t6")&&!namTa.equals("t7")&&songi.size()>7&&ra==false&&!dpoisk.equals("")) {
            infoOneSong songpos = new infoOneSong();
            songpos.setnameintext("pos");
            songpos.setName("Расширенный поиск");
            Arhivetwo.add(songpos);}
        if (mAdapter == null) {mAdapter = new SongAdapter(Arhivetwo);
            mSongRec.setAdapter(mAdapter);}
        else {mAdapter.setSongs(Arhivetwo);
            mAdapter.notifyDataSetChanged();}
    }

    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        infoOneSong mSongg;
        private TextView mTitle; //название одной песни в общем списке
        private TextView mText;//отрывок из текста песни при использовании расширенного поиска
        private ImageView mImageView; //иконка
        public SongHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.spisocksong,parent,false));
            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.song_Title);
            Boltactiv();;Italikactiv();Allactiv();
            mText = (TextView) itemView.findViewById(R.id.song_Text);
            mImageView = (ImageView) itemView.findViewById(R.id.mImage);

        }
        public void bind(infoOneSong songg) {mSongg = songg;
        //для создания в конце кнопки "расширенный поиск" я использовал тот же элемент что и для песен, и чтобы при вызове процыдуры onClick() отличить ее от всех песен, в обьектной переменной хранится слово "pos"
            if (!songg.getnameintext().equals("pos")) {
                mText.setVisibility(View.GONE);
                if (mSongg.getnameintext().equals("name")) {
                    String SO = mSongg.getName();
                    int x1=mSongg.getn1(),
                            x2=mSongg.getn2();
                    final SpannableStringBuilder text = new SpannableStringBuilder(SO);
                    final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(245,78,126));
                    text.setSpan(style,x1,x2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mTitle.setText(text);
                    mTitle.setTextSize(18);}



                else if (mSongg.getnameintext().equals("text")) {
                    mText.setVisibility(View.VISIBLE);
                    int x1=mSongg.getn1(),
                            x2=mSongg.getn2();
                    String SO = mSongg.getText().replace("\n"," ");
                    if (x1>=18) {SO = "..."+SO.substring(x1-18);x2=x2-x1+21;x1=21;}
                    if (SO.length()>50) {SO = SO.substring(0,50)+"...";}
                    mTitle.setText(mSongg.getName());
                    mTitle.setTextSize(18);
                    mText.setText(SO);}

                idimage(Nomer(mSongg.nsb));
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(idI);

            }
            else {
                mTitle.setText(mSongg.getName());
                mTitle.setTextSize(30);
                mTitle.setGravity(Gravity.CENTER);
                mImageView.setVisibility(View.GONE);
                mText.setVisibility(View.GONE);}}
        @Override
        public void onClick(View view) {
            if (!mSongg.getnameintext().equals("pos")) {
                Intent intent = Activ3.newIntent(getActivity(),mSongg,sitc);
                obnovTable("t7",mSongg.getnsb()+";"+mSongg.getidn());
                startActivity(intent);}
            else {ra=true;update2();}
        }
        public void Boltactiv() {
            if (sit[5]==0&&sit[4]==0) {mTitle.setTypeface(null, Typeface.NORMAL);}
            else if (sit[5]==0&&sit[4]==1){mTitle.setTypeface(null, Typeface.BOLD);}
            else if (sit[5]==1&&sit[4]==0){mTitle.setTypeface(null, Typeface.ITALIC);}
            else if (sit[5]==1&&sit[4]==1){mTitle.setTypeface(null, Typeface.BOLD_ITALIC);}
        }
        public void Italikactiv() {
            if (sit[4]==0&&sit[5]==0) {mTitle.setTypeface(null, Typeface.NORMAL);}
            else if (sit[4]==0&&sit[5]==1){mTitle.setTypeface(null, Typeface.ITALIC);}
            else if (sit[4]==1&&sit[5]==0){mTitle.setTypeface(null, Typeface.BOLD);}
            else if (sit[4]==1&&sit[5]==1){mTitle.setTypeface(null, Typeface.BOLD_ITALIC);
            }}
        public void Allactiv() {
            if (sit[6]==0) {mTitle.setAllCaps(false);}
            else{mTitle.setAllCaps(true);}
        }
    }


    private class SongAdapter extends RecyclerView.Adapter<SongHolder> {
        private List<infoOneSong> mSong;
        public SongAdapter(List<infoOneSong> songg) {
            mSong = songg;
        }
        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SongHolder(layoutInflater,parent);
        }
        @Override
        public void onBindViewHolder(SongHolder holder, int position) {
            infoOneSong infoOneSongg = mSong.get(position);
            holder.bind(infoOneSongg);
        }
        @Override
        public int getItemCount() {
            return mSong.size();
        }
        public void setSongs(List<infoOneSong> song) {mSong = song;}
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.panel2,menu);
        MenuItem Mitem = menu.findItem(R.id.plus);
        MenuItem Mitem2 = menu.findItem(R.id.del);
        if (!namTa.equals("t5")) {
            Mitem.setVisible(false);}
        if (!namTa.equals("t7")) {
            Mitem2.setVisible(false);}
    }
    //описание пунктов меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:
                FragmentManager manager = getFragmentManager();
                PlusObnov dialog = PlusObnov.newInstance(color);
                dialog.setTargetFragment(Fragment2.this, po);
                dialog.show(manager, PO);
                return true;
            case R.id.del:
                mdb2.delete("t7", null,null);
                updateUI();
                return true;
            default: return super.onOptionsItemSelected(item) ;
        }
    }

    @Override
    public void onActivityResult(int requesCode, int resultCode, Intent data) {
        if (requesCode == po) {
            akordspo = "";
            akordspo = (String) data.getSerializableExtra(PlusObnov.EXTRA_POA);
            namepo = (String) data.getSerializableExtra(PlusObnov.EXTRA_PON);
            slovapo = (String) data.getSerializableExtra(PlusObnov.EXTRA_POS);
            ton = "";
            OpredTon(akordspo);
            infoOneSong newsong =new infoOneSong();
            newsong.setAkords(akordspo);
            newsong.setName(namepo);
            newsong.setText(slovapo);
            newsong.setTon(ton);
            updateCrime(newsong);
            updateUI();
        }
    }

    public void updateCrime(infoOneSong isong) {
        ContentValues values = getContentValues(isong);
        mdb2.insert("t5",null,values);
    }

    private ContentValues getContentValues(infoOneSong isong) {
        ContentValues values = new ContentValues();
        values.put("name",isong.getName());
        values.put("slova",isong.getText());
        values.put("ton",isong.getTon());
        values.put("akords",isong.getAkords());
        return values;}

    public void OpredTon(String akkordslast) {String sum=""; int x=0,x1=0,i=0; boolean b= true;
        String akkordsnew = akkordslast+"      ";
        while (b && i< akkordslast.length()) {
            for (int j = 0; j < 24; j++) { String oneakk = akkordsnew.substring(i, i + masT[j].length()); char sa =akkordsnew.charAt(i+masT[j].length());
                if (oneakk.indexOf(masT[j]) !=-1 && sa!='m' && sa !='#') {ton = masT[j];b=false;
                }
            }
            i++;
        }
    }


    public void Dpoisk(String dpois) {dpoisk=dpois;l=dpoisk.length();ra=false;update2();}
    public void vivod(String s) {        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();}
    public void plusdata(String namT) { namTa = namT;}
    public void idimage(int name0) {
        if (name0 == 1) {idI = R.drawable.iconkah;}
        else if (name0 == 2) {idI =  R.drawable.iconkam;}
        else if (name0 == 3) {idI =  R.drawable.iconkad;}
        else if (name0 == 4) {idI =  R.drawable.iconkat;}
        else if (name0 == 5) {idI =  R.drawable.iconkamy;}
        else if (name0 == 6) {idI =  R.drawable.iconkaiz;}
        else if (name0 == 7) {idI =  R.drawable.iconkais;}
    }
    Integer Nomer(String s) {String c="";for (int i=0; i<s.length();i++) {if (s.charAt(i)>='0' && s.charAt(i)<='9') {c+=s.charAt(i);}} return Integer.parseInt(c);}

    @Override
    public  void onStart() {super.onStart();
        if (namTa.equals("t6")||namTa.equals("t5")||namTa.equals("t7")) {
            updateUI();}
        else {update2();} }

    void obnovTable(String tab,String adreses) {
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
        ContentValues values = new ContentValues();
        values.put("adreses", adreses);
        mdb2.insert(tab,null,values);
        cursor.close();
    }
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
    }
    void Thema(int sit) {sitc = sit;}
    public void setcolor(int color0) {color = color0;}


}
