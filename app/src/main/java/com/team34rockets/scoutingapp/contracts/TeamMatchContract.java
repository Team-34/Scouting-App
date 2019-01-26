package com.team34rockets.scoutingapp.contracts;

import android.app.Activity;
import android.content.Context;

import com.team34rockets.scoutingapp.adapters.ScoutingQuestionAdapter;

public interface TeamMatchContract {

    interface View {
        void updateInfo(ScoutingQuestionAdapter scoutingQuestionAdapter, String additionalDetails);

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
