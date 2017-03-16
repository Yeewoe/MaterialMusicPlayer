package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.AlbumSongsModel;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.AlbumDetailFragment;

/**
 * Created by yeewoe on 2016/12/3.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = AlbumSongsModel.class)
public interface AlbumSongsComponent {

    void inject(AlbumDetailFragment albumDetailFragment);

}
