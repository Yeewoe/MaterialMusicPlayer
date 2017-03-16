package org.yeewoe.listener.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yeewoe on 2016/11/3.
 */

public class ArtistInfo {

    private static final String ARTIST = "artist";

    @Expose
    @SerializedName(ARTIST)
    public LastfmArtist mArtist;
}
