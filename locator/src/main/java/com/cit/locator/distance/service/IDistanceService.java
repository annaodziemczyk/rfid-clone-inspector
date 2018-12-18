package com.cit.locator.distance.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.airport.om.Airport;
import com.cit.locator.distance.om.TravelRoute;
import com.google.maps.model.TravelMode;

import java.time.Instant;

public interface IDistanceService {
    TravelRoute findShortestDrivingRoute(GeoLocation from, GeoLocation to, Instant departureTime);
    TravelRoute findShortestTransitRoute(GeoLocation from, GeoLocation to, Instant departureTime);

    long calculateTravelTime(double distanceInMeters, TravelMode travelMode);

    double calculateDistanceInMeters(GeoLocation from, GeoLocation to);
    double calculateDistanceInMeters(GeoLocation from, Airport to);
}
