package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.PlayqueueSongContract;
import org.yeewoe.listener.mvp.presenter.PlayqueueSongPresenter;
import org.yeewoe.listener.mvp.usecase.GetSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/27.
 */
@Module
public class PlayqueueSongModule {

    @Provides
    GetSongs getSongsUsecase(Repository repository) {
        return new GetSongs(repository);
    }

    @Provides
    PlayqueueSongContract.Presenter getPlayqueueSongUsecase(GetSongs getSongs) {
        return new PlayqueueSongPresenter(getSongs);
    }
}
