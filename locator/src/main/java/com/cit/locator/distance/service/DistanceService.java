package com.cit.locator.distance.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.googleapi.GoogleApiService;
import com.cit.locator.distance.om.TravelRoute;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DistanceService implements IDistanceService {

    @Autowired
    private GoogleApiService googleDistanceService;

    @Override
    public TravelRoute findShortestDrivingRoute(GeoLocation from, GeoLocation to, Instant departureTime) {

        return googleDistanceService.distancematrix(from, to, TravelMode.DRIVING, departureTime);
    }

    @Override
    public TravelRoute findShortestTransitRoute(GeoLocation from, GeoLocation to, Instant departureTime){
        return googleDistanceService.distancematrix(from, to, TravelMode.TRANSIT, departureTime);
    }

}
