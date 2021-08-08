package com.dstrube.helloaumazondible;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.GridView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //singleton initialization of my data source
        //we don't want more than one of these running around
        MyDataSource dataSource = MyDataSource.getInstance();
        //get the data
        dataSource.getMoreData();

        //the adapter used for the gridview
        MyAdapter adapter = new MyAdapter(this, dataSource, getWindowXSize());
        //the gridview
        GridView gridView = findViewById(R.id.gridView);
        gridView.setOnScrollListener(adapter);
        gridView.setAdapter(adapter);
    }

    /**
     * Called by the adapter initializer, this returns the x size of the screen
     * @return x size of the window
     */
    public int getWindowXSize(){
//        Display display = getWindowManager().getDefaultDisplay();
        //^Deprecated; alert says to use this:
        //Context.getDisplay();
        // but it's not that easy
        Context context = getApplicationContext();
        Display display = context.getDisplay();
        Point size = new Point();
//        display.getSize(size);
        //^Deprecated
        display.getRealSize(size);
        return size.x;
    }
}
