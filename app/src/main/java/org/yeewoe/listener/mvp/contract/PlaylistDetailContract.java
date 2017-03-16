package org.yeewoe.listener.mvp.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/5.
 */

public interface PlaylistDetailContract {

    interface View extends BaseView{

        Context getContext();

        void showPlaylistSongs(List<Song> songList);

        void showPlaylistArt(Drawable playlistArt);

        void showPlaylistArt(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter<View>{

        void loadPlaylistSongs(long playlistID);

        void loadPlaylistArt(long firstAlbumID);
    }
}
