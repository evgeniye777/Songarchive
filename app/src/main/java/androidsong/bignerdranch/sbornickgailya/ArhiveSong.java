package androidsong.bignerdranch.sbornickgailya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.SnapHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArhiveSong {
    private static ArhiveSong B;
    private static final String TAG = "MainActivity";
    private List<infoOneSong> Arhive;
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    private String namTa;

    public static ArhiveSong get(Context context) {
        if (B == null) {
            B = new ArhiveSong(context);
        }
        return B;
    }

    private ArhiveSong(Context context) {

    }

    public List<infoOneSong> getInfoOne() {
        return Arhive;
    }
//метод для хранения всех песен выбранного сборника
    public void Obnovlenie() {
        int n = 1;
        if (namTa.length() > 2) {
            n = 5;
        }
        String masSb[] = new String[n];
        int x = 0, y = 0;
        for (int i = 0; i < namTa.length(); i++) {
            if (namTa.charAt(i) == ';') {
                masSb[y] = namTa.substring(x, i);
                x = i + 1;
                y++;
            }
        }
        masSb[n - 1] = namTa.substring(x);
        Arhive = new ArrayList<>();
        //в таблице 5 столбцов, поэтому нужно 5 переменных для храниня id, названия, слов, тональности и аккордов
        String product = "";
        String product2 = "";
        String product3 = "";
        String product4 = "";
        String product0;
        for (int i = 0; i < masSb.length; i++) {
            Cursor cursor;
            Log.i(TAG,masSb[i]);
            if (!masSb[i].equals("t5")&&!masSb[i].equals("t6")&&!masSb[i].equals("t7")) {
            cursor = db.rawQuery("SELECT * FROM " + masSb[i], null);}
            else {cursor = db2.rawQuery("SELECT * FROM " + masSb[i], null);
                }
            cursor.moveToFirst();
            String tab = masSb[i];
            while (!cursor.isAfterLast()) {
                product0 = cursor.getString(0);
                product = cursor.getString(1);
                if ((!masSb[i].equals("t6"))&&(!masSb[i].equals("t7"))) {
                product2 = cursor.getString(2);
                product3 = cursor.getString(3);
                product4 = cursor.getString(4);
                    Log.i(TAG,masSb[i]);}
                else {tab = product.substring(0,product.indexOf(";")); String id = product.substring(product.indexOf(";")+1);
                    Cursor cursor2;
                    if (!tab.equals("t5")) {
                    cursor2 = db.rawQuery("SELECT * FROM " + tab, null);}
                    else {cursor2 = db2.rawQuery("SELECT * FROM " + tab, null);}
                    if (tab.equals("t5")) {
                        cursor2.moveToFirst();
                        while (!cursor2.getString(0).equals(""+id)) {cursor2.moveToNext();}
                    }
                    else {
                    cursor2.moveToPosition(Integer.parseInt(id)-1);}
                            product0 = cursor2.getString(0);
                            product = cursor2.getString(1);
                            product2 = cursor2.getString(2);
                            product3 = cursor2.getString(3);
                            product4 = cursor2.getString(4);
                    cursor2.close();
                }
                cursor.moveToNext();

                infoOneSong Song = new infoOneSong();
                ;
                Song.setText(product2);
                if (product.length() > 32) {
                    product = product.substring(0, 32) + "...";
                }
                Song.setName(product);
                Song.setAkords(product4);
                Song.setTon(product3);
                Song.setnsb(tab);
                Song.N1(0);
                Song.N2(0);
                Song.setidn(product0);
                Song.setnameintext("name");
                    Arhive.add(Song);

            }
            cursor.close();
        }
    }
// метод для записи новой песни или внесения изменений
    public void updateCrime(infoOneSong isong) {
        ContentValues values = getContentValues(isong);
        if (!isong.getnsb().equals("t5")&&!isong.getnsb().equals("t6")&&!isong.getnsb().equals("t7")) {
        db.update(isong.getnsb(), values, "_id" + " = ?", new String[]{isong.getidn()});}
        else {db2.update(isong.getnsb(), values, "_id" + " = ?", new String[]{isong.getidn()});}
    }

    private ContentValues getContentValues(infoOneSong isong) {
        ContentValues values = new ContentValues();
        values.put("_id", isong.getidn());
        values.put("name", isong.getName());
        values.put("slova", isong.getText());
        values.put("ton", isong.getTon());
        values.put("akords", isong.getAkords());
        return values;
    }
//  с помощью этого метода данному классу передаются две базы данных и название сборника
    public void plusbasa(SQLiteDatabase db0, SQLiteDatabase db1, String namT) {
        db = db0;
        db2 = db1;
        namTa = namT;
    }
//удаление песни
    public void delCrime(infoOneSong c) {
        if (!c.getnsb().equals("t5")&&!c.getnsb().equals("t6")&&!c.getnsb().equals("t7")) {
        db.delete(c.getnsb(), "_id = ?", new String[]{c.getidn().toString()});}
        else {db2.delete(c.getnsb(), "_id = ?", new String[]{c.getidn().toString()});}
        Obnovlenie();
    }
}


