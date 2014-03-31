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

import com.endpoint.lg.support.window.WindowIdentity;
import com.endpoint.lg.support.window.WindowInstanceIdentity;
import com.endpoint.lg.support.window.ManagedWindow;

import interactivespaces.activity.impl.BaseActivity;
import interactivespaces.activity.component.binary.BasicNativeActivityComponent;
import interactivespaces.service.template.Templater;
import interactivespaces.service.template.TemplaterService;
import interactivespaces.util.process.restart.RestartStrategy;
import interactivespaces.util.process.restart.LimitedRetryRestartStrategy;

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
   * Configuration key to specify window viewport
   */
  private static final String CONFIG_VIEWPORT_TARGET = "lg.window.viewport.target";

  /**
   * Configuration key to override window name
   */
  private static final String CONFIG_WINDOW_NAME = "lg.earth.window.name";

  /**
   * Configuration key for hidden gui
   */
  private static final String CONFIG_GUI_HIDDEN = "lg.earth.gui.hidden";

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
  private RestartStrategy earthRestartStrategy;

  /**
   * Restart Strategy Listener for our native activity
   */
  private EarthClientRestartListener earthRestartListener;

  /**
   * WindowIdentity for the Earth Client window
   */
  private WindowIdentity windowId;

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

    // start building Earth command line arguments
    String extraEarthFlags = "";

    // only set SpaceNav flags if configured
    if (!getConfiguration().getRequiredPropertyString(CONFIG_SPACENAV_DEVICE).equals("")) {
      extraEarthFlags += getConfiguration().getRequiredPropertyString(CONFIG_SPACENAV_FLAGS);
    }

    // handle window name or viewport target values from activity config
    if (getConfiguration().getPropertyString(CONFIG_WINDOW_NAME) != null
        && !getConfiguration().getPropertyString(CONFIG_WINDOW_NAME).isEmpty()) {
      extraEarthFlags +=
          String.format(" -name $s", getConfiguration().getPropertyString(CONFIG_WINDOW_NAME));
    } else if (getConfiguration().getPropertyString(CONFIG_VIEWPORT_TARGET) != null
        && !getConfiguration().getPropertyString(CONFIG_VIEWPORT_TARGET).isEmpty()) {
      extraEarthFlags += String.format(" -name %s", getUuid());
    }

    // handle lg.earth.gui.hidden boolean from activity config
    if (Boolean.TRUE.equals(getConfiguration().getRequiredPropertyBoolean(CONFIG_GUI_HIDDEN))) {
      extraEarthFlags += " --hidegui";
    }

    // update activity executable flags configuration with EXTRA flags
    getConfiguration().setValue(
        CONFIG_ACTIVITY_EXECUTABLE_FLAGS,
        String.format("%s %s",
            getConfiguration().getRequiredPropertyString(CONFIG_ACTIVITY_EXECUTABLE_FLAGS),
            extraEarthFlags));

    // set up the configuration template writer
    File installDir = getActivityFilesystem().getInstallDirectory();
    EarthClientConfiguration config = new EarthClientConfiguration(getConfiguration(), installDir);

    configWriter = new EarthClientConfigWriter(config, templater);

    // set up the native component
    earthComponent = new BasicNativeActivityComponent();
    earthRestartListener = new EarthClientRestartListener(window, configWriter, getLog());

    int restartAttempts = getConfiguration().getRequiredPropertyInteger(CONFIG_RESTART_ATTEMPTS);
    int restartDelay = getConfiguration().getRequiredPropertyInteger(CONFIG_RESTART_DELAY);

    earthRestartStrategy =
        new LimitedRetryRestartStrategy(restartAttempts, restartDelay, 4000, getSpaceEnvironment());

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
}
