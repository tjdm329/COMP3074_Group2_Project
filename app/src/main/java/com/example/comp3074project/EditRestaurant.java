package com.example.comp3074project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditRestaurant extends AppCompatActivity {

    private EditText etName, etAddress, etPhone, etRating, etTags, etDesc;
    private int getId;
    private UserListDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_restaurant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editRestaurant), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new UserListDbHelper(this);

        // set the elements from activity layout
        etName = findViewById(R.id.etEditName);
        etAddress = findViewById(R.id.etEditAddress);
        etPhone = findViewById(R.id.etEditPhone);
        etRating = findViewById(R.id.etEditRating);
        etRating.setFilters(new android.text.InputFilter[]{ new EditRestaurant.TwoDecimalDigitsInputFilter() });
        etTags = findViewById(R.id.etEditTags);
        etDesc = findViewById(R.id.etEditDesc);


        // Receive values sent from ViewRestaurant
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getId = extras.getInt("id");
            etName.setText(extras.getString("name"));
            etAddress.setText(extras.getString("address"));
            etPhone.setText(extras.getString("phone"));
            etRating.setText(String.valueOf(extras.getFloat("rating")));
            etTags.setText(extras.getString("tags"));
            etDesc.setText(extras.getString("description"));
        }

        // ----- NAVIGATION BUTTONS
        // Cancel edit button - returns to main page for simplicity
        Button btnCancel = findViewById(R.id.btnCancelEdit);
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(EditRestaurant.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        // Update entry button - updates the row with new values
        Button btnAddToList = findViewById(R.id.btnEditToList);
        btnAddToList.setOnClickListener(s -> updateEntry());
    }

    private void updateEntry() {
        int id = getId;
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
                id,
                name,
                address,
                phone,
                rating,
                tags,
                description
        );

        if (dbHelper.updateUserRestaurant(r)){
            android.widget.Toast.makeText(this, "Restaurant Updated", android.widget.Toast.LENGTH_SHORT).show();
            finish();
        } else{
            android.widget.Toast.makeText(this, "Failed to Update", android.widget.Toast.LENGTH_SHORT).show();
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