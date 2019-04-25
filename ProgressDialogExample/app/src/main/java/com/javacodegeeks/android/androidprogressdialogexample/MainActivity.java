package com.javacodegeeks.android.androidprogressdialogexample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

//////////////////
/* 
* Progress Dialog
* uses progress bar and progress ring, along with threads
*/
//////////////////

public class MainActivity extends Activity {
	
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		updateBarHandler = new Handler();
	}

	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...",	"Downloading Image ...", true);
		ringProgressDialog.setCancelable(true);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Here you should write your time consuming task...
					// Let the progress ring for 10 seconds...
					Thread.sleep(10000);
				} catch (Exception e) {
					Log.e("launchRingDialog Thread", "Caught exception: " + e);
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}

	public void launchBarDialog(View view) {
		barProgressDialog = new ProgressDialog(MainActivity.this);
		
		barProgressDialog.setTitle("Downloading Image ...");
		barProgressDialog.setMessage("Download in progress ...");
		barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(20);
		barProgressDialog.show();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					// Here you should write your time consuming task...
					while (barProgressDialog.getProgress() <= barProgressDialog.getMax()) {
						
						Thread.sleep(1000);
						
						updateBarHandler.post(new Runnable() {

                            public void run() {

                            	barProgressDialog.incrementProgressBy(2);

                              }

                          });

						
						if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {
							
							barProgressDialog.dismiss();
						
						}
					}
				} catch (Exception e) {
					Log.e("launchBarDialog Thread", "Caught exception: " + e);
				}
			}
		}).start();
	}
}