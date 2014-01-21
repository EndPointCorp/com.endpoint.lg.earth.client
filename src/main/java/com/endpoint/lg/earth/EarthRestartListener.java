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

package com.endpoint.lg.earth;

import interactivespaces.InteractiveSpacesException;
import interactivespaces.configuration.Configuration;
import interactivespaces.util.process.restart.Restartable;
import interactivespaces.util.process.restart.RestartStrategy;
import interactivespaces.util.process.restart.RestartStrategyListener;

import com.google.common.collect.Lists;

import org.apache.commons.logging.Log;

import java.io.File;
import interactivespaces.util.io.FileSupport;
import interactivespaces.util.io.FileSupportImpl;

/**
 * RestartStrategy Listener implementation for Google Earth NativeRunner. 
 *
 * Every time Earth starts, we want the underlying configuration/cache to be in
 * a known state.
 *
 * @author Kiel Christofferson <kiel@endpoint.com>
 */
public class EarthRestartListener implements RestartStrategyListener {
  /**
   * Configuration from the activity
   */
  private final Configuration config;

  /**
   * Log for the listener
   */
  private final Log log;

  /**
   * TempDirq for Earth
   */
  private File earthTempDirectory;

  /**
   * CacheDir for Earth
   */
  private File earthCacheDirectory;

  /**
   * The file support to use for file operations
   */
  private final FileSupport fileSupport = FileSupportImpl.INSTANCE;

  /**
   * Restart vote boolean
   */
  private boolean returnVote;

  /**
   * Construct the listener
   *
   * @param log
   */
  public EarthRestartListener(Configuration config, Log log) {
    this.config = config;
    this.log = log;
  }

  /**
   * Restart attempt callback
   *   purge cache directories
   */
  @Override
  public boolean onRestartAttempt(RestartStrategy strategy, Restartable restartable, boolean restartVote) {
    log.info("Earth RestartStrategy Attempt");

    // these need to come from the config
    earthTempDirectory = new File("/home/lg/.googleearth/Temp");
    earthCacheDirectory = new File("/home/lg/.googleearth/Cache");
    fileSupport.directoryExists(earthTempDirectory, "GE Temp directory failure");
    fileSupport.deleteDirectoryContents(earthTempDirectory);
    fileSupport.directoryExists(earthCacheDirectory, "GE Cache directory failure");
    fileSupport.deleteDirectoryContents(earthCacheDirectory);

    // would we ever vote AGAINST restarting Earth?
    returnVote = true;
    return returnVote;
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
