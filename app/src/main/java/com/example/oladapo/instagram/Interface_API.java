package com.example.oladapo.instagram;

import java.util.List;

public interface Interface_API {
    public void onSuccess(List<String> imagesUrl, String nextPageUrl);
    public void onFail(Exception exception);
}
