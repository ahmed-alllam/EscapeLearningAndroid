package com.escapelearning.escapelearning.data.repositories;

import android.content.Context;

import com.escapelearning.escapelearning.data.db.AppDatabase;
import com.escapelearning.escapelearning.data.db.dao.AuthTokenDao;
import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.network.APIEndpoints;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.network.APIService;
import com.escapelearning.escapelearning.data.network.NetworkBoundResource;

import io.reactivex.Single;

public class AuthTokenRepository {
    private APIEndpoints apiEndpoints = APIService.getService();
    private AuthTokenDao authTokenDao;

    public AuthTokenRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        authTokenDao = appDatabase.getAuthTokenDao();
    }

    public Single<APIResponse<AuthToken>> login(String userName, String password, String role) {
        return new NetworkBoundResource<AuthToken>() {
            @Override
            protected void saveToDB(AuthToken token, boolean isUpdate) {
                authTokenDao.addAuthToken(token);
                AuthTokenDao.setToken(token.getToken());
            }

            @Override
            protected boolean shouldFetchFromAPI(AuthToken data) {
                return true;
            }

            @Override
            protected Single<AuthToken> fetchFromDB() {
                return null;
            }

            @Override
            protected boolean shouldFetchFromDB() {
                return false;
            }

            @Override
            protected Single<AuthToken> getAPICall() {
                return apiEndpoints.login(userName, password, role);
            }
        }.asSingle();
    }
}
