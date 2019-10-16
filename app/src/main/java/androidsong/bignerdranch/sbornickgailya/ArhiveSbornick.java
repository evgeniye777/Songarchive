package androidsong.bignerdranch.sbornickgailya;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArhiveSbornick {
    private static ArhiveSbornick A;
    private List<infoOneSbornick> Arhive;
    private String[] massb;
    public static ArhiveSbornick get(Context context,String s) {
        if (A == null) { A = new ArhiveSbornick(context, s);}
        return A;
    }
    private ArhiveSbornick(Context context,String s) {
        zapoln(s);
        Arhive = new ArrayList<>();
        for (String m:massb) {infoOneSbornick Sbornick = new infoOneSbornick();
            Sbornick.infoOneSbornick();
        Sbornick.setName(m);
        Arhive.add(Sbornick);}
    }
    public List<infoOneSbornick>getInfoOne() {
    return Arhive;
    }

    public void zapoln(String bdn) {massb = new String[kolvo(bdn)];   int n =0;int x=0;for (int i=0; i<bdn.length(); i++) {
        if (bdn.charAt(i) ==';') {massb[x] = bdn.substring(n,i); n = i+1;x++;}}}
        public int kolvo(String s) {int n=0;for (int i=0;i<s.length();i++) {if (s.charAt(i)==';') {n++;}}return n;}
}
