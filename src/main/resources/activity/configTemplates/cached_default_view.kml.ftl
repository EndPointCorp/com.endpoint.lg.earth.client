<?xml version="1.0" encoding="UTF-8"?>
<kml xmlns="http://www.opengis.net/kml/2.2"
    xmlns:gx="http://www.google.com/kml/ext/2.2"
    xmlns:kml="http://www.opengis.net/kml/2.2"
    xmlns:atom="http://www.w3.org/2005/Atom">
    <!--// This file is a FreeMarker-processed template, created by Interactive Spaces //-->
    <Document>
        <name>cached_default_view.kml</name>
        <StyleMap id="sn_m-pushpin">
            <Pair>
                <key>normal</key>
                <styleUrl>#sn_pushpin</styleUrl>
            </Pair>
            <Pair>
                <key>highlight</key>
                <styleUrl>#sh_pushpin</styleUrl>
            </Pair>
        </StyleMap>
        <Style id="sn_pushpin">
            <IconStyle>
                <color>00000000</color>
            </IconStyle>
            <LabelStyle>
                <color>00ffffff</color>
            </LabelStyle>
        </Style>
        <Placemark id="default_starting_location">
            <name>LG Start</name>
            ${ge.kml.defaultView}
            <styleUrl>#sn_m-pushpin</styleUrl>
            <Point>
                <coordinates>${ge.kml.defaultViewLat},${ge.kml.defaultViewLon}</coordinates>
            </Point>
        </Placemark>
    </Document>
</kml>
