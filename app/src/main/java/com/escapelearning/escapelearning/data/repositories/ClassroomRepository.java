package com.escapelearning.escapelearning.data.repositories;

import android.content.Context;

import com.escapelearning.escapelearning.data.db.AppDatabase;
import com.escapelearning.escapelearning.data.db.dao.ClassroomDao;
import com.escapelearning.escapelearning.data.models.Classroom;
import com.escapelearning.escapelearning.data.models.ResponseList;
import com.escapelearning.escapelearning.data.network.APIEndpoints;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.network.APIService;
import com.escapelearning.escapelearning.data.network.NetworkBoundResource;

import java.util.List;

import io.reactivex.Single;

public class ClassroomRepository {
    private APIEndpoints apiEndpoints = APIService.getService();
    private ClassroomDao classroomDao;

    public ClassroomRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        classroomDao = appDatabase.getClassroomDao();
    }

    public Single<APIResponse<List<Classroom>>> searchClassrooms(String schoolName) {
        return new NetworkBoundResource<List<Classroom>>(){
            @Override
            protected boolean shouldFetchFromDB() {
                return false;
            }

            @Override
            protected Single<List<Classroom>> fetchFromDB() {
                return null;
            }

            @Override
            protected boolean shouldFetchFromAPI(List<Classroom> data) {
                return true;
            }

            @Override
            protected Single<List<Classroom>> getAPICall() {
                return apiEndpoints.searchClassrooms(schoolName);
            }

            @Override
            protected void saveToDB(List<Classroom> classrooms, boolean isUpdate) {
                classroomDao.addClassrooms(classrooms);
            }
        }.asSingle();
    }
}
