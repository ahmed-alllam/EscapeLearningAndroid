package com.escapelearning.escapelearning.data.repositories;

import android.content.Context;

import com.escapelearning.escapelearning.data.db.AppDatabase;
import com.escapelearning.escapelearning.data.db.dao.ClassroomDao;
import com.escapelearning.escapelearning.data.db.dao.StudentDao;
import com.escapelearning.escapelearning.data.models.Student;
import com.escapelearning.escapelearning.data.network.APIEndpoints;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.network.APIService;
import com.escapelearning.escapelearning.data.network.NetworkBoundResource;

import io.reactivex.Single;

public class StudentRepository {
    private APIEndpoints apiEndpoints = APIService.getService();
    private StudentDao studentDao;

    public StudentRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        studentDao = appDatabase.getStudentDao();
    }

    public Single<APIResponse<Student>> signup(String name, String schoolName, String schoolCode,
                                               String className, String username, String password) {
        return new NetworkBoundResource<Student>(){
            @Override
            protected boolean shouldFetchFromDB() {
                return false;
            }

            @Override
            protected Single<Student> fetchFromDB() {
                return null;
            }

            @Override
            protected boolean shouldFetchFromAPI(Student data) {
                return true;
            }

            @Override
            protected Single<Student> getAPICall() {
                return apiEndpoints.signupStudent(name, schoolName, schoolCode,
                        className, username, password);
            }

            @Override
            protected void saveToDB(Student student, boolean isUpdate) {
                student.setCurrentUser(true);
                studentDao.addStudent(student);
            }
        }.asSingle();
    }
}
