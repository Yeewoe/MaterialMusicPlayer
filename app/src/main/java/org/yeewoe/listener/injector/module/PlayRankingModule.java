package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.PlayRankingContract;
import org.yeewoe.listener.mvp.presenter.PlayRankingPresenter;
import org.yeewoe.listener.mvp.usecase.GetSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/9.
 */
@Module
public class PlayRankingModule {

    @Provides
    GetSongs getSongsUsecase(Repository repository) {
        return new GetSongs(repository);
    }

    @Provides
    PlayRankingContract.Presenter getPlayRankingPresenter(GetSongs getSongs) {
        return new PlayRankingPresenter(getSongs);
    }
}
