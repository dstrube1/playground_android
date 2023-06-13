package com.twotothefifth.drawrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Time;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SinglePlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private Logger logger;
    private String[] shapes;

    public static final String SELECTED_ITEM_TAG = "selectedItem";
    private SwitchCompat switchView;
    public static final String HARD_MODE_TAG = "hardMode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);
        logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.log(Level.INFO, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.log(Level.INFO, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.log(Level.INFO, "onResume");
        dropdown = findViewById(R.id.spinner1);
        shapes = new String[]{"SQUARE", "TRIANGLE", "CIRCLE"};
        final String[] items = new String[]{"--SELECT--", "SQUARE", "TRIANGLE", "CIRCLE", "RANDOM"};

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            logger.log(Level.INFO, "Required permission found");
        } else {
            logger.log(Level.WARNING, "Required permission not found. Trying to get it...");

            ActivityCompat.requestPermissions(this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
        }

        switchView = null; //findViewById(R.id.switchView);


    }

    @Override
    public void onItemSelected(AdapterView<?> var1, View var2, int position, long var4) {
        final String selectedItem = (String) dropdown.getItemAtPosition(position);

        boolean launchMap = true;
        String shape = "";
        switch (selectedItem){
            case "RANDOM":
                shape = getRandom();
                break;
            case "SQUARE":
            case "TRIANGLE":
            case "CIRCLE":
                shape = selectedItem;
                break;
            default:
                //"--SELECT--" - do nothing
                launchMap = false;
                break;
        }
        if (launchMap && !shape.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra(SELECTED_ITEM_TAG, shape);
//            intent.putExtra(HARD_MODE_TAG, switchView.isSelected());
            startActivity(intent);
        }
    }

    private String getRandom(){
        final Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        final int index = random.nextInt(shapes.length);
        return shapes[index];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        logger.log(Level.INFO, "onNothingSelected");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.log(Level.INFO, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.log(Level.INFO, "onStop");
    }
}