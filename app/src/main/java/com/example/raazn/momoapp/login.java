package com.example.raazn.momoapp;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    SharedPreferences spf;
    SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spf=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        spe=spf.edit();

    }
    public void login(View v){

        String name=spf.getString("uname", "admin");

        String pass=spf.getString("pass", "admin");
        EditText et1=(EditText)findViewById(R.id.uid);
        EditText et2=(EditText)findViewById(R.id.pass);


        if(et1.getText().toString().equalsIgnoreCase(name) && et2.getText().toString().equalsIgnoreCase(pass)){

            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplication(),Management.class));
            startActivity(i);

        }
        else
            Toast.makeText(getApplicationContext(),"Invalid User id or Password",Toast.LENGTH_SHORT).show();



    }
}
