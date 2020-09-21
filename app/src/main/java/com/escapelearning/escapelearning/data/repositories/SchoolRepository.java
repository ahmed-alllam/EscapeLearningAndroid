package com.escapelearning.escapelearning.data.repositories;

import android.content.Context;

import com.escapelearning.escapelearning.data.db.AppDatabase;
import com.escapelearning.escapelearning.data.db.dao.SchoolDao;
import com.escapelearning.escapelearning.data.models.ResponseList;
import com.escapelearning.escapelearning.data.models.School;
import com.escapelearning.escapelearning.data.network.APIEndpoints;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.network.APIService;
import com.escapelearning.escapelearning.data.network.NetworkBoundResource;

import java.util.List;

import io.reactivex.Single;

public class SchoolRepository {
    private APIEndpoints apiEndpoints = APIService.getService();
    private SchoolDao schoolDao;

    public SchoolRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        schoolDao = appDatabase.getSchoolDao();
    }

    public Single<APIResponse<List<School>>> searchSchools(String name) {
        return new NetworkBoundResource<List<School>>(){
            @Override
            protected boolean shouldFetchFromDB() {
                return false;
            }

            @Override
            protected Single<List<School>> fetchFromDB() {
                return null;
            }

            @Override
            protected boolean shouldFetchFromAPI(List<School> data) {
                return true;
            }

            @Override
            protected Single<List<School>> getAPICall() {
                return apiEndpoints.searchSchools(name);
            }

            @Override
            protected void saveToDB(List<School> items, boolean isUpdate) {
                schoolDao.addSchools(items);
            }
        }.asSingle();
    }
}
