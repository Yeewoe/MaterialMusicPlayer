package org.yeewoe.listener.injector.component;

import android.app.Application;

import dagger.Component;
import org.yeewoe.org.yeewoe.listener.ListenerApp;
import org.yeewoe.listener.injector.module.ApplicationModule;
import org.yeewoe.listener.injector.module.NetworkModule;
import org.yeewoe.listener.injector.scope.PerApplication;
import org.yeewoe.listener.respository.interfaces.Repository;

/**
 * Created by yeewoe on 2016/11/3.
 */
@PerApplication
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    Application application();

    ListenerApp listenerApplication();

    Repository repository();
}
