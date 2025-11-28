package com.example.comp3074project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserListDbHelper dbHelper;
    private List<Restaurant> userList;
    private ArrayAdapter<Restaurant> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new UserListDbHelper(this);
        listView = findViewById(R.id.lvUserList);

        // get data from the db
        userList = dbHelper.getUserList();

        // set adapter
        adapter = new ArrayAdapter<Restaurant>(this, R.layout.mylist_layout, R.id.restName, userList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Restaurant restaurant = getItem(position);

                // sets the name corresponding to the user's input
                TextView name = view.findViewById(R.id.restName);
                name.setText(restaurant.getName());

                // sets the rating corresponding to the user's input
                RatingBar stars = view.findViewById(R.id.rating);
                stars.setRating(restaurant.getRating());

                return view;
            }
        };
        listView.setAdapter(adapter);

        // when user clicks on a row in their list, go to restaurant details page
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Restaurant r = userList.get(position);

            Intent intent = new Intent(MainActivity.this, ViewRestaurant.class);

            intent.putExtra("id", r.getId());
            intent.putExtra("name", r.getName());
            intent.putExtra("address", r.getAddress());
            intent.putExtra("phone", r.getPhone());
            intent.putExtra("rating", r.getRating());
            intent.putExtra("tags", r.getTags());
            intent.putExtra("description", r.getDescription());

            startActivity(intent);
        });

        // --- NAVIGATION BUTTONS
        // Button to go to the About screen
        Button btnGoToAbout = findViewById(R.id.btnAbout);
        btnGoToAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        });

        //Button to go to the Add Restaurant screen
        Button btnAddRestaurant = findViewById(R.id.btnAdd);
        btnAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRestaurant.class);
            startActivity(intent);
        });

        //Button to go to the Search screen
        Button btnGoToSearch = findViewById(R.id.btnSearch);
        btnGoToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
        });
    }

    // refresh the users list when returning to the main screen
    @Override
    protected void onResume() {
        super.onResume();

        userList.clear();
        userList.addAll(dbHelper.getUserList());
        adapter.notifyDataSetChanged();
    }
}