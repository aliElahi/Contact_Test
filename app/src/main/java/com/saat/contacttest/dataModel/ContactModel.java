package com.saat.contacttest.dataModel;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.saat.contacttest.model.Common;

import java.util.List;

@Entity
public class ContactModel {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private List<String> phones;

    private String photoUri;

    public ContactModel() {
    }

    public ContactModel(@NonNull Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.phones = builder.phones;
        this.photoUri = builder.photoUri;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if(obj instanceof ContactModel){
            ContactModel model = (ContactModel)obj;
            return TextUtils.equals(this.id,model.id);
        }
        else
            return false;

    }

    public boolean isSameValue(ContactModel model){
        if(model== null)
            return false;

        if(this.name.equals(model.name)){
            if(TextUtils.equals(this.photoUri,model.photoUri)){
                return Common.equalLists(phones,model.phones);
            }
        }
        return false;
    }


    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public static class Builder{
        private String  id;
        private String name;
        private List<String> phones;
        private String photoUri;

        public Builder setId(String  id){
            this.id = id;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setPhone(List<String> phone){
            this.phones = phone;
            return this;
        }
        public Builder setPhotoUri(String photoUri){
            this.photoUri = photoUri;
            return this;
        }

        public ContactModel build(){
            return new ContactModel(this);
        }
    }
}
