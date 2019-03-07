package com.team34rockets.scoutingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.team34rockets.scoutingapp.adapters.MatchListAdapter;
import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.models.Team;
import com.team34rockets.scoutingapp.presenter.TeamViewPresenter;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TeamViewActivity extends AppCompatActivity implements TeamViewContract.View {

    TeamViewContract.Presenter presenter = new TeamViewPresenter();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);
        presenter.attach(this);
        recyclerSetup();
        presenter.onCreate();
    }

    @Override
    public void updateTeamData(Team team, MatchListAdapter matchListAdapter) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        ((TextView) findViewById(R.id.teamName)).setText(team.getName());
        ((TextView) findViewById(R.id.teamNumber)).setText(String.valueOf(team.getNumber()));
        ((TextView) findViewById(R.id.ranking)).setText(String.valueOf(team.getRank()));
        ((TextView) findViewById(R.id.teamApr)).setText(df.format(team.getScaledApr(
                presenter.getCompetition())));
        ((TextView) findViewById(R.id.teamOpr)).setText(df.format(team.getOpr()));
        ((TextView) findViewById(R.id.teamDpr)).setText(df.format(team.getDpr()));
        ((TextView) findViewById(R.id.teamCcwm)).setText(df.format(team.getCcwm()));
        recyclerView.setAdapter(matchListAdapter);
    }

    void recyclerSetup() {
        recyclerView = findViewById(R.id.matchHistoryRv);
        recyclerView.setLayoutManager(layoutManager);
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
    public String getTeamString() {
        return getIntent().getStringExtra("team");
    }

    @Override
    public int getPosition() {
        return getIntent().getIntExtra("position", 1);
    }

}
