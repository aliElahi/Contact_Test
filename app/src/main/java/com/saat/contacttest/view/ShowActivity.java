package com.saat.contacttest.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.saat.contacttest.App;
import com.saat.contacttest.R;
import com.saat.contacttest.dataModel.ContactModel;
import com.saat.contacttest.databinding.ActivityShowBinding;
import com.saat.contacttest.viewModel.CustomViewModelFactory;
import com.saat.contacttest.viewModel.ShowActivityViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ShowActivity extends AppCompatActivity {

    public static final String ID = "id";

    @Inject
    Picasso picasso;

    @Inject
    CustomViewModelFactory factory;

    private ShowActivityViewModel viewModel;

    @Inject
    ShowAdapter adapter;

    @Inject
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App)getApplication())
                .getComponent()
                .showActivityInject(this);

        setContentView(R.layout.activity_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //toolBarLayout.setTitle(getTitle());

        viewModel = factory.create(ShowActivityViewModel.class);

        String id = getIntent().getStringExtra(ID);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getContactModel(id).observe(this,model -> {

            toolbar.setTitle(model.getName());
            toolBarLayout.setTitle(model.getName());

            adapter.setList(model.getPhones());
            
        });


    }
}