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

import interactivespaces.configuration.Configuration;

import java.io.File;

/**
 * A configuration for Earth.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class EarthClientConfiguration {

  public class Gui {
    private String hidden;
    private int compassNavigation;
    private String movementVisualization;
    private String showStatusBar;
    private double flytoSpeed;
    private String use3dController;

    public String getHidden() {
      return hidden;
    }

    public void setHidden(String hidden) {
      this.hidden = hidden;
    }

    public int getCompassNavigation() {
      return compassNavigation;
    }

    public void setCompassNavigation(int compassNavigation) {
      this.compassNavigation = compassNavigation;
    }

    public String getMovementVisualization() {
      return movementVisualization;
    }

    public void setMovementVisualization(String movementVisualization) {
      this.movementVisualization = movementVisualization;
    }

    public String getShowStatusBar() {
      return showStatusBar;
    }

    public void setShowStatusBar(String showStatusBar) {
      this.showStatusBar = showStatusBar;
    }

    public double getFlytoSpeed() {
      return flytoSpeed;
    }

    public void setFlytoSpeed(double flytoSpeed) {
      this.flytoSpeed = flytoSpeed;
    }

    public String getUse3dController() {
      return use3dController;
    }

    public void setUse3dController(String use3dController) {
      this.use3dController = use3dController;
    }
  }

  private Gui gui = new Gui();

  public Gui getGui() {
    return gui;
  }

  public class Render {
    private int anisotropicFiltering;
    private String highQualityTerrain;
    private double iconSize;
    private String use3dImagery;
    private String textureCompression;

    public int getAnisotropicFiltering() {
      return anisotropicFiltering;
    }

    public void setAnisotropicFiltering(int anisotropicFiltering) {
      this.anisotropicFiltering = anisotropicFiltering;
    }

    public String getHighQualityTerrain() {
      return highQualityTerrain;
    }

    public void setHighQualityTerrain(String highQualityTerrain) {
      this.highQualityTerrain = highQualityTerrain;
    }

    public double getIconSize() {
      return iconSize;
    }

    public void setIconSize(double iconSize) {
      this.iconSize = iconSize;
    }

    public String getUse3dImagery() {
      return use3dImagery;
    }

    public void setUse3dImagery(String use3dImagery) {
      this.use3dImagery = use3dImagery;
    }

    public String getTextureCompression() {
      return textureCompression;
    }

    public void setTextureCompression(String textureCompression) {
      this.textureCompression = textureCompression;
    }
  }

  private Render render = new Render();

  public Render getRender() {
    return render;
  }

  public class Cache {
    private int memorySize;
    private int diskSize;
    private String location;

    public int getMemorySize() {
      return memorySize;
    }

    public void setMemorySize(int memorySize) {
      this.memorySize = memorySize;
    }

    public int getDiskSize() {
      return diskSize;
    }

    public void setDiskSize(int diskSize) {
      this.diskSize = diskSize;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }
  }

  private Cache cache = new Cache();

  public Cache getCache() {
    return cache;
  }

  public class ViewSync {
    private String send;
    private String receiver;
    private String hostname;
    private int port;
    private int horizFov;
    private int yawOffset;
    private int pitchOffset;
    private int rollOffset;

    public String getSend() {
      return send;
    }

    public void setSend(String send) {
      this.send = send;
    }

    public String getReceiver() {
      return receiver;
    }

    public void setReceiver(String receiver) {
      this.receiver = receiver;
    }

    public String getHostname() {
      return hostname;
    }

    public void setHostname(String hostname) {
      this.hostname = hostname;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public int getHorizFov() {
      return horizFov;
    }

    public void setHorizFov(int horizFov) {
      this.horizFov = horizFov;
    }

    public int getYawOffset() {
      return yawOffset;
    }

    public void setYawOffset(int yawOffset) {
      this.yawOffset = yawOffset;
    }

    public int getPitchOffset() {
      return pitchOffset;
    }

    public void setPitchOffset(int pitchOffset) {
      this.pitchOffset = pitchOffset;
    }

    public int getRollOffset() {
      return rollOffset;
    }

    public void setRollOffset(int rollOffset) {
      this.rollOffset = rollOffset;
    }
  }

  private ViewSync viewSync = new ViewSync();

  public ViewSync getViewSync() {
    return viewSync;
  }

  public class SpaceNavigator {
    private String device;
    private double sensitivity;

    public String getDevice() {
      return device;
    }

    public void setDevice(String device) {
      this.device = device;
    }

    public double getSensitivity() {
      return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
      this.sensitivity = sensitivity;
    }
  }

  private SpaceNavigator spaceNavigator = new SpaceNavigator();

  public SpaceNavigator getSpaceNavigator() {
    return spaceNavigator;
  }

  public class Launcher {
    private String location;

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }
  }

  private Launcher launcher = new Launcher();

  public Launcher getLauncher() {
    return launcher;
  }

  public class Kml {
    private String location;
    private String syncBase;
    private String defaultView;
    private double defaultViewLat;
    private double defaultViewLon;

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public String getSyncBase() {
      return syncBase;
    }

    public void setSyncBase(String syncUrl) {
      this.syncBase = syncUrl;
    }

    public String getDefaultView() {
      return defaultView;
    }

    public void setDefaultView(String defaultView) {
      this.defaultView = defaultView;
    }

    public double getDefaultViewLat() {
      return defaultViewLat;
    }

    public void setDefaultViewLat(double defaultViewLat) {
      this.defaultViewLat = defaultViewLat;
    }

    public double getDefaultViewLon() {
      return defaultViewLon;
    }

    public void setDefaultViewLon(double defaultViewLon) {
      this.defaultViewLon = defaultViewLon;
    }
  }

  private Kml kml = new Kml();

  public Kml getKml() {
    return kml;
  }

  public class Window {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  private Window window = new Window();

  public Window getWindow() {
    return window;
  }

  // big pile of layer configuration classes are broken out
  private EarthClientConfigLayers layers = new EarthClientConfigLayers();

  public EarthClientConfigLayers getLayers() {
    return layers;
  }

  private File dotDirectory;
  private File configDirectory;

  public File getDotDirectory() {
    return dotDirectory;
  }

  public File getConfigDirectory() {
    return configDirectory;
  }

  /**
   * Copies values from the activity configuration.
   * 
   * @param activityConfig
   */
  public EarthClientConfiguration(final Configuration activityConfig,
      final File earthInstallDirectory) {

    dotDirectory = new File(earthInstallDirectory, "earth/.googleearth");
    configDirectory = new File(earthInstallDirectory, "earth/.config/Google");

    gui.setHidden(activityConfig.getRequiredPropertyString("lg.earth.gui.hidden"));
    gui.setCompassNavigation(activityConfig
        .getRequiredPropertyInteger("lg.earth.gui.compassNavigation"));
    gui.setMovementVisualization(activityConfig
        .getRequiredPropertyString("lg.earth.gui.movementVisualization"));
    gui.setShowStatusBar(activityConfig.getRequiredPropertyString("lg.earth.gui.showStatusBar"));
    gui.setFlytoSpeed(activityConfig.getRequiredPropertyDouble("lg.earth.gui.flytoSpeed"));
    gui.setUse3dController(activityConfig.getRequiredPropertyString("lg.earth.gui.use3dController"));

    render.setAnisotropicFiltering(activityConfig
        .getRequiredPropertyInteger("lg.earth.render.anisotropicFiltering"));
    render.setHighQualityTerrain(activityConfig
        .getRequiredPropertyString("lg.earth.render.highQualityTerrain"));
    render.setIconSize(activityConfig.getRequiredPropertyDouble("lg.earth.render.iconSize"));
    render
        .setUse3dImagery(activityConfig.getRequiredPropertyString("lg.earth.render.use3dImagery"));
    render.setTextureCompression(activityConfig
        .getRequiredPropertyString("lg.earth.render.textureCompression"));

    cache.setDiskSize(activityConfig.getRequiredPropertyInteger("lg.earth.cache.diskSize"));
    cache.setMemorySize(activityConfig.getRequiredPropertyInteger("lg.earth.cache.memorySize"));
    cache.setLocation(activityConfig.getPropertyString("lg.earth.cache.location", new File(
        dotDirectory, "Cache").getAbsolutePath()));

    launcher.setLocation(activityConfig.getRequiredPropertyString("lg.earth.launcher.location"));

    kml.setSyncBase(activityConfig.getRequiredPropertyString("lg.earth.kml.syncBase"));
    kml.setDefaultView(activityConfig.getRequiredPropertyString("lg.earth.kml.defaultView"));
    kml.setDefaultViewLat(activityConfig.getRequiredPropertyDouble("lg.earth.kml.defaultViewLat"));
    kml.setDefaultViewLon(activityConfig.getRequiredPropertyDouble("lg.earth.kml.defaultViewLon"));
    kml.setLocation(activityConfig.getPropertyString("lg.earth.kml.location",
        dotDirectory.getAbsolutePath()));

    if (activityConfig.getPropertyString("lg.earth.window.name") != null
        && !activityConfig.getPropertyString("lg.earth.window.name").isEmpty()) {
      window.setName(activityConfig.getPropertyString("lg.earth.window.name"));
    } else if (activityConfig.getPropertyString("lg.window.viewport.target") != null
        && !activityConfig.getPropertyString("lg.window.viewport.target").isEmpty()) {
      window.setName(activityConfig.getPropertyString("lg.window.viewport.target"));
    } else {
      window.setName("generic");
    }

    layers.earthBorders.setCoastLines(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.borders.coastLines"));
    layers.earthBorders.setInternational(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.borders.international"));
    layers.earthBorders.setStatesAndProvinces(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.borders.statesAndProvinces"));
    layers.earthBorders.setWaterBodies(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.borders.waterBodies"));
    layers.setEarthBorders(layers.earthBorders);

    layers.earthBuildings.setGray(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.buildings.gray"));
    layers.earthBuildings.setPhotoRealistic(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.buildings.photoRealistic"));
    layers.earthBuildings.setTrees(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.buildings.trees"));
    layers.setEarthBuildings(layers.earthBuildings);

    layers.earthLabels.setCountries(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.countries"));
    layers.earthLabels.setIslands(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.islands"));
    layers.earthLabels.setLocalPlaceNames(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.localPlaceNames"));
    layers.earthLabels.setPopulatedPlaces(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.populatedPlaces"));
    layers.earthLabels.setRegionsAndCounties(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.regionsAndCounties"));
    layers.earthLabels.setStatesAndProvinces(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.statesAndProvinces"));
    layers.earthLabels.setWaterBodies(activityConfig
        .getRequiredPropertyString("lg.earth.layers.earth.labels.waterBodies"));
    layers.setEarthLabels(layers.earthLabels);

    layers.setEarthRoads(activityConfig.getRequiredPropertyString("lg.earth.layers.earth.roads"));
  }
}
