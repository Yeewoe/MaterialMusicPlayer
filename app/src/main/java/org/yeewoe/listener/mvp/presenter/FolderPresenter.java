package org.yeewoe.listener.mvp.presenter;

import java.util.List;

import org.yeewoe.listener.mvp.contract.FoldersContract;
import org.yeewoe.listener.mvp.model.FolderInfo;
import org.yeewoe.listener.mvp.usecase.GetFolders;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yeewoe on 2016/12/11.
 */

public class FolderPresenter implements FoldersContract.Presenter{

    private GetFolders mUsecase;
    private FoldersContract.View mView;
    private CompositeSubscription mCompositeSubscription;

    public FolderPresenter(GetFolders getFolders) {
        this.mUsecase = getFolders;
    }

    @Override
    public void attachView(FoldersContract.View view) {
        this.mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        loadFolders();
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void loadFolders() {
        mCompositeSubscription.clear();
        Subscription subscription = mUsecase.execute(new GetFolders.RequestValues())
                .getFolderList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FolderInfo>>() {
                    @Override
                    public void call(List<FolderInfo> folderInfos) {
                        if (folderInfos == null || folderInfos.size() == 0) {
                            mView.showEmptyView();
                        } else {
                            mView.showFolders(folderInfos);
                        }
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
