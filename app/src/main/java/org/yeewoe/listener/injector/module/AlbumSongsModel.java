package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.AlbumDetailContract;
import org.yeewoe.listener.mvp.presenter.AlbumDetailPresenter;
import org.yeewoe.listener.mvp.usecase.GetAlbumSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/3.
 */
@Module
public class AlbumSongsModel {

    @Provides
    GetAlbumSongs getAlbumSongUsecase(Repository repository) {
        return new GetAlbumSongs(repository);
    }

    @Provides
    AlbumDetailContract.Presenter getAlbumDetailPresenter(GetAlbumSongs getAlbumSongs) {
        return new AlbumDetailPresenter(getAlbumSongs);
    }
}
