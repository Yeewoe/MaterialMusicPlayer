package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.PlaylistModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.PlaylistFragment;

/**
 * Created by yeewoe on 2016/12/5.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PlaylistModule.class)
public interface PlaylistComponent {

    void inject(PlaylistFragment playlistFragment);
}
