package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import databases.DATABases;

public class Fragment2 extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String Saved = "subtitle";
    private RecyclerView mSongRec;
    private SongAdapter mAdapter;
    private String dpoisk="";
    int l=0;
    boolean ra=false;
    private DATABases basa;
    private SQLiteDatabase mdb;
    String masT[] = {"A", "A#", "H", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "Am", "Am#", "Hm", "Cm", "Cm#", "Dm", "Dm#", "Em", "Fm", "Fm#", "Gm", "Gm#"};
    private String namTa;
    ArhiveSong arhiveSong = ArhiveSong.get(getActivity());
    List<infoOneSong> songi;
    String product="";
    int raz =0;
    List<infoOneSong> Arhive0 = new ArrayList<>();
    List<infoOneSong> Arhive1 = new ArrayList<>();
    List<infoOneSong> Arhive2 = new ArrayList<>();
    List<infoOneSong> Arhive3 = new ArrayList<>();
    List<infoOneSong> Arhive4 = new ArrayList<>();
    List<infoOneSong> Arhive5 = new ArrayList<>();
    List<infoOneSong> Arhive = new ArrayList<> ();
    List<infoOneSong> Arhivetwo = new ArrayList<> ();
    int sitc;
    int idI;
    private static final String PO = "pobnov";
    private static final int po = 0;
    String ton,akordspo,namepo,slovapo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forfragment2,container,false);
        mSongRec = (RecyclerView) view.findViewById(R.id.forfragment2);
        mSongRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {

        arhiveSong.plusbasa(mdb,namTa);
        arhiveSong.Obnovlenie();
        songi = arhiveSong.getInfoOne();
        if (mAdapter == null) {mAdapter = new SongAdapter(songi);
        mSongRec.setAdapter(mAdapter);}
    else {mAdapter.setSongs(songi);
    mAdapter.notifyDataSetChanged();}
    }

    public void update2() {Arhivetwo.clear();Arhive.clear();
        /*if (l==0) {obnovArhive(arhiveSong.getInfoOne());}
        if (l==1) {obnovArhive(Arhive0);vivod(""+l+"  "+Arhive0.size());}
        else if (l==2) {obnovArhive(Arhive1);}
        else if (l==3) {obnovArhive(Arhive2);}
        else if (l==4) {obnovArhive(Arhive3);}
        else if (l==5) {obnovArhive(Arhive4);}
        else if (l>5)  {obnovArhive(Arhive5);}*/
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
        /*if (l==0) {obnovArhive0(Arhivetwo);}
        if (l==1) {obnovArhive1(Arhivetwo);}
        else if (l==2) {obnovArhive2(Arhivetwo);}
        else if (l==3) {obnovArhive3(Arhivetwo);}
        else if (l==4) {obnovArhive4(Arhivetwo);}
        else if (l==5) {obnovArhive5(Arhivetwo);}*/

    /*}
    else {
        Arhivetwo=songi;
    }*/ if (!namTa.equals("t6")&&!namTa.equals("t7")&&songi.size()>7&&ra==false&&!dpoisk.equals("")) {
        infoOneSong songpos = new infoOneSong();
        songpos.setnameintext("pos");
        songpos.setName("Расширенный поиск");
        Arhivetwo.add(songpos);}
        if (mAdapter == null) {mAdapter = new SongAdapter(Arhivetwo);
            mSongRec.setAdapter(mAdapter);}
        else {mAdapter.setSongs(Arhivetwo);
            mAdapter.notifyDataSetChanged();}
    }

    /*@Override
    public  void onStart() {super.onStart();
      updateUI(); }*/
    private class SongHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        infoOneSong mSongg;
        private TextView mTitle;
        private TextView mText;
        private ImageView mImageView;
        public SongHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.spisocksong,parent,false));
            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.song_Title);
            mText = (TextView) itemView.findViewById(R.id.song_Text);
            if (sitc==1) {mTitle.setTextColor(Color.WHITE);
                mText.setTextColor(Color.WHITE);}
            mImageView = (ImageView) itemView.findViewById(R.id.mImage);

        }
        public void bind(infoOneSong songg) {mSongg = songg;
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
                /*final SpannableStringBuilder text = new SpannableStringBuilder(SO);
                final ForegroundColorSpan style = new ForegroundColorSpan(Color.rgb(245,78,126));
                text.setSpan(style,21,21+x2-x1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/
                mTitle.setText(mSongg.getName());
            mTitle.setTextSize(18);
                mText.setText(SO);}

        idimage(Nomer(mSongg.nsb));
            mImageView.setImageResource(idI);

        }
        else {
            mTitle.setText(mSongg.getName());
            //mTitle.setTextColor(0x0000ff);
            mTitle.setTextSize(30);
            mTitle.setGravity(Gravity.CENTER);
            //mTitle.setWidth(20);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:
                FragmentManager manager = getFragmentManager();
                PlusObnov dialog = PlusObnov.newInstance();
                dialog.setTargetFragment(Fragment2.this, po);
                dialog.show(manager, PO);
                return true;
            case R.id.del:
                mdb.delete("t7", null,null);
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
        mdb.insert("t5",null,values);
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
    public void plusdata(/*SQLiteDatabase db,*/String namT) {/*mdb = db;*/ namTa = namT;}
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
            ContentValues values = new ContentValues();
            values.put("adreses", adreses);
            mdb.insert(tab,null,values);
        cursor.close();
    }
    void Thema(int sit) {sitc = sit;}

    void obnovArhive(List<infoOneSong> list) {Arhive.clear();
    for (infoOneSong song:list) {Arhive.add(song);}}
    void obnovArhive0(List<infoOneSong> list) {Arhive0.clear();
        for (infoOneSong song:list) {Arhive0.add(song);}}
    void obnovArhive1(List<infoOneSong> list) {Arhive1.clear();
        for (infoOneSong song:list) {Arhive1.add(song);}}
    void obnovArhive2(List<infoOneSong> list) {Arhive2.clear();
        for (infoOneSong song:list) {Arhive2.add(song);}}
    void obnovArhive3(List<infoOneSong> list) {Arhive3.clear();
        for (infoOneSong song:list) {Arhive3.add(song);}}
    void obnovArhive4(List<infoOneSong> list) {Arhive4.clear();
        for (infoOneSong song:list) {Arhive4.add(song);}}
    void obnovArhive5(List<infoOneSong> list) {Arhive5.clear();
        for (infoOneSong song:list) {Arhive5.add(song);}}
}
