/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.endpoint.lg.earth.client;

import interactivespaces.util.process.restart.Restartable;
import interactivespaces.util.process.restart.RestartStrategy;
import interactivespaces.util.process.restart.RestartStrategyListener;

import org.apache.commons.logging.Log;

/**
 * RestartStrategy Listener implementation for Google Earth NativeRunner.
 * 
 * Every time Earth starts, we want the underlying configuration/cache to be in
 * a known state.
 * 
 * @author Kiel Christofferson <kiel@endpoint.com>
 */
public class EarthClientRestartListener<T extends Restartable> implements RestartStrategyListener {
  /**
   * Configuration template writer from the activity
   */
  private final EarthClientConfigWriter configWriter;

  /**
   * Log for the listener
   */
  private final Log log;

  /**
   * Construct the listener
   * 
   * @param window
   * @param configWriter
   * @param log
   */
  public EarthClientRestartListener(EarthClientConfigWriter configWriter, Log log) {
    this.configWriter = configWriter;
    this.log = log;
  }

  /**
   * Restart attempt callback purge cache directories and re-manage the window
   */
  @Override
  public boolean onRestartAttempt(RestartStrategy strategy, Restartable restartable,
      boolean restartVote) {
    log.info("Earth RestartStrategy Attempt");

    // re-write the configuration templates
    configWriter.write();

    // would we ever vote AGAINST restarting Earth?
    return true;
  }

  /**
   * Restart success callback
   */
  @Override
  public void onRestartSuccess(RestartStrategy strategy, Restartable restartable) {
    log.info("Earth RestartStrategy Success");
  }

  /**
   * Restart failure callback
   */
  @Override
  public void onRestartFailure(RestartStrategy strategy, Restartable restartable) {
    log.info("Earth RestartStrategy Failure");
  }
}
