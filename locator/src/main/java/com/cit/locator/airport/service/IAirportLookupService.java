package com.cit.locator.airport.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.airport.om.Airport;

/**
 * Created by odziea on 12/18/2018.
 */
public interface IAirportLookupService {
    Airport findNearestAirport(GeoLocation geoLocation);
}
