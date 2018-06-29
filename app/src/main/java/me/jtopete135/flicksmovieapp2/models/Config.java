package me.jtopete135.flicksmovieapp2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config  {

    //the base url for loading images
    String imageBaseUrl;
    //the poster size
    String posterSize;
    //backdrop size to use when fetching poster image
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        //get the image base url
        imageBaseUrl = images.getString("secure_base_url");
        //get poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //use the option at index 3 or fallback on w342
        posterSize = posterSizeOptions.optString(3,"w342");
        //get backdrop sizes for fetched images
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        //use the option at index 1 or w780 as a fallback
        backdropSize = backdropSizeOptions.optString(1,"w780");

    }

    public String getBackdropSize() {
        return backdropSize;
    }

    //helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl,size,path);
    }


    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }
}
