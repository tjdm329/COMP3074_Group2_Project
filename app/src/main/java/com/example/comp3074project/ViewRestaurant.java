package com.example.comp3074project;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewRestaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewRestaurant), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // set the elements from activity layout
        TextView tvName = findViewById(R.id.tvViewName);
        TextView tvAddress = findViewById(R.id.tvViewAddress);
        TextView tvPhone = findViewById(R.id.tvViewPhone);
        TextView tvTags = findViewById(R.id.tvViewTags);
        TextView tvDesc = findViewById(R.id.tvViewDesc);
        RatingBar viewRate = findViewById(R.id.tvViewRating);

        // get data from the row selected
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvName.setText(extras.getString("name"));
            tvAddress.setText(extras.getString("address"));
            tvPhone.setText(extras.getString("phone"));
            tvTags.setText(extras.getString("tags"));
            tvDesc.setText(extras.getString("description"));

            float rating = extras.getFloat("rating", 0f);
            viewRate.setRating(rating);
        }



        // --- NAVIGATION BUTTONS
        // Button to delete from database
        Button btnDelete = findViewById(R.id.btnViewDelete);


        //Button to go to the update page
        Button btnUpdateDetails = findViewById(R.id.btnViewEdit);
        btnUpdateDetails.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRestaurant.this, AddRestaurant.class);
            startActivity(intent);
            finish();
        });

        //Button to go back to main screen
        Button btnGoToSearch = findViewById(R.id.btnViewGoBack);
        btnGoToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRestaurant.this, MainActivity.class);
            startActivity(intent);
            finish();
        });



    }
}