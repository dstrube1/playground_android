package com.dstrube.gpstracking;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * This class is a bit misnamed because it can function without GPS, purely via
 * cell towers or WiFi
 * 
 * @author david.strube
 * 
 */
public class GPSTracker extends Service implements LocationListener {

	private final Logger logger;

	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	private boolean canGetLocation = false;

	Location location; // location
	private double latitude; // latitude
	private double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60;
	// 1 minute = 60,000 milliseconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	/**
	 * Constructor
	 * 
	 * @param context a context
	 */
	public GPSTracker(Context context) {
		logger = Logger.getLogger(this.getClass().getName());
		this.mContext = context;
		Location location = getLocation();
		logger.log(Level.INFO, "location is " + (location == null ? "null" : "not null") );
		if (location != null && canGetLocation()){
			logger.log(Level.INFO, "latitude: " + getLatitude() + "; longitude: " + getLongitude());
			logger.log(Level.INFO, "address: " + getAddress());
		}
	}

	/**
	 * This is the meat of the class, the answer to your prayers, your dreams
	 * come true.
	 * 
	 * @return a Location
	 */
	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled;
				// no need to set canGetLocation = false;
				// that is its default, and nothing else (besides the below
				// code) will change that after creating this object -
				// instantiating this class - since canGetLocation is now
				// explicitly private.
				logger.log(Level.WARNING, "isGPSEnabled and isNetworkEnabled are both false" );
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
				    final String permission = "android.permission.ACCESS_COARSE_LOCATION";
					//This throws a NullPointerException, but is apparently necessary for the following line:
					try {
						enforcePermission(permission, 0, 0, "Missing permission: " + permission);
					} catch (NullPointerException npe){
						Log.e(this.getClass().getName(),"NullPointerException from enforcePermission");//, npe);
					}
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d(this.getClass().getName(), "locationManager has requested LocationUpdates");
					// why / how / when would this be null?
					// does requestLocationUpdates possibly set it to null? :
					if (locationManager != null) {
						Log.d(this.getClass().getName(), "locationManager is not null; setting location with getLastKnownLocation");
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							Log.d(this.getClass().getName(), "getLastKnownLocation has latitude " + latitude + " and longitude " + longitude);
						}
					}
				}

				// Note: another if, not else, because we want to keep both
				// options optional

				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null && locationManager != null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d(this.getClass().getName(), "GPS Enabled");
						// why / how / when would this be null?
						// does requestLocationUpdates possibly set it to null?
						// :
						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								Log.d(this.getClass().getName(), "getLastKnownLocation has latitude " + latitude + " and longitude " + longitude);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app, but will not disable GPS
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	/**
	 * Unused
	 */
	@Override
	public void onLocationChanged(Location location) {
		// Geocoder geocoder;
		// List<Address> addresses;
		// geocoder = new Geocoder(this, Locale.getDefault());
		// try {
		// addresses = geocoder.getFromLocation(location.getLatitude(),
		// location.getLongitude(), 1);

		// String address = addresses.get(0).getAddressLine(0);
		// String city = addresses.get(0).getAddressLine(1);
		// String country = addresses.get(0).getAddressLine(2);

		/*
		 * Toast.makeText( this.mContext, "Your Location is - \nLat: " +
		 * latitude + "\nLong: " + longitude + "\nAddress: " + address + ; *
		 * "\n" + city + "\n"+ country, Toast.LENGTH_LONG) .show();
		 */
		// Toast.makeText(
		// this.mContext,
		// "Your Location is - \nLat: " + latitude + "\nLong: "
		// + longitude, Toast.LENGTH_LONG).show();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		logger.log(Level.INFO, "onLocationChanged");
	}

	/**
	 * Unused
	 */
	@Override
	public void onProviderDisabled(String provider) {
		logger.log(Level.WARNING, "onProviderDisabled: " + provider);
	}

	/**
	 * Unused
	 */
	@Override
	public void onProviderEnabled(String provider) {
		logger.log(Level.INFO, "onProviderEnabled: " + provider);
	}

	/**
	 * Unused
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		logger.log(Level.INFO, "onStatusChanged: " + provider + "; status: " + status);
		String lastKey = null;
		for (String key : extras.keySet()){
			logger.log(Level.INFO, "Bundle key: " + key);
			lastKey = key;
		}
		int satellites = extras.getInt(lastKey);
		logger.log(Level.INFO, "satellites: " + satellites);
	}

	/**
	 * Unused
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	public String getAddress() {
		Geocoder geocoder;
		List<Address> addresses;

		// Context ctx = null;
		// try {
		// // getBaseContext() == null
		// ctx = getApplicationContext(); //this one throws an exception just
		// trying to instantiate it
		// } catch (Exception e) {
		// e.printStackTrace(); // NullPointer
		// ctx = this;
		// }

		String returnAddress = "None";
		Locale locale = Locale.getDefault();

		try {
			geocoder = new Geocoder(mContext, locale);
			if (Geocoder.isPresent()) {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
				if (addresses.size() > 0) {
					String address = addresses.get(0).getAddressLine(0);
					String city = addresses.get(0).getAddressLine(1);
					String country = addresses.get(0).getAddressLine(2);
					returnAddress = address + ", " + city + ", " + country;
				}else{
					Toast.makeText(mContext, "No address found.", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(mContext, "Geocoder is not present.", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnAddress;
	}

}
