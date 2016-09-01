package com.example.raazn.momoapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Additem extends AppCompatActivity {
    EditText et1,et2,et3,et4,et5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);


        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        et3=(EditText)findViewById(R.id.editText3);
        et4=(EditText)findViewById(R.id.editText4);
        et5=(EditText)findViewById(R.id.editText5);

    }
    public void additem(View v){
        DatabaseHelper mydb=new DatabaseHelper(this);
        boolean result=mydb.insert_itemdetail(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString(), et5.getText().toString());
        if(result){
            Toast.makeText(getApplicationContext(), "Insert Successful", Toast.LENGTH_SHORT).show();
            mydb.insert_item_price();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");
        }
        else
            Toast.makeText(getApplicationContext(), "Failed to Insert Data", Toast.LENGTH_SHORT).show();
    }

}
