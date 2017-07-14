package com.example.yashashvi.emergency;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;
import static android.widget.Toast.LENGTH_SHORT;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;



        /*if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }*/



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
    public void sendMessage(View view)
    {
        /*Intent intent = new Intent(this, sendMessage.class);
        startActivity(intent);*/

        sendsms();

    }

    public void nearestPoliceStation(View view)
    {

        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse()

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+"&daddr=Police Station"));
        //Intent intent = new Intent(this, nearestPoliceStation.class);
        startActivity(intent);


    }

    public void call(View view)
    {
        Intent intent = new Intent(this, phoneCall.class);
        startActivity(intent);
    }

    public void userguide(View view)
    {
        Intent intent = new Intent(this, userGuide.class);
        startActivity(intent);
    }


    private TrackGPS gps;
    double longitude;
    double latitude;

    public void sendsms()
    {




        gps = new TrackGPS(MainActivity.this);


        if(gps.canGetLocation()){


            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {

            gps.showSettingsAlert();
        }


        List<Contact> contacts = db.getAllContacts();
        String num = "";
        int count = 1;
        for (Contact cn : contacts) {
            if(count == 1) {
                num += cn.getPhoneNumber();
                count++;
            }
            else
            {
                num += ";" + cn.getPhoneNumber();
            }
        }

        String msg = "Please Help!\n I think I am in danger! \n My location : " + latitude + "," + longitude;


        //Log.i("Send SMS", "");

        //Intent i = new Intent(this, sendSMS.class);
        //startActivity(i);
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String(num));
        //smsIntent.putExtra("address"  , new String ("9620265666"));
        smsIntent.putExtra("sms_body", msg);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    void requestLocationPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if ((permissionCheck == PackageManager.PERMISSION_GRANTED)) {

            Toast.makeText(this, "Location activated", LENGTH_SHORT).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 42);

        }
    }

    public void onLocationChanged(Location location)
    {
        latitude = (double)location.getLatitude();
        longitude = (double)location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    public void storeContact(View view)
    {
        Intent i = new Intent(this, storeContact.class);
        startActivity(i);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:07903365322"));

            int perm = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE);
            Toast.makeText(this, "Return value " + perm, Toast.LENGTH_SHORT);

        /*if (ContextCompat.checkSelfPermission(phoneCall.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission not granted",Toast.LENGTH_SHORT).show();
            return;
        }*/
            startActivity(callIntent);

        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }
        return true;
    }

    public void crime_rate(View view)
    {
        Intent i = new Intent(this, crimeRate.class);
        startActivity(i);
    }



}
