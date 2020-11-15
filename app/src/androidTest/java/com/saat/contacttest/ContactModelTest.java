package com.saat.contacttest;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.saat.contacttest.dataModel.ContactModel;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ContactModelTest {

    @Test
    public void testSameValue(){

        ContactModel model = new ContactModel.Builder()
                .setName("ali")
                .setPhotoUri("sss")
                .setId("ee")
                .setPhone(null)
                .build();

        ContactModel model1 = new ContactModel.Builder()
                .setName("ali")
                .setPhotoUri("sss")
                .setId("ee")
                .setPhone(null)
                .build();

        ContactModel model2 = new ContactModel.Builder()
                .setName("ali")
                .setPhotoUri("ss")
                .setId("e")
                .setPhone(null)
                .build();


        List<String> list = new ArrayList<>();
        list.add("Ss");
        ContactModel model3 = new ContactModel.Builder()
                .setName("alii")
                .setPhotoUri("ss")
                .setId("ee")
                .setPhone(list)
                .build();

        List<String> list2 = new ArrayList<>();
        list.add("Ss");
        ContactModel model4 = new ContactModel.Builder()
                .setName("alii")
                .setPhotoUri("sss")
                .setId("ee")
                .setPhone(list2)
                .build();


        assert model.isSameValue(model1);

        assert !model.isSameValue(model2);

        assert !model2.isSameValue(model3);

        assert !model3.isSameValue(model4);


    }
}
