/*
 * Copyright (C) 2015 End Point Corporation
 * Copyright (C) 2014 Google Inc.
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

import interactivespaces.service.template.Templater;
import interactivespaces.util.data.json.JsonBuilder;
import interactivespaces.util.io.FileSupport;
import interactivespaces.util.io.FileSupportImpl;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * A repeatable configuration template writer and cache clearer for Earth.
 * 
 * @author Matt Vollrath <matt@endpoint.com>
 */
public class EarthClientConfigWriter {
  /**
   * A list of configuration files to be templated.
   */
  public static final ImmutableList<String> CONFIG_TEMPLATED_FILES = ImmutableList.of(
      "GoogleEarthPlus.conf", "GECommonSettings.conf");

  /**
   * A list of dotfiles to be templated.
   */
  public static final ImmutableList<String> DOT_TEMPLATED_FILES = ImmutableList.of("myplaces.kml",
      "cached_default_view.kml");

  /**
   * Namespace for the configuration in the template object.
   */
  public static final String CONFIG_NAMESPACE = "ge";

  private FileSupport fileSupport = FileSupportImpl.INSTANCE;

  private EarthClientConfiguration config;
  private Templater templater;

  public EarthClientConfigWriter(EarthClientConfiguration config, Templater templater) {
    this.config = config;
    this.templater = templater;
  }

  /**
   * Helper for running the underlying template writer.
   * 
   * @param destFiles
   *          a list of files to be templated
   * @param content
   *          the configuration object to be provided to the templates
   * @param destPath
   *          destination path for the templated files
   */
  private void runTemplates(List<String> destFiles, Map<String, Object> content, File destPath) {
    for (String destFile : destFiles) {
      String sourceTemplate = String.format("%s.ftl", destFile);

      templater.writeTemplate(sourceTemplate, content, new File(destPath, destFile));
    }
  }

  /**
   * Clears the configuration and dot directories.
   */
  public void clear() {
    fileSupport.deleteDirectoryContents(config.getConfigDirectory());
    fileSupport.deleteDirectoryContents(config.getDotDirectory());
  }

  /**
   * Wipes out configuration/dot paths and writes the templates.
   */
  public void write() {
    clear();

    fileSupport.directoryExists(config.getConfigDirectory());
    fileSupport.directoryExists(config.getDotDirectory());

    JsonBuilder builder = new JsonBuilder();
    builder.put(CONFIG_NAMESPACE, config);

    Map<String, Object> builtConfig = builder.build();

    runTemplates(CONFIG_TEMPLATED_FILES, builtConfig, config.getConfigDirectory());
    runTemplates(DOT_TEMPLATED_FILES, builtConfig, config.getDotDirectory());
  }
}
