package com.example.comp3074project;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ViewRestaurant extends AppCompatActivity {
    int getId;
    private UserListDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_restaurant);
        dbHelper = new UserListDbHelper(this);
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
            getId = extras.getInt("id");
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
        btnDelete.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(ViewRestaurant.this)
                    .setTitle("Delete Restaurant")
                    .setMessage("Remove this from your list?")
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (extras != null){
                            int id = extras.getInt("id");
                            boolean deleted = dbHelper.deleteUserEntry(id);
                            if (deleted) {
                                android.widget.Toast.makeText(this, "Entry Deleted", android.widget.Toast.LENGTH_SHORT).show();
                                MainActivity.userList.removeIf(r -> r.getId() == id);
                                MainActivity.adapter.notifyDataSetChanged();
                                finish();
                            } else {
                                android.widget.Toast.makeText(this, "Failed to Delete or Not Found", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            android.widget.Toast.makeText(this, "Failed to Delete", android.widget.Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).show();
        });

        //Button to go to the update page
        Button btnUpdateDetails = findViewById(R.id.btnViewEdit);
        btnUpdateDetails.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRestaurant.this, EditRestaurant.class);
            assert extras != null;
            intent.putExtra("id", extras.getInt("id"));
            intent.putExtra("name", extras.getString("name"));
            intent.putExtra("address",extras.getString("address"));
            intent.putExtra("phone", extras.getString("phone"));
            intent.putExtra("rating", extras.getFloat("rating", 0f));
            intent.putExtra("tags", extras.getString("tags"));
            intent.putExtra("description", extras.getString("description"));
            startActivity(intent);
            finish();

        });

        //Button to go back to main screen
        Button btnGoBack = findViewById(R.id.btnViewGoBack);
        btnGoBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewRestaurant.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}