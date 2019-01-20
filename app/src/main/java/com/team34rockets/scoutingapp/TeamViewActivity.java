package com.team34rockets.scoutingapp;

import android.os.Bundle;

import com.team34rockets.scoutingapp.contracts.TeamViewContract;
import com.team34rockets.scoutingapp.models.Team;

import androidx.appcompat.app.AppCompatActivity;

public class TeamViewActivity extends AppCompatActivity implements TeamViewContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);
    }

    @Override
    public void updateTeamData(Team team) {

    }
}
