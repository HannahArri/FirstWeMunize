package com.example.hannah.wemunize;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class ChildRegiteration extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    private static final  String TAG = "ChildRegiteration";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    int Place_Request_code = 1;
    private long UPDATE_INTERVAL = 2*1000; /* 10 secs*/
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_registeration);
        getSupportActionBar().setHomeButtonEnabled(true);

        //instantiate google api client builder and Location Manager
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addOnConnectionFailedListener(this)
        .addConnectionCallbacks(this)
        .addApi(LocationServices.API)
        .build();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Bundle bundle  = getdata();
        ChildDetails childDetails = new ChildDetails();

        if(bundle != null){
            childDetails.setArguments(bundle);
        }
        else{
            Toast.makeText(this, "Unable to lock in gps coordinates", Toast.LENGTH_SHORT).show();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content, childDetails, childDetails.getTag())
                .disallowAddToBackStack()
                .commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home :
                Intent homeIntent = new Intent(this, OptionsPage.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onConnected(Bundle bundle){
        //check if permissions have been granted
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // TODO: Consider calling
            // ActivityCompat #requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequesrPermissionResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            //  to handle the case where the user grants the permissions. see the documentation
            // for ActivityCompat #requestPermissions for more details
            return;
        }
        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null){
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLatitude());
        }
        else{
            Toast.makeText(this, "Locaton not detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i){
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.i(TAG, "Connection failed. Error:" + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates(){
        // create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if(ActivityCompat.checkSelfPermission(this,  android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d("reque", "----->>>>>");
    }

    //updtes locaion if location has changed and sets new values
    @Override
    public void onLocationChanged(Location location){
        String [] longlat = {String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())};
    }


    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    //shows alert to ask user to enable location
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    //checks if location is enabled on device
    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    public Bundle getdata(){
        Bundle bundle = new Bundle();
        bundle.putString("Latitude", latitude);
        bundle.putString("Longitude", longitude);
        return bundle;
    }
}
