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

/**
 * A layers object for Earth configuration
 * 
 * @author Kiel Christofferson <kiel@endpoint.com>
 */
public class EarthClientConfigLayers {

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

  public class EarthBuildings {
    private String gray;
    private String photoRealistic;
    private String trees;

    public String getGray() {
      return gray;
    }
    public void setGray(String gray) {
      this.gray = gray;
    }
    public String getPhotoRealistic() {
      return photoRealistic;
    }
    public void setPhotoRealistic(String photoRealistic) {
      this.photoRealistic = photoRealistic;
    }
    public String getTrees() {
      return trees;
    }
    public void setTrees(String trees) {
      this.trees = trees;
    }
  }

  public class EarthLabels {
    private String countries;
    private String islands;
    private String localPlaceNames;
    private String populatedPlaces;
    private String regionsAndCounties;
    private String statesAndProvinces;
    private String waterBodies;

    /**
     * @return the countries
     */
    public String getCountries() {
      return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(String countries) {
      this.countries = countries;
    }

    /**
     * @return the islands
     */
    public String getIslands() {
      return islands;
    }

    /**
     * @param islands the islands to set
     */
    public void setIslands(String islands) {
      this.islands = islands;
    }

    /**
     * @return the localPlaceNames
     */
    public String getLocalPlaceNames() {
      return localPlaceNames;
    }

    /**
     * @param localPlaceNames the localPlaceNames to set
     */
    public void setLocalPlaceNames(String localPlaceNames) {
      this.localPlaceNames = localPlaceNames;
    }

    /**
     * @return the populatedPlaces
     */
    public String getPopulatedPlaces() {
      return populatedPlaces;
    }

    /**
     * @param populatedPlaces the populatedPlaces to set
     */
    public void setPopulatedPlaces(String populatedPlaces) {
      this.populatedPlaces = populatedPlaces;
    }

    /**
     * @return the regionsAndCounties
     */
    public String getRegionsAndCounties() {
      return regionsAndCounties;
    }

    /**
     * @param regionsAndCounties the regionsAndCounties to set
     */
    public void setRegionsAndCounties(String regionsAndCounties) {
      this.regionsAndCounties = regionsAndCounties;
    }

    /**
     * @return the statesAndProvinces
     */
    public String getStatesAndProvinces() {
      return statesAndProvinces;
    }

    /**
     * @param statesAndProvinces the statesAndProvinces to set
     */
    public void setStatesAndProvinces(String statesAndProvinces) {
      this.statesAndProvinces = statesAndProvinces;
    }

    /**
     * @return the waterBodies
     */
    public String getWaterBodies() {
      return waterBodies;
    }

    /**
     * @param waterBodies the waterBodies to set
     */
    public void setWaterBodies(String waterBodies) {
      this.waterBodies = waterBodies;
    }
  }

  EarthBorders earthBorders = new EarthBorders();
  public EarthBorders getEarthBorders() {
    return earthBorders;
  }
  public void setEarthBorders(EarthBorders earthBorders) {
    this.earthBorders = earthBorders;
  }

  EarthBuildings earthBuildings = new EarthBuildings();
  public EarthBuildings getEarthBuildings() {
    return earthBuildings;
  }
  public void setEarthBuildings(EarthBuildings earthBuildings) {
    this.earthBuildings = earthBuildings;
  }

  EarthLabels earthLabels = new EarthLabels();
  public EarthLabels getEarthLabels() {
    return earthLabels;
  }
  public void setEarthLabels(EarthLabels earthLabels) {
    this.earthLabels = earthLabels;
  }

  String earthRoads = new String();
  public String getEarthRoads() {
    return earthRoads;
  }
  public void setEarthRoads(String earthRoads) {
    this.earthRoads = earthRoads;
  }
}
