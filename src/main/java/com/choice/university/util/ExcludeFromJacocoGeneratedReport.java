package com.choice.university.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate a class/method should be skipped from the Jacoco Test Coverage
 * verification. The annotation is meant to be used in places where a Unit test is not possible or
 * necessary, but the report forcefully demands one. Be very mindful about using the annotation.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeFromJacocoGeneratedReport {

}