package com.escapelearning.escapelearning.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.escapelearning.escapelearning.data.models.ResponseList;
import com.escapelearning.escapelearning.data.models.School;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.repositories.SchoolRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SchoolViewModel extends ViewModel {
    private SchoolRepository schoolRepository;
    private MediatorLiveData<APIResponse<List<School>>> schools = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private SchoolRepository getSchoolRepository(Context context) {
        if (schoolRepository == null)
            schoolRepository = new SchoolRepository(context);
        return schoolRepository;
    }

    public void searchSchools(Context context, String name) {
        disposable.add(getSchoolRepository(context)
                .searchSchools(name)
                .subscribe(schools::setValue));
    }

    public MediatorLiveData<APIResponse<List<School>>> getSchoolsLiveData() {
        return schools;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
