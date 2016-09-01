package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class priceInformation extends AppCompatActivity {
    ListView lView;
    priceInformation priceinfo;
    LinearLayout layout;
    DatabaseHelper mydb;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_information);
        mydb=new DatabaseHelper(this);
        c=mydb.get_price_detail();
        priceinfo=this;
        lView=(ListView)findViewById(R.id.listView3);
        lView.setAdapter(new priceAdapter(this));


        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(priceinfo);
                layout = new LinearLayout(getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText e1 = new EditText(priceinfo);
                e1.setInputType(InputType.TYPE_CLASS_NUMBER);
                e1.setHint("New Price");
                layout.addView(e1);
                final EditText e2=new EditText(priceinfo);
                e2.setInputType(InputType.TYPE_CLASS_NUMBER);
                e2.setHint("New Discount%");
                layout.addView(e2);
                alert.setTitle("Update Price Detail of " + c.getString(0));

                alert.setView(layout);

                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        try {
                            mydb.update_price(c.getString(0), e1.getText().toString(), e2.getText().toString());
                            refreshPrice();
                            Toast.makeText(getApplicationContext(), "Updated ", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Not Updated ", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                alert.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.delete_price(c.getString(0));
                        Toast.makeText(getApplicationContext(), "Deleted ", Toast.LENGTH_SHORT).show();
                        refreshPrice();
                        dialog.cancel();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        layout.removeView(e1);
                        layout.removeView(e2);
                    }
                });

                alert.show();
            }
        });
    }
    public  void refreshPrice(){
        lView.setAdapter(new priceAdapter(this));
    }


}
