package com.dstrube.navigationspinnertest;

import android.widget.ImageView;

public class SpinnerItem {
	private final String title;
	private final ImageView image;

	public SpinnerItem() {
		title = "";
		image = null;
	}

	public SpinnerItem(String title, ImageView image) {
		this.title = title;
		this.image = image;
	}

}
