package org.yeewoe.listener.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yeewoe on 2016/11/3.
 */

public class Artwork {

    private static final String URL = "#text";
    private static final String SIZE = "size";

    @SerializedName(URL)
    public String mUrl;

    @SerializedName(SIZE)
    public String mSize;
}
