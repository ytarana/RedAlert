package com.example.yashashvi.emergency;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class storeContact extends Activity {

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
        setContentView(R.layout.activity_store_contact);

        db = new DatabaseHandler(this);

        //textView1 = (TextView) findViewById(R.id.textView1);
        //textView2 = (TextView) findViewById(R.id.textView2);
        //text = (TextView) findViewById(R.id.contacts);
        viewContacts();


    }

    public void pickContact(View v) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }

        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            db.addContact(new Contact(name, phoneNo));

            textView1.setText(name);
            textView2.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewContacts();

    }

    public void viewContacts() {

        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();
        String log = " ";
        id_index.removeAll(id_index);
        ArrayList view_contacts = new ArrayList();

        for (Contact cn : contacts) {
            log += "Id: " + cn.getID() + "\nName: " + cn.getName() + "\nPhone: " + cn.getPhoneNumber();

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

                String selected_contact [] = details.split("\n");
                String [] selected_id = selected_contact[0].split(": ");
                String [] selected_name = selected_contact[1].split(": ");
                String [] selected_num = selected_contact[2].split(": ");

                int del_id = Integer.parseInt(selected_id[1]);

                del_contact = new Contact();

                del_contact.setID(del_id);
                del_contact.setName(selected_name[1]);
                del_contact.setPhoneNumber(selected_num[1]);

                Toast.makeText(storeContact.this, selected_id[1] + "\n" + selected_name[1] + "\n"+ selected_num[1], Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(storeContact.this, view);


                //popupMenu.setOnMenuItemClickListener(this);
                //popupMenu.inflate(R.menu.popup_menu);
                //popupMenu.show();


                PopupMenu popup = new PopupMenu(storeContact.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        db.deleteContact(del_contact);
                        viewContacts();
                        Toast.makeText(storeContact.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();

            }
        });
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_contact:

                Toast.makeText(this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                return true;

        }
        return true;
    }



}
