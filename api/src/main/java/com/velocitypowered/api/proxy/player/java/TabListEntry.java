/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * The Velocity API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the api top-level directory.
 */

package com.velocitypowered.api.proxy.player.java;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents a single entry in a {@link TabList}, specifically for Java players only.
 */
public interface TabListEntry {

  /**
   * Returns the parent {@link TabList} of this {@code this} {@link TabListEntry}.
   *
   * @return parent {@link TabList}
   */
  TabList parent();

  /**
   * Returns the {@link JavaPlayerIdentity} of the entry, which uniquely identifies the entry with the
   * containing {@link java.util.UUID}, as well as deciding what is shown as the player head in the
   * tab list.
   *
   * @return {@link JavaPlayerIdentity} of the entry
   */
  JavaPlayerIdentity gameProfile();

  /**
   * Returns an optional text {@link Component}, which if present is the text
   * displayed for {@code this} entry in the {@link TabList}, otherwise
   * {@link JavaPlayerIdentity#name()} is shown.
   *
   * @return text {@link Component} of name displayed in the tab list
   */
  @Nullable Component displayName();

  /**
   * Sets the text {@link Component} to be displayed for {@code this} {@link TabListEntry}. If
   * {@code null}, {@link JavaPlayerIdentity#name()} will be shown.
   *
   * @param displayName to show in the {@link TabList} for {@code this} entry
   * @return {@code this}, for chaining
   */
  TabListEntry setDisplayName(@Nullable Component displayName);

  /**
   * Returns the latency for {@code this} entry.
   *
   * <p>The icon shown in the tab list is calculated by the latency as follows:</p>
   *
   * <ul>
   *  <li>A negative latency will display the no connection icon</li>
   *  <li>0-150 will display 5 bars</li>
   *  <li>150-300 will display 4 bars</li>
   *  <li>300-600 will display 3 bars</li>
   *  <li>600-1000 will display 2 bars</li>
   *  <li>A latency move than 1 second will display 1 bar</li>
   * </ul>
   *
   * @return latency set for {@code this} entry
   */
  int ping();

  /**
   * Sets the latency for {@code this} entry to the specified value.
   *
   * @param latency to changed to
   * @return {@code this}, for chaining
   * @see #ping()
   */
  TabListEntry setPing(int latency);

  /**
   * Gets the game mode {@code this} entry has been set to.
   *
   * <p>The number corresponds to the game mode in the following way:</p>
   * <ol start="0">
   * <li>Survival</li>
   * <li>Creative</li>
   * <li>Adventure</li>
   * <li>Spectator</li>
   * </ol>
   *
   * @return the game mode
   */
  int gameMode();

  /**
   * Sets the game mode for {@code this} entry to the specified value.
   *
   * @param gameMode to change to
   * @return {@code this}, for chaining
   * @see #gameMode()
   */
  TabListEntry setGameMode(int gameMode);

  /**
   * Returns a {@link Builder} to create a {@link TabListEntry}.
   *
   * @return {@link TabListEntry} builder
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * Represents a builder which creates {@link TabListEntry}s.
   *
   * @see TabListEntry
   */
  class Builder {

    private @Nullable TabList tabList;
    private @Nullable JavaPlayerIdentity profile;
    private @Nullable Component displayName;
    private int latency = 0;
    private int gameMode = 0;

    private Builder() {
    }

    /**
     * Sets the parent {@link TabList} for this entry, the entry will only be able to be added to
     * that specific {@link TabList}.
     *
     * @param tabList to set
     * @return {@code this}, for chaining
     */
    public Builder tabList(TabList tabList) {
      this.tabList = tabList;
      return this;
    }

    /**
     * Sets the {@link JavaPlayerIdentity} of the {@link TabListEntry}.
     *
     * @param profile to set
     * @return {@code this}, for chaining
     * @see TabListEntry#gameProfile()
     */
    public Builder profile(JavaPlayerIdentity profile) {
      this.profile = profile;
      return this;
    }

    /**
     * Sets the displayed name of the {@link TabListEntry}.
     *
     * @param displayName to set
     * @return {@code this}, for chaining
     * @see TabListEntry#displayName()
     */
    public Builder displayName(@Nullable Component displayName) {
      this.displayName = displayName;
      return this;
    }

    /**
     * Sets the latency of the {@link TabListEntry}.
     *
     * @param latency to set
     * @return {@code this}, for chaining
     * @see TabListEntry#ping()
     */
    public Builder latency(int latency) {
      this.latency = latency;
      return this;
    }

    /**
     * Sets the game mode of the {@link TabListEntry}.
     *
     * @param gameMode to set
     * @return {@code this}, for chaining
     * @see TabListEntry#gameMode()
     */
    public Builder gameMode(int gameMode) {
      this.gameMode = gameMode;
      return this;
    }

    /**
     * Constructs the {@link TabListEntry} specified by {@code this} {@link Builder}.
     *
     * @return the constructed {@link TabListEntry}
     */
    public TabListEntry build() {
      if (tabList == null) {
        throw new IllegalStateException("The Tablist must be set when building a TabListEntry");
      }
      if (profile == null) {
        throw new IllegalStateException("The GameProfile must be set when building a TabListEntry");
      }
      return tabList.buildEntry(profile, displayName, latency, gameMode);
    }
  }
}