package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ItemDetail extends AppCompatActivity {
    DatabaseHelper mydb;
    ItemDetail itemDetail;
    ListView lview;
    Cursor i_cursor;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mydb=new DatabaseHelper(this);
        itemDetail=this;
        i_cursor=mydb.get_item_detail();
        lview=(ListView)findViewById(R.id.itemListView);
        lview.setAdapter(new itemAdapter(this));
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i_cursor.moveToPosition(position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(itemDetail);
                item=i_cursor.getString(0);
                builder1.setTitle("Delete " + item + "?");

                builder1.setCancelable(true);

                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (int j = 1; j <= 4; j++) {
                                    if (!i_cursor.getString(j).equals(""))
                                        mydb.delete_price(i_cursor.getString(j));

                                }
                                int i = mydb.delete_item(item);

                                if (i > 0) {
                                    Toast.makeText(getApplicationContext(), "Item Deleted Successful", Toast.LENGTH_SHORT).show();
                                    refreshAdapter();
                                }


                                if (i <= 0)
                                    Toast.makeText(getApplicationContext(), "Faied to Delete", Toast.LENGTH_SHORT).show();



                                dialog.cancel();
                            }
                        }

                );
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

    }
    public void additem(View v){
        Intent i=new Intent();
        i.setComponent(new ComponentName(getApplicationContext(), Additem.class));
        startActivity(i);
    }
    public  void refreshAdapter(){
        lview.setAdapter(new itemAdapter(this));

    }
}
