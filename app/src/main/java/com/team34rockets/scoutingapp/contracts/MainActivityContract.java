package com.team34rockets.scoutingapp.contracts;

import android.app.Activity;
import android.content.Context;

import com.team34rockets.scoutingapp.MainActivity;
import com.team34rockets.scoutingapp.adapters.TeamListAdapter;
import com.team34rockets.scoutingapp.models.Competition;
import com.team34rockets.scoutingapp.models.Team;

public interface MainActivityContract {
    interface View {
        void updateTeamList(TeamListAdapter teamListAdapter);

        Context getContext();

        Activity getActivity();

        void addActivityResultListener(MainActivity.ActivityResultListener activityResultListener);
    }

    interface Presenter {
        void onCreate();

        void displayTeam(Team team);

        Competition getCompetition();

        boolean permissionCheck();

        void refresh();

        void attach(View view);

        void detach();

    }
}
