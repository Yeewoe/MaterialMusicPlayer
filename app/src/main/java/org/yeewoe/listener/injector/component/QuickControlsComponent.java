package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ActivityModule;
import org.yeewoe.listener.injector.module.QuickControlsModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.QuickControlsFragment;

/**
 * Created by yeewoe on 2016/11/8.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, QuickControlsModule.class})
public interface QuickControlsComponent {

    void inject(QuickControlsFragment quickControlsFragment);

}
