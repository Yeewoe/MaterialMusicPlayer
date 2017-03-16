package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ActivityModule;
import org.yeewoe.listener.injector.module.SongsModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.SongsFragment;

/**
 * Created by yeewoe on 2016/11/12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SongsModule.class})
public interface SongsComponent {

    void inject(SongsFragment songsFragment);
}
