package com.team34rockets.scoutingapp.contracts;

import android.app.Activity;
import android.content.Context;

import com.team34rockets.scoutingapp.MainActivity;
import com.team34rockets.scoutingapp.models.Team;

import java.util.List;

public interface MainActivityContract {
    interface View {
        void updateTeamList(List<Team> teamList);

        Context getContext();

        Activity getActivity();

        void addActivityResultListener(MainActivity.ActivityResultListener activityResultListener);
    }

    interface Presenter {
        void onCreate();

        void displayTeam(Team team);

        boolean permissionCheck();

        void refresh();

        void attach(View view);

        void detach();

    }
}
