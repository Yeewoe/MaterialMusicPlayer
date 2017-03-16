package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.PlaylistSongModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.PlaylistDetailFragment;

/**
 * Created by yeewoe on 2016/12/6.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PlaylistSongModule.class)
public interface PlaylistSongComponent {

    void inject(PlaylistDetailFragment playlistDetailFragment);
}
