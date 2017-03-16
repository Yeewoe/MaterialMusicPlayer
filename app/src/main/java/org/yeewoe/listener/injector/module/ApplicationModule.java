package org.yeewoe.listener.injector.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

import org.yeewoe.org.yeewoe.listener.ListenerApp;
import org.yeewoe.listener.injector.scope.PerApplication;

/**
 * Created by yeewoe on 2016/11/1.
 */
@Module
public class ApplicationModule {
    private final ListenerApp mListenerApp;

    public ApplicationModule(ListenerApp listenerApp) {
        this.mListenerApp = listenerApp;
    }

    @Provides
    @PerApplication
    public ListenerApp provideListenerApp() {
        return mListenerApp;
    }

    @Provides
    @PerApplication
    public Application provideApplication() {
        return mListenerApp;
    }
}
