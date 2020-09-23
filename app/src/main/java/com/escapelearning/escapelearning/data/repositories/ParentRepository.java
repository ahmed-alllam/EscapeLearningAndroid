package com.escapelearning.escapelearning.data.repositories;

import android.content.Context;

import com.escapelearning.escapelearning.data.db.AppDatabase;
import com.escapelearning.escapelearning.data.db.dao.ParentDao;
import com.escapelearning.escapelearning.data.models.Parent;
import com.escapelearning.escapelearning.data.network.APIEndpoints;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.network.APIService;
import com.escapelearning.escapelearning.data.network.NetworkBoundResource;

import io.reactivex.Single;

public class ParentRepository {
    private APIEndpoints apiEndpoints = APIService.getService();
    private ParentDao parentDao;

    public ParentRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        parentDao = appDatabase.getParentDao();
    }

    public Single<APIResponse<Parent>> signup(String name, String childCode,
                                              String username, String password) {
        return new NetworkBoundResource<Parent>() {
            @Override
            protected boolean shouldFetchFromDB() {
                return false;
            }

            @Override
            protected Single<Parent> fetchFromDB() {
                return null;
            }

            @Override
            protected boolean shouldFetchFromAPI(Parent data) {
                return true;
            }

            @Override
            protected Single<Parent> getAPICall() {
                return apiEndpoints.signupParent(name, childCode, username, password);
            }

            @Override
            protected void saveToDB(Parent parent, boolean isUpdate) {
                parent.setCurrentUser(true);
                parentDao.addParent(parent);
            }
        }.asSingle();
    }
}
