package com.escapelearning.escapelearning.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Classroom {
    @NonNull
    @PrimaryKey
    private String slug = "";
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return slug.equals(classroom.slug) &&
                Objects.equals(name, classroom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug, name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
