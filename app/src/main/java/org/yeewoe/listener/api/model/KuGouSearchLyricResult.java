package org.yeewoe.listener.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yeewoe on 2017/1/20.
 */

public class KuGouSearchLyricResult {

    private static final String INFO = "info";
    private static final String STATUS = "status";
    private static final String PROPOSAL = "proposal";
    private static final String KEYWORD = "keyword";
    private static final String CANDIDATES = "candidates";

    @SerializedName(INFO)
    public String info;

    @SerializedName(STATUS)
    public int status;

    @SerializedName(PROPOSAL)
    public String proposal;

    @SerializedName(KEYWORD)
    public String keyword;

    @SerializedName(CANDIDATES)
    public List<Candidates> candidates;


    public static class Candidates {

        private static final String NICKNAME = "nickname";
        private static final String ACCESSKEY = "accesskey";
        private static final String SCORE = "score";
        private static final String DURATION = "duration";
        private static final String UID = "uid";
        private static final String SONG = "song";
        private static final String ID = "id";
        private static final String SINGER = "singer";
        private static final String LANGUAGE = "language";

        @SerializedName(NICKNAME)
        public String nickname;

        @SerializedName(ACCESSKEY)
        public String accesskey;

        @SerializedName(SCORE)
        public int score;

        @SerializedName(DURATION)
        public long duration;

        @SerializedName(UID)
        public String uid;

        @SerializedName(SONG)
        public String songName;

        @SerializedName(ID)
        public String id;

        @SerializedName(SINGER)
        public String singer;

        @SerializedName(LANGUAGE)
        public String language;

    }
}
