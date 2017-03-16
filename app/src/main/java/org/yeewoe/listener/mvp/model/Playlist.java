package org.yeewoe.listener.mvp.model;

/**
 * Created by yeewoe on 2016/11/3.
 */

public class Playlist {

    public final long id;
    public final String name;
    public final int songCount;

    public Playlist() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
    }

    public Playlist(long _id, String _name, int _songCount) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
    }

}
