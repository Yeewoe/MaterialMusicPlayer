package org.yeewoe.listener.mvp.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import org.yeewoe.org.yeewoe.listener.ListenerApp;
import org.yeewoe.listener.mvp.contract.ArtistDetailContract;
import org.yeewoe.listener.mvp.model.ArtistArt;
import org.yeewoe.listener.util.ATEUtil;
import org.yeewoe.listener.util.PreferencesUtility;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yeewoe on 2016/11/24.
 */

public class ArtistDetailPresenter implements ArtistDetailContract.Presenter {

    private ArtistDetailContract.View mView;
    private CompositeSubscription mCompositeSubscription;

    public ArtistDetailPresenter() {

    }

    @Override
    public void attachView(ArtistDetailContract.View view) {
        mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        throw new RuntimeException("please call subscribe(long artistID)");
    }

    @Override
    public void subscribe(long artistID) {
        loadArtistArt(artistID);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void loadArtistArt(long artistID) {
        String artistArtJson=PreferencesUtility.getInstance(mView.getContext()).getArtistArt(artistID);
        if (!TextUtils.isEmpty(artistArtJson)) {
            ArtistArt artistArt = new Gson().fromJson(artistArtJson, ArtistArt.class);
            Glide.with(ListenerApp.getContext())
                    .load(artistArt.getExtralarge())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                    .error(ATEUtil.getDefaultSingerDrawable(mView.getContext()))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            mView.showArtistArt(errorDrawable);
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            mView.showArtistArt(resource);
                        }
                    });
        }
    }

}
