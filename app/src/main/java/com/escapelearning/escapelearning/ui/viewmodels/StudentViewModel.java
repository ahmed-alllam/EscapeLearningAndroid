package com.escapelearning.escapelearning.ui.viewmodels;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.escapelearning.escapelearning.data.models.Student;
import com.escapelearning.escapelearning.data.network.APIResponse;
import com.escapelearning.escapelearning.data.repositories.StudentRepository;

import io.reactivex.disposables.CompositeDisposable;

public class StudentViewModel extends ViewModel {
    private StudentRepository studentRepository;
    private MediatorLiveData<APIResponse<Student>> student = new MediatorLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    private StudentRepository getStudentRepository(Context context) {
        if (studentRepository == null)
            studentRepository = new StudentRepository(context);
        return studentRepository;
    }

    public void signup(Context context, String name, String schoolName, String schoolCode,
                       String className, String username, String password) {
        disposable.add(getStudentRepository(context)
                .signup(name, schoolName, schoolCode,
                        className, username, password)
                .subscribe(student::setValue));
    }

    public MediatorLiveData<APIResponse<Student>> getStudent() {
        return student;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
