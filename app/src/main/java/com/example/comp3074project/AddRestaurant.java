package com.example.comp3074project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddRestaurant extends AppCompatActivity {

    private EditText etName, etAddress, etPhone, etRating, etTags, etDesc;
    private UserListDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addRestaurant), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etName = findViewById(R.id.etInputName);
        etAddress = findViewById(R.id.etInputAddress);
        etPhone = findViewById(R.id.etInputPhone);
        etRating = findViewById(R.id.etInputRating);
        etRating.setFilters(new android.text.InputFilter[]{ new TwoDecimalDigitsInputFilter() });
        etTags = findViewById(R.id.etInputTags);
        etDesc = findViewById(R.id.etInputDesc);

        dbHelper = new UserListDbHelper(this);

        Button btnCancel = findViewById(R.id.btnCancelAdd);
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AddRestaurant.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnAddToList = findViewById(R.id.btnAddToList);
        btnAddToList.setOnClickListener(s -> addToList());

    }

    private void addToList() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();
        String tags = etTags.getText().toString().trim();
        String description = etDesc.getText().toString().trim();

        //validation
        if (name.isEmpty() ) {
            android.widget.Toast.makeText(this, "Please enter a name", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        if (ratingStr.isEmpty()){
            android.widget.Toast.makeText(this, "Please rate 1 - 5", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        //convert rating to float
        float rating = Float.parseFloat(ratingStr);
        if (rating < 1 || rating > 5) {
            android.widget.Toast.makeText(this, "Please rate 1 - 5", android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        //save inputs into restaurant object
        Restaurant r = new Restaurant(
                0, // id automatically generated
                name,
                address,
                phone,
                rating,
                tags,
                description
        );

        //add to user repository
        if (dbHelper.addToUserList(r)){
            android.widget.Toast.makeText(this, "Restaurant added", android.widget.Toast.LENGTH_SHORT).show();
            finish();
        } else{
            android.widget.Toast.makeText(this, "Failed to Add", android.widget.Toast.LENGTH_SHORT).show();
        }

    }

    //Checks if the input matches at double format
    private static class TwoDecimalDigitsInputFilter implements android.text.InputFilter {
        @Override
        public CharSequence filter(CharSequence src, int start, int end,
                                   android.text.Spanned dst, int dstart, int dend) {
            String newVal = dst.subSequence(0, dstart) + src.toString() + dst.subSequence(dend, dst.length());
            if (newVal.matches("^\\d*(\\.\\d{0,2})?$")) return null;
            return "";
        }
    }
}