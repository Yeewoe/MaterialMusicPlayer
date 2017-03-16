package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.PlayqueueSongModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.dialogs.PlayqueueDialog;

/**
 * Created by yeewoe on 2016/12/27.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PlayqueueSongModule.class)
public interface PlayqueueSongComponent {

    void inject(PlayqueueDialog playqueueDialog);
}
