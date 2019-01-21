package com.team34rockets.scoutingapp.presenter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.team34rockets.scoutingapp.MainActivity;
import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.handlers.SheetsHandler;
import com.team34rockets.scoutingapp.models.Team;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View view;
    List<Team> teamList;
    public static final int AUTH_CODE = 254;
    SheetsHandler sheetsHandler;
    private List<List<Object>> data;

    @Override
    public void onCreate() {
        sheetsHandler = new SheetsHandler(view.getActivity());
        refresh();
        view.updateTeamList(teamList);
    }

    @Override
    public void displayTeam(Team team) {

    }

    @Override
    public boolean permissionCheck() {
        if (ContextCompat.checkSelfPermission(view.getContext(),
                Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(),
                    new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET},
                    MainActivity.YEETEMUP);
            return false;
        } else if (
                ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(view.getActivity(),
                    new String[]{Manifest.permission.GET_ACCOUNTS, Manifest.permission.INTERNET},
                    MainActivity.YEETEMUP);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void attach(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {

    }

    @Override
    public void refresh() {
        sheetsHandler.setSheetsHandlerListener(new SheetsHandler.SheetsHandlerListener() {
            @Override
            public void onReady(List<List<Object>> result) {
                data = result;
            }

            @Override
            public void onFail() {

            }
        });
        AsyncTask<Void, Void, List<List<Object>>> task =
                sheetsHandler.getValueTask("1BKY4UFuQaFRqNQhhoW95vlwZR3Dqr8eiW8-lLsA2gOY",
                        "Sheet1");
        task.execute();
    }
}
