package com.eduardoapps.comoves;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

       Button boton_relanzar = (Button) findViewById(R.id.boton_relanzar);

        boton_relanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoInternet.this,SplashScreen.class);
                startActivity(i);
            }
        });

    }
}
