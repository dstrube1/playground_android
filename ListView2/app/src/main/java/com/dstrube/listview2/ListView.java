package com.dstrube.listview2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListView extends RecyclerView {
    //ListActivity is deprecated as of API 30:
    //https://developer.android.com/reference/android/app/ListActivity
    //Use RecyclerView:
    //https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.html
    // or this:
    //https://developer.android.com/reference/androidx/fragment/app/ListFragment.html

    public ListView(@NonNull Context context) {
        super(context);
    }

    //TODO: the rest
}
