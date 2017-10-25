package com.github.niltsiar.ultimatescrobbler.di;

import com.github.niltsiar.ultimatescrobbler.UltimateScrobblerApplication;
import com.github.niltsiar.ultimatescrobbler.di.module.ActivityBindingModule;
import com.github.niltsiar.ultimatescrobbler.di.module.ApplicationModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {ApplicationModule.class, ActivityBindingModule.class, AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<UltimateScrobblerApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<UltimateScrobblerApplication> {
    }
}
