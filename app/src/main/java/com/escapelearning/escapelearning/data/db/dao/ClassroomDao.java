package com.escapelearning.escapelearning.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.escapelearning.escapelearning.data.models.Classroom;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ClassroomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addClassrooms(List<Classroom> classrooms);

    @Query("SELECT * FROM classroom WHERE slug=:slug")
    public abstract Single<Classroom> getClassroom(String slug);
}
