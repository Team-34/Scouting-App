package com.team34rockets.scoutingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.team34rockets.scoutingapp.adapters.ScoutingQuestionAdapter;
import com.team34rockets.scoutingapp.contracts.TeamMatchContract;
import com.team34rockets.scoutingapp.presenter.TeamMatchPresenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeamMatchActivity extends AppCompatActivity implements TeamMatchContract.View {

    TeamMatchContract.Presenter presenter = new TeamMatchPresenter();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_match);
        presenter.attach(this);
        recyclerSetup();
        presenter.onCreate();
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.teamMatchRv);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void updateInfo(ScoutingQuestionAdapter scoutingQuestionAdapter,
                           String additionalDetails) {
        recyclerView.setAdapter(scoutingQuestionAdapter);
        TextView textView = findViewById(R.id.matchDetails);
        textView.setText(additionalDetails);
    }

    @Override
    public String retrieveExtra() {
        return getIntent().getStringExtra("competition");
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
