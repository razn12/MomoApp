package com.example.raazn.momoapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by raazn on 28-Jul-16.
 */
public class itemAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Cursor i_cursor;
    DatabaseHelper mydb;
    TextView t1,t2,t3,t4,t5;
    public itemAdapter(ItemDetail itemDetail) {
        inflater = LayoutInflater.from(itemDetail);
        mydb=new DatabaseHelper(itemDetail);
        i_cursor=mydb.get_item_detail();
    }

    @Override
    public int getCount() {
        return i_cursor.getCount();
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
        v = inflater.inflate(R.layout.item_subitem, null);
        t1=(TextView)v.findViewById(R.id.i1);
        t2=(TextView)v.findViewById(R.id.i2);
        t3=(TextView)v.findViewById(R.id.i3);
        t4=(TextView)v.findViewById(R.id.i4);
        t5=(TextView)v.findViewById(R.id.i5);

        i_cursor.moveToPosition(position);

        t1.setText(i_cursor.getString(0));
        t2.setText(i_cursor.getString(1));
        t3.setText(i_cursor.getString(2));
        t4.setText(i_cursor.getString(3));
        t5.setText(i_cursor.getString(4));


        return v;
    }
}
