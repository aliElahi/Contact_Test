package com.saat.contacttest.model.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.dataModel.PhoneNumberModel;


@androidx.room.Database(entities = {ContactModel.class, PhoneNumberModel.class}, version = 1 , exportSchema = false)
@TypeConverters(ListPhoneNumberTypeConverter.class)
public abstract class Database extends RoomDatabase {

    private static volatile Database INSTANCE;

    public abstract ContactDao getDataModelDao();

    public abstract PhoneNumberDao getPhoneNumberDao();

    public static synchronized Database getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), Database.class, "database")
                    .build();
        }

        return INSTANCE;

    }
}