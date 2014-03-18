package org.djodjo.tarator.testrunner.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotates a context as being the InstrumentationContext of an instrumentation.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface InstrumentationContext { }
