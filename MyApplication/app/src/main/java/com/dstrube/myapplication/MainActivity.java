package com.dstrube.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.dstrube.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getName();

    // Used to load the 'myapplication' library on application startup.
    static {
        System.loadLibrary("myapplication");
        //or this?:
        //System.loadLibrary("native-lib");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
        //Button button = binding.button;
        /*
        OR:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
         */
    }
    public void handleClick(View view) {
        Log.d(TAG, "before binaryTreeSort");
        MySortTest.binaryTreeSort(0);
        Log.d(TAG, "after binaryTreeSort");
    }

    /**
     * A native method that is implemented by the 'myapplication' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

}