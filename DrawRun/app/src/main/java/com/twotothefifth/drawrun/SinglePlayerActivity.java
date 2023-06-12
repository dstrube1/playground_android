package com.twotothefifth.drawrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SinglePlayerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private Logger logger;

    public static final String SELECTED_ITEM_TAG = "selectedItem";

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
        String[] items = new String[]{"--SELECT--", "SQUARE", "CIRCLE", "TRIANGLE", "RANDOM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> var1, View var2, int position, long var4) {
        String selectedItem = (String) dropdown.getItemAtPosition(position);

        boolean launchMap = false;
        switch (selectedItem){
            case "RANDOM":
                Toast.makeText(
                        getApplicationContext(),
                        "TODO", Toast.LENGTH_SHORT).show();
            case "SQUARE":
            case "CIRCLE":
            case "TRIANGLE":
                launchMap = true;
                break;
            default:
                //"--SELECT--" - do nothing
                break;
        }
        if (launchMap) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra(SELECTED_ITEM_TAG, selectedItem);
            startActivity(intent);
        }
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