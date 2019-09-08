package androidsong.bignerdranch.sbornickgailya;

import android.app.Dialog;
import android.bignerdranch.sbornickgailya.R;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class VuborTonaClass extends DialogFragment {
    public  static  final String EXTRA_TON = "com.bignerdranceh.android.criminalintent.ton";
    public  static  final String EXTRA_TONn = "com.bignerdranceh.android.criminalintent.tonn";
    String masT[] =  {"A","A#","H","C","C#","D","D#","E","F","F#","G","G#"};
    Button T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12;
    String Tnew;
    String Tlast,TlastO;
    //private  static final String ARG_DATE = "ton";
    public static VuborTonaClass newInstance(String Ton) {
        //Bundle args = new Bundle();
        //args.putSerializable(ARG_DATE,Ton);
        VuborTonaClass fragment = new VuborTonaClass();
        //fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.vuborton,null);
        T1 = (Button) v.findViewById(R.id.ak1); T1.setText(masT[0]);
        T2 = (Button) v.findViewById(R.id.ak2); T2.setText(masT[1]);
        T3 = (Button) v.findViewById(R.id.ak3); T3.setText(masT[2]);
        T4 = (Button) v.findViewById(R.id.ak4); T4.setText(masT[3]);
        T5 = (Button) v.findViewById(R.id.ak5); T5.setText(masT[4]);
        T6 = (Button) v.findViewById(R.id.ak6); T6.setText(masT[5]);
        T7 = (Button) v.findViewById(R.id.ak7); T7.setText(masT[6]);
        T8 = (Button) v.findViewById(R.id.ak8); T8.setText(masT[7]);
        T9 = (Button) v.findViewById(R.id.ak9); T9.setText(masT[8]);
        T10 = (Button) v.findViewById(R.id.ak10); T10.setText(masT[9]);
        T11 = (Button) v.findViewById(R.id.ak11); T11.setText(masT[10]);
        T12 = (Button) v.findViewById(R.id.ak12); T12.setText(masT[11]);
        nameKn(Tlast);
        sostKn(Tlast);

       T1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T1.getText().toString(); sostKn(Tnew);
            sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T2.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T3.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T4.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T5.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T6.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T7.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T8.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T9.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T10.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T11.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        T12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Tnew  = T12.getText().toString(); sostKn(Tnew);
                sendResult(0,(Nmas(Tnew)-Nmas(TlastO)+12)%12);
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Выберите тональность")
               .create();
    }

    private  void sendResult(int resultCode, int STR){
        if (getTargetFragment() == null) {return;}
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TON, STR);
        intent.putExtra(EXTRA_TONn,Tnew);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();   //закрытие этого фрагмента
    }
public void most(String tnew,String torg) {Tlast = tnew;TlastO = torg;}

public void sostKn(String kn) {
        kn = kn.replace("m","");
    if (kn.startsWith("A") && !kn.startsWith("A#")){T1.setEnabled(false);} else {T1.setEnabled(true);}
    if (kn.startsWith("A#")){ T2.setEnabled(false);} else {T2.setEnabled(true);}
    if (kn.startsWith("H")){ T3.setEnabled(false);} else {T3.setEnabled(true);}
    if (kn.startsWith("C") && !kn.startsWith("C#")){ T4.setEnabled(false);} else {T4.setEnabled(true);}
    if (kn.startsWith("C#")){ T5.setEnabled(false);} else {T5.setEnabled(true);}
    if (kn.startsWith("D") && !kn.startsWith("D#")){ T6.setEnabled(false);} else {T6.setEnabled(true);}
    if (kn.startsWith("D#")){ T7.setEnabled(false);} else {T7.setEnabled(true);}
    if (kn.startsWith("E")){ T8.setEnabled(false);} else {T8.setEnabled(true);}
    if (kn.startsWith("F") && !kn.startsWith("F#")){ T9.setEnabled(false);} else {T9.setEnabled(true);}
    if (kn.startsWith("F#")){ T10.setEnabled(false);} else {T10.setEnabled(true);}
    if (kn.startsWith("G") && !kn.startsWith("G#")){ T11.setEnabled(false);} else {T11.setEnabled(true);}
    if (kn.startsWith("G#")){ T12.setEnabled(false);} else {T12.setEnabled(true);}
}

public void  nameKn(String kn) {if (kn.indexOf("m") !=-1) {
    T1.setText("Am");T2.setText("Am#");T3.setText("Hm");T4.setText("Cm");
    T5.setText("Cm#");T6.setText("Dm");T7.setText("Dm#");T8.setText("Em");
    T9.setText("Fm");T10.setText("Fm#");T11.setText("Gm");T12.setText("Gm#");
}else { T1.setText("A");T2.setText("A#");T3.setText("H");T4.setText("C");
    T5.setText("C#");T6.setText("D");T7.setText("D#");T8.setText("E");
    T9.setText("F");T10.setText("F#");T11.setText("G");T12.setText("G#");}}
public int Nmas(String ak) {String a=""+ak.replace("m","");int n=0; for (int i=0; i<masT.length;i++) {String a1=""+masT[i];if (a.startsWith(a1)&&a.length()==a1.length()) {n=i;break;}}return n;}
}