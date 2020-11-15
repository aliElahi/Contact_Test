package com.saat.contacttest.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.saat.contacttest.model.ContactRepository;

public class CustomViewModelFactory implements ViewModelProvider.Factory {

    private final ContactRepository repository;
    private final Application application;

    public CustomViewModelFactory(Application application, ContactRepository repository) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if(aClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(application,repository);
        }
        else if (aClass.isAssignableFrom(ShowActivityViewModel.class)){
            return (T)new ShowActivityViewModel(repository);
        }
        else{
            throw new IllegalArgumentException("this type view model not supported in CustomViewModelFactory");
        }
    }
}
