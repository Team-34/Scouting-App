package com.team34rockets.scoutingapp.contracts;

import android.app.Activity;
import android.content.Context;

import com.team34rockets.scoutingapp.models.Competition;
import com.team34rockets.scoutingapp.models.Team;

public interface TeamViewContract {

    interface View {
        void updateTeamData(Team team);

        Context getContext();

        Activity getActivity();

        String getTeamString();

        int getPosition();
    }

    interface Presenter {
        void onCreate();

        void matchSelect();

        Competition getCompetition();

        void attach(View view);

        void detach();
    }
}
