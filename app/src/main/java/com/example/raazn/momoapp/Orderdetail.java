package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Orderdetail extends AppCompatActivity {
    ListView lView;
    DatabaseHelper mdb;
    TextView t1;
    Orderdetail od;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        lView=(ListView)findViewById(R.id.listView2);
        lView.setAdapter(new listOrderAdpter(this));
        mdb=new DatabaseHelper(this);
        t1=(TextView)findViewById(R.id.order_total);
        float total=mdb.order_total();
        t1.setText(String.valueOf(total));
        od=this;
    }
    public void delete(View v){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Clear the records?");

        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int i = mdb.delete_order();
                        if (i != -1) {
                            Toast.makeText(getApplicationContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                        }
                        mdb.update_order_autoinc();
                        refresh();
                        od.t1.setText("0");

                        dialog.cancel();
                    }
                });

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
    public void refresh(){
        lView.setAdapter(new listOrderAdpter(this));
    }
    public void search(){
        EditText e1=(EditText)findViewById(R.id.esearch);
        Cursor c=mdb.get_qty_amount(e1.getText().toString());
        float qty=0,amount=0;
        for(int i=0;i<c.getCount();i++){
            c.moveToPosition(i);
            qty=qty+Float.parseFloat(c.getString(0));
            amount=amount+Float.parseFloat(c.getString(1));
        }
        /*Snackbar.make(v,e1.getText().toString()+ " : Quantity = " + String.valueOf(qty) + " and Total Price = $ "+String.valueOf(amount), 5000)
                .setAction("Action", null).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search){
          //  search();
        }
        return super.onOptionsItemSelected(item);
    }
}
