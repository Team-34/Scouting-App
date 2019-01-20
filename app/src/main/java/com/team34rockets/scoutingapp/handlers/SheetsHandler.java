package com.team34rockets.scoutingapp.handlers;

import android.content.res.Resources;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.team34rockets.scoutingapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class SheetsHandler {
    private static final String APPLICATION_NAME = "Team 34 2019 Scouting App";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections
            .singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    private Sheets service;
    private NetHttpTransport HTTP_TRANSPORT;

    private SheetsHandlerListener sheetsHandlerListener = null;

    public SheetsHandler() {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                    getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            if (sheetsHandlerListener != null) sheetsHandlerListener.onReady();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            if (sheetsHandlerListener != null) sheetsHandlerListener.onFail();
        }
    }

    public SheetsHandler(SheetsHandlerListener listener) {
        this.sheetsHandlerListener = listener;
        try {
            HTTP_TRANSPORT = new NetHttpTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                    getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            if (sheetsHandlerListener != null) sheetsHandlerListener.onReady();
        } catch (IOException e) {
            Log.e("ERROR", "Welp", e);
            if (sheetsHandlerListener != null) sheetsHandlerListener.onFail();
        }
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        Log.d("SETUP", "Retrieving credentials");
        InputStream in = Resources.getSystem().openRawResource(R.raw.credentials);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(
                        new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public List<List<Object>> getValue(String iD, String range) throws IOException {
        ValueRange result = service.spreadsheets().values().get(iD, range).execute();
        List<List<Object>> values = result.getValues();
        return values;
    }

    public void setSheetsHandlerListener(SheetsHandlerListener sheetsHandlerListener) {
        this.sheetsHandlerListener = sheetsHandlerListener;
    }

    public interface SheetsHandlerListener {
        void onReady();

        void onFail();
    }
}
