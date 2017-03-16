package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.PlaylistDetailContract;
import org.yeewoe.listener.mvp.presenter.PlaylistDetailPresenter;
import org.yeewoe.listener.mvp.usecase.GetPlaylistSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/6.
 */
@Module
public class PlaylistSongModule {

    @Provides
    GetPlaylistSongs getPlaylistSongsUsecase(Repository repository) {
        return new GetPlaylistSongs(repository);
    }

    @Provides
    PlaylistDetailContract.Presenter getPlaylistDetailPresenter(GetPlaylistSongs getPlaylistSongs) {
        return new PlaylistDetailPresenter(getPlaylistSongs);
    }
}
