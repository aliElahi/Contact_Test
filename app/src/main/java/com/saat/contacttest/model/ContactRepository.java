package com.saat.contacttest.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.model.database.ContactDao;
import com.saat.contacttest.model.worker.ReadAllContactWorker;

import java.util.List;

import javax.inject.Inject;

public class ContactRepository {

    private Application application;
    private ContactDao dao;

    private LiveData<PagedList<ContactModel>> liveData;

    @Inject
    public ContactRepository(Application application , ContactDao dao) {
        this.application = application;
        this.dao = dao;
        liveData = new LivePagedListBuilder<>(this.dao.getAllContactLiveDataPaged(),50).build();
    }
    
    public void readeAllContactFromDevice() {
        OneTimeWorkRequest request = OneTimeWorkRequest.from(ReadAllContactWorker.class);
        WorkManager.getInstance(application)
                .enqueue(request);
    }

    public LiveData<List<ContactModel>> getContactLiveData(){
        return dao.getAllDataModelLiveData();
    }

    public LiveData<PagedList<ContactModel>> getLiveDataPagedList(){
        return liveData;
    }

    public LiveData<ContactModel> getContactModel(String id){
        return dao.getContactByIdLiveData(id);
    }

}
