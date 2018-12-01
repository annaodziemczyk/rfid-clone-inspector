package com.cit.locator.distance.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.googleapi.GoogleApiService;
import com.cit.locator.distance.om.Distance;
import com.cit.locator.distance.om.TravelMeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService implements IDistanceService {

    @Autowired
    private GoogleApiService googleDistanceService;

    @Override
    public Distance findTravelRoute(GeoLocation from, GeoLocation to, TravelMeans travelMeans) {

        return googleDistanceService.execute(from, to, travelMeans);
    }


}
