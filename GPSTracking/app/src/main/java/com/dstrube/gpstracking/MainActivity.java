package com.dstrube.gpstracking;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logger = Logger.getLogger(getResources().getString(R.string.app_name));
//        Logger.getGlobal().log(Level.SEVERE, "onCreate");
        logger.log(Level.INFO, "onCreate");
        /*
        what doesn't work:
        getAnonymous
        Level.FINE
        Level.ALL

        what does work:
        getGlobal
        Level.INFO
        Level.WARNING
        Level.SEVERE = Error
        */
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.log(Level.SEVERE, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.log(Level.SEVERE, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.log(Level.SEVERE, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.log(Level.SEVERE, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.log(Level.SEVERE, "onStop");
    }

    @Override
    protected void onDestroy() {
        //Can't log this, probably can't catch it either
        Logger.getGlobal().log(Level.SEVERE, "onDestroy");
        super.onDestroy();
    }
}
