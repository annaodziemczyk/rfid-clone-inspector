package com.cit.locator.distance.om;

public class Distance {
    private double lengthInMeters;
    private long durationInSeconds;
    private TravelMeans travelMeans;

    public double getLengthInMeters() {
        return lengthInMeters;
    }

    public void setLengthInMeters(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public TravelMeans getTravelMeans() {
        return travelMeans;
    }

    public void setTravelMeans(TravelMeans travelMeans) {
        this.travelMeans = travelMeans;
    }
}
