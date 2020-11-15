package com.saat.contacttest.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;

import com.saat.contacttest.App;
import com.saat.contacttest.model.ContactObserver;

import javax.inject.Inject;

public class ContactWatchService extends Service {

    private ServiceHandler mServiceHandler;
    HandlerThread thread;

    @Inject
    ContactObserver observer;

    public ContactWatchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ((App)getApplication()).getComponent()
                .contactWatchServiceInject(this);

        thread = new HandlerThread("ServiceStartArguments");
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceHandler = new ServiceHandler(thread.getLooper());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            getApplication()
                    .getContentResolver()
                    .unregisterContentObserver(observer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mServiceHandler.removeCallbacksAndMessages(null);
        try {
            thread.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent=new Intent(getApplicationContext(), ContactWatchService.class);
        startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startContactObserver(){
        try{
            //Registering contact observer
            //getApplication().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true,new MyContentObserver(new Handler(),getApplicationContext()));
            getApplication()
                    .getContentResolver()
                    .registerContentObserver(ContactsContract.Contacts.CONTENT_URI
                            ,true,
                            observer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                //Register contact observer
                startContactObserver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}