package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.FoldersContract;
import org.yeewoe.listener.mvp.presenter.FolderPresenter;
import org.yeewoe.listener.mvp.usecase.GetFolders;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/12/11.
 */
@Module
public class FolderModule {

    @Provides
    GetFolders getFoldersUsecase(Repository repository) {
        return new GetFolders(repository);
    }

    @Provides
    FoldersContract.Presenter getFoldersPresenter(GetFolders getFolders) {
        return new FolderPresenter(getFolders);
    }
}
