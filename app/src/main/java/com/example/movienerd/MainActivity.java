package com.example.movienerd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.movienerd.RecyclerView.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setRecyclerView(this);
    }

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.recycler_view);

        //set GridLayoutManager in recyclerView and show items in grid with two columns
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        list.add("ic_android_black_24");
        adapter = new CardAdapter(list, activity);
        recyclerView.setAdapter(adapter);
    }
}