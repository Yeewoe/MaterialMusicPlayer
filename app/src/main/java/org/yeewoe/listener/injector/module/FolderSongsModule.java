package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.FolderSongsContract;
import org.yeewoe.listener.mvp.presenter.FolderSongsPresenter;
import org.yeewoe.listener.mvp.usecase.GetFolderSongs;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/12.
 */
@Module
public class FolderSongsModule {

    @Provides
    GetFolderSongs getFolderSongsUsecase(Repository repository) {
        return new GetFolderSongs(repository);
    }

    @Provides
    FolderSongsContract.Presenter getFolderSongsPresenter(GetFolderSongs getFolderSongs) {
        return new FolderSongsPresenter(getFolderSongs);
    }
}
