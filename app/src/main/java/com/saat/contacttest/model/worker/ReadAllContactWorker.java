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
import com.saat.contacttest.model.ContactContentResolver;
import com.saat.contacttest.model.database.ContactDao;
import com.saat.contacttest.model.database.PhoneNumberDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReadAllContactWorker extends Worker {


    private static final String TAG = ReadAllContactWorker.class.getSimpleName();

    @Inject
    ContactDao dao;

    @Inject
    PhoneNumberDao phoneNumberDao;

    public ReadAllContactWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        ((App)getApplicationContext()).getComponent()
                .ReadAllContactWorkerInject(this);
    }

    @NonNull
    @Override
    public Result doWork() {

        boolean flag = false;

        //cursor for iterate the table of contact info
        try (Cursor cur = ContactContentResolver.getContactCursor(getApplicationContext())) {
            if (cur != null && cur.getCount() > 0) {
                cur.moveToFirst();
                do {
                    // get info from current row
                    String contactID = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactDisplayName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String imageUri = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                    ContactModel model = new ContactModel.Builder()
                            .setId(contactID)
                            .setPhotoUri(imageUri)
                            .setName(contactDisplayName)
                            .build();
                    // if this contact have phone number add it to list of contact model
                    if(cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0){
                        model.setPhones(getPhoneList(contactID));
                        Log.d(TAG, "doWork: " + contactID + " phone : "+ model.getPhones().size() + " Name: " +contactDisplayName +" imageUri : " + imageUri );

                    }

                    //this part is check the model is exist update or if not add it to database
                    ContactModel model1 = dao.getContactById(contactID);
                    if(model1 == null){
                        dao.insert(model);
                    }
                    else if (!model.isSameValue(model1)){
                        dao.update(model);
                    }

                } while (cur.moveToNext());
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag ? Result.success() : Result.failure();
        }
        //return Result.success();
    }

    /**
     * this method is return list of phone number by contact id
     * @param contactID id of contact in contact application
     * @return list of string phone number
     */
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
