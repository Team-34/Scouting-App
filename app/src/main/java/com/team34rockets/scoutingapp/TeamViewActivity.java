package com.team34rockets.scoutingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.models.Team;
import com.team34rockets.scoutingapp.presenter.TeamViewPresenter;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class TeamViewActivity extends AppCompatActivity implements TeamViewContract.View {

    TeamViewContract.Presenter presenter = new TeamViewPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);
        presenter.attach(this);
        presenter.onCreate();
    }

    @Override
    public void updateTeamData(Team team) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        ((TextView) findViewById(R.id.teamName)).setText(team.getName());
        ((TextView) findViewById(R.id.teamNumber)).setText(String.valueOf(team.getNumber()));
        ((TextView) findViewById(R.id.ranking)).setText(String.valueOf(team.getRank()));
        ((TextView) findViewById(R.id.teamApr)).setText(df.format(team.getScaledApr(
                presenter.getCompetition())));
        ((TextView) findViewById(R.id.teamOpr)).setText(String.valueOf(team.getOpr()));
        ((TextView) findViewById(R.id.teamDpr)).setText(String.valueOf(team.getDpr()));
        ((TextView) findViewById(R.id.teamCcwm)).setText(String.valueOf(team.getCcwm()));
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
