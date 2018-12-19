package com.cit.locator.distance.service;

import com.cit.common.om.location.GeoLocation;
import com.cit.locator.airport.om.Airport;
import com.cit.locator.airport.service.IAirportLookupService;
import com.cit.locator.distance.googleapi.GoogleApiService;
import com.cit.locator.distance.om.TravelRoute;
import com.cit.locator.exception.TravelRouteNotFoundException;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class DistanceService implements IDistanceService {

    @Autowired
    private GoogleApiService googleDistanceService;

    @Autowired
    private IAirportLookupService airportLookupService;

    private static final int EARTH_RADIUS = 6371;
    private final double AVERAGE_WALKING_SPEED = 1.4;
    private final double AVERAGE_DRIVING_SPEED = 13.8889;
    private final double AVERAGE_FLYING_SPEED = 222.222;


    @Override
    public TravelRoute findShortestDrivingRoute(GeoLocation from, GeoLocation to, Instant departureTime) {

        TravelRoute travelRoute=new TravelRoute();

        try {
            travelRoute=googleDistanceService.distancematrix(from, to, TravelMode.DRIVING, departureTime);
        } catch (TravelRouteNotFoundException e) {
            travelRoute.setTravelMode(TravelMode.TRANSIT);
            travelRoute.setLengthInMeters(this.calculateDistanceInMeters(from, to));
            travelRoute.setDurationInSeconds(this.calculateTravelTime(travelRoute.getLengthInMeters(), TravelMode.TRANSIT));
        }

        return travelRoute;
    }

    @Override
    public TravelRoute findShortestTransitRoute(GeoLocation from, GeoLocation to, Instant departureTime){
        TravelRoute travelRoute=new TravelRoute();

        try {
            travelRoute=googleDistanceService.distancematrix(from, to, TravelMode.TRANSIT, departureTime);
        } catch (TravelRouteNotFoundException e) {
            travelRoute.setTravelMode(TravelMode.TRANSIT);
            travelRoute.setLengthInMeters(this.calculateDistanceInMeters(from, to));
            travelRoute.setDurationInSeconds(this.calculateTravelTime(travelRoute.getLengthInMeters(), TravelMode.TRANSIT));
        }

        Airport fromNearestAirport = findNearestAirport(from);
        Airport toNearestAirport = findNearestAirport(to);

        double distanceToAirport = calculateDistanceInMeters(from, fromNearestAirport);
        distanceToAirport+=calculateDistanceInMeters(to, toNearestAirport);
        long travelTimeToAirport = calculateTravelTime(distanceToAirport, TravelMode.DRIVING);

        if(fromNearestAirport!=null && toNearestAirport!=null){
            double distanceBetweenAirports=calculateDistanceInMeters(fromNearestAirport, toNearestAirport);
            long travelTimeBetweenNearestAirports = this.calculateTravelTime(distanceBetweenAirports, TravelMode.TRANSIT);

            long airTravelTime = travelTimeBetweenNearestAirports+travelTimeToAirport;

            if(airTravelTime<travelRoute.getDurationInSeconds()){
                travelRoute.setDurationInSeconds(airTravelTime);
                travelRoute.setLengthInMeters(distanceBetweenAirports+distanceToAirport);
            }
        }

        return travelRoute;
    }

    private Airport findNearestAirport(GeoLocation geoLocation){
        Airport nearestAirport=this.airportLookupService.findNearestAirport(geoLocation);
        return nearestAirport;
    }

    @Override
    public long calculateTravelTime(double distanceInMeters, TravelMode travelMode){
        switch (travelMode){
            case DRIVING:
                return (new Double(distanceInMeters/AVERAGE_DRIVING_SPEED)).longValue();
            case TRANSIT:
                return (new Double(distanceInMeters/AVERAGE_FLYING_SPEED)).longValue();
            default:
                return (new Double(distanceInMeters/AVERAGE_WALKING_SPEED)).longValue();
        }
    }

    private double calculateDistanceInMeters(Airport from, Airport to){
        GeoLocation fromGeoLocation=new GeoLocation();
        fromGeoLocation.setX(from.getLatitude());
        fromGeoLocation.setY(from.getLongitude());
        GeoLocation toGeoLocation=new GeoLocation();
        toGeoLocation.setX(to.getLatitude());
        toGeoLocation.setY(to.getLongitude());
        return this.calculateDistanceInMeters(fromGeoLocation, toGeoLocation);
    }

    @Override
    public double calculateDistanceInMeters(GeoLocation from, Airport to){
        GeoLocation toGeoLocation=new GeoLocation();
        toGeoLocation.setX(to.getLatitude());
        toGeoLocation.setY(to.getLongitude());
        return this.calculateDistanceInMeters(from, toGeoLocation);
    }

    /**
     * credit: https://github.com/jasonwinn/haversine
     * @param from
     * @param to
     * @return
     */
    @Override
    public double calculateDistanceInMeters(GeoLocation from,
                                    GeoLocation to) {

        double dLat  = Math.toRadians((to.getX() - from.getX()));
        double dLong = Math.toRadians((to.getY() - from.getY()));

        double latFrom = Math.toRadians(from.getX());
        double latTo =Math.toRadians(to.getX());

        double a = haversin(dLat) + Math.cos(latFrom) * Math.cos(latTo) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }

    public double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }


}
