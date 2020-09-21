package com.escapelearning.escapelearning.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.escapelearning.escapelearning.data.models.School;
import com.escapelearning.escapelearning.data.models.Student;

@Dao
public interface StudentDao {
    @Insert
    public abstract void addStudent(Student student);
}
