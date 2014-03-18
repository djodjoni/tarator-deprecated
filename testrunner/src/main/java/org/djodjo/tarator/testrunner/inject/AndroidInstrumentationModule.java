package org.djodjo.tarator.testrunner.inject;

import android.app.Instrumentation;
import android.content.Context;

import org.djodjo.tarator.testrunner.ActivityLifecycleMonitor;
import org.djodjo.tarator.testrunner.ActivityLifecycleMonitorRegistry;
import org.djodjo.tarator.testrunner.InstrumentationRegistry;

import dagger.Module;
import dagger.Provides;

import static com.google.common.base.Preconditions.checkNotNull;

@Module(library = true)
public class AndroidInstrumentationModule {

  private final ActivityLifecycleMonitor lifecycleMonitor;
  private final Instrumentation instrumentation;

  public AndroidInstrumentationModule() {
    this(ActivityLifecycleMonitorRegistry.getInstance(),
        InstrumentationRegistry.getInstance());
  }

  public AndroidInstrumentationModule(ActivityLifecycleMonitor lifecycleMonitor,
      Instrumentation instrumentation) {
    this.lifecycleMonitor = checkNotNull(lifecycleMonitor);
    this.instrumentation = checkNotNull(instrumentation);
  }

  @Provides
  public ActivityLifecycleMonitor provideLifecycleMonitor() {
    return lifecycleMonitor;
  }

  @Provides
  public Instrumentation provideInstrumentation() {
    return instrumentation;
  }

  @Provides
  @TargetContext
  public Context provideTargetContext(Instrumentation instrumentation) {
    return instrumentation.getTargetContext();
  }

  @Provides
  @InstrumentationContext
  public Context provideInstrumentationContext(Instrumentation instrumentation) {
    return instrumentation.getContext();
  }

}
