package com.dstrube.jsontest_postdb_prenotification.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class is not yet used, but maybe will someday
 * @author david.strube
 *
 */
public class Employee implements Parcelable {

	private static final String CREATOR = "";

	private final String name;
	private final String email;
	private final String title;
	private final String thumbUrl;
	
	public Employee(){
		name="";
		email = "";
		title = "";
		thumbUrl = "";
	}
	public Employee(String name, String email, String title, String thumbUrl){
		this.name=name;
		this.email = email;
		this.title = title;
		this.thumbUrl = thumbUrl;
	}
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public String getTitle(){
		return title;
	}
	public String getThumbUrl(){
		return thumbUrl;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
