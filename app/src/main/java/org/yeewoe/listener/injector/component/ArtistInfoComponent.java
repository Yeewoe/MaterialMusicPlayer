package org.yeewoe.listener.injector.component;

import dagger.Component;
import org.yeewoe.listener.injector.module.ArtistInfoModule;
import org.yeewoe.listener.injector.scope.PerActivity;
import org.yeewoe.listener.ui.adapter.ArtistAdapter;
import org.yeewoe.listener.ui.fragment.ArtistDetailFragment;

/**
 * Created by yeewoe on 2016/11/13.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ArtistInfoModule.class)
public interface ArtistInfoComponent {

    void injectForAdapter(ArtistAdapter artistAdapter);

    void injectForFragment(ArtistDetailFragment fragment);
}
