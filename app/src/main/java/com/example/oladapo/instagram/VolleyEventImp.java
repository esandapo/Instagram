package com.example.oladapo.instagram;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * This class is used to implement and handle the Volley events.
 */
public class VolleyEventImp implements Response.Listener<JSONObject>, Response.ErrorListener  {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        My_Instagram_API.dispatchFailEvent(volleyError);
    }

    @Override
    public void onResponse(JSONObject ApiResult) {
        getData getData = new getData(ApiResult);
        try{
            List<String> imagesUrl = getData.getImagesUrl();
            String nextPageUrl = getData.getNextPageUrl();
            My_Instagram_API.dispatchSuccessEvent(imagesUrl, nextPageUrl);
        }catch (JSONException exception){
            My_Instagram_API.dispatchFailEvent(exception);
        }
    }
}
