package com.example.raazn.momoapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by raazn on 13-Jul-16.
 */
public class listOrderAdpter extends BaseAdapter {
    LayoutInflater inflater;
    Cursor o_cursor;
    DatabaseHelper mydb;
    TextView t1,t2,t3,t4,t5;
    public listOrderAdpter(Orderdetail orderdetail) {
        inflater = LayoutInflater.from(orderdetail);
        mydb=new DatabaseHelper(orderdetail);
        o_cursor=mydb.get_order_detail();
    }

    @Override
    public int getCount() {
        return o_cursor.getCount();
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
        v = inflater.inflate(R.layout.report, null);
        t1=(TextView)v.findViewById(R.id.o1);
        t2=(TextView)v.findViewById(R.id.o2);
        t3=(TextView)v.findViewById(R.id.o3);
        t4=(TextView)v.findViewById(R.id.o4);
        t5=(TextView)v.findViewById(R.id.o5);
        o_cursor.moveToPosition(position);

        t1.setText(o_cursor.getString(0));
        t2.setText(o_cursor.getString(1));
        t3.setText(o_cursor.getString(2));
        t4.setText(o_cursor.getString(3));
        t5.setText(o_cursor.getString(4));
        return v;
    }
}
