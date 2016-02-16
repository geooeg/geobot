package com.vgilab.geobot.everytrail;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author smuellner
 */
@XmlRootElement(name="etTripSearchResponse")
public class EtTripSearchResponse {
    private String status;

    private EtTrips trips;
        
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the trips
     */
    public EtTrips getTrips() {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrips(EtTrips trips) {
        this.trips = trips;
    }
    
    
}