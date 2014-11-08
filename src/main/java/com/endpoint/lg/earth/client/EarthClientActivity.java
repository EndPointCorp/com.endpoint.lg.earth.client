/*
 * Copyright (C) 2012 Google Inc.
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

import com.endpoint.lg.support.interactivespaces.ConfigurationHelper;
import com.endpoint.lg.support.window.ManagedWindow;
import com.endpoint.lg.support.window.WindowIdentity;
import com.endpoint.lg.support.window.WindowInstanceIdentity;

import interactivespaces.activity.component.binary.BasicNativeActivityComponent;
import interactivespaces.activity.impl.BaseActivity;
import interactivespaces.InteractiveSpacesException;
import interactivespaces.service.template.Templater;
import interactivespaces.service.template.TemplaterService;
import interactivespaces.util.process.NativeApplicationRunner;
import interactivespaces.util.process.restart.LimitedRetryRestartStrategy;
import interactivespaces.util.process.restart.RestartStrategy;

import java.io.File;

/**
 * An Interactive Spaces activity which starts and stops a Google Earth
 * instance.
 * 
 * <p>
 * This activity writes the Earth config templates before Earth is started up.
 * 
 * @author Keith M. Hughes
 * @author Matt Vollrath <matt@endpoint.com>
 * @author Kiel Christofferson <kiel@endpoint.com>
 * @author Wojciech Ziniewicz <wojtek@endpoint.com>
 */
public class EarthClientActivity extends BaseActivity {

  /**
   * The folder in the activity which stores the configuration templates.
   */
  private static final String CONFIG_TEMPLATES_DIRECTORY = "configTemplates";

  /**
   * Configuration key for native executable flags
   */
  private static final String CONFIG_ACTIVITY_EXECUTABLE_FLAGS =
      "space.activity.component.native.executable.flags.linux";

  /**
   * Configuration key for hidden gui
   */
  private static final String CONFIG_GUI_HIDDEN = "lg.earth.gui.hidden";

  /**
   * Configuration key for viewsync-related Earth flags
   */
  private static final String CONFIG_VIEWSYNC_FLAGS = "lg.earth.viewsync.flags";

  /**
   * Configuration key for Earth wrapper script flags
   */
  private static final String CONFIG_WRAPPER_FLAGS = "lg.earth.wrapper.flags";

  /**
   * Configuration key for SpaceNav device
   */
  private static final String CONFIG_SPACENAV_DEVICE = "lg.earth.spaceNavigator.device";

  /**
   * Configuration key for SpaceNav flags
   */
  private static final String CONFIG_SPACENAV_FLAGS = "lg.earth.spaceNavigator.flags";

  /**
   * Configuration key for number of restart attempts.
   */
  private static final String CONFIG_RESTART_ATTEMPTS = "lg.earth.restart.attempts";

  /**
   * Configuration key for delay between restart attempts.
   */
  private static final String CONFIG_RESTART_DELAY = "lg.earth.restart.delay";

  /**
   * Number of milliseconds Earth must be running for a restart to be considered
   * a success.
   */
  private static final long RESTART_SUCCESS_TIME = 10000;

  /**
   * Templater for the activity.
   */
  private Templater templater;

  /**
   * Native component for the activity, Google Earth binary
   */
  private BasicNativeActivityComponent earthComponent;

  /**
   * Restart Strategy for our activity runner
   */
  private RestartStrategy<NativeApplicationRunner> earthRestartStrategy;

  /**
   * Restart Strategy Listener for our native activity
   */
  private EarthClientRestartListener<NativeApplicationRunner> earthRestartListener;

  /**
   * WindowIdentity for the Earth Client window
   */
  private WindowInstanceIdentity windowId;

  /**
   * ManagedWindow for the Earth Client window
   */
  private ManagedWindow window;

  /**
   * Configuration template writer.
   */
  private EarthClientConfigWriter configWriter;

  /**
   * Sets up the configuration templates and adds a basic
   * NativeActivityComponent
   * 
   */
  @Override
  public void onActivitySetup() {
    // set up the config templater
    TemplaterService service =
        getSpaceEnvironment().getServiceRegistry()
            .getRequiredService(TemplaterService.SERVICE_NAME);
    File templateFolder = getActivityFilesystem().getInstallFile(CONFIG_TEMPLATES_DIRECTORY);
    getLog().info(String.format("Template folder is: %s", templateFolder.getAbsolutePath()));

    templater = service.newTemplater(templateFolder);
    getLog().info(String.format("Template service created: %s", templater));

    addManagedResource(templater);

    // set up window management
    windowId = new WindowInstanceIdentity(getUuid());
    window = new ManagedWindow(this, windowId);

    addManagedResource(window);

    File installDir = getActivityFilesystem().getInstallDirectory();

    // start building Earth command line arguments
        // XXX this doesn't deal well with controllers installed in directories
        // that would require quoting. Getting it fixed is taking far longer
        // than it's worth, for now.
    String extraEarthFlags = " --home-dir=" + installDir.getAbsolutePath() + "/earth/ ";

    extraEarthFlags += getConfigArray(CONFIG_WRAPPER_FLAGS);

    extraEarthFlags += " -- ";

    extraEarthFlags += getConfigArray(CONFIG_VIEWSYNC_FLAGS);

    // only set SpaceNav flags if configured
    if (!getConfiguration().getRequiredPropertyString(CONFIG_SPACENAV_DEVICE).equals("")) {
      extraEarthFlags += getConfigArray(CONFIG_SPACENAV_FLAGS);
    }

    extraEarthFlags += " -- ";

    // handle window name or viewport target values from activity config
    extraEarthFlags += String.format(" -name %s", getUuid());

    // handle lg.earth.gui.hidden boolean from activity config
    // NB: Yes, --hidegui appears to take two dashes, in contrast to all the other options
    if (Boolean.TRUE.equals(getConfiguration().getRequiredPropertyBoolean(CONFIG_GUI_HIDDEN))) {
      extraEarthFlags += " --hidegui";
    }

    // update activity executable flags configuration with EXTRA flags
    getConfiguration().setValue(
        CONFIG_ACTIVITY_EXECUTABLE_FLAGS,
        String.format("%s %s",
            extraEarthFlags,
            getConfigArray(CONFIG_ACTIVITY_EXECUTABLE_FLAGS)));

    // set up the configuration template writer
    EarthClientConfiguration config = new EarthClientConfiguration(getConfiguration(), installDir);

    configWriter = new EarthClientConfigWriter(config, templater);

    // set up the native component
    earthComponent = new BasicNativeActivityComponent();
    earthRestartListener = new EarthClientRestartListener<NativeApplicationRunner>(configWriter, getLog());

    int restartAttempts = getConfiguration().getRequiredPropertyInteger(CONFIG_RESTART_ATTEMPTS);
    long restartDelay = getConfiguration().getRequiredPropertyLong(CONFIG_RESTART_DELAY);

    earthRestartStrategy =
        new LimitedRetryRestartStrategy<NativeApplicationRunner>(
            restartAttempts, restartDelay, RESTART_SUCCESS_TIME, getSpaceEnvironment()
        );

    earthRestartStrategy.addRestartStrategyListener(earthRestartListener);
    addActivityComponent(earthComponent);
  }

  @Override
  public void onActivityStartup() {
    configWriter.write();
    earthComponent.getNativeActivityRunner().setRestartStrategy(earthRestartStrategy);
  }

  @Override
  public void onActivityActivate() {
    window.setVisible(true);
  }

  @Override
  public void onActivityDeactivate() {
    window.setVisible(false);
  }

  private String getConfigArray(String key) {
    return ConfigurationHelper.getConfigurationConcat(getConfiguration(), key, " ");
  }
}
