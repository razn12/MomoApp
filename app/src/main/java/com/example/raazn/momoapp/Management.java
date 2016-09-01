package com.example.raazn.momoapp;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Management extends AppCompatActivity {
    EditText et1,et2,et3;
    SharedPreferences spf;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        spf=getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        spe=spf.edit();
    }
    public void additem(View v){
        Intent i = new Intent();
        i.setComponent(new ComponentName(getApplication(), ItemDetail.class));
        startActivity(i);
    }

    public void addprice(View v){
        Intent i = new Intent();
        i.setComponent(new ComponentName(getApplication(), priceInformation.class));
        startActivity(i);

    }
    public void orderDetail(View v){
        Intent i = new Intent();
        i.setComponent(new ComponentName(getApplication(), Orderdetail.class));
        startActivity(i);

    }
    public void register(View v){
        final Dialog d=new Dialog(this);
        d.setContentView(R.layout.register);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        d.setTitle(Html.fromHtml("<font color='#FFFFFF'>Register</font>"));

        d.show();
        et1=(EditText)d.findViewById(R.id.user);
        et2=(EditText)d.findViewById(R.id.pass);
        et3=(EditText)d.findViewById(R.id.confirm);
        Button b1=(Button)d.findViewById(R.id.reg);
        Button b2=(Button)d.findViewById(R.id.cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et2.getText().toString().equals(et3.getText().toString())) {

                    spe.putString("uname", et1.getText().toString());
                    spe.putString("pass", et2.getText().toString());
                    spe.commit();
                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                    d.dismiss();
                }
                else
                    Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_SHORT).show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }
}
