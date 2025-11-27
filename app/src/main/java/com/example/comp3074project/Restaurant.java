package com.example.comp3074project;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "restaurant_id")
    public int id;
    @NotNull
    @ColumnInfo(name="restaurant_name")
    public String name;
    @ColumnInfo(name="restaurant_address")
    public String address;
    @ColumnInfo(name="restaurant_phone")
    public String phone;
    @ColumnInfo(name="restaurant_rating")
    public float rating;
    @ColumnInfo(name="restaurant_tags")
    public String tags;
    @ColumnInfo(name="restaurant_desc")
    public String description;

    public Restaurant(int id, @NonNull String name, String address, String phone,
                      float rating, String tags, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
        this.tags = tags;
        this.description = description;
    }


    // Getters
    public int getId() { return id; }
    public @NotNull String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public float getRating() { return rating; }
    public String getTags() { return tags; }
    public String getDescription() { return description; }

    // Setters
    public void setName(@NotNull String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRating(float rating) { this.rating = rating; }
    public void setTags(String tags) { this.tags = tags; }
    public void setDescription(String description) { this.description = description; }




    @Override
    public String toString() {
        return name + " - My Rating: " + rating;
    }

}
