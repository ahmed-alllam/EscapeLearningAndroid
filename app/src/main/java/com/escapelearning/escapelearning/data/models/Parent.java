package com.escapelearning.escapelearning.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Parent {
    @NonNull
    @PrimaryKey
    private String username = "";
    private String profile_photo;
    private String name;
    private boolean isCurrentUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parent parent = (Parent) o;
        return isCurrentUser == parent.isCurrentUser &&
                username.equals(parent.username) &&
                Objects.equals(profile_photo, parent.profile_photo) &&
                Objects.equals(name, parent.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, profile_photo, name, isCurrentUser);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }
}
