package com.example.touristapplicationbyhighhopes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ActivityAdapter adapter;
    private List<ActivityItem> activityItems;
    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize views
        recyclerView = findViewById(R.id.activityRecyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        profileIcon = findViewById(R.id.profileIcon);

        // Set up RecyclerView
        activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("Kaamulan Park", R.drawable.kaamulan_park));
        activityItems.add(new ActivityItem("Monastery of Transfiguration", R.drawable.monastery_of_transfiguration));
        activityItems.add(new ActivityItem("Malaybalay city Plaza", R.drawable.malaybalay_city_plaza));
        activityItems.add(new ActivityItem("Mt Capistrano", R.drawable.mt_capistrano));
        activityItems.add(new ActivityItem("CapitolGround", R.drawable.pic));
        activityItems.add(new ActivityItem("Dalwangan Centennial marker", R.drawable.dal));
        activityItems.add(new ActivityItem("Ereccion Del Pueblo", R.drawable.ere));
        activityItems.add(new ActivityItem("Nasuli Spring", R.drawable.nas));
        activityItems.add(new ActivityItem("Two Trees", R.drawable.two));
        activityItems.add(new ActivityItem("Mt Kitanglad", R.drawable.mt));

        adapter = new ActivityAdapter(this, activityItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        // Set click listener for profile icon
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
