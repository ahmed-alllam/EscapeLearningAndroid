package com.escapelearning.escapelearning.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.escapelearning.escapelearning.data.models.Student;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addStudent(Student student);
}
