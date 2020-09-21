package com.escapelearning.escapelearning.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class School {
    @NonNull
    @PrimaryKey
    private String slug = "";
    private String image;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return slug.equals(school.slug) &&
                Objects.equals(image, school.image) &&
                Objects.equals(name, school.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, image, name);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
