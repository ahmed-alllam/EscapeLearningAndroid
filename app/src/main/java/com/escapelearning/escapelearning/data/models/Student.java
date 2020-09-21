package com.escapelearning.escapelearning.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Student {
    @NonNull
    @PrimaryKey
    private String username = "";
    private String profile_photo;
    private int points;
    private int rating;
    private String name;
    private String code;
    private boolean isCurrentUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return points == student.points &&
                rating == student.rating &&
                username.equals(student.username) &&
                Objects.equals(profile_photo, student.profile_photo) &&
                Objects.equals(name, student.name) &&
                Objects.equals(code, student.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, profile_photo, points, rating, name, code);
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }
}
