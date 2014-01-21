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

import interactivespaces.activity.impl.ros.BaseRoutableRosActivity;
import interactivespaces.activity.component.binary.NativeActivityComponent;
import interactivespaces.service.template.Templater;
import interactivespaces.service.template.TemplaterService;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.process.restart.RestartStrategy;
import interactivespaces.util.process.restart.LimitedRetryRestartStrategy;

import java.io.File;
import java.util.Map;

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
 */
public class EarthClientActivity extends BaseRoutableRosActivity {

  /**
   * The folder in the activity which stores the configuration templates.
   */
  public static final String CONFIG_TEMPLATES_DIRECTORY = "configTemplates";

  /**
   * Templater for the activity.
   */
  private Templater templater;

  /**
   * Native component for the activity, Google Earth binary
   */
  private NativeActivityComponent earthComponent;

  /**
   * Restart Strategy for our activity runner
   */
  private RestartStrategy earthRestartStrategy;

  /**
   * Restart Strategy Listener for our native activity
   */
  private EarthClientRestartListener earthRestartListener;

  /**
   * Sets up the configuration templates and adds a basic
   * NativeActivityComponent
   */
  @Override
  public void onActivitySetup() {
    TemplaterService service =
      getSpaceEnvironment().getServiceRegistry().getRequiredService(TemplaterService.SERVICE_NAME);
    File templateFolder = getActivityFilesystem().getInstallFile(CONFIG_TEMPLATES_DIRECTORY);
    getLog().info(String.format("Template folder is %s", templateFolder.getAbsolutePath()));

    templater = service.newTemplater(templateFolder);
    getLog().info(String.format("Template service created: %s", templater));

    addManagedResource(templater);

    // need to process "lg.earth.gui.hidden" config var and prepend --hidegui, then
    //earthComponent = new NativeActivityComponent(executablePathProperty, executableFlagsProperty);
    earthComponent = new NativeActivityComponent();
    earthRestartListener = new EarthClientRestartListener(getConfiguration(), getLog());
    earthRestartStrategy = new LimitedRetryRestartStrategy(4,1000,4000,getSpaceEnvironment());

    earthRestartStrategy.addRestartStrategyListener(earthRestartListener);
    addActivityComponent(earthComponent);
  }

  /**
   * Activity Startup
   *
   * TODO: move pieces into Setup() when 1.5.4 is released
   */
  @Override
  public void onActivityStartup() {
    writeEarthConfigs();
    earthComponent.getNativeActivityRunner().setRestartStrategy(earthRestartStrategy);
  }

  /**
   * Handles configuration changes.
   *
   * @param update configuration updates
   */
  @Override
  public void onActivityConfigurationUpdate(Map<String, Object> update) {
    writeEarthConfigs();
  }

  /**
   * Writes out the Earth Configs.
   */
  private void writeEarthConfigs() {
    Map<String, String> env = System.getenv();
    EarthClientConfiguration earthConfig = new EarthClientConfiguration(getConfiguration());

    JsonBuilder builder = new JsonBuilder();
    builder.put("ge", earthConfig);

    getLog().info(builder.toString());

    templater.writeTemplate( "GoogleEarthPlus.conf.ftl", builder.build(), new File(
          String.format( "%s/%s", env.get("HOME"), ".config/Google/GoogleEarthPlus.conf") ));
    templater.writeTemplate( "GECommonSettings.conf.ftl", builder.build(), new File(
          String.format("%s/%s", env.get("HOME"), ".config/Google/GECommonSettings.conf") ));
    templater.writeTemplate( "myplaces.kml.ftl", builder.build(), new File(
          String.format("%s/%s", env.get("HOME"), ".googleearth/myplaces.kml")));
    templater.writeTemplate( "cached_default_view.kml.ftl", builder.build(), new File(
          String.format("%s/%s", env.get("HOME"), ".googleearth/cached_default_view.kml") ));
  }
}
