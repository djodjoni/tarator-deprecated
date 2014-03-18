package org.djodjo.tarator.testrunner.intento;

/**
 * An {@link android.content.Intent} that has been processed to determine the set of packages to which it resolves.
 */
public interface ResolvedIntent {

  /**
   * Returns {@code true} if this recorded intent can be handled by an activity in the given
   * package.
   */
  public boolean canBeHandledBy(String appPackage);
}
