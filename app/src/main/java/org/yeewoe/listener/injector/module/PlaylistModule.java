package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.PlaylistContract;
import org.yeewoe.listener.mvp.presenter.PlaylistPresenter;
import org.yeewoe.listener.mvp.usecase.GetPlaylists;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/5.
 */
@Module
public class PlaylistModule {

    @Provides
    GetPlaylists getPlaylistUsecase(Repository repository) {
        return new GetPlaylists(repository);
    }

    @Provides
    PlaylistContract.Presenter getPlaylistPresenter(GetPlaylists getPlaylists) {
        return new PlaylistPresenter(getPlaylists);
    }
}
