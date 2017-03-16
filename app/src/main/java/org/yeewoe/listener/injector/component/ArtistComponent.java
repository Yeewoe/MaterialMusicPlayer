package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ActivityModule;
import org.yeewoe.listener.injector.module.ArtistsModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.ArtistFragment;

/**
 * Created by yeewoe on 2016/11/13.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ArtistsModule.class})
public interface ArtistComponent {

    void inject(ArtistFragment artistFragment);
}
