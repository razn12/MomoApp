package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by raazn on 12-Jul-16.
 */
public class listAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Cursor order_cursor;
    DatabaseHelper mydb;
    TextView t1,t2,t3,t4,t5;
    ListView lview;
    MainActivity mcontent;

    public listAdapter(MainActivity mactivity,ListView lv) {
        inflater = LayoutInflater.from(mactivity);
        mydb=new DatabaseHelper(mactivity);
        order_cursor=mydb.get_temp_detail();
        lview=lv;
        mcontent=mactivity;
    }

    @Override
    public int getCount() {
        return order_cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.activity_listitem, null);
        t1=(TextView)v.findViewById(R.id.sn);
        t2=(TextView)v.findViewById(R.id.order);
        t3=(TextView)v.findViewById(R.id.price);
        t4=(TextView)v.findViewById(R.id.quantity);
        t5=(TextView)v.findViewById(R.id.amount);

        order_cursor.moveToPosition(position);

        t1.setText(order_cursor.getString(0));
        t2.setText(order_cursor.getString(1));
        t3.setText(order_cursor.getString(2));
        t4.setText(order_cursor.getString(3));
        t5.setText(order_cursor.getString(4));

        return v;
    }

}
