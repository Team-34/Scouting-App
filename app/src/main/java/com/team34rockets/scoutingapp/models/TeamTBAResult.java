package com.team34rockets.scoutingapp.models;

import java.util.Map;

public class TeamTBAResult {

    private String address;
    private String city;
    private String country;
    private String gmaps_place_id;
    private String gmaps_url;
    private Map<String, String> home_championship;
    private String key;
    private String lat;
    private String lng;
    private String location_name;
    private String motto;
    private String name;
    private String nickname;
    private String postal_code;
    private String rookie_year;
    private String state_prov;
    private String team_number;
    private String website;

    public TeamTBAResult() {
    }

    public TeamTBAResult(String address, String city, String country, String gmaps_place_id,
                         String gmaps_url,
                         Map<String, String> home_championship, String key, String lat,
                         String lng, String location_name, String motto, String name,
                         String nickname, String postal_code, String rookie_year,
                         String state_prov, String team_number, String website) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.gmaps_place_id = gmaps_place_id;
        this.gmaps_url = gmaps_url;
        this.home_championship = home_championship;
        this.key = key;
        this.lat = lat;
        this.lng = lng;
        this.location_name = location_name;
        this.motto = motto;
        this.name = name;
        this.nickname = nickname;
        this.postal_code = postal_code;
        this.rookie_year = rookie_year;
        this.state_prov = state_prov;
        this.team_number = team_number;
        this.website = website;
    }

    public static TeamTBAResult TeamTBAResultBuilder(Map<String, Object> data) {
        TeamTBAResult result = new TeamTBAResult();
        result.setAddress((String) data.get("address"));
        result.setCity((String) data.get("city"));
        result.setCountry((String) data.get("country"));
        result.setGmaps_place_id((String) data.get("gmaps_place_id"));
        result.setGmaps_url((String) data.get("gmaps_url"));
        result.setHome_championship((Map<String, String>) data.get("home_championship"));
        result.setKey((String) data.get("key"));
        result.setLat((String) data.get("lat"));
        result.setLng((String) data.get("lng"));
        result.setLocation_name("location_name");
        result.setMotto((String) data.get("motto"));
        result.setName((String) data.get("name"));
        result.setNickname((String) data.get("nickname"));
        result.setPostal_code((String) data.get("postal_code"));
        result.setRookie_year((String) data.get("rookie_year"));
        result.setState_prov((String) data.get("state_prov"));
        result.setTeam_number((String) data.get("team_number"));
        result.setWebsite((String) data.get("website"));
        return result;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGmaps_place_id() {
        return gmaps_place_id;
    }

    public void setGmaps_place_id(String gmaps_place_id) {
        this.gmaps_place_id = gmaps_place_id;
    }

    public String getGmaps_url() {
        return gmaps_url;
    }

    public void setGmaps_url(String gmaps_url) {
        this.gmaps_url = gmaps_url;
    }

    public Map<String, String> getHome_championship() {
        return home_championship;
    }

    public void setHome_championship(
            Map<String, String> home_championship) {
        this.home_championship = home_championship;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getRookie_year() {
        return rookie_year;
    }

    public void setRookie_year(String rookie_year) {
        this.rookie_year = rookie_year;
    }

    public String getState_prov() {
        return state_prov;
    }

    public void setState_prov(String state_prov) {
        this.state_prov = state_prov;
    }

    public String getTeam_number() {
        return team_number;
    }

    public void setTeam_number(String team_number) {
        this.team_number = team_number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
