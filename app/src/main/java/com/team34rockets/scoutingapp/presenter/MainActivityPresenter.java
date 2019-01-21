package com.team34rockets.scoutingapp.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.team34rockets.scoutingapp.MainActivity;
import com.team34rockets.scoutingapp.contracts.MainActivityContract;
import com.team34rockets.scoutingapp.handlers.SheetsHandler;
import com.team34rockets.scoutingapp.models.ScoutingReport;
import com.team34rockets.scoutingapp.models.Team;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private static final String SHEET_ID = "1m9F2-QNP1Krg_UmXeGONmE9Dv02_zbFhZFhvzOSUEKQ";
    private static final String SHEET_NAME = "Form Responses 1";
    public static final int AUTH_CODE = 254;
    private MainActivityContract.View view;
    private List<List<Object>> data;
    private List<Team> teamList;
    private SheetsHandler sheetsHandler;

    @Override
    public void onCreate() {
        sheetsHandler = new SheetsHandler(view.getActivity());
        sheetsHandler.setSheetsHandlerListener(new SheetsHandler.SheetsHandlerListener() {
            @Override
            public void onReady(List<List<Object>> result) {
                Log.d("E", "Result Received!");
                refresh();
            }

            @Override
            public void onFail() {
                Log.e("E", "Failed");
            }
        });
        view.addActivityResultListener(new MainActivity.ActivityResultListener() {
            @Override
            public void onResult(int requestCode, int resultCode, Intent intent) {
                Log.d("E", "Starting service");
                sheetsHandler.createService(intent);
                Log.d("E", "Getting Value...");
                sheetsHandler.getValueTask(SHEET_ID, SHEET_NAME).execute();
            }
        });
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
        for (int i = 0; i < sheetsHandler.getData().size(); i++) {
            if (i != 0) {
                ScoutingReport report = new ScoutingReport.ScoutingReportBuilder()
                        .build(sheetsHandler, i);
                Team team = new Team(report.teamNumber, "");
                team.addScouingReport(report);
                teamList.add(team);
            }
        }

        Log.d("Read", "Read three");
    }
}
