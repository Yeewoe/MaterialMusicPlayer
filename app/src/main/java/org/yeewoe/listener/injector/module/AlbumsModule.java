package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.AlbumsContract;
import org.yeewoe.listener.mvp.presenter.AlbumsPresenter;
import org.yeewoe.listener.mvp.usecase.GetAlbums;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/12.
 */
@Module
public class AlbumsModule {

    @Provides
    AlbumsContract.Presenter getAlbumsPresenter(GetAlbums getAlbums) {
        return new AlbumsPresenter(getAlbums);
    }

    @Provides
    GetAlbums getAlbumsUsecase(Repository repository) {
        return new GetAlbums(repository);
    }
}
