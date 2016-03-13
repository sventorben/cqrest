package de.sven_torben.cqrest;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

final class OptionsInfo extends ConcurrentHashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  public OptionsInfo(final Collection<CommandInfo> commandsInfos) {
    this.put("supportedCommands", commandsInfos);
  }

}
