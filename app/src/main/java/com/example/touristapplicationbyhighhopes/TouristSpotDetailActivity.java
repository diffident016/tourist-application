package com.example.touristapplicationbyhighhopes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TouristSpotDetailActivity extends AppCompatActivity {

    private RecyclerView rvComments;
    private CommentAdapter commentAdapter;
    private List<CommentItem> commentItems;
    private EditText etUserComment;
    private String spotName;
    private int imageResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_spot_detail);

        // Get data from intent
        Intent intent = getIntent();
        spotName = intent.getStringExtra("spotName");
        imageResourceId = intent.getIntExtra("imageResourceId", -1);

        // Initialize views
        ImageView ivTouristSpotImage = findViewById(R.id.ivTouristSpotImage);
        TextView tvTouristSpotDescription = findViewById(R.id.tvTouristSpotDescription);
        etUserComment = findViewById(R.id.etUserComment);
        Button btnComment = findViewById(R.id.btnComment);
        TextView tvCommentsTitle = findViewById(R.id.tvCommentsTitle);
        rvComments = findViewById(R.id.rvComments);
        Button btnGuideMe = findViewById(R.id.btnGuideMe);

        // Set data to views
        ivTouristSpotImage.setImageResource(imageResourceId);
        tvTouristSpotDescription.setText(getDescriptionForSpot(spotName));

        // Initialize comments list and adapter
        commentItems = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentItems);
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        // Set click listener for comment button
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserComment();
            }
        });

        // Set click listener for guide me button
        btnGuideMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guideIntent = new Intent(TouristSpotDetailActivity.this, TouristSpotMapActivity.class);
                guideIntent.putExtra("spotName", spotName);
                startActivity(guideIntent);
            }
        });
    }

    private String getDescriptionForSpot(String spotName) {
        // Implement this method to return the description based on the spot name
        switch (spotName) {
            case "Kaamulan Park":
                return "Kaamulan Park is a beautiful park located in Malaybalay City. It is known for its lush greenery and serene environment.";
            case "Monastery of Transfiguration":
                return "The Monastery of Transfiguration is a tranquil place of worship and reflection, located in the hills of Malaybalay.";
            case "Malaybalay city Plaza":
                return "Malaybalay City Plaza is a central public space where people gather for various events and activities.";
            case "Mt Capistrano":
                return "Mt Capistrano offers a challenging hike with rewarding views at the summit, perfect for adventure seekers.";
            case "CapitolGround":
                return "Capitol Ground is likely a reference to the Malaybalay City Hall and its surrounding grounds, which might offer a glimpse into the city's governance and history.";
            case "Dalwangan Centennial marker":
                return "The Dalwangan Centennial marker commemorates a significant event or milestone in the city's history, possibly its centennial celebration.";
            case "Ereccion Del Pueblo":
                return "Ereccion Del Pueblo is a historic marker that translates to Erection of the Town in English, which might signify the founding or establishment of Malaybalay as a town.";
            case "Nasuli Spring":
                return "Nasuli Spring is a natural attraction that offers a refreshing experience, possibly with crystal-clear waters and a serene atmosphere.";
            case "Two Trees":
                return "Two Trees Peak is a popular hiking spot that offers a short and affordable hike, taking around 1 hour and 30 minutes to reach the peak. From the top, you can see the mountains and the city of Malaybalay, and if you're lucky, a sea of clouds at dawn. The hike is free, and you only need to spend on water and food";
            case "Mt Kitanglad":
                return "Mt. Kitanglad is a majestic mountain that offers breathtaking views and possibly trekking or hiking opportunities for adventure-seekers.";

                default:
                return "Description not available.";
        }
    }

    private void addUserComment() {
        // Get the user's comment
        String userComment = etUserComment.getText().toString().trim();

        // Validate comment
        if (!userComment.isEmpty()) {
            // Add the comment to the list
            commentItems.add(new CommentItem("Anonymous", userComment));
            commentAdapter.notifyDataSetChanged();

            // Clear the input field
            etUserComment.setText("");

            // Show comments title if it's not visible
            if (commentItems.size() > 0) {
                findViewById(R.id.tvCommentsTitle).setVisibility(View.VISIBLE);
            }
        } else {
            // Optionally show a message if the comment is empty
            // Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
        }
    }
}
