package com.cit.locator.distance.googleapi;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.distance.googleapi.config.GoogleApiConfig;
import com.cit.locator.distance.om.Distance;
import com.cit.locator.distance.om.TravelMeans;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Service
public class GoogleApiService implements IGoogleApiService {

    @Autowired
    private GoogleApiConfig googleApiConfig;

    public Distance execute(GeoLocation from, GeoLocation to, TravelMeans travelMeans) {

        Distance distance = new Distance();
        distance.setTravelMeans(travelMeans);
        final CountDownLatch latch = new CountDownLatch(1);
        LatLng sourcePoint = new LatLng(from.getX(), from.getY());
        LatLng destinationPoint = new LatLng(to.getX(), to.getY());

        GeoApiContext.Builder apiContext = new GeoApiContext.Builder().apiKey(googleApiConfig.getKey());
        GeoApiContext geoContext = apiContext.build();
        DistanceMatrixApiRequest matrixRequest = DistanceMatrixApi.newRequest(geoContext)
                .origins(sourcePoint)
                .destinations(destinationPoint)
                .mode(TravelMode.DRIVING)
                .trafficModel(TrafficModel.BEST_GUESS)
                .departureTime(new Date().toInstant());
        matrixRequest.setCallback(new PendingResult.Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix distanceMatrix) {

                DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];
                distance.setTravelMeans(travelMeans);
                distance.setDurationInSeconds(element.duration.inSeconds);
                distance.setLengthInMeters(element.distance.inMeters);
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


        return distance;
    }





//        GoogleResponseDTO result = null;
//        Distance distance = new Distance();
//        try {
//            result = (GoogleResponseDTO)this.getForObject(this.getRequestURL(from, to, travelMeans), GoogleResponseDTO.class);
//            if(result.getStatus().equalsIgnoreCase("OK") && !result.getRows().get(0).getElements().get(0).getStatus().equalsIgnoreCase("ZERO_RESULTS")){
//                distance = googleApiMapper.googleResponseDtoToDistance(result);
//            }else if(result.getStatus().equals("REQUEST_DENIED")){
//                throw new IllegalArgumentException("GoogleDistanceService REQUEST_DENIED ");
//
//            }
//
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        return distance;


}