package com.cit.locator.distance.googleapi;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.googleapi.config.GoogleApiConfig;
import com.cit.locator.distance.om.TravelRoute;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Service
public class GoogleApiService implements IGoogleApiService {

    @Autowired
    private GoogleApiConfig googleApiConfig;

    public TravelRoute distancematrix(GeoLocation from, GeoLocation to, TravelMode travelMode, Instant departureTime) {

        TravelRoute travelRoute = new TravelRoute();
        travelRoute.setTravelMode(travelMode);
        final CountDownLatch latch = new CountDownLatch(1);
        LatLng sourcePoint = new LatLng(from.getX(), from.getY());
        LatLng destinationPoint = new LatLng(to.getX(), to.getY());

        GeoApiContext.Builder apiContext = new GeoApiContext.Builder().apiKey(googleApiConfig.getKey());
        GeoApiContext geoContext = apiContext.build();
        DistanceMatrixApiRequest matrixRequest = DistanceMatrixApi.newRequest(geoContext)
                .origins(sourcePoint)
                .destinations(destinationPoint)
                .mode(travelMode)
                .trafficModel(TrafficModel.BEST_GUESS)
                .departureTime(departureTime);

        matrixRequest.setCallback(new PendingResult.Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix distanceMatrix) {

                DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
                travelRoute.setTravelMode(travelMode);
                travelRoute.setDurationInSeconds(element.duration.inSeconds);
                travelRoute.setLengthInMeters(element.distance.inMeters);
                latch.countDown();

            }

            @Override
            public void onFailure(Throwable throwable) {
                latch.countDown();
            }


        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return travelRoute;
    }


}