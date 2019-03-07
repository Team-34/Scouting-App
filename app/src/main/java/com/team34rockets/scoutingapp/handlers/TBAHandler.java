package com.team34rockets.scoutingapp.handlers;

import android.os.StrictMode;
import android.util.JsonReader;
import android.util.JsonToken;

import com.team34rockets.scoutingapp.models.tbaresults.EventOprsTbaResult;
import com.team34rockets.scoutingapp.models.tbaresults.TeamEventKeysTbaResult;
import com.team34rockets.scoutingapp.models.tbaresults.TeamTbaResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TBAHandler {

    public static String GetTeamName(int teamNumber) throws IOException {
        HttpURLConnection httpURLConnection =
                startConnection(String.valueOf(teamNumber), MODE.TEAM);
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

    /**
     * If not actually connecting by team number pass in zero or something
     *
     * @param additionalData the team number to retrieve info from
     * @param mode           What mode to use it in; Can be Team, event_key, or stats
     * @return a connection
     * @throws IOException if we cant connect
     */
    private static HttpURLConnection startConnection(String additionalData, MODE mode)
            throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String sTeamNumber = String.valueOf(additionalData);
        String token;
        switch (mode) {
            case TEAM: {
                token = "https://www.thebluealliance.com/api/v3/team/frc"
                        .concat(sTeamNumber);
                break;
            }
            case EVENT_KEY: {
                token = "https://www.thebluealliance.com/api/v3/team/frc"
                        .concat(sTeamNumber);
                token = token.concat("/events/2019/keys");
                break;
            }
            case STATS: {
                token = "https://www.thebluealliance.com/api/v3/event/".concat(additionalData);
                token = token.concat("/oprs");
                break;
            }
            default:
                token = "https://www.thebluealliance.com/api/v3/team/frc"
                        .concat(sTeamNumber);
                break;
        }

        URL url = new URL(token);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("X-TBA-Auth-Key",
                "ErpFzyZ0lESX6fGTXBoBeFV8kHAeuCM6rrY0Z3CkxW56AvOac7WnSfDOgDcxnYYo");
        httpURLConnection.setRequestProperty("accept", "application/json");
        httpURLConnection.setRequestMethod("GET");
        return httpURLConnection;
    }

    public static TeamTbaResult GetTeamData(int teamNumber) throws IOException {
        HttpURLConnection httpURLConnection =
                startConnection(String.valueOf(teamNumber), MODE.TEAM);
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
        httpURLConnection.disconnect();
        return TeamTbaResult.Builder(returnResult);
    }

    public static TeamEventKeysTbaResult GetKeysData(int teamNumber) throws IOException {
        HttpURLConnection httpURLConnection =
                startConnection(String.valueOf(teamNumber), MODE.EVENT_KEY);
        TeamEventKeysTbaResult result = new TeamEventKeysTbaResult();
        result.setEvents(new ArrayList<String>());
        if (httpURLConnection.getResponseCode() == 200) {
            JsonReader reader =
                    new JsonReader(new InputStreamReader(httpURLConnection.getInputStream()));
            reader.beginArray();
            while (reader.hasNext()) {
                result.getEvents().add(reader.nextString());
            }
        }
        httpURLConnection.disconnect();
        return result;
    }

    public static EventOprsTbaResult GetStatsData(String eventKey) throws IOException {
        HttpURLConnection httpURLConnection = startConnection(eventKey, MODE.STATS);
        EventOprsTbaResult result = new EventOprsTbaResult();
        if (httpURLConnection.getResponseCode() == 200) {
            JsonReader reader =
                    new JsonReader(new InputStreamReader(httpURLConnection.getInputStream()));
            reader.beginObject();
            Map<String, Double> ccwms = new HashMap<>();
            Map<String, Double> oprs = new HashMap<>();
            Map<String, Double> dprs = new HashMap<>();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equalsIgnoreCase("ccwms")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        ccwms.put(reader.nextName(), reader.nextDouble());
                    }
                    reader.endObject();
                } else if (name.equalsIgnoreCase("oprs")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        oprs.put(reader.nextName(), reader.nextDouble());
                    }
                    reader.endObject();
                } else if (name.equalsIgnoreCase("dprs")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        dprs.put(reader.nextName(), reader.nextDouble());
                    }
                    reader.endObject();
                }
            }
            result.setCcwms(ccwms);
            result.setDprs(dprs);
            result.setOprs(oprs);
        }
        return result;
    }

    public enum MODE {
        TEAM,
        EVENT_KEY,
        STATS
    }

}
