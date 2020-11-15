package com.saat.contacttest.dagger;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.saat.contacttest.model.ContactObserver;
import com.saat.contacttest.model.database.ContactDao;
import com.saat.contacttest.model.database.Database;
import com.saat.contacttest.model.database.PhoneNumberDao;


import dagger.Module;
import dagger.Provides;

@Module(includes = {ApplicationModule.class})
public class RepositoryModule {

    @Provides
    public ContactDao contactDaoProvide(@NonNull Database database){
        return database.getDataModelDao();
    }

    @Provides
    public Database databaseProvide(Application application){
        return Database.getDatabase(application);
    }

    @Provides
    public PhoneNumberDao phoneNumberDaoProvide(@NonNull Database database){
        return database.getPhoneNumberDao();
    }

    @Provides
    public ContactObserver contactObserverProvide(Application application,ContactDao dao){
        return new ContactObserver(new Handler(),application);
    }

}
