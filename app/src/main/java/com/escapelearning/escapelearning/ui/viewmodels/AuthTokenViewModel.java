package com.escapelearning.escapelearning.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.escapelearning.escapelearning.data.models.AuthToken;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.repositories.AuthTokenRepository;

import io.reactivex.disposables.CompositeDisposable;

public class AuthTokenViewModel extends ViewModel {
    private AuthTokenRepository authTokenRepository;
    private MediatorLiveData<APIResponse<AuthToken>> token = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private AuthTokenRepository getAuthTokenRepository(Context context) {
        if (authTokenRepository == null)
            authTokenRepository = new AuthTokenRepository(context);
        return authTokenRepository;
    }

    public void login(Context context, String userName, String password, String role) {
        disposable.add(getAuthTokenRepository(context)
                .login(userName, password, role)
                .subscribe(token::setValue));
    }

    public MediatorLiveData<APIResponse<AuthToken>> getToken() {
        return token;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
