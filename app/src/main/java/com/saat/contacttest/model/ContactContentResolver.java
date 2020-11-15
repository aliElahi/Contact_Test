package com.saat.contacttest.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

public class ContactContentResolver {

    public static Cursor getContactCursor(@NonNull Context context){
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        return cr.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null);
    }
}
