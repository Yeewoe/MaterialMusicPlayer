package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.FolderSongsModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.FolderSongsFragment;

/**
 * Created by yeewoe on 2016/12/12.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FolderSongsModule.class)
public interface FolderSongsComponent {

    void inject(FolderSongsFragment folderSongsFragment);
}
