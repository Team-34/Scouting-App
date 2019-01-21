package com.team34rockets.scoutingapp.handlers;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.team34rockets.scoutingapp.presenter.MainActivityPresenter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SheetsHandler {
    GoogleAccountCredential credential;
    ProgressDialog progressDialog;
    Sheets service;
    private static final String APPLICATION_NAME = "Team 34 2019 Scouting App";
    private SheetsHandlerListener listener;
    private Activity activity;

    public SheetsHandler(Activity activity) {
        this.activity = activity;
        credential = GoogleAccountCredential.usingOAuth2(activity,
                Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
                .setBackOff(new ExponentialBackOff());
        credential.setSelectedAccount(new Account("benard.allotey4@gmail.com",
                "com.team34rockets.scoutingapp"));
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Sending...");
        service = new Sheets.Builder(AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<List<Object>> getValue(String iD, String range) throws IOException {
        ValueRange result = service.spreadsheets().values().get(iD, range).execute();
        return result.getValues();
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTask<Void, Void, List<List<Object>>> getValueTask(final String iD, final String
            range) {
        return new AsyncTask<Void, Void, List<List<Object>>>() {
            @Override
            protected List<List<Object>> doInBackground(Void... voids) {
                ValueRange result = null;
                try {
                    result = service.spreadsheets().values().get(iD, range).execute();
                    Log.d("?", "WE GOT EM?");
                    return result.getValues();
                } catch (UserRecoverableAuthIOException e) {
                    Log.e("RIP", "F", e);
                    activity.startActivityForResult(e.getIntent(), MainActivityPresenter.AUTH_CODE);
                    return null;
                } catch (IOException e) {
                    Log.e("RIP", "F", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<List<Object>> result) {
                listener.onReady(result);
            }
        };

    }

    public void setSheetsHandlerListener(SheetsHandlerListener sheetsHandlerListener) {
        this.listener = sheetsHandlerListener;
    }

    public interface SheetsHandlerListener {
        void onReady(List<List<Object>> result);

        void onFail();
    }

}
