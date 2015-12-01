package de.sven_torben.cqrest.representations;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This is a marker interface for commands. All commands must implement this interface.
 * <p>
 * The purpose of this interface is basically to tell Jackson to use the logical type name 
 * as type information during deserialization. The type name must be encoded as a property "@type" 
 * in JSON message, e.g.:
 * <pre><code>
 *     {
 *         "@type" : "CancelOrder",
 *         "orderId" : 12345
 *     }
 * </code></pre>
 * </p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
public interface Command {
}

