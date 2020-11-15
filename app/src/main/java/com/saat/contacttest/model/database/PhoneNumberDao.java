package com.saat.contacttest.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.dataModel.PhoneNumberModel;

import java.util.List;

@Dao
public interface PhoneNumberDao {

    @Insert
    long insert(PhoneNumberModel model);

    @Query("SELECT * FROM PhoneNumberModel WHERE id = :id")
    ContactModel getContactById(int id);

    @Update
    int update(PhoneNumberModel model);

    @Delete
    void delete(PhoneNumberModel model);

    @Query("DELETE FROM PhoneNumberModel")
    void deleteAll();

    @Query("SELECT * FROM PhoneNumberModel")
    LiveData<List<PhoneNumberModel>> getAllDataModelLiveData();
}
