package com.team34rockets.scoutingapp.handlers;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.JsonToken;

import com.team34rockets.scoutingapp.models.TeamTBAResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TBAHandler {

    public static String GetTeamName(int teamNumber) throws IOException {
        HttpURLConnection httpURLConnection = startConnectionByTeamNumber(teamNumber);
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

    private static HttpURLConnection startConnectionByTeamNumber(int teamNumber)
            throws IOException {
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
        return httpURLConnection;
    }

    public static TeamTBAResult GetTeamData(int teamNumber) throws IOException {
        HttpURLConnection httpURLConnection = startConnectionByTeamNumber(teamNumber);
        Map<String, Object> returnResult = new HashMap<>();
        if (httpURLConnection.getResponseCode() == 200) {
            JsonReader reader =
                    new JsonReader(new InputStreamReader(httpURLConnection.getInputStream()));
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equalsIgnoreCase("home_championship")) {
                    Map<String, String> champ_map = new HashMap<>();
                    reader.beginObject();
                    while (reader.hasNext()) {
                        champ_map.put(reader.nextName(), reader.nextString());
                    }
                    returnResult.put(name, champ_map);
                    reader.endObject();
                    continue;
                }
                if (reader.peek() == JsonToken.NULL) {
                    returnResult.put(name, null);
                    reader.skipValue();
                    continue;
                }
                returnResult.put(name, reader.nextString());
            }
        }
        return TeamTBAResult.TeamTBAResultBuilder(returnResult);
    }

}
