package com.saat.contacttest.dagger;

import com.saat.contacttest.model.worker.CheckContactWorker;
import com.saat.contacttest.model.worker.ReadAllContactWorker;
import com.saat.contacttest.services.ContactWatchService;
import com.saat.contacttest.view.MainActivity;
import com.saat.contacttest.view.ShowActivity;

import dagger.Component;

@Component(modules = {ViewModule.class})
public interface ApplicationComponent {

    void mainActivityInject(MainActivity mainActivity);

    void showActivityInject(ShowActivity activity);

    void ReadAllContactWorkerInject(ReadAllContactWorker worker);

    void contactWatchServiceInject(ContactWatchService service);

    void CheckContactWorkerInject(CheckContactWorker worker);

}
