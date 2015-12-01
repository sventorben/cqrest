package de.sven_torben.cqrest.annotations.handler;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.sven_torben.cqrest.CommandHandlingResource;
import de.sven_torben.cqrest.representations.Command;

/**
 * Indicates that the annotated method handles {@link Command Commands}.
 *
 * @see Command
 * @see CommandHandlingResource
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandHandler {
}