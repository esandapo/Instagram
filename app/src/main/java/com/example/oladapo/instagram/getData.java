package com.example.oladapo.instagram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves the URLs from the JSON
 */
public class getData {
    private JSONObject result_API;

    public getData(JSONObject result_API){
        this.result_API = result_API;
    }

    public List<String> getImagesUrl() throws JSONException{
        JSONArray data = result_API.getJSONArray("data");
        int length = data.length();
        ArrayList<String> result = new ArrayList<String>();

        for(int i=0;i<length;i++){
            JSONObject entry = data.getJSONObject(i);
            JSONObject imagesData = entry.getJSONObject("images");
            JSONObject imageData = imagesData.getJSONObject("low_resolution");
            result.add(imageData.getString("url"));
        }
        return result;
    }

    public String getNextPageUrl() throws JSONException{
        JSONObject pagination = result_API.getJSONObject("pagination");
        return pagination.getString("next_url");
    }
}
