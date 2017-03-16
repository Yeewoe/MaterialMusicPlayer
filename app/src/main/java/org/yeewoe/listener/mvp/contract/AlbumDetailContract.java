package org.yeewoe.listener.mvp.contract;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/3.
 */

public interface AlbumDetailContract {

    interface View extends BaseView{

        Context getContext();

        void showAlbumSongs(List<Song> songList);

        void showAlbumArt(Drawable albumArt);

        void showAlbumArt(Bitmap bitmap);
    }

    interface Presenter extends BasePresenter<View>{

        void subscribe(long albumID);

        void loadAlbumSongs(long albumID);

        void loadAlbumArt(long albumID);
    }
}
