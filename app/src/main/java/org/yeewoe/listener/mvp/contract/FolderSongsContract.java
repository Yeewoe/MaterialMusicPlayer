package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.model.Song;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/12.
 */

public interface FolderSongsContract {

    interface View extends BaseView{

        void showSongs(List<Song> songList);

    }

    interface Presenter extends BasePresenter<View>{

        void loadSongs(String path);

    }
}
