package com.example.oladapo.instagram;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


/**
 * This class Wraps the Instagram's API to get the images URL
 */
public class My_Instagram_API {
    static String endPointURL = "https://api.instagram.com/v1/tags/selfie/media/recent/?client_id=acd4873fd2a54a859b882abb7e269524";
    static List<Interface_API> listeners;
    static boolean stillLoading;
    private static RequestQueue mRequestQueue;

    static {
        stillLoading = false;
        listeners = new ArrayList<Interface_API>();
    }

    private static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(MainActivity_instagram.getApplicationStaticContext());
        }
        return mRequestQueue;
    }

    private static <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public static void addEventsListener(Interface_API iInstagramAPI){
        if(listeners.indexOf(iInstagramAPI) > -1) return;
        listeners.add(iInstagramAPI);
    }

    public static void dispatchSuccessEvent(List<String> imagesUrl, String nextPageUrl){
        stillLoading = false;
        endPointURL = nextPageUrl;
        for (Interface_API iInstagramAPI : listeners)
            iInstagramAPI.onSuccess(imagesUrl, nextPageUrl);
    }

    public static void dispatchFailEvent(Exception exception){
        stillLoading = false;
        for (Interface_API iInstagramAPI : listeners)
            iInstagramAPI.onFail(exception);
    }

    /**
     * This method request more images when is needed.
     */
    public static void requestNextPageAsync(){
        if(stillLoading)return;
        stillLoading = true;
        VolleyEventImp volleyEventImp = new VolleyEventImp();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, endPointURL, null, volleyEventImp, volleyEventImp);
        addToRequestQueue(request);
    }
}


