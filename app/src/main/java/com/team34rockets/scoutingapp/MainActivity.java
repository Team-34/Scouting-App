package com.team34rockets.scoutingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.team34rockets.scoutingapp.adapters.TeamListAdapter;
import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.presenter.MainActivityPresenter;

import java.util.ArrayList;
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
    public static final int ACCOUNT_CHOSE = 33;
    private List<ActivityResultListener> activityResultListeners = new ArrayList<>();
    private TeamListAdapter teamListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bernard's fun regional");
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
        //if (requestCode == MainActivity.ACCOUNT_CHOSE) presenter.refresh();
        for (ActivityResultListener listener : activityResultListeners) {
            listener.onResult(requestCode, resultCode, intent);
        }
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.teamListRV);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void updateTeamList(TeamListAdapter teamListAdapter) {
        recyclerView.setAdapter(teamListAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void addActivityResultListener(ActivityResultListener activityResultListener) {
        activityResultListeners.add(activityResultListener);
    }

    public interface ActivityResultListener {
        void onResult(int requestCode, int resultCode, Intent intent);
    }
}
