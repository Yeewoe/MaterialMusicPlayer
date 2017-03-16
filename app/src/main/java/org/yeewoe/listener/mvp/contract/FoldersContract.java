package org.yeewoe.listener.mvp.contract;

import android.content.Context;

import java.util.List;

import org.yeewoe.listener.mvp.model.FolderInfo;
import org.yeewoe.listener.mvp.presenter.BasePresenter;
import org.yeewoe.listener.mvp.view.BaseView;

/**
 * Created by yeewoe on 2016/12/11.
 */

public interface FoldersContract {

    interface View extends BaseView{

        Context getContext();

        void showEmptyView();

        void showFolders(List<FolderInfo> folderInfos);
    }

    interface Presenter extends BasePresenter<View>{

        void loadFolders();
    }
}
