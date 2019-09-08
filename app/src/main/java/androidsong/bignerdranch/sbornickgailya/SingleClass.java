package androidsong.bignerdranch.sbornickgailya;

import android.bignerdranch.sbornickgailya.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
public abstract class SingleClass extends AppCompatActivity{
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foractiv1);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.forActiv1);
        if (fragment == null) {fragment = createFragment();
            fm.beginTransaction().add(R.id.forActiv1,fragment).commit();}
    }
}
