package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.PlayRankingModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.PlayRankingFragment;

/**
 * Created by yeewoe on 2016/12/9.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PlayRankingModule.class)
public interface PlayRankingComponent {

    void inject(PlayRankingFragment playRankingFragment);
}
