package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.ArtistDetailContract;
import org.yeewoe.listener.mvp.presenter.ArtistDetailPresenter;
import org.yeewoe.listener.mvp.usecase.GetArtistInfo;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/13.
 */
@Module
public class ArtistInfoModule {

    @Provides
    GetArtistInfo getArtistInfoUsecase(Repository repository) {
        return new GetArtistInfo(repository);
    }

    @Provides
    ArtistDetailContract.Presenter getArtistDetailPresenter() {
        return new ArtistDetailPresenter();
    }
}
