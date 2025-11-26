package com.example.comp3074project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnGoToAbout = findViewById(R.id.btnAbout);
        btnGoToAbout.setOnClickListener(v -> {
            Intent intent = new Intent(Search.this, About.class);
            startActivity(intent);
            finish();
        });

        Button btnMyList = findViewById(R.id.btnMyList);
        btnMyList.setOnClickListener(v -> {
            Intent intent = new Intent(Search.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}