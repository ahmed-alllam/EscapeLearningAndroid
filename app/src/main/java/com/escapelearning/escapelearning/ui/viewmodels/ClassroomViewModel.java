package com.escapelearning.escapelearning.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.escapelearning.escapelearning.data.models.Classroom;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.repositories.ClassroomRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ClassroomViewModel extends ViewModel {
    private ClassroomRepository classroomRepository;
    private MediatorLiveData<APIResponse<List<Classroom>>> classrooms = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private ClassroomRepository getClassroomRepository(Context context) {
        if (classroomRepository == null)
            classroomRepository = new ClassroomRepository(context);
        return classroomRepository;
    }

    public void searchClassrooms(Context context, String schoolName) {
        disposable.add(getClassroomRepository(context)
                .searchClassrooms(schoolName)
                .subscribe(classrooms::setValue));
    }

    public MediatorLiveData<APIResponse<List<Classroom>>> getClassroomsLiveData() {
        return classrooms;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
