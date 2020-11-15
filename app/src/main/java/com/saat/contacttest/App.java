package com.saat.contacttest;

import android.app.Application;

import com.saat.contacttest.dagger.ApplicationComponent;
import com.saat.contacttest.dagger.ApplicationModule;
import com.saat.contacttest.dagger.DaggerApplicationComponent;
import com.saat.contacttest.dagger.RepositoryModule;
import com.saat.contacttest.dagger.ViewModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .repositoryModule(new RepositoryModule())
                .viewModule(new ViewModule())
                .build();

    }

    public ApplicationComponent getComponent() {
        return component;
    }

}
