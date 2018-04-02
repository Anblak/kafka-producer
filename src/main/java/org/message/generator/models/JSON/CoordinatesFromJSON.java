package org.message.generator.models.JSON;

public class CoordinatesFromJSON {
    private String city;
    private String growth_from_2000_to_2013;
    private double latitude;
    private double longitude;
    private String population;
    private String rank;
    private String state;

    public CoordinatesFromJSON() {
    }

    public String getCity() {
        return city;
    }

    public CoordinatesFromJSON setCity(String city) {
        this.city = city;
        return this;
    }

    public String getGrowth_from_2000_to_2013() {
        return growth_from_2000_to_2013;
    }

    public CoordinatesFromJSON setGrowth_from_2000_to_2013(String growth_from_2000_to_2013) {
        this.growth_from_2000_to_2013 = growth_from_2000_to_2013;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public CoordinatesFromJSON setPopulation(String population) {
        this.population = population;
        return this;
    }

    public String getRank() {
        return rank;
    }

    public CoordinatesFromJSON setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getState() {
        return state;
    }

    public CoordinatesFromJSON setState(String state) {
        this.state = state;
        return this;
    }
}
