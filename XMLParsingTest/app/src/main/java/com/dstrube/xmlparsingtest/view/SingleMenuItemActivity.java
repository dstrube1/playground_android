package com.dstrube.xmlparsingtest.view;

import com.dstrube.xmlparsingtest.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.single_list_item);

		// getting intent data
		Intent in = getIntent();

		// Get XML values from previous intent
		// XML node keys
		String KEY_NAME = "name";
		String name = in.getStringExtra(KEY_NAME);
		String KEY_COST = "cost";
		String cost = in.getStringExtra(KEY_COST);
	String KEY_DESC = "description";
	String description = in.getStringExtra(KEY_DESC);

		// Displaying all values on the screen
		TextView lblName = (TextView) findViewById(R.id.name_label);
		TextView lblCost = (TextView) findViewById(R.id.cost_label);
		TextView lblDesc = (TextView) findViewById(R.id.description_label);

		lblName.setText(name);
		lblCost.setText(cost);
		lblDesc.setText(description);
	}
}
