package com.escapelearning.escapelearning.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.escapelearning.escapelearning.data.db.dao.AuthTokenDao;
import com.escapelearning.escapelearning.data.db.dao.ClassroomDao;
import com.escapelearning.escapelearning.data.db.dao.ParentDao;
import com.escapelearning.escapelearning.data.db.dao.SchoolDao;
import com.escapelearning.escapelearning.data.db.dao.StudentDao;
import com.escapelearning.escapelearning.data.db.dao.TeacherDao;
import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.models.Classroom;
import com.escapelearning.escapelearning.data.models.Parent;
import com.escapelearning.escapelearning.data.models.School;
import com.escapelearning.escapelearning.data.models.Student;
import com.escapelearning.escapelearning.data.models.Teacher;

@Database(entities = {AuthToken.class, Student.class, Teacher.class, Parent.class,
            School.class, Classroom.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "escapelearning-db";
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null)
                    instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .build();
            }
        }
        return instance;
    }

    public abstract AuthTokenDao getAuthTokenDao();

    public abstract StudentDao getStudentDao();

    public abstract TeacherDao getTeacherDao();

    public abstract ParentDao getParentDao();

    public abstract SchoolDao getSchoolDao();

    public abstract ClassroomDao getClassroomDao();
}
