package com.escapelearning.escapelearning.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.escapelearning.escapelearning.data.models.School;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addSchools(List<School> schools);

    @Query("SELECT * FROM school WHERE slug=:slug")
    public abstract Single<School> getSchool(String slug);
}
