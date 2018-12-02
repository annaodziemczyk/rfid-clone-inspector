package com.cit.locator.distance.googleapi;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.om.TravelRoute;
import com.google.maps.model.TravelMode;

import java.time.Instant;

/**
 * Created by odziea on 11/20/2018.
 */
public interface IGoogleApiService {
    TravelRoute distancematrix(GeoLocation current, GeoLocation previous, TravelMode travelMode, Instant departureTime );
}
