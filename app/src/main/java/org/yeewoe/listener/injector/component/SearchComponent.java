package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.SearchModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.SearchFragment;

/**
 * Created by yeewoe on 2017/1/3.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchModule.class)
public interface SearchComponent {

    void inject(SearchFragment searchFragment);
}
