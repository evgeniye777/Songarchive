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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import databases.DATABases;

public class Fragment1 extends Fragment {
    private static final String TAG = "MainActivity";
private static final String Saved = "";
private RecyclerView mSbornickRec;
private SbornickAdapter mAdapter;
    private DATABases basa;
    private SQLiteDatabase mdb;
    private String vs;
    private ImageView page;
    int idI;
    int sitc;
    String name;
@Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {int vs=savedInstanceState.getInt(Saved,0);
    sitc=vs;
    }
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
mainx();
}

@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.forfragment1,container,false);
   mSbornickRec = (RecyclerView) view.findViewById(R.id.forfragment1);
   mSbornickRec.setLayoutManager(new LinearLayoutManager(getActivity()));
   if (savedInstanceState !=null) { }
   updateUI();
   return view;
}
private void updateUI() {
    vs = vs.replace("null","");
    vs = vs.replace("sqlite_sequence;","");
    vs = vs.replace("android_metadata;","");
    vs = vs.replace("t8;","");
    ArhiveSbornick arhiveSbornick  = ArhiveSbornick.get(getActivity(),vs);
    List<infoOneSbornick> sbornicki = arhiveSbornick.getInfoOne();
    if (mAdapter == null) {
        mAdapter = new SbornickAdapter(sbornicki);
    mSbornickRec.setAdapter(mAdapter);}
    else {mAdapter.setSborniks(sbornicki);
    mAdapter.notifyDataSetChanged();}
}
private class SbornickHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
infoOneSbornick mSbornickk;
    private TextView mTitle;
    public SbornickHolder(LayoutInflater inflater,ViewGroup parent) {
    super(inflater.inflate(R.layout.spisocksbornick,parent,false));
    itemView.setOnClickListener(this);
    mTitle = (TextView) itemView.findViewById(R.id.sbornick_Title);
    if (sitc==1) {mTitle.setTextColor(Color.WHITE);}
    page = (ImageView) itemView.findViewById(R.id.icsolved);
}
public void bind(infoOneSbornick sbornickk) {mSbornickk = sbornickk;
idimage(Nomer(mSbornickk.getName()));
   mTitle.setText(name);
   page.setImageResource(idI);

}
    @Override
    public void onClick(View view) {
        Intent intent = Active2.newIntent(getActivity(),mSbornickk.getName(),sitc);
        startActivity(intent);
    }
}
private class SbornickAdapter extends RecyclerView.Adapter<SbornickHolder> {
    private List<infoOneSbornick> mSbornick;
    public SbornickAdapter(List<infoOneSbornick> sbornickk) {
        mSbornick = sbornickk;
    }
    @NonNull
    @Override
    public SbornickHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
return new SbornickHolder(layoutInflater,parent);
    }
    @Override
    public void onBindViewHolder(SbornickHolder holder,int position) {
        infoOneSbornick infoOneSbornickk = mSbornick.get(position);
        holder.bind(infoOneSbornickk);
    }
    @Override
    public int getItemCount() {
        return mSbornick.size();
    }
    public void setSborniks(List<infoOneSbornick> sborniks) {mSbornick = sborniks;}
}
    public void vivod(String s) {        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();}


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
    super.onCreateOptionsMenu(menu,inflater);
    inflater.inflate(R.menu.panel1,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.poisk:
                Intent intent = Active2.newIntent(getActivity(),"t1;t2;t3;t4;t5",sitc);
                startActivity(intent);
                return true;
            case R.id.sitings:
                Intent intent2 = Activesitings.newIntent(getActivity());
                startActivity(intent2);
                return true;
                default: return super.onOptionsItemSelected(item) ;
        }
    }

    public  void mainx(){
        Cursor c = mdb.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

       c.moveToFirst();
            while ( !c.isAfterLast() ) {
                String ps = c.getString( c.getColumnIndex("name"));
               vs+= ps+";";

                c.moveToNext();
            }


    };
public void idimage(int name0) {
    if (name0 == 1) {idI = R.drawable.iconkah; name = "Хвали Творца";}
else if (name0 == 2) {idI =  R.drawable.iconkam; name = "Молодежный сборник";}
else if (name0 == 3) {idI =  R.drawable.iconkad; name = "Детский сборник";}
    else if (name0 == 4) {idI =  R.drawable.iconkat; name = "Татарский сборник";}
    else if (name0 == 5) {idI =  R.drawable.iconkamy; name = "Мои песни";}
    else if (name0 == 6) {idI =  R.drawable.iconkaiz; name = "Избранное";}
    else if (name0 == 7) {idI =  R.drawable.iconkais; name = "История";}
}
void Thema(int sit) {sitc=sit;}
    Integer Nomer(String s) {String c="";for (int i=0; i<s.length();i++) {if (s.charAt(i)>='0' && s.charAt(i)<='9') {c+=s.charAt(i);}} return Integer.parseInt(c);}


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Saved,sitc);
    };
}
