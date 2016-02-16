package com.vgilab.geobot.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author ljzhang
 */
@Entity
@Table(name = "coordinates", uniqueConstraints = @UniqueConstraint(columnNames = {"unique_name"}))
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 12L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "maker")
    private String maker;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "software")
    private String software;
    
    @Column(name = "source")
    private String source;
    
    @Column(name = "unique_name")
    private String uniqueName;
   
    @Column(name = "version_id")
    private String versionID;
    
    @Column(name = "longitude_ref")
    private String longitudeRef;
    
    @Column(name = "longitude")
    private String longitude;
    
    @Column(name = "longitude_conv")
    private BigDecimal longitudeConv;
    
    @Column(name = "latitude_ref")
    private String latitudeRef;
    
    @Column(name = "latitude")
    private String latitude;
    
    @Column(name = "latitude_conv")
    private BigDecimal latitudeConv;
    
    @Column(name = "altitude_ref")
    private String altitudeRef;
    
    @Column(name = "altitude")
    private String altitude;
    
    @Column(name = "altitude_conv")
    private BigDecimal altitudeConv;
    
    @Column(name = "accuracy")
    private String accuracy;
    
    @Column(name = "gps_timestamp")
    private String gpsTimeStamp;
    
    @Column(name = "gps_datestamp")
    private String gpsDateStamp;
    
    @Column(name = "gps_dop")
    private String gpsDilutionOfPrecision;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Calendar timestamp;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    private Calendar createdOn;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on", nullable = true)
    private Calendar modifiedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the uniqueName
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * @param uniqueName the uniqueName to set
     */
    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }
    
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

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
     * @return the createdOn
     */
    public Calendar getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn the createdOn to set
     */
    public void setCreatedOn(Calendar createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * @return the modifiedOn
     */
    public Calendar getModifiedOn() {
        return modifiedOn;
    }

    /**
     * @param modifiedOn the modifiedOn to set
     */
    public void setModifiedOn(Calendar modifiedOn) {
        this.modifiedOn = modifiedOn;
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


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.vgilab.geobot.persistence.entity.Coordinates[ id=" + getId() + " ]";
    }

    /**
     * @return the longitudeConv
     */
    public BigDecimal getLongitudeConv() {
        return longitudeConv;
    }

    /**
     * @param longitudeConv the longitudeConv to set
     */
    public void setLongitudeConv(BigDecimal longitudeConv) {
        this.longitudeConv = longitudeConv;
    }

    /**
     * @return the latitudeConv
     */
    public BigDecimal getLatitudeConv() {
        return latitudeConv;
    }

    /**
     * @param latitudeConv the latitudeConv to set
     */
    public void setLatitudeConv(BigDecimal latitudeConv) {
        this.latitudeConv = latitudeConv;
    }

    /**
     * @return the altitudeConv
     */
    public BigDecimal getAltitudeConv() {
        return altitudeConv;
    }

    /**
     * @param altitudeConv the altitudeConv to set
     */
    public void setAltitudeConv(BigDecimal altitudeConv) {
        this.altitudeConv = altitudeConv;
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

    /**
     * @return the timestamp
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

}
