package com.team34rockets.scoutingapp.handlers;

import android.os.StrictMode;
import android.util.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TBAHandler {

    public static String GetTeamName(int teamNumber) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String sTeamNumber = String.valueOf(teamNumber);
        String token = "https://www.thebluealliance.com/api/v3/team/frc".concat(sTeamNumber);
        URL url = new URL(token);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("X-TBA-Auth-Key",
                "ErpFzyZ0lESX6fGTXBoBeFV8kHAeuCM6rrY0Z3CkxW56AvOac7WnSfDOgDcxnYYo");
        httpURLConnection.setRequestProperty("accept", "application/json");
        httpURLConnection.setRequestMethod("GET");
        if (httpURLConnection.getResponseCode() == 200) {
            InputStream stream = new ByteArrayInputStream(httpURLConnection.getResponseMessage()
                    .getBytes(StandardCharsets.UTF_8));
            JsonReader reader =
                    new JsonReader(new InputStreamReader(httpURLConnection.getInputStream()));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equalsIgnoreCase("nickname")) return reader.nextString();
                else reader.skipValue();
            }
            reader.endObject();
        }
        httpURLConnection.disconnect();
        return null;
    }
}
