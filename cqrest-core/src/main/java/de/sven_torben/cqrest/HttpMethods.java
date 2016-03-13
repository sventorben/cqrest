package de.sven_torben.cqrest;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum HttpMethods {

  COMMAND("COMMAND"), QUERY("QUERY"), OPTIONS("OPTIONS");

  public static final Set<String> ALL = EnumSet.allOf(HttpMethods.class).stream()
      .map(m -> m.asString()).collect(Collectors.toSet());

  private final String method;

  private HttpMethods(final String method) {
    this.method = method;
  }

  public final String asString() {
    return method;
  }
}
