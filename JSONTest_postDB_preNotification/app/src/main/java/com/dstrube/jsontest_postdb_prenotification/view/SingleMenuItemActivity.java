package com.dstrube.jsontest_postdb_prenotification.view;

import com.dstrube.jsontest_postdb_prenotification.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

//This class represents the activity that is displayed when clicking on a single item from the list
public class SingleMenuItemActivity extends Activity {

	/**
	 * Default beginner
	 * Set things up
	 * Make it all do its thing
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item); //single_list_item here, list_item in MainActivity
        
        // getting intent data
        Intent intent = getIntent();
        
        // Get JSON values from previous intent
		// JSON node keys
		String TAG_FIRSTNAME = "firstName";
		String firstName = intent.getStringExtra(TAG_FIRSTNAME);
		String TAG_LASTNAME = "lastName";
		String lastName = intent.getStringExtra(TAG_LASTNAME);
        String name = firstName + " " + lastName;
		String TAG_EMAIL = "email";
		String email = intent.getStringExtra(TAG_EMAIL);
		String TAG_TITLE = "title";
		String title = intent.getStringExtra(TAG_TITLE);
        
        // Displaying all values on the screen
        TextView lblName = findViewById(R.id.name_label);
        TextView lblEmail = findViewById(R.id.email_label);
        TextView lblTitle = findViewById(R.id.title_label);
        
        lblName.setText(name);
        lblEmail.setText(email);
        lblTitle.setText(title);

	}

}
