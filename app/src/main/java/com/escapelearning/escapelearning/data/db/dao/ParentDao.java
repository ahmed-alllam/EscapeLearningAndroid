package com.escapelearning.escapelearning.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.escapelearning.escapelearning.data.models.Parent;

@Dao
public interface ParentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addParent(Parent parent);
}
