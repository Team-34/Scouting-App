package com.team34rockets.scoutingapp.contracts;

import com.team34rockets.scoutingapp.models.Team;

public interface TeamViewContract {

    interface View {
        void updateTeamData(Team team);
    }

    interface Presenter {
        void onCreate();

        void matchSelect();

        void attach(View view);

        void detach();
    }
}
