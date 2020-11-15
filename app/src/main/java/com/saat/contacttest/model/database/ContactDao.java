package com.saat.contacttest.model.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.saat.contacttest.dataModel.ContactModel;

import java.util.List;

import javax.inject.Inject;

@Dao
public interface ContactDao {
    @Insert
    long insert(ContactModel model);

    @Insert
    void insertList(List<ContactModel> list);

    @Query("SELECT * FROM ContactModel WHERE id = :id")
    ContactModel getContactById(String  id);

    @Query("SELECT * FROM ContactModel WHERE id = :id")
    LiveData<ContactModel> getContactByIdLiveData(String  id);

    @Query("SELECT COUNT(*) FROM contactmodel")
    int getCountOfRow();

    @Update
    int update(ContactModel model);

    @Update
    void update(List<ContactModel> list);

    @Delete
    void delete(ContactModel model);

    @Delete
    void deleteList(List<ContactModel> list);

    @Query("DELETE FROM ContactModel")
    void deleteAll();

    @Query("SELECT * FROM ContactModel ORDER BY id ASC")
    LiveData<List<ContactModel>> getAllDataModelLiveData();

    @Query("SELECT * FROM contactmodel ORDER BY id ASC")
    DataSource.Factory<Integer,ContactModel> getAllContactLiveDataPaged();

    @Query("SELECT * FROM contactmodel")
    List<ContactModel> getAllContactModel();

}
