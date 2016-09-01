package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Cursor gridcursor,ordercursor,pricecursor;
    DatabaseHelper mydb;
    ListView lView;
    EditText cash;
    TextView total;
    int i;
    Cursor c,price_discount_cursor;
    MainActivity mainActivity;
    TextToSpeech t1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cash=(EditText)findViewById(R.id.cash);
        total=(TextView)findViewById(R.id.total);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivity=this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mydb=new DatabaseHelper(this);
        gridcursor=mydb.get_item_detail();
        if(gridcursor.getCount()==0)
            Toast.makeText(getApplicationContext(),"Insert item detail",Toast.LENGTH_SHORT).show();

        GridView gView=(GridView)findViewById(R.id.gridView);
        gView.setAdapter(new gridAdapter(this, gridcursor,gView));

        lView=(ListView)findViewById(R.id.listView);
        lView.setAdapter(new listAdapter(this,lView));
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                c = mydb.get_temp_detail();
                c.moveToPosition(position);

                AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
                final EditText edittext = new EditText(mainActivity);
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                edittext.setHint("New Quantity");
                alert.setMessage("Enter new quantity of  " + c.getString(1));
                alert.setTitle("Update Quantity");
                alert.setView(edittext);

                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String quantity = edittext.getText().toString();
                        if(!quantity.equals("")){
                            price_discount_cursor = mydb.get_price_discount(c.getString(1));
                            if (!price_discount_cursor.moveToFirst()) ;
                            System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                            float price = Float.parseFloat(price_discount_cursor.getString(0));
                            float discount = Float.parseFloat(price_discount_cursor.getString(1));
                            float total = Integer.parseInt(quantity) * (price - ((discount / 100) * price));
                            mydb.update_quantity(Integer.parseInt(c.getString(0)), quantity, String.valueOf(total));
                            refreshAdapter();
                            Toast.makeText(getApplicationContext(), "Updated ", Toast.LENGTH_SHORT).show();
                            mainActivity.total.setText(String.valueOf(mydb.get_total_price()));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Not Updated ", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mydb.delete_temp_item(Integer.parseInt(c.getString(0)));
                        refreshAdapter();
                        Toast.makeText(getApplicationContext(), "Deleted " + c.getString(1), Toast.LENGTH_SHORT).show();
                        mainActivity.total.setText(String.valueOf(mydb.get_total_price()));
                        dialog.cancel();
                    }
                });

                alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        total.setText(String.valueOf(mydb.get_total_price()));

        t1=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Close The Momo Corner?");
            builder1.setIcon(R.drawable.logo);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
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
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar itemdb clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"The Momo Corner"+"\n"+"Version 1.0",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view itemdb clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.login) {
            Intent i = new Intent();
            i.setComponent(new ComponentName(getApplication(),login.class));
            startActivity(i);
        } else if (id == R.id.aboutus) {
            Toast.makeText(getApplicationContext(),"The Momo Corner"+"\n"+"Version 1.0",Toast.LENGTH_LONG).show();

        } else if (id == R.id.dev) {
            Toast.makeText(getApplicationContext(),"Rajendra Prajapati"+"\n"+"raznpra@gmail.com",Toast.LENGTH_LONG).show();

        } else if (id == R.id.ver) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void refreshAdapter(){

        lView.setAdapter(new listAdapter(this,lView));


    }
    public void transaction(View v){
        System.out.println("***********************");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Enter the Transaction?");

        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean result=mydb.insert_transaction();
                        System.out.println(result);

                        int i = mydb.delete_temp();
                        if (i != 0&&result) {
                            Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                            mydb.update_autoinc();

                        }
                        cash.setText("");
                        refreshAdapter();
                        total.setText("0");
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
    public void cash(View v){
        i=0;
        final Dialog d=new Dialog(this);
        d.setContentView(R.layout.cash_dialog_box);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
        d.setTitle(Html.fromHtml("<font color='#FFFFFF'>Select Cash</font>"));

        d.show();
        ImageView five=(ImageView)d.findViewById(R.id.imageView2);
        ImageView ten=(ImageView)d.findViewById(R.id.imageView6);
        ImageView twenty=(ImageView)d.findViewById(R.id.imageView3);
        ImageView fifty=(ImageView)d.findViewById(R.id.imageView4);
        ImageView hundreed=(ImageView)d.findViewById(R.id.imageView5);
        Button ok=(Button)d.findViewById(R.id.ok);
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+5;
                System.out.println(i);
                Toast.makeText(getApplicationContext(),"Price: "+i,Toast.LENGTH_SHORT).show();
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+10;
                Toast.makeText(getApplicationContext(),"Price: "+i,Toast.LENGTH_SHORT).show();
            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+20;
                Toast.makeText(getApplicationContext(),"Price: "+i,Toast.LENGTH_SHORT).show();;
            }
        });
        fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+50;System.out.println(i);
                Toast.makeText(getApplicationContext(),"Price: "+i,Toast.LENGTH_SHORT).show();
            }
        });
        hundreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+100;System.out.println(i);
                Toast.makeText(getApplicationContext(),"Price: "+i,Toast.LENGTH_SHORT).show();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(i);
                String totalcash= String.valueOf(i);
                cash.setText(totalcash);
                d.dismiss();
            }
        });
    }
    public  void go(View v){
        float total= 0;
        try {
            total = Float.parseFloat(cash.getText().toString())-mydb.get_total_price();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(),"Enter cash",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if(total>0){
            String text="Return price is $ "+String.valueOf(total)+" . Close transaction";
            Snackbar.make(v, text, 5000)
                    .setAction("Action", null).show();
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        }else {
            Toast.makeText(getApplicationContext(),"Insufficient Balance",Toast.LENGTH_SHORT).show();
            t1.speak("Sorry insufficient balance", TextToSpeech.QUEUE_FLUSH, null);
        }

    }


}
