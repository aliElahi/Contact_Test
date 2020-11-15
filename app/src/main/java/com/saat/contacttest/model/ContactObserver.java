package com.saat.contacttest.model;

import android.app.Application;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.saat.contacttest.model.database.ContactDao;
import com.saat.contacttest.model.worker.CheckContactWorker;

public class ContactObserver extends ContentObserver {

    private static final String TAG = ContactObserver.class.getSimpleName();

    private Application application;

    public ContactObserver(Handler handler) {
        super(handler);
    }

    public ContactObserver(Handler handler , Application application){
        super(handler);
        this.application = application;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        //onChange(selfChange,null);
        check();
    }

    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        check();
    }

  private void check(){
      OneTimeWorkRequest request = OneTimeWorkRequest
              .from(CheckContactWorker.class);
      WorkManager
              .getInstance(application)
              .enqueueUniqueWork("check contact", ExistingWorkPolicy.KEEP, request);
  }
}
