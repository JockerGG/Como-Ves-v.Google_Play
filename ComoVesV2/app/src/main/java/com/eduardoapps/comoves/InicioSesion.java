package com.eduardoapps.comoves;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class InicioSesion extends AppCompatActivity {

    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    String url = "https://www.mipeiper.com/register";

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        editTextEmail = (EditText) findViewById(R.id.usuario_login);
        editTextPassword = (EditText) findViewById(R.id.contrasena_login);
        buttonSignIn = (Button) findViewById(R.id.boton_iniciarSesion);
        textViewSignup = (TextView) findViewById(R.id.link);


        textViewSignup.setText(Html.fromHtml("<a href=" + url + ">Registrate Aquí</a>"));
        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);

                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });


       // firebaseAuth = FirebaseAuth.getInstance();
        /*if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            Intent i = new Intent(InicioSesion.this, MainActivity.class);
            startActivity(i);
            finish();
            //opening profile activity

        }*/


        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage(" Iniciando sesion...");
                progressDialog.show();
                userLogin();
            }
        });
    }


    //method for user login
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Introduce tu e-mail",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Introduce tu contraseña",Toast.LENGTH_SHORT).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog
        String urlApiValidacion="https://www.mipeiper.com/api/v1/jauth?email="+email+"&password="+password+"&id_revista=1";

        ///PROCESO DE VALIDACIÓN HACIENDO PETICIÓN A LA API DE MIPEIPER
        JsonObjectRequest peticionHTTP= new JsonObjectRequest(Request.Method.GET, urlApiValidacion, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //JSONObject mainUsers=jsonObject.getJSONObject("token");
                    //JSONObject usuario=mainUsers.getJSONObject(0);
                    String token=jsonObject.getString("token");
                    System.out.println("TOKEN USUARIO: "+token);

                    if(!token.equals("")){

                        startActivity(new Intent(InicioSesion.this,MainActivity.class));
                        finish();
                    }

                }catch (JSONException e){
                    Log.e("Error JSON", e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("Respuesta:","Error");
                progressDialog.dismiss();
                Toast.makeText(InicioSesion.this, "Usuario y/o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        });

        ApplicationController.getInstance().addToRequestQueue(peticionHTTP);



        //logging in the user
        /*firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            startActivity(new Intent(InicioSesion.this,MainActivity.class));

                            finish();

                        }
                    }
                });*/

    }
}
