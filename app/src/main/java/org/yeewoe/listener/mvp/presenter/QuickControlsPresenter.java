package org.yeewoe.listener.mvp.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import org.yeewoe.listener.MusicPlayer;
import org.yeewoe.listener.mvp.contract.QuickControlsContract;
import org.yeewoe.listener.mvp.usecase.GetLyric;
import org.yeewoe.listener.util.ATEUtil;
import org.yeewoe.listener.util.ListenerUtil;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yeewoe on 2016/11/8.
 */

public class QuickControlsPresenter implements QuickControlsContract.Presenter {

    private GetLyric mGetLyric;
    private CompositeSubscription mCompositeSubscription;
    private QuickControlsContract.View mView;

    private boolean mDuetoplaypause = false;

    public QuickControlsPresenter(GetLyric getLyric) {
        this.mGetLyric = getLyric;
    }

    @Override
    public void attachView(QuickControlsContract.View view) {
        this.mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void onPlayPauseClick() {
        mDuetoplaypause = true;
        if (MusicPlayer.isPlaying()) {
            mView.setPlayPauseButton(false);
        } else {
            mView.setPlayPauseButton(true);
        }

        MusicPlayer.playOrPause();

    }

    @Override
    public void onPreviousClick() {
        MusicPlayer.previous(mView.getContext(), true);
    }

    @Override
    public void loadLyric() {
        mCompositeSubscription.clear();
        String title = MusicPlayer.getTrackName();
        String artist = MusicPlayer.getArtistName();
        long duration = MusicPlayer.duration();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(artist)) {
            return;
        }
        Subscription subscription = mGetLyric.execute(new GetLyric.RequestValues(title, artist, duration))
                .getLyricFile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLyric(null);
                    }

                    @Override
                    public void onNext(File file) {
                        if (file == null) {
                            mView.showLyric(null);
                        } else {
                            mView.showLyric(file);
                        }
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onNextClick() {
        MusicPlayer.next();
    }

    @Override
    public void updateNowPlayingCard() {
        if (MusicPlayer.isPlaying()) {
            if (!mView.getPlayPauseStatus()) {//true表示按钮为待暂停状态
                mView.setPlayPauseButton(true);
            }
        } else {
            if (mView.getPlayPauseStatus()) {
                mView.setPlayPauseButton(false);
            }
        }

        final String title = MusicPlayer.getTrackName();
        final String artist = MusicPlayer.getArtistName();
        mView.setTitle(title);
        mView.setArtist(artist);

        if (!mDuetoplaypause){
            Glide.with(mView.getContext())
                    .load(ListenerUtil.getAlbumArtUri(MusicPlayer.getCurrentAlbumId()).toString())
                    .asBitmap()
                    .error(ATEUtil.getDefaultAlbumDrawable(mView.getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<Bitmap>(){
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            mView.setAlbumArt(errorDrawable);
                            if (!TextUtils.isEmpty(title) || !TextUtils.isEmpty(artist)) {
                                new Palette.Builder(((BitmapDrawable) errorDrawable).getBitmap()).generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        mView.setPalette(palette);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mView.setAlbumArt(resource);
                            new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    mView.setPalette(palette);
                                }
                            });
                        }
                    });
        }

        mDuetoplaypause = false;
        mView.setProgressMax((int) MusicPlayer.duration());
        mView.startUpdateProgress();
    }
}
