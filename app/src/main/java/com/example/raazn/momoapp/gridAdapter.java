package com.example.raazn.momoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by raazn on 10-Jul-16.
 */
public class gridAdapter extends BaseAdapter {
    Cursor c,price_discount_cursor,temp_cursor;
    LayoutInflater inflater;
    MainActivity mcontext;
    GridView gview;
    CheckBox c1,c2,c3,c4;
    TextView t1;
    DatabaseHelper mydb;
    String quantity="1";
    //String quantity;

    public  gridAdapter(MainActivity mactivity, Cursor data,GridView g) {
        c = data;
        inflater = LayoutInflater.from(mactivity);
        mcontext=mactivity;
        gview=g;
        mydb=new DatabaseHelper(mactivity);

    }
    @Override
    public int getCount() {
        return c.getCount();
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

        v = inflater.inflate(R.layout.grid2, null);
        t1=(TextView)v.findViewById(R.id.gtext);
        c.moveToPosition(position);
        t1.setText(c.getString(0));
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c.moveToPosition(position);
                final Dialog d=new Dialog(mcontext);
                d.setContentView(R.layout.grid_popup);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));
                String title="<font color='#FFFFFF'>"+c.getString(0) + " items"+"</font>";
                d.setTitle(Html.fromHtml(title));

                d.show();
                Button b1=(Button)d.findViewById(R.id.b1);
                Button b2=(Button)d.findViewById(R.id.b2);
                Button b3=(Button)d.findViewById(R.id.b3);
                Button b4=(Button)d.findViewById(R.id.b4);
                    b1.setText(c.getString(1));
                    b2.setText(c.getString(2));
                    b3.setText(c.getString(3));
                    b4.setText(c.getString(4));

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!c.getString(1).equals("")){
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(1));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));
                                System.out.println("!!!!!!!!!!!!!!!!!"+total);
                                boolean insert=mydb.insert_temp_detail(c.getString(1),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(1),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                        else{
                            Toast.makeText(mcontext.getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }
                        d.dismiss();
                    }

                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!c.getString(2).equals("")){
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(2));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(2),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(2),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                        else{
                            Toast.makeText(mcontext.getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }
                        d.dismiss();
                    }

                });
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!c.getString(3).equals("")){
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(3));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(3),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(3),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(mcontext.getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }


                        d.dismiss();
                    }

                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!c.getString(4).equals("")){
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(4));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(4),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(4),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(mcontext.getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }


                        d.dismiss();
                    }

                });
            }
        });
        /*t1=(TextView)v.findViewById(R.id.item);
        c1=(CheckBox)v.findViewById(R.id.checkBox1);
        c2=(CheckBox)v.findViewById(R.id.checkBox2);
        c3=(CheckBox)v.findViewById(R.id.checkBox3);
        c4=(CheckBox)v.findViewById(R.id.checkBox4);
        c.moveToPosition(position);
        t1.setText(c.getString(0));
        if(c.getString(1)!=null)
            c1.setText(c.getString(1));
        if(c.getString(2)!=null)
            c2.setText(c.getString(2));
        if(c.getString(3)!=null)
            c3.setText(c.getString(3));
        if(c.getString(4)!=null)
            c4.setText(c.getString(4));

        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = gview.getPositionForView(buttonView);
                System.out.println("Pos ["+pos+"]");
                c.moveToPosition(pos);
                System.out.println(c.getString(1));
                if(isChecked){
                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    final EditText edittext = new EditText(mcontext);
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setMessage("Total quantity of " + c.getString(1));
                    alert.setTitle("Enter the Quantity");

                    alert.setView(edittext);

                    alert.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value

                            //OR
                            String quantity = edittext.getText().toString();
                            System.out.println("**** item name ****"+c.getString(1));
                            System.out.println("$$$$$$$$$ quantity $$$$$$$$ "+quantity);
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(1));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));
                                System.out.println("!!!!!!!!!!!!!!!!!"+total);
                                boolean insert=mydb.insert_temp_detail(c.getString(1),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(1),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();


                }
                else {
                    Toast.makeText(mcontext.getApplication(),"Select check box",Toast.LENGTH_SHORT).show();
                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = gview.getPositionForView(buttonView);
                System.out.println("Pos ["+pos+"]");
                c.moveToPosition(pos);
                System.out.println(c.getString(2));
                if(isChecked){
                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    final EditText edittext = new EditText(mcontext);
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setMessage("Total quantity of "+c.getString(2));
                    alert.setTitle("Enter the Quantity");

                    alert.setView(edittext);

                    alert.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String quantity = edittext.getText().toString();
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(2));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(2),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(2),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();


                }
                else {
                    Toast.makeText(mcontext.getApplication(),"Select check box",Toast.LENGTH_SHORT).show();
                }
            }
        });
        c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = gview.getPositionForView(buttonView);
                System.out.println("Pos ["+pos+"]");
                c.moveToPosition(pos);
                System.out.println(c.getString(3));
                if(isChecked){
                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    final EditText edittext = new EditText(mcontext);
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setMessage("Total quantity of "+c.getString(3));
                    alert.setTitle("Enter the Quantity");

                    alert.setView(edittext);

                    alert.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value

                            //OR
                            String quantity = edittext.getText().toString();
                            System.out.println("**** item name ****"+c.getString(1));
                            System.out.println("$$$$$$$$$ quantity $$$$$$$$ "+quantity);
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(3));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(3),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(3),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();


                }
                else {
                    Toast.makeText(mcontext.getApplication(),"Select check box",Toast.LENGTH_SHORT).show();
                }
            }
        });
        c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = gview.getPositionForView(buttonView);
                System.out.println("Pos [" + pos + "]");
                c.moveToPosition(pos);
                System.out.println(c.getString(4));
                if(c.getString(4)!=null){

                }
                if(isChecked){
                    AlertDialog.Builder alert = new AlertDialog.Builder(mcontext);
                    final EditText edittext = new EditText(mcontext);
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setMessage("Total quantity of "+c.getString(4));
                    alert.setTitle("Enter the Quantity");

                    alert.setView(edittext);

                    alert.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value

                            //OR
                            String quantity = edittext.getText().toString();
                            System.out.println("**** item name ****"+c.getString(4));
                            System.out.println("$$$$$$$$$ quantity $$$$$$$$ "+quantity);
                            try {
                                price_discount_cursor=mydb.get_price_discount(c.getString(1));
                                if(!price_discount_cursor.moveToFirst());
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                System.out.println("### price ###" + price_discount_cursor.getString(0) + "### discount ###" + price_discount_cursor.getString(1));
                                float price= Float.parseFloat(price_discount_cursor.getString(0));
                                float discount= Float.parseFloat(price_discount_cursor.getString(1));
                                float total=Integer.parseInt(quantity)*(price-((discount/100)*price));

                                boolean insert=mydb.insert_temp_detail(c.getString(4),price_discount_cursor.getString(0),quantity,String.valueOf(total));
                                if(insert) {
                                    Toast.makeText(mcontext.getApplicationContext(), "Item Ordered", Toast.LENGTH_SHORT).show();

                                    mcontext.total.setText(String.valueOf(mydb.get_total_price()));
                                    mcontext.refreshAdapter();
                                }
                            } catch (Exception e) {
                                Toast.makeText(mcontext.getApplicationContext(),"Insert price detail of "+c.getString(4),Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();


                }
                else {
                    Toast.makeText(mcontext.getApplication(),"Select check box",Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        return v;
    }


}
