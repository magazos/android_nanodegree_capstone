package com.github.niltsiar.ultimatescrobbler.di;

import android.app.Application;
import com.github.niltsiar.ultimatescrobbler.UltimateScrobblerApplication;
import com.github.niltsiar.ultimatescrobbler.di.module.ActivityBindingModule;
import com.github.niltsiar.ultimatescrobbler.di.module.ApplicationModule;
import com.github.niltsiar.ultimatescrobbler.di.module.NetworkModule;
import com.github.niltsiar.ultimatescrobbler.di.module.ServiceBindingModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {ApplicationModule.class, ActivityBindingModule.class, ServiceBindingModule.class, AndroidSupportInjectionModule.class, NetworkModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(UltimateScrobblerApplication ultimateScrobblerApplication);
}
