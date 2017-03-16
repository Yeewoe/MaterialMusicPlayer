package org.yeewoe.listener.mvp.contract;

import java.util.List;

import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2017/1/3.
 */

public interface SearchContract {

    interface View extends BaseView {

        void showSearchResult(List<Object> list);

        void showEmptyView();
    }

    interface Presenter extends BasePresenter<View> {

        void search(String queryString);
    }

}
