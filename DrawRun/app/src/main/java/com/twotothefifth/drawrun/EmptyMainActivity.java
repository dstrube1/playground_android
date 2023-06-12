package com.twotothefifth.drawrun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EmptyMainActivity extends AppCompatActivity {

    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_main);
        logger = Logger.getLogger(this.getClass().getName());//getResources().getString(R.string.app_name));
        logger.log(Level.INFO, "onCreate");
    }

    public void multi_player_click(View view) {
        Toast.makeText(getApplicationContext(), "Coming soon", Toast.LENGTH_LONG).show();
    }

    public void single_player_click(View view) {
        Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
        startActivity(intent);
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

    @Override
    protected void onDestroy() {
        //Can't log this, probably can't catch it either
        Logger.getGlobal().log(Level.SEVERE, "onDestroy");
        super.onDestroy();
    }
}