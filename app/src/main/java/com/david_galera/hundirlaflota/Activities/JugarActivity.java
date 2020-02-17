package com.david_galera.hundirlaflota.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.david_galera.hundirlaflota.Fragments.DetallesFragment;
import com.david_galera.hundirlaflota.Fragments.TableroFragment;
import com.david_galera.hundirlaflota.R;

public class JugarActivity extends FragmentActivity implements DetallesFragment.DataListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);


    }

    @Override
    public void sendData(TextView turno, TextView cronometro, TextView barcosde2, TextView barcosde3, TextView barcosde4) {

        TableroFragment tableroFragment = (TableroFragment) getSupportFragmentManager().findFragmentById(R.id.tableroFragment);

        tableroFragment.renderTextViews(turno, cronometro, barcosde2, barcosde3, barcosde4);


    }
}
