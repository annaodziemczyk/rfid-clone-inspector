package com.cit.locator.distance.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.om.TravelRoute;

import java.time.Instant;

public interface IDistanceService {
    TravelRoute findShortestDrivingRoute(GeoLocation from, GeoLocation to, Instant departureTime);
    TravelRoute findShortestTransitRoute(GeoLocation from, GeoLocation to, Instant departureTime);
}
