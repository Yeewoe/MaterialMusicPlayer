package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.ArtistContract;
import org.yeewoe.listener.mvp.presenter.ArtistPresenter;
import org.yeewoe.listener.mvp.usecase.GetArtists;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/13.
 */
@Module
public class ArtistsModule {

    @Provides
    ArtistContract.Presenter getArtistPresenter(GetArtists getArtists) {
        return new ArtistPresenter(getArtists);
    }

    @Provides
    GetArtists getArtistsUsecase(Repository repository) {
        return new GetArtists(repository);
    }
}
