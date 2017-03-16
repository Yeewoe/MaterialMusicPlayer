package org.yeewoe.listener.injector.module;

import dagger.Module;
import dagger.Provides;
import org.yeewoe.listener.mvp.contract.SearchContract;
import org.yeewoe.listener.mvp.presenter.SearchPresenter;
import org.yeewoe.listener.mvp.usecase.GetSearchResult;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2017/1/3.
 */
@Module
public class SearchModule {

    @Provides
    SearchContract.Presenter getSearchPresenter(GetSearchResult getSearchResult) {
        return new SearchPresenter(getSearchResult);
    }

    @Provides
    GetSearchResult getSearchResultUsecase(Repository repository) {
        return new GetSearchResult(repository);
    }
}
