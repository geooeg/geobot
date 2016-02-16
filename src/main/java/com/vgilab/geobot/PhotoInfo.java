package com.vgilab.geobot;

/**
 *
 * @author ljzhang
 */
public class PhotoInfo {
/*
     GPSVersionID null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSLatitudeRef null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSLatitude null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSLongitudeRef null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSLongitude null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSAltitude null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSTimeStamp null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSImgDirectionRef null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSImgDirection null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSMapDatum null
Dez 22, 2015 7:32:25 PM com.vgilab.geobot.FlickrApi flickrPhotosGetPosition
SCHWERWIEGEND: GPSDateStamp null
    GPSVersionID 2.2.0.0
    */
    private String versionID;
    private String longitudeRef;
    private String longitude;
    private String latitudeRef;
    private String latitude;
    private String altitudeRef;
    private String altitude;
    private String accuracy;
    private String gpsTimeStamp;
    private String gpsDateStamp;
    private String gpsDilutionOfPrecision;
    private String maker;
    private String model;
    private String software;
    
    

    /**
     * @return the versionID
     */
    public String getVersionID() {
        return versionID;
    }

    /**
     * @param versionID the versionID to set
     */
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    /**
     * @return the longitudeRef
     */
    public String getLongitudeRef() {
        return longitudeRef;
    }

    /**
     * @param longitudeRef the longitudeRef to set
     */
    public void setLongitudeRef(String longitudeRef) {
        this.longitudeRef = longitudeRef;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the latitudeRef
     */
    public String getLatitudeRef() {
        return latitudeRef;
    }

    /**
     * @param latitudeRef the latitudeRef to set
     */
    public void setLatitudeRef(String latitudeRef) {
        this.latitudeRef = latitudeRef;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the altitudeRef
     */
    public String getAltitudeRef() {
        return altitudeRef;
    }

    /**
     * @param altitudeRef the altitudeRef to set
     */
    public void setAltitudeRef(String altitudeRef) {
        this.altitudeRef = altitudeRef;
    }

    /**
     * @return the altitude
     */
    public String getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the gpsTimeStamp
     */
    public String getGpsTimeStamp() {
        return gpsTimeStamp;
    }

    /**
     * @param gpsTimeStamp the gpsTimeStamp to set
     */
    public void setGpsTimeStamp(String gpsTimeStamp) {
        this.gpsTimeStamp = gpsTimeStamp;
    }   

    /**
     * @return the accuracy
     */
    public String getAccuracy() {
        return accuracy;
    }

    /**
     * @param accuracy the accuracy to set
     */
    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    } 

    /**
     * @return the maker
     */
    public String getMaker() {
        return maker;
    }

    /**
     * @param maker the maker to set
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the software
     */
    public String getSoftware() {
        return software;
    }

    /**
     * @param software the software to set
     */
    public void setSoftware(String software) {
        this.software = software;
    }

    /**
     * @return the gpsDateStamp
     */
    public String getGpsDateStamp() {
        return gpsDateStamp;
    }

    /**
     * @param gpsDateStamp the gpsDateStamp to set
     */
    public void setGpsDateStamp(String gpsDateStamp) {
        this.gpsDateStamp = gpsDateStamp;
    }

    /**
     * @return the gpsDilutionOfPrecision
     */
    public String getGpsDilutionOfPrecision() {
        return gpsDilutionOfPrecision;
    }

    /**
     * @param gpsDilutionOfPrecision the gpsDilutionOfPrecision to set
     */
    public void setGpsDilutionOfPrecision(String gpsDilutionOfPrecision) {
        this.gpsDilutionOfPrecision = gpsDilutionOfPrecision;
    }
    
    @Override
    public String toString() {
        return "Lat.: " + this.getLatitude() + ", Lat. Ref.: " + this.getLatitudeRef() + " Lng.: " + this.getLongitude() + ", Lng. Ref." + this.getLongitudeRef() + " Alt.: " + this.getAltitude()+ ", Alt. Ref." + this.getAltitudeRef()+ ", Accuracy: " + this.getAccuracy();
    }
}
