package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.SongsContract;
import org.yeewoe.listener.mvp.presenter.SongsPresenter;
import org.yeewoe.listener.mvp.usecase.GetSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/12.
 */
@Module
public class SongsModule {

    @Provides
    SongsContract.Presenter getSongsPresenter(GetSongs getSongs) {
        return new SongsPresenter(getSongs);
    }

    @Provides
    GetSongs getSongsUsecase(Repository repository) {
        return new GetSongs(repository);
    }
}
