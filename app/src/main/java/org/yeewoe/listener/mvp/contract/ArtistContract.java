package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.model.Artist;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/11/13.
 */

public interface ArtistContract {

    interface View extends BaseView{

        void showArtists(List<Artist> artists);

        void showEmptyView();
    }

    interface Presenter extends BasePresenter<View>{

        void loadArtists(String action);
    }
}
