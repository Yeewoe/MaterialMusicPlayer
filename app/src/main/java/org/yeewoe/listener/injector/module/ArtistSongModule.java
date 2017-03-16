package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.ArtistSongContract;
import org.yeewoe.listener.mvp.presenter.ArtistSongPresenter;
import org.yeewoe.listener.mvp.usecase.GetArtistSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/25.
 */
@Module
public class ArtistSongModule {

    @Provides
    GetArtistSongs getArtistSongsUsecase(Repository repository) {
        return new GetArtistSongs(repository);
    }

    @Provides
    ArtistSongContract.Presenter getArtistSongPresenter(GetArtistSongs getArtistSongs) {
        return new ArtistSongPresenter(getArtistSongs);
    }
}
