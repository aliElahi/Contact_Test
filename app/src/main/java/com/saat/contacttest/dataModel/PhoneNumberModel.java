package com.saat.contacttest.dataModel;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = ContactModel.class,
    parentColumns = "id",
    childColumns = "contactId",
    onDelete = ForeignKey.CASCADE))
public class PhoneNumberModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contactId")
    private String  contactId;

    private String phoneNumber;

    public PhoneNumberModel() {
    }

    public PhoneNumberModel(@NonNull Builder builder) {
        this.contactId = builder.contactId;
        this.phoneNumber = builder.phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getContactId() {
        return contactId;
    }

    public void setContactId(String  contactId) {
        this.contactId = contactId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static class Builder{
        private String  contactId;
        private String phoneNumber;

        public Builder setContactId(String  id){
            this.contactId = id;
            return this;
        }
        public Builder setPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PhoneNumberModel build(){
            return new PhoneNumberModel(this);
        }
    }
}
