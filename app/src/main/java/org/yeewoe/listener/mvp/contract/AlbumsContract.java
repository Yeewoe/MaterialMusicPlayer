package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.model.Album;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/11/12.
 */

public interface AlbumsContract {

    interface View extends BaseView{

        void showAlbums(List<Album> albumList);

        void showEmptyView();
    }

    interface Presenter extends BasePresenter<View>{

        void loadAlbums(String action);

    }
}
