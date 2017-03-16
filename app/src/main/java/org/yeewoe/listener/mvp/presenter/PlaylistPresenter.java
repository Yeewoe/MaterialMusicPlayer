package org.yeewoe.listener.mvp.presenter;

import java.util.List;

import org.yeewoe.listener.mvp.contract.PlaylistContract;
import org.yeewoe.listener.mvp.model.Playlist;
import org.yeewoe.listener.mvp.usecase.GetPlaylists;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yeewoe on 2016/12/4.
 */

public class PlaylistPresenter implements PlaylistContract.Presenter{

    private GetPlaylists mUsecase;
    private PlaylistContract.View mView;
    private CompositeSubscription mCompositeSubscription;

    public PlaylistPresenter(GetPlaylists getPlaylists) {
        mUsecase = getPlaylists;
    }

    @Override
    public void attachView(PlaylistContract.View view) {
        mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        loadPlaylist();
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void loadPlaylist() {
        mCompositeSubscription.clear();
        Subscription subscription = mUsecase.execute(new GetPlaylists.RequestValues(false))
                .getPlaylists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Playlist>>() {
                    @Override
                    public void call(List<Playlist> playlists) {
                        if (playlists == null || playlists.size() == 0) {
                            mView.showEmptyView();
                        } else {
                            mView.showPlaylist(playlists);
                        }
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
