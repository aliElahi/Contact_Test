package com.saat.contacttest.model.worker;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.saat.contacttest.App;
import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.model.Common;
import com.saat.contacttest.model.ContactContentResolver;
import com.saat.contacttest.model.ContactRepository;
import com.saat.contacttest.model.database.ContactDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CheckContactWorker extends Worker {


    @Inject
    ContactRepository repository;

    @Inject
    ContactDao dao;


    public CheckContactWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        ((App)getApplicationContext())
                .getComponent()
                .CheckContactWorkerInject(this);
    }

    @NonNull
    @Override
    public Result doWork() {

        if(Common.IsContactPermission(getApplicationContext())){
            try(Cursor cursor = ContactContentResolver.getContactCursor(getApplicationContext())){
                if(cursor != null){
                    int contactNumber = cursor.getCount();
                    int rowNumberInDb = dao.getCountOfRow();

                    List<ContactModel> fromContentProvider = new ArrayList<>();

                    cursor.moveToFirst();
                    do {
                        // get info from current row
                        String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String contactDisplayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                        ContactModel model = new ContactModel.Builder()
                                .setId(contactID)
                                .setPhotoUri(imageUri)
                                .setName(contactDisplayName)
                                .build();
                        // if this contact have phone number add it to list of contact model
                        if(cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0){
                            model.setPhones(getPhoneList(contactID));
                        }

                        fromContentProvider.add(model);

                    } while (cursor.moveToNext());


                    if(contactNumber < rowNumberInDb){
                        List<ContactModel> fromDb = dao.getAllContactModel();
                        fromDb.removeAll(fromContentProvider);
                        dao.deleteList(fromDb);
                        //onDelete();
                    }
                    else if (contactNumber > rowNumberInDb){
                        List<ContactModel> fromDb = dao.getAllContactModel();
                        fromContentProvider.removeAll(fromDb);
                        dao.insertList(fromContentProvider);
                       // onAdd();
                    }
                    else {

                        for (ContactModel model: fromContentProvider) {
                            ContactModel modelFromDb = dao.getContactById(model.getId());
                            if(!model.isSameValue(modelFromDb)){
                                dao.update(model);
                            }
                        }

                        dao.update(fromContentProvider);
                        //onUpdate();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                return Result.failure();
            }
        }

        return Result.success();
    }


    private List<String> getPhoneList(String contactID){

        List<String> list = null;
        try (Cursor phoneCursor = getApplicationContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID,
                null,
                null))
        {

            list = new ArrayList<>();
            while (phoneCursor.moveToNext()) {

                String phoneNumber = phoneCursor.getString(
                        phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                list.add(phoneNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
