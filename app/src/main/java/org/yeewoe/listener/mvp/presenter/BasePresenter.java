package org.yeewoe.listener.mvp.presenter;

import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/11/7.
 */

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void subscribe();

    void unsubscribe();
}
