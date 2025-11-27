package com.example.comp3074project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserListDbHelper dbHelper;
    private List<Restaurant> userList;
    private ArrayAdapter<Restaurant> adapter;
    private ListView userListView;


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

        Button btnGoToAbout = findViewById(R.id.btnAbout);
        btnGoToAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        });

        Button btnAddRestaurant = findViewById(R.id.btnAdd);
        btnAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRestaurant.class);
            startActivity(intent);
        });

        Button btnGoToSearch = findViewById(R.id.btnSearch);
        btnGoToSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Search.class);
            startActivity(intent);
        });


    }





}