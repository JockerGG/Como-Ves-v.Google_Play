package com.eduardoapps.comoves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;
    //FragmentPeliculas fragmentPeliculas;


    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //configuramos TimerTask para que inicie nuestra actividad principal
        TimerTask action = new TimerTask() {
            @Override
            public void run() {

                if(conectadoWifi()){
                    startActivity(new Intent(SplashScreen.this,InicioSesion.class));
                    finish();
                }

                else{
                    startActivity(new Intent(SplashScreen.this, MisLecturasActivity.class));
                    finish();
                }

            }
        };

//creamos el timer que ejecutará nuestra TimerTask después de 3 segundos
        new Timer().schedule(action, 2000);


    }


}
