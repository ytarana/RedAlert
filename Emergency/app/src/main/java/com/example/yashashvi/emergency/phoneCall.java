package com.example.yashashvi.emergency;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.content.pm.PackageManager;

import android.Manifest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class phoneCall extends AppCompatActivity {





    DatabaseHandler db;
    TextView text;
    ListView lv;
    Contact del_contact;

    ArrayList id_index = new ArrayList();

    private static final int RESULT_PICK_CONTACT = 85500;

    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);
        db = new DatabaseHandler(this);
        viewContacts();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_phone_call, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void contact1(View view)
    {

        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:09423577023"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }



    public void contact2(View view)
    {
        /*Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:9663602784"));
        startActivity(callIntent);

        /*if (ActivityCompat.checkSelfPermission(phoneCall.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(callIntent);*/


        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        callIntent.setData(Uri.parse("tel:9663602784"));
        startActivity(callIntent);
    }

    public void contact3(View view)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:07903365322"));

        int perm = ContextCompat.checkSelfPermission(phoneCall.this,Manifest.permission.CALL_PHONE );
        Toast.makeText(this,"Return value "+perm, Toast.LENGTH_SHORT);

        /*if (ContextCompat.checkSelfPermission(phoneCall.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
            return;
        }*/
        startActivity(callIntent);
    }




    public void viewContacts() {

        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        String log = " ";
        id_index.removeAll(id_index);
        ArrayList view_contacts = new ArrayList();

        for (Contact cn : contacts) {
            log += "Name: " + cn.getName() + "\nPhone: " + cn.getPhoneNumber();

            view_contacts.add(log);

            log = "";
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        //text.setText(log);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, view_contacts);

        lv = (ListView) findViewById(R.id.contacts_list);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Object o = lv.getItemAtPosition(position);
                String details = o.toString();

                String selected_contact[] = details.split("\n");
                //String[] selected_id = selected_contact[0].split(": ");
                String[] selected_name = selected_contact[0].split(": ");
                String[] selected_num = selected_contact[1].split(": ");




                String num = "tel:" + selected_num[1];

                //Toast.makeText(phoneCall.this, num, Toast.LENGTH_SHORT).show();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(num));

                int perm = ContextCompat.checkSelfPermission(phoneCall.this, Manifest.permission.CALL_PHONE);
                //Toast.makeText(phoneCall.this, "Return value " + perm, Toast.LENGTH_SHORT);

        /*if (ContextCompat.checkSelfPermission(phoneCall.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
            return;
        }*/
                startActivity(callIntent);


                //Toast.makeText(storeContact.this, selected_id[1] + "\n" + selected_name[1] + "\n"+ selected_num[1], Toast.LENGTH_SHORT).show();


            }

        });
    }





}
