package com.dstrube.bardboard;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15;
    Button random, adArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        /*
        * https://developer.android.com/develop/ui/views/layout/recyclerview
        * https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
        * https://www.freshbytelabs.com/2018/12/android-recyclerview-with-cardview.html
        * https://www.geeksforgeeks.org/android-recyclerview/
        * */
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<ButtonRow> buttons = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            buttons.add(new ButtonRow(""+((i*3)+1), ""+((i*3)+2), ""+((i*3)+3), 330, 480));
        }
        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        MyAdapter adapter = new MyAdapter(buttons);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b10 = findViewById(R.id.b10);
        b11 = findViewById(R.id.b11);
        b12 = findViewById(R.id.b12);
        b13 = findViewById(R.id.b13);
        b14 = findViewById(R.id.b14);
        b15 = findViewById(R.id.b15);*/

        random = findViewById(R.id.random);
        adArea = findViewById(R.id.adArea);
//        final int width = 480;
        final int height = 330;

        /*b1.setWidth(width);
        b2.setWidth(width);
        b3.setWidth(width);
        b4.setWidth(width);
        b5.setWidth(width);
        b6.setWidth(width);
        b7.setWidth(width);
        b8.setWidth(width);
        b9.setWidth(width);
        b10.setWidth(width);
        b11.setWidth(width);
        b12.setWidth(width);
        b13.setWidth(width);
        b14.setWidth(width);
        b15.setWidth(width);

        b1.setHeight(height);
        b2.setHeight(height);
        b3.setHeight(height);
        b4.setHeight(height);
        b5.setHeight(height);
        b6.setHeight(height);
        b7.setHeight(height);
        b8.setHeight(height);
        b9.setHeight(height);
        b10.setHeight(height);
        b11.setHeight(height);
        b12.setHeight(height);
        b13.setHeight(height);
        b14.setHeight(height);
        b15.setHeight(height);*/

        random.setHeight(height);
        adArea.setHeight(height);
    }
}