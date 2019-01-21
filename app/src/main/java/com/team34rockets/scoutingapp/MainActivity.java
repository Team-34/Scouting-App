package com.team34rockets.scoutingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.models.Team;
import com.team34rockets.scoutingapp.presenter.MainActivityPresenter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    MainActivityContract.Presenter presenter = new MainActivityPresenter();
    public static final int YEETEMUP = 6934;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerSetup();

        if (presenter.permissionCheck()) {
            presenter.onCreate();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case YEETEMUP: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onCreate();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == MainActivityPresenter.AUTH_CODE) presenter.refresh();
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.teamListRV);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void updateTeamList(List<Team> teamList) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
