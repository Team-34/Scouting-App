package com.team34rockets.scoutingapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.attach(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerSetup();
        presenter.onCreate();
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.teamListRV);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void updateTeamList(List<Team> teamList) {

    }
}
