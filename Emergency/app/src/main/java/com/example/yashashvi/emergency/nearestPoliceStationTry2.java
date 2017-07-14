package com.example.yashashvi.emergency;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import static android.widget.Toast.LENGTH_SHORT;

public class nearestPoliceStationTry2 extends AppCompatActivity {


    private static final String GOOGLE_API_KEY = "AIzaSyBN9x6dsRwiplYBYc3dfzfaCLrkNqjNlok";
    GoogleMap googleMap;
    EditText placeText;
    double latitude = 0;
    double longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_police_station_try2);

        googleMap.setMyLocationEnabled(true);

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        //googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        requestLocationPermission();
        //Location location;
        try {
            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, (LocationListener) this);

        } catch (SecurityException e) {
            Toast.makeText(this, "Problem with GPS", LENGTH_SHORT).show(); // lets the user know there is a problem with the gps
        }


        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=Police Station"));
        startActivity(intent);


    }


    void requestLocationPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(nearestPoliceStationTry2.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if ((permissionCheck == PackageManager.PERMISSION_GRANTED)) {

            Toast.makeText(this, "Location activated", LENGTH_SHORT).show();

        } else {

            ActivityCompat.requestPermissions(nearestPoliceStationTry2.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 42);

        }


    }




    //@Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
}
