package com.saat.contacttest.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.saat.contacttest.App;
import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.databinding.ActivityMainBinding;
import com.saat.contacttest.model.Common;
import com.saat.contacttest.viewModel.CustomViewModelFactory;
import com.saat.contacttest.viewModel.MainActivityViewModel;

import javax.inject.Inject;

public class MainActivity
        extends AppCompatActivity
        implements MainActivityAdapter.OnItemClickListener{

    public final static int MY_PERMISSIONS_READ_CONTACTS = 1;

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    MainActivityPagedAdapter adapter;
    @Inject
    MainActivityAdapter adapter1;
    @Inject
    CustomViewModelFactory factory;

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App)getApplication())
                .getComponent()
                .mainActivityInject(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = factory.create(MainActivityViewModel.class);

        adapter.setListener(this);
        adapter1.setListener(this);

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter1);


        viewModel.getContactLiveData()
                .observe(this,contactModels -> {
                    adapter1.setList(contactModels);
                    binding.swipeRefreshLayout.setEnabled(false);
                });

       /* viewModel.getPagedListLiveData().observe(this,contactModels -> {
            adapter.submitList(contactModels);
            binding.swipeRefreshLayout.setEnabled(false);
        });*/

        binding.swipeRefreshLayout.setOnRefreshListener(this::start);
        binding.button.setOnClickListener(this::onClick);

        start();
    }

    private void start() {
        try {
            if (Common.IsContactPermission(this)) {//Checking permission
                //Starting service for registering ContactObserver
                viewModel.readAllContact();
               // viewModel.startContactWatchesService(this);
            } else {
                //Ask for READ_CONTACTS permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_READ_CONTACTS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.checkContact();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //If permission granted
        if (requestCode == MY_PERMISSIONS_READ_CONTACTS &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            start();
        }
    }

    @Override
    public void onClick(ContactModel model) {
        if (model.getPhones() != null) {
            Intent intent = new Intent(this,ShowActivity.class);
            intent.putExtra(ShowActivity.ID,model.getId());
            startActivity(intent);
        }
    }

    private void onClick(View view) {
        start();
    }
}