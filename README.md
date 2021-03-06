Google Earth Client
===================

Java package: com.endpoint.lg.earth.client

This activity stops and starts a Google Earth client instance. It writes the Google Earth configuration files on startup and configuration change.

Prerequisites
-------------
This activity manages the Native Binary from an installed Google Earth desktop application. It is only tested on GNU/Linux. There are two significant requirements for this activity:
* The first is to have the desktop application installed http://www.google.com/earth/.
* The second is a tricky hack - all occurrances of '/etc/passwd' within Earth's shared libraries (most notably "libfreebl3.so"), should be replaced with '/dev/null' (perhaps using your favorite hex editor).
 * This has the desired effect of causing Earth to respect the ```HOME``` envrionment variable.
 * During activity startup, ```HOME``` will be set to a directory specific to the deployed Live Activity.

Configuration variables for LG-CMS activities
---------------------------------------------

```
earth.client
    space.activity.component.native.executable.flags.linux      Extra earth flags
    lg.window.viewport.target               Viewport name for earth scene messages
    lg.earth.gui.hidden                     Should the GUI be hidden (true / false)
    lg.earth.spaceNavigator.device          Does a spacenav exist
    lg.earth.spaceNavigator.flags           Earth flags to handle spacenav device
    lg.earth.viewsync.flags                 Earth flags for viewsync stuff
    lg.earth.wrapper.flags                  Flags for Earth client wrapper
    lg.earth.restart.attempts               How many times to restart the earth client if it crashes
    lg.earth.restart.delay                  How long to wait before restarting earth, if it crashes
    # Settings for various Earth behaviors. Values given are either
    # guessed, or pulled as best as I could from non-IS configurations
    lg.earth.cache.diskSize=1024
    lg.earth.cache.location=##HOMEDIR##/.googleearth/Cache
    lg.earth.cache.memorySize=768
    lg.earth.gui.compassNavigation=0
    lg.earth.gui.flytoSpeed=0.17
    lg.earth.gui.hidden=false
    lg.earth.gui.movementVisualization=false
    lg.earth.gui.showStatusBar=true
    lg.earth.gui.use3dController=false
    # XXX No idea what value to put here
    lg.earth.kml.defaultView=
    lg.earth.kml.defaultViewLat=12
    lg.earth.kml.defaultViewLon=12
    lg.earth.kml.location=##HOMEDIR##/.googleearth
    lg.earth.kml.syncBase=http://lg-head:9001
    # XXX No idea what value to put here
    lg.earth.launcher.location=
    lg.earth.layers.earth.borders.coastLines=false
    lg.earth.layers.earth.borders.international=false
    lg.earth.layers.earth.borders.statesAndProvinces=false
    lg.earth.layers.earth.borders.waterBodies=false
    lg.earth.layers.earth.buildings.gray=false
    lg.earth.layers.earth.buildings.photoRealistic=true
    lg.earth.layers.earth.buildings.trees=true
    lg.earth.layers.earth.labels.countries=false
    lg.earth.layers.earth.labels.islands=false
    lg.earth.layers.earth.labels.localPlaceNames=false
    lg.earth.layers.earth.labels.populatedPlaces=false
    lg.earth.layers.earth.labels.regionsAndCounties=false
    lg.earth.layers.earth.labels.statesAndProvinces=false
    lg.earth.layers.earth.labels.waterBodies=false
    lg.earth.layers.earth.roads=false
    lg.earth.render.anisotropicFiltering=2
    lg.earth.render.highQualityTerrain=true
    lg.earth.render.iconSize=1
    lg.earth.render.textureCompression=true
    lg.earth.render.use3dImagery=true

# Working flags:
# ... touchscreen
lg.earth.gui.flytoSpeed=0.17
lg.earth.gui.hidden=false
lg.earth.gui.movementVisualization=false
lg.earth.gui.showStatusBar=true
lg.earth.gui.use3dController=true
lg.earth.kml.defaultView=
lg.earth.kml.defaultViewLat=12
lg.earth.kml.defaultViewLon=12
lg.earth.kml.syncBase=http://lg-head:9001
lg.earth.launcher.location=
lg.earth.layers.earth.borders.coastLines=false
lg.earth.layers.earth.borders.international=false
lg.earth.layers.earth.borders.statesAndProvinces=false
lg.earth.layers.earth.borders.waterBodies=false
lg.earth.layers.earth.buildings.gray=false
lg.earth.layers.earth.buildings.photoRealistic=true
lg.earth.layers.earth.buildings.trees=true
lg.earth.layers.earth.labels.countries=false
lg.earth.layers.earth.labels.islands=false
lg.earth.layers.earth.labels.localPlaceNames=false
lg.earth.layers.earth.labels.populatedPlaces=false
lg.earth.layers.earth.labels.regionsAndCounties=false
lg.earth.layers.earth.labels.statesAndProvinces=false
lg.earth.layers.earth.labels.waterBodies=false
lg.earth.layers.earth.roads=false
lg.earth.render.anisotropicFiltering=2
lg.earth.render.highQualityTerrain=true
lg.earth.render.iconSize=1
lg.earth.render.textureCompression=true
lg.earth.render.use3dImagery=true
lg.earth.restart.attempts=5
lg.earth.restart.delay=10
lg.earth.spaceNavigator.device=true
lg.earth.spaceNavigator.flags.1=-sSpaceNavigator/device=/dev/input/spacenavigator
lg.earth.spaceNavigator.flags.2=-sSpaceNavigator/gutterValue=0.1 -sSpaceNavigator/sensitivityPitch=.010
lg.earth.spaceNavigator.flags.3=-sSpaceNavigator/sensitivityRoll=.0010 -sSpaceNavigator/sensitivityYaw=.00350
lg.earth.spaceNavigator.flags.4=-sSpaceNavigator/sensitivityX=.250 -sSpaceNavigator/sensitivityY=.250
lg.earth.spaceNavigator.flags.5=-sSpaceNavigator/sensitivityZ=.020 -sSpaceNavigator/zeroPitch=0.0
lg.earth.spaceNavigator.flags.6=-sSpaceNavigator/zeroRoll=0.0 -sSpaceNavigator/zeroYaw=0.0
lg.earth.spaceNavigator.flags.7=-sSpaceNavigator/zeroX=0.0 -sSpaceNavigator/zeroY=0.0 -sSpaceNavigator/zeroZ=0.0
lg.earth.viewsync.flags=-style GTK+ -sConnection/disableRequestBatching=true -sViewSync/send=true -sViewSync/hostname=10.42.42.255 -sViewSync/port=45678 -sViewSync/yawOffset=0 -sViewSync/pitchOffset=0 -sViewSync/rollOffset=0 -sViewSync/horizFov=29
lg.earth.wrapper.flags=
lg.window.viewport.target=touchscreen
space.activity.component.native.executable.flags.linux=-multiple

# ... non-touchscreen
lg.earth.gui.flytoSpeed=0.17
lg.earth.gui.hidden=false
lg.earth.gui.movementVisualization=false
lg.earth.gui.showStatusBar=true
lg.earth.gui.use3dController=true
lg.earth.kml.defaultView=
lg.earth.kml.defaultViewLat=12
lg.earth.kml.defaultViewLon=12
lg.earth.kml.syncBase=http://lg-head:9001
lg.earth.launcher.location=
lg.earth.layers.earth.borders.coastLines=false
lg.earth.layers.earth.borders.international=false
lg.earth.layers.earth.borders.statesAndProvinces=false
lg.earth.layers.earth.borders.waterBodies=false
lg.earth.layers.earth.buildings.gray=false
lg.earth.layers.earth.buildings.photoRealistic=true
lg.earth.layers.earth.buildings.trees=true
lg.earth.layers.earth.labels.countries=false
lg.earth.layers.earth.labels.islands=false
lg.earth.layers.earth.labels.localPlaceNames=false
lg.earth.layers.earth.labels.populatedPlaces=false
lg.earth.layers.earth.labels.regionsAndCounties=false
lg.earth.layers.earth.labels.statesAndProvinces=false
lg.earth.layers.earth.labels.waterBodies=false
lg.earth.layers.earth.roads=false
lg.earth.render.anisotropicFiltering=2
lg.earth.render.highQualityTerrain=true
lg.earth.render.iconSize=1
lg.earth.render.textureCompression=true
lg.earth.render.use3dImagery=true
lg.earth.restart.attempts=5
lg.earth.restart.delay=10
lg.earth.spaceNavigator.device=true
lg.earth.spaceNavigator.flags=
lg.earth.viewsync.flags=-style GTK+ -sConnection/disableRequestBatching=true -sViewSync/receive=true -sViewSync/hostname=10.42.42.255 -sViewSync/port=45678 -sViewSync/yawOffset=-36 -sViewSync/pitchOffset=0 -sViewSync/rollOffset=0 -sViewSync/horizFov=29 -multiple
lg.earth.wrapper.flags=
lg.window.viewport.target=42-b
space.activity.component.native.executable.flags.linux=
```


Copyright (C) 2015 Google Inc.  
Copyright (C) 2015 End Point Corporation

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
