package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.model.Playlist;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/4.
 */

public interface PlaylistContract {

    interface View extends BaseView{

        void showPlaylist(List<Playlist> playlists);

        void showEmptyView();

    }

    interface Presenter extends BasePresenter<View>{

        void loadPlaylist();
    }
}
