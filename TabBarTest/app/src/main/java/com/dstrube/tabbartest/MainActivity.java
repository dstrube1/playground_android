package com.dstrube.tabbartest;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.dstrube.tabbartest.R.*;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private final Logger logger = Logger.getLogger(MainActivity.class.getName());

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Neither adding a default clause
            // nor adding a static import for com.dstrube.tabbartest.R.*
            //got rid of this warning:
            ///Resource IDs will be non-final in Android Gradle Plugin version 7.0, avoid using them in switch case statements
            //Great, thanks for that -_-
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(string.title_home);
                    return true;
                case id.navigation_dashboard:
                    mTextMessage.setText(string.title_dashboard);
                    return true;
                case id.navigation_notifications:
                    mTextMessage.setText(string.title_notifications);
                    return true;
                default:
                    logger.log(Level.WARNING, "Unexpected value: " + item.getItemId());
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        mTextMessage = findViewById(id.message);
        BottomNavigationView navigation = findViewById(id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
