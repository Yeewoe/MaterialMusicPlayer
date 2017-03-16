package org.yeewoe.listener.mvp.contract;

import android.content.Context;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/27.
 */

public interface PlayqueueSongContract {

    interface View extends BaseView{

        Context getContext();

        void showSongs(List<Song> songs);

    }

    interface Presenter extends BasePresenter<View>{

        void loadSongs();
    }
}
