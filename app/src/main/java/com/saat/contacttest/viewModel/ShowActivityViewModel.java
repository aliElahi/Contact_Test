package com.saat.contacttest.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.model.ContactRepository;

import javax.inject.Inject;

public class ShowActivityViewModel extends ViewModel {

    ContactRepository repository ;

    @Inject
    public ShowActivityViewModel(ContactRepository repository) {
        this.repository = repository;
    }

    public LiveData<ContactModel> getContactModel(String id){
        return repository.getContactModel(id);
    }



}
