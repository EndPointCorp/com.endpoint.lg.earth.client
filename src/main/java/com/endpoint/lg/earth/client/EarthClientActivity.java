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
import com.endpoint.lg.support.viewsync.EarthViewSyncState;

import interactivespaces.activity.impl.ros.BaseRoutableRosActivity;
import interactivespaces.activity.binary.NativeActivityRunnerFactory;
import interactivespaces.activity.binary.NativeApplicationRunner;
import interactivespaces.activity.component.binary.BasicNativeActivityComponent;
import interactivespaces.service.comm.network.client.UdpBroadcastClientNetworkCommunicationEndpoint;
import interactivespaces.service.comm.network.client.UdpBroadcastClientNetworkCommunicationEndpointListener;
import interactivespaces.service.comm.network.client.UdpClientNetworkCommunicationEndpointService;
import interactivespaces.service.template.Templater;
import interactivespaces.service.template.TemplaterService;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.process.restart.RestartStrategy;
import interactivespaces.util.process.restart.LimitedRetryRestartStrategy;
import interactivespaces.util.io.FileSupport;
import interactivespaces.util.io.FileSupportImpl;

import com.google.common.collect.Maps;

import java.io.File;
import java.util.Map;

import java.net.InetSocketAddress;

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
public class EarthClientActivity extends BaseRoutableRosActivity {

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
   * Configuration key for viewsync "send"
   */
  private static final String CONFIG_VIEWSYNC_SEND = "lg.earth.viewSync.send";

  /**
   * Configuration key for viewsync "port"
   */
  private static final String CONFIG_VIEWSYNC_PORT = "lg.earth.viewSync.port";

  /**
   * Configuration key for viewsync listener port
   */
  private static final String CONFIG_VIEWSYNC_LISTENER_PORT = "lg.earth.viewSync.listenerPort";

  /**
   * Configuration key for SpaceNav device
   */
  private static final String CONFIG_SPACENAV_DEVICE = "lg.earth.spaceNavigator.device";

  /**
   * Configuration key for SpaceNav flags
   */
  private static final String CONFIG_SPACENAV_FLAGS = "lg.earth.spaceNavigator.flags";

  /**
   * Path to the socat binary.
   */
  private static final String SOCAT_BIN = "/usr/bin/socat";

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
   * Config directory for Earth
   */
  private File earthConfigDirectory;

  /**
   * Dot directory for Earth
   */
  private File earthDotDirectory;

  /**
   * file support to use for file operations
   */
  private final FileSupport fileSupport = FileSupportImpl.INSTANCE;

  /**
   * Sets up the configuration templates and adds a basic
   * NativeActivityComponent
   * 
   */
  @Override
  public void onActivitySetup() {

    TemplaterService service =
        getSpaceEnvironment().getServiceRegistry()
            .getRequiredService(TemplaterService.SERVICE_NAME);
    File templateFolder = getActivityFilesystem().getInstallFile(CONFIG_TEMPLATES_DIRECTORY);
    getLog().info(String.format("Template folder is: %s", templateFolder.getAbsolutePath()));

    templater = service.newTemplater(templateFolder);
    getLog().info(String.format("Template service created: %s", templater));

    addManagedResource(templater);

    windowId = new WindowInstanceIdentity(getUuid());
    window = new ManagedWindow(this, windowId);

    addManagedResource(window);

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

    earthComponent = new BasicNativeActivityComponent();
    earthRestartListener = new EarthClientRestartListener(window, getConfiguration(), getLog());
    earthRestartStrategy = new LimitedRetryRestartStrategy(4, 1000, 4000, getSpaceEnvironment());

    earthRestartStrategy.addRestartStrategyListener(earthRestartListener);
    addActivityComponent(earthComponent);

    if (getConfiguration().getRequiredPropertyBoolean(CONFIG_VIEWSYNC_SEND) == true) {
      int viewSyncPort = getConfiguration().getRequiredPropertyInteger(CONFIG_VIEWSYNC_PORT);

      int viewSyncListenerPort =
          getConfiguration().getRequiredPropertyInteger(CONFIG_VIEWSYNC_LISTENER_PORT);

      UdpClientNetworkCommunicationEndpointService udpCommService =
          getSpaceEnvironment().getServiceRegistry().getService(
              UdpClientNetworkCommunicationEndpointService.NAME);

      UdpBroadcastClientNetworkCommunicationEndpoint udpBcastClient =
          udpCommService.newBroadcastClient(viewSyncListenerPort, getLog());

      // TODO: refactor Listener code into separate source file
      udpBcastClient.addListener(new UdpBroadcastClientNetworkCommunicationEndpointListener() {
        public void onUdpMessage(UdpBroadcastClientNetworkCommunicationEndpoint endpoint,
            byte[] message, InetSocketAddress remoteAddress) {
          String viewSyncData = new String(message);
          getLog().debug(String.format("%s %s", "send viewsync message:", viewSyncData));
          EarthViewSyncState state = new EarthViewSyncState(viewSyncData);
          sendOutputJsonBuilder("viewsync_output", state.getJsonBuilder());
        }
      });

      addManagedResource(udpBcastClient);
      getLog().info("Added UDP ViewSync listener");

      NativeActivityRunnerFactory runnerFactory = getController().getNativeActivityRunnerFactory();
      NativeApplicationRunner socatRunner = runnerFactory.newPlatformNativeActivityRunner(getLog());

      Map<String, Object> socatConfig = Maps.newHashMap();

      String socatFlags =
          String.format("UDP4-RECV:%d,reuseaddr UDP4-DATAGRAM:127.0.0.1:%d", viewSyncPort,
              viewSyncListenerPort);

      socatConfig.put(NativeApplicationRunner.ACTIVITYNAME, SOCAT_BIN);
      socatConfig.put(NativeApplicationRunner.FLAGS, socatFlags);

      socatRunner.configure(socatConfig);
      addManagedResource(socatRunner);
    }
  }

  @Override
  public void onActivityStartup() {
    writeEarthConfigs();
    earthComponent.getNativeActivityRunner().setRestartStrategy(earthRestartStrategy);
  }

  /**
   * Handles configuration changes.
   * 
   * @param update
   *          configuration updates
   */
  @Override
  public void onActivityConfigurationUpdate(Map<String, Object> update) {
    writeEarthConfigs();
  }

  /**
   * Writes out the Earth Configs.
   */
  private void writeEarthConfigs() {

    earthConfigDirectory =
        new File(String.format("%s/%s", getActivityFilesystem().getInstallDirectory(),
            "earth/.config/Google"));
    earthDotDirectory =
        new File(String.format("%s/%s", getActivityFilesystem().getInstallDirectory(),
            "earth/.googleearth"));

    EarthClientConfiguration earthConfig =
        new EarthClientConfiguration(getConfiguration(), earthDotDirectory);

    JsonBuilder builder = new JsonBuilder();
    builder.put("ge", earthConfig);

    getLog().info(builder.toString());

    fileSupport.directoryExists(earthConfigDirectory, "GE Config directory failure");
    fileSupport.directoryExists(earthDotDirectory, "GE .googleearth directory failure");

    templater.writeTemplate("GoogleEarthPlus.conf.ftl", builder.build(),
        new File(String.format("%s/%s", earthConfigDirectory, "GoogleEarthPlus.conf")));
    templater.writeTemplate("GECommonSettings.conf.ftl", builder.build(),
        new File(String.format("%s/%s", earthConfigDirectory, "GECommonSettings.conf")));
    templater.writeTemplate("myplaces.kml.ftl", builder.build(),
        new File(String.format("%s/%s", earthDotDirectory, "myplaces.kml")));
    templater.writeTemplate("cached_default_view.kml.ftl", builder.build(),
        new File(String.format("%s/%s", earthDotDirectory, "cached_default_view.kml")));

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
