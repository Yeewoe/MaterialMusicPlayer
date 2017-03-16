package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/11/12.
 */

public interface SongsContract {

    interface View extends BaseView {

        void showSongs(List<Song> songList);

        void showEmptyView();
    }

    interface Presenter extends BasePresenter<View>{

        void loadSongs(String action);
    }
}
