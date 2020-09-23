package com.escapelearning.escapelearning.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.escapelearning.escapelearning.data.models.Parent;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.repositories.ParentRepository;

import io.reactivex.disposables.CompositeDisposable;

public class ParentViewModel extends ViewModel {
    private ParentRepository parentRepository;
    private MediatorLiveData<APIResponse<Parent>> parent = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private ParentRepository getParentRepository(Context context) {
        if (parentRepository == null)
            parentRepository = new ParentRepository(context);
        return parentRepository;
    }

    public void signup(Context context, String name, String childCode,
                       String username, String password) {
        disposable.add(getParentRepository(context)
                .signup(name, childCode, username, password)
                .subscribe(parent::setValue));
    }

    public MediatorLiveData<APIResponse<Parent>> getParent() {
        return parent;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
