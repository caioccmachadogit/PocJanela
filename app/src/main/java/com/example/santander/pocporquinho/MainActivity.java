package com.example.santander.pocporquinho;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int segmento = 1;

        PorquinhoComponent porquinhoComponent = new PorquinhoComponent(new PorquinhoView() {
            @Override
            public void actionInvista() {
                Log.v("actionInvista", "actionInvista");
            }

            @Override
            public void actionAbrir() {
                Log.v("actionAbrir", "actionAbrir");
            }

            @Override
            public void actionFechar() {
                Log.v("actionFechar", "actionFechar");
            }

            @Override
            public int getAjusteTelaHeight() {
                return getSupportActionBar().getHeight();
            }

            @Override
            public int getSegmento() {
                return segmento;
            }
        }, this);
        porquinhoComponent.initComponent();
    }
}
