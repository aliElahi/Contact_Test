package com.saat.contacttest.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.model.Common;
import com.saat.contacttest.model.ContactRepository;
import com.saat.contacttest.model.worker.CheckContactWorker;
import com.saat.contacttest.services.ContactWatchService;

import java.util.List;

import javax.inject.Inject;

public class MainActivityViewModel extends AndroidViewModel {

    private final ContactRepository repository;

    @Inject
    public MainActivityViewModel(@NonNull Application application ,ContactRepository repository ) {
        super(application);
        this.repository = repository;
    }

    /* @Inject
     public MainActivityViewModel(ContactRepository repository) {
         this.repository = repository;
     }
 */
    public LiveData<List<ContactModel>> getContactLiveData() {
        return repository.getContactLiveData();
    }

    public void readAllContact(){
        repository.readeAllContactFromDevice();
    }

    public void startContactWatchesService(Context context){
       /* if(Common.isMyServiceRunning(ContactWatchService.class,context)){
            Intent intent = new Intent(context, ContactWatchService.class);
            context.getApplicationContext().startService(intent);
        }*/

        Intent intent = new Intent(context, ContactWatchService.class);
        context.getApplicationContext().startService(intent);

    }

    public LiveData<PagedList<ContactModel>> getPagedListLiveData(){
        return repository.getLiveDataPagedList();
    }

    public void checkContact(){
        OneTimeWorkRequest request = OneTimeWorkRequest
                .from(CheckContactWorker.class);
        WorkManager
                .getInstance()
                .enqueueUniqueWork("check contact", ExistingWorkPolicy.KEEP, request);
    }
}
