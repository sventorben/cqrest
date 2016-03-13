package de.sven_torben.cqrest.annotations.handler;

import de.sven_torben.cqrest.CommandHandlingResource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated method handles commands.
 *
 * @see CommandHandlingResource
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandHandler {
}