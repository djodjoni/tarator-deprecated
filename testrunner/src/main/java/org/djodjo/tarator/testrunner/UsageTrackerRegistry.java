package org.djodjo.tarator.testrunner;

import static org.djodjo.tarator.testrunner.util.Checks.checkNotNull;

/**
 * A registry to hold the global {@link UsageTracker}.
 *
 * Instrumentation will configure this registry at startup.
 */
public final class UsageTrackerRegistry {

  // By default we use a NoOp class.
  private static volatile UsageTracker instance =
      new UsageTracker.NoOpUsageTracker();

  public static void registerInstance(UsageTracker tracker) {
    instance = checkNotNull(tracker);
  }

  public static UsageTracker getInstance() {
    return instance;
  }


  private UsageTrackerRegistry() {}

}
