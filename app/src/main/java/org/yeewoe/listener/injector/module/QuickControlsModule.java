package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.QuickControlsContract;
import org.yeewoe.listener.mvp.presenter.QuickControlsPresenter;
import org.yeewoe.listener.mvp.usecase.GetLyric;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/7.
 */
@Module
public class QuickControlsModule {

    @Provides
    QuickControlsContract.Presenter getQuickControlsPresenter(GetLyric getLyric) {
        return new QuickControlsPresenter(getLyric);
    }

    @Provides
    GetLyric getLyricUsecase(Repository repository) {
        return new GetLyric(repository);
    }

}
