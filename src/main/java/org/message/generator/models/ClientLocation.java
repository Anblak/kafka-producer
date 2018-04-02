package org.message.generator.models;

public class ClientLocation {
    private double location[];
    private String city;
    private String state;
    private String population;

    public ClientLocation() {
    }

    public ClientLocation(double[] location, String city, String state, String population) {
        this.location = location;
        this.city = city;
        this.state = state;
        this.population = population;
    }

    public double[] getLocation() {
        return location;
    }

    public ClientLocation setLocation(double[] location) {
        this.location = location;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ClientLocation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public ClientLocation setState(String state) {
        this.state = state;
        return this;
    }

    public String getPopulation() {
        return population;
    }

    public ClientLocation setPopulation(String population) {
        this.population = population;
        return this;
    }
}
