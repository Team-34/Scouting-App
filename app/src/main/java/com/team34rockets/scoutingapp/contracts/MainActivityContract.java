package com.team34rockets.scoutingapp.contracts;

import com.team34rockets.scoutingapp.models.Team;

import java.util.List;

public interface MainActivityContract {
    interface View {
        void updateTeamList(List<Team> teamList);
    }

    interface Presenter {
        void onCreate();

        void displayTeam(Team team);

        void refresh();

        void attach(View view);

        void detach();

    }
}
