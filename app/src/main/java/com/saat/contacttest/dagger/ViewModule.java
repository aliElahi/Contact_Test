package com.saat.contacttest.dagger;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saat.contacttest.model.ContactRepository;
import com.saat.contacttest.view.MainActivityAdapter;
import com.saat.contacttest.view.MainActivityPagedAdapter;
import com.saat.contacttest.view.ShowAdapter;
import com.saat.contacttest.viewModel.CustomViewModelFactory;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RepositoryModule.class , ApplicationModule.class})
public class ViewModule {

    @Provides
    public CustomViewModelFactory viewModelFactoryProvide(Application application,ContactRepository repository){
        return new CustomViewModelFactory(application,repository);
    }

    @Provides
    public LinearLayoutManager linearLayoutManagerProvide(Application application){
        return new LinearLayoutManager(application,LinearLayoutManager.VERTICAL,false);
    }

    @Provides
    public MainActivityAdapter mainActivityAdapterProvide(Picasso picasso){
        return new MainActivityAdapter(picasso);
    }

    @Provides
    public MainActivityPagedAdapter mainActivityPagedAdapterProvide(Picasso picasso){
        return new MainActivityPagedAdapter(picasso);
    }

    @Provides
    public Picasso picassoProvide(Application application){
        return new Picasso.
                Builder(application).
                build();
    }

    @Provides
    public ShowAdapter showAdapterProvide(){
        return new ShowAdapter();
    }
}
