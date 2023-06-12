package com.twotothefifth.drawrun;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twotothefifth.drawrun.databinding.ActivityMapsBinding;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger = Logger.getLogger(getResources().getString(R.string.app_name));
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        String shape = intent.getStringExtra(SinglePlayerActivity.SELECTED_ITEM_TAG);
        Toast.makeText(
                getApplicationContext(),
                "Drawing : " + shape, Toast.LENGTH_SHORT).show();
         /*
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl;
        if (pm != null) {
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
            //Acquire the lock
            wl.acquire(10000);
        }else {
            Log.e(TAG, "Null PowerManager");
            return;
        }
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.log(Level.INFO, "In onResume: ...");

        logger.log(Level.INFO, "binding is " + (binding == null ? "null" : "not null"));
        logger.log(Level.INFO, "mMap is " + (mMap == null ? "null" : "not null"));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng centennialOlympicPark = new LatLng(33.7612882, -84.3930867);
//
//        mMap.addMarker(new MarkerOptions().position(centennialOlympicPark).title("Marker in Centennial Olympic Park"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(centennialOlympicPark));

        //Checking for required permission before initializing GPSTracker
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            logger.log(Level.INFO, "Required permission found");
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            logger.log(Level.WARNING, "Required permission not found. Trying to get it...");

            Toast.makeText(this, "Please close and reopen the app after granting this permission.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
            return;
        }

        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        //https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory#newLatLngZoom(com.google.android.gms.maps.model.LatLng,%20float)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(gpsTracker.getLocation().getLatitude(), gpsTracker.getLocation().getLongitude()),
                centennialOlympicPark,
                19.0f));

        logger.log(Level.WARNING, "address: " + gpsTracker.getAddress());
    }

    public void begin_click(View view) {
        Toast.makeText(this, "Beginning...", Toast.LENGTH_LONG).show();
    }
}