package com.dstrube.simplenotification;

import android.app.Activity;
import android.os.Bundle;

public class NotificationReceiverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_alert);
    }
}
