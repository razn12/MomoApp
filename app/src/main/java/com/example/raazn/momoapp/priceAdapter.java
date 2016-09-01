package com.example.raazn.momoapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by raazn on 14-Jul-16.
 */
public class priceAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Cursor p_cursor;
    DatabaseHelper mydb;
    TextView t1,t2,t3;
    public priceAdapter(priceInformation priceInformation) {
        inflater = LayoutInflater.from(priceInformation);
        mydb=new DatabaseHelper(priceInformation);
        p_cursor=mydb.get_price_detail();

    }

    @Override
    public int getCount() {
        return p_cursor.getCount();
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
        v = inflater.inflate(R.layout.price_item, null);
        t1=(TextView)v.findViewById(R.id.p1);
        t2=(TextView)v.findViewById(R.id.p2);
        t3=(TextView)v.findViewById(R.id.p3);

        p_cursor.moveToPosition(position);

        t1.setText(p_cursor.getString(0));
        t2.setText(p_cursor.getString(1));
        t3.setText(p_cursor.getString(2));


        return v;
    }
}
