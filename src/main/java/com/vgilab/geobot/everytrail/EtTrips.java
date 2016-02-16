package com.vgilab.geobot.everytrail;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  <trips totalCount="10493" returnedCount="20">
 * @author smuellner
 */
@XmlRootElement(name="trips")
public class EtTrips {
    private List<EtTrip> trip;

    /**
     * @return the trip
     */
    public List<EtTrip> getTrip() {
        return trip;
    }

    /**
     * @param trip the trip to set
     */
    public void setTrip(List<EtTrip> trip) {
        this.trip = trip;
    }
}
