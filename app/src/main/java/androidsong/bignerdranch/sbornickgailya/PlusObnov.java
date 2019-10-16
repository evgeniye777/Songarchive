package androidsong.bignerdranch.sbornickgailya;

import android.app.Activity;
import android.app.Dialog;
import android.bignerdranch.sbornickgailya.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class PlusObnov extends DialogFragment {
    //класс для открытия окна в котором можно внести изменения одной песни
    public  static  final String EXTRA_POA = "com.bignerdranceh.android.criminalintent.poa";
    public  static  final String EXTRA_PON = "com.bignerdranceh.android.criminalintent.pon";
    public  static  final String EXTRA_POS = "com.bignerdranceh.android.criminalintent.pos";
    String akords,name,slova;
    static int color;
    EditText Ea,En,Es;
    public static PlusObnov newInstance(int color0) {
        color = color0;
        PlusObnov fragment = new PlusObnov();
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(color);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.plusobnov,null);
        //три поля аккорды название слова
        Ea = (EditText) v.findViewById(R.id.akords);
        En = (EditText) v.findViewById(R.id.name);
        Es = (EditText) v.findViewById(R.id.slova);
        Ea.setText(akords);
        En.setText(name);
        Es.setText(slova);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Заполните поля").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                akords = Ea.getText().toString();
                name = En.getText().toString();
                slova = Es.getText().toString();
                sendResult(Activity.RESULT_OK, akords,name,slova);
            }
        }).create();
    }
//метод для выполнения сохранения изменений
    private  void sendResult(int resultCode, String akords0, String name0,String slova0){
        if (getTargetFragment() == null) {return;}
        Intent intent = new Intent();
        intent.putExtra(EXTRA_POA, akords0);
        intent.putExtra(EXTRA_PON, name0);
        intent.putExtra(EXTRA_POS, slova0);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();   //закрытие этого фрагмента
    }
    //метод для передачи в это окно имеющихся слов аккордов названия если происходит изменение а не создание новой песни
    public void most(String akords0,String name0,String slova0) {
        akords=akords0;
    name =name0;
    slova = slova0;
    }
}
