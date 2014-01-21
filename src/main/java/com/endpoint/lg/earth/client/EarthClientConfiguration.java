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

import interactivespaces.configuration.Configuration;

import java.util.Map;

/**
 * A configuration for Earth.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class EarthConfiguration {
  private final String CACHE_PATH = ".googleearth/Cache";
  private final String KML_PATH = ".googleearth";

  public class Gui {
    private String hidden;
    private int compassNavigation;
    private String movementVisualization;
    private String showStatusBar;
    private double flytoSpeed;

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

  public class EarthBorders {
    private String coastLines;
    private String international;
    private String statesAndProvinces;
    private String waterBodies;

    public String getCoastLines() {
      return coastLines;
    }
    public void setCoastLines(String coastLines) {
      this.coastLines = coastLines;
    }
    public String getInternational() {
      return international;
    }
    public void setInternational(String international) {
      this.international = international;
    }
    public String getStatesAndProvinces() {
      return statesAndProvinces;
    }
    public void setStatesAndProvinces(String statesAndProvinces) {
      this.statesAndProvinces = statesAndProvinces;
    }
    public String getWaterBodies() {
      return waterBodies;
    }
    public void setWaterBodies(String waterBodies) {
      this.waterBodies = waterBodies;
    }
  }

  public class EarthLayers {
    private EarthBorders earthBorders = new EarthBorders();
    public EarthBorders getEarthBorders() {
      return earthBorders;
    }
    public void setEarthBorders(EarthBorders earthBorders) {
      this.earthBorders = earthBorders;
    }
  }

  public class Layers {
    private EarthLayers earthLayers = new EarthLayers();
    public EarthLayers getEarthLayers() {
      return earthLayers;
    }
    public void setEarthLayers(EarthLayers earthLayers) {
      this.earthLayers = earthLayers;
    }
  }

  private Layers layers = new Layers();
  public Layers getLayers() {
    return layers;
  }

  /**
   * Copies values from the activity configuration.
   * 
   * @param activityConfig
   */
  public EarthConfiguration(final Configuration activityConfig) {
    Map<String, String> env = System.getenv();

    gui.setHidden(activityConfig.getRequiredPropertyString("lg.earth.gui.hidden"));
    gui.setCompassNavigation(activityConfig.getRequiredPropertyInteger("lg.earth.gui.compassNavigation"));
    gui.setMovementVisualization(activityConfig.getRequiredPropertyString("lg.earth.gui.movementVisualization"));
    gui.setShowStatusBar(activityConfig.getRequiredPropertyString("lg.earth.gui.showStatusBar"));
    gui.setFlytoSpeed(activityConfig.getRequiredPropertyDouble("lg.earth.gui.flytoSpeed"));

    render.setAnisotropicFiltering(activityConfig.getRequiredPropertyInteger("lg.earth.render.anisotropicFiltering"));
    render.setHighQualityTerrain(activityConfig.getRequiredPropertyString("lg.earth.render.highQualityTerrain"));
    render.setIconSize(activityConfig.getRequiredPropertyDouble("lg.earth.render.iconSize"));
    render.setUse3dImagery(activityConfig.getRequiredPropertyString("lg.earth.render.use3dImagery"));

    cache.setDiskSize(activityConfig.getRequiredPropertyInteger("lg.earth.cache.diskSize"));
    cache.setMemorySize(activityConfig.getRequiredPropertyInteger("lg.earth.cache.memorySize"));
    cache.setLocation(activityConfig.getPropertyString(
          "lg.earth.cache.location",
          String.format("%s/%s", env.get("HOME"), CACHE_PATH)
          ));

    launcher.setLocation(activityConfig.getRequiredPropertyString("lg.earth.launcher.location"));

    kml.setSyncBase(activityConfig.getRequiredPropertyString("lg.earth.kml.syncBase"));
    kml.setDefaultView(activityConfig.getRequiredPropertyString("lg.earth.kml.defaultView"));
    kml.setDefaultViewLat(activityConfig.getRequiredPropertyDouble("lg.earth.kml.defaultViewLat"));
    kml.setDefaultViewLon(activityConfig.getRequiredPropertyDouble("lg.earth.kml.defaultViewLon"));
    kml.setLocation(activityConfig.getPropertyString(
          "lg.earth.kml.location",
          String.format("%s/%s", env.get("HOME"), KML_PATH)
          ));

    window.setName(activityConfig.getRequiredPropertyString("lg.earth.window.name"));

    layers.earthLayers.earthBorders.setCoastLines(activityConfig.getRequiredPropertyString("lg.earth.layers.earth.borders.coastLines"));
    layers.earthLayers.earthBorders.setInternational(activityConfig.getRequiredPropertyString("lg.earth.layers.earth.borders.international"));
    layers.earthLayers.earthBorders.setStatesAndProvinces(activityConfig.getRequiredPropertyString("lg.earth.layers.earth.borders.statesAndProvinces"));
    layers.earthLayers.earthBorders.setWaterBodies(activityConfig.getRequiredPropertyString("lg.earth.layers.earth.borders.waterBodies"));
    layers.earthLayers.setEarthBorders(layers.earthLayers.earthBorders);
    layers.setEarthLayers(layers.earthLayers);
  }
}
