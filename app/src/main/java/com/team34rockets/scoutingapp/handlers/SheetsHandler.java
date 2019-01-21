package com.team34rockets.scoutingapp.handlers;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.team34rockets.scoutingapp.MainActivity;
import com.team34rockets.scoutingapp.presenter.MainActivityPresenter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SheetsHandler {
    private Sheets service;
    private static final String APPLICATION_NAME = "Team 34 2019 Scouting App";
    private SheetsHandlerListener listener;
    private Activity activity;
    private GoogleAccountCredential credential;

    public SheetsHandler(Activity activity) {
        this.activity = activity;
        credential = GoogleAccountCredential.usingOAuth2(activity,
                Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY))
                .setBackOff(new ExponentialBackOff());
        activity.startActivityForResult(credential.newChooseAccountIntent(),
                MainActivity.ACCOUNT_CHOSE);
    }

    public void createService(Intent intent) {
        credential.setSelectedAccountName(intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
        service = new Sheets.Builder(AndroidHttp.newCompatibleTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @SuppressLint("StaticFieldLeak")
    public AsyncTask<Void, Void, List<List<Object>>> getValueTask(final String iD, final String
            range) {
        return new AsyncTask<Void, Void, List<List<Object>>>() {
            @Override
            protected List<List<Object>> doInBackground(Void... voids) {
                try {
                    ValueRange result = service.spreadsheets().values().get(iD, range).execute();
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
