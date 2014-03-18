package org.djodjo.tarator.testrunner;

import android.app.Instrumentation;

import java.util.concurrent.atomic.AtomicReference;

import static org.djodjo.tarator.testrunner.util.Checks.checkNotNull;

/**
 * Holds a reference to the instrumentation running the JVM.
 */
public final class InstrumentationRegistry {

  private static final AtomicReference<Instrumentation> instrumentationRef =
      new AtomicReference<Instrumentation>(null);

  /**
   * Returns the instrumentation currently running the VM
   *
   * @throws IllegalStateException if instrumentation hasn't been registred
   */
  public static Instrumentation getInstance() {
    return checkNotNull(instrumentationRef.get(), "No instrumentation registered. " +
        "Must run under a registering instrumentation.");
  }

  /**
   * Records/exposes the instrumentation currently running in the vm.
   *
   * This is a global registry - so be aware of the impact of calling this method!
   */
  public static void registerInstance(Instrumentation instrumentation) {
    instrumentationRef.set(instrumentation);
  }

  private InstrumentationRegistry() { }
}
