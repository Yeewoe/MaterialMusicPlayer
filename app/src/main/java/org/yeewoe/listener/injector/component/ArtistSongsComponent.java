package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ArtistSongModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.fragment.ArtistMusicFragment;

/**
 * Created by yeewoe on 2016/11/25.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ArtistSongModule.class)
public interface ArtistSongsComponent {

    void inject(ArtistMusicFragment artistMusicFragment);
}
