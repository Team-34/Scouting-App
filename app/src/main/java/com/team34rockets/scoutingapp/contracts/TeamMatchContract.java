package com.team34rockets.scoutingapp.contracts;

import android.app.Activity;
import android.content.Context;

import com.team34rockets.scoutingapp.models.ScoutingReport;

public interface TeamMatchContract {

    interface View {
        void updateInfo(ScoutingReport scoutingReport);

        String retrieveExtra();

        Context getContext();

        Activity getActivity();

    }

    interface Presenter {
        void attach(View view);

        void detach();

        void onCreate();
    }
}
