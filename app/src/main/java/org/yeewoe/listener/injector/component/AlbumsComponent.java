package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ActivityModule;
import org.yeewoe.listener.injector.module.AlbumsModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.AlbumFragment;

/**
 * Created by yeewoe on 2016/11/12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AlbumsModule.class})
public interface AlbumsComponent {

    void inject(AlbumFragment albumFragment);
}
