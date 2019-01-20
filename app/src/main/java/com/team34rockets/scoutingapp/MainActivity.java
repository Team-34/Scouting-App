package com.team34rockets.scoutingapp;

import android.os.Bundle;
import android.util.Log;

import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.handlers.SheetsHandler;
import com.team34rockets.scoutingapp.models.Team;
import com.team34rockets.scoutingapp.presenter.MainActivityPresenter;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    MainActivityContract.Presenter presenter = new MainActivityPresenter();
    SheetsHandler sheetsHandler;
    List<List<Object>> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerSetup();
        presenter.onCreate();
        SheetsHandler.SheetsHandlerListener listener = new SheetsHandler.SheetsHandlerListener() {
            @Override
            public void onReady() {
                try {
                    data = sheetsHandler.getValue("1BKY4UFuQaFRqNQhhoW95vlwZR3Dqr8eiW8-lLsA2gOY",
                            "Sheet1");
                    Log.d("Read", "Read");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {
                Log.d("Read", "Failed");
            }
        };
        sheetsHandler = new SheetsHandler(listener);
        Log.d("Tag", "yeet");
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.teamListRV);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void updateTeamList(List<Team> teamList) {

    }
}
