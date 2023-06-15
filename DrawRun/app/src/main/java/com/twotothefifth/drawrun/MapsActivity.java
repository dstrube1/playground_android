package com.twotothefifth.drawrun;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
//import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.twotothefifth.drawrun.databinding.ActivityMapsBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Logger logger;
    private String shape;
    private GPSTracker gpsTracker;
    private LatLng center;
    private Map<LatLng, Long> coords;
    private Context context;
    private Handler handler;
    private final int interval = 1000; // 1 second
    private boolean isHardMode = false;
    private boolean isHybridMap = true;
    private Button beginButton;
    private Button endButton;
    private int lineColor;
    private double latLngDelta;
    private int radius;
    private final int polylineWidth = 10;

    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger = Logger.getLogger(this.getClass().getName());
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        final Intent intent = getIntent();

        shape = intent.getStringExtra(SinglePlayerActivity.SELECTED_ITEM_TAG);
        isHardMode = intent.getBooleanExtra(SinglePlayerActivity.HARD_MODE_TAG, false);
        isHybridMap = intent.getBooleanExtra(SinglePlayerActivity.HYBRID_MAP_TAG, false);

        Toast.makeText(
                getApplicationContext(),
                "Drawing : " + shape, Toast.LENGTH_SHORT).show();

//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl;
//        if (pm != null) {
//            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getResources().getString(R.string.app_name));
//            //Acquire the lock
//            wl.acquire(5*60*1000L /*5 minutes*/);
//        }else {
//            logger.log(Level.SEVERE,"Null PowerManager");
//            Toast.makeText(getApplicationContext(), "Warning: unable to achieve wakelock.", Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.log(Level.INFO, "In onResume: ...");

        beginButton = findViewById(R.id.beginButton);
        endButton = findViewById(R.id.endButton);
        endButton.setEnabled(false);

        context = this;

        lineColor = ContextCompat.getColor(context, R.color.white);

        if (isHardMode) {
            latLngDelta = 0.00025;
            radius = 25;
        } else {
            latLngDelta = 0.0001;
            radius = 10;
        }

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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Checking for required permission before initializing GPSTracker
        //Since we also checked for this in SinglePlayerActivity, this should never be a problem
        // (unless user denied permission on previous screen, thinking that'd be okay).
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

            ActivityCompat.requestPermissions(this, new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }

        gpsTracker = new GPSTracker(getApplicationContext());

        center = new LatLng(33.761291, -84.393233);

        if (isHybridMap) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        //https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory#newLatLngZoom(com.google.android.gms.maps.model.LatLng,%20float)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(gpsTracker.getLocation().getLatitude(), gpsTracker.getLocation().getLongitude()),
                center,
                21.0f));

        logger.log(Level.INFO, "address: " + gpsTracker.getAddress());

        switch (shape) {
            case "SQUARE":
                drawSquare();
                break;
            case "TRIANGLE":
                drawTriangle();
                break;
            case "CIRCLE":
                drawCircle();
                break;
            default:
                break;
        }
    }

    private void drawSquare() {
        final LatLng pointA = new LatLng(center.latitude + latLngDelta, center.longitude + latLngDelta);
        final LatLng pointB = new LatLng(center.latitude + latLngDelta, center.longitude - latLngDelta);
        final LatLng pointC = new LatLng(center.latitude - latLngDelta, center.longitude - latLngDelta);
        final LatLng pointD = new LatLng(center.latitude - latLngDelta, center.longitude + latLngDelta);

        /*final Polygon square = */mMap.addPolygon(new PolygonOptions()
                .clickable(false)
                .add(pointA, pointB, pointC, pointD));
    }

    private void drawTriangle() {
        final LatLng pointA = new LatLng(center.latitude + latLngDelta, center.longitude);
        final LatLng pointB = new LatLng(center.latitude - latLngDelta, center.longitude - latLngDelta);
        final LatLng pointC = new LatLng(center.latitude - latLngDelta, center.longitude + latLngDelta);

        /*final Polygon triangle = */mMap.addPolygon(new PolygonOptions()
                .clickable(false)
                .add(pointA, pointB, pointC));
    }

    private void drawCircle() {
        //https://developers.google.com/android/reference/com/google/android/gms/maps/model/Circle
        /*final Circle circle = */mMap.addCircle(new CircleOptions()
                .center(center)
                .radius(radius));
    }

    public void begin_click(View view) {
        Toast.makeText(this, "Beginning " + shape + "...", Toast.LENGTH_LONG).show();
        coords = new HashMap<>();

        handler = new Handler();

        //Every 5 seconds, add a LatLng to the map
        handler.postDelayed(runnable, interval);
        beginButton.setEnabled(false);
        endButton.setEnabled(true);
        if (polyline != null) {
            polyline.setVisible(false);
        }
    }

    private int noMoveCount = 0;

    private final Runnable runnable = new Runnable() {
        public void run() {
            if (gpsTracker == null){
                Toast.makeText(context, "Error - null gpsTracker", Toast.LENGTH_LONG).show();
                return;
            }
            if (gpsTracker.getLocation() == null){
                Toast.makeText(context, "Error - null gpsTracker.getLocation()", Toast.LENGTH_LONG).show();
                return;
            }
            final LatLng inputKey = new LatLng(gpsTracker.getLocation().getLatitude(), gpsTracker.getLocation().getLongitude());
            if (!coords.containsKey(inputKey)) {
                logger.log(Level.INFO, "Still playing...");
//                Toast.makeText(context, "Still playing...", Toast.LENGTH_SHORT).show();
                coords.put(inputKey, System.currentTimeMillis());
                noMoveCount = 0;
                handler.postDelayed(this, interval);
            } else {
                noMoveCount++;
                if (noMoveCount >= 5) {
                    //If user hasn't moved in 5 seconds, assume they're done
                    Toast.makeText(context, "No movement in 5 seconds. Finishing play...", Toast.LENGTH_SHORT).show();
                    polyline = mMap.addPolyline(new PolylineOptions()
                            .color(lineColor)
                            .width(polylineWidth)
                            .clickable(false)
                            .addAll(coords.keySet()));
                    polyline.setVisible(true);
                    logger.log(Level.INFO, "Drawn from timeout");
                } else {
                    handler.postDelayed(this, interval);
                }
            }
        }
    };

    public void start_over_click(View view) {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        beginButton.setEnabled(true);
        endButton.setEnabled(false);
        mMap.clear();
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
    }

    public void end_click(View view) {
        beginButton.setEnabled(true);
        endButton.setEnabled(false);
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        if (!coords.isEmpty()) {
            polyline = mMap.addPolyline(new PolylineOptions()
                    .color(lineColor)
                    .width(polylineWidth)
                    .clickable(false)
                    .addAll(coords.keySet()));
            polyline.setVisible(true);
            logger.log(Level.INFO, "Drawn from End button");
        }
    }
}