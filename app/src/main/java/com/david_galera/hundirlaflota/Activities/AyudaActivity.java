package com.david_galera.hundirlaflota.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.david_galera.hundirlaflota.R;

public class AyudaActivity extends AppCompatActivity {

    private Button buttonVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        buttonVolver = (Button)findViewById(R.id.buttonVolver);

        buttonVolver.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }

        });
    }
}
