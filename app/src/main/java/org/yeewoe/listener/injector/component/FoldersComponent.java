package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.FolderModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.FoldersFragment;

/**
 * Created by yeewoe on 2016/12/11.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FolderModule.class)
public interface FoldersComponent {

    void inject(FoldersFragment foldersFragment);
}
