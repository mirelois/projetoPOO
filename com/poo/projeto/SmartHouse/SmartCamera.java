package com.poo.projeto.SmartHouse;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class SmartCamera extends SmartDevice implements Serializable {
    private Integer[] resolution;
    private Integer dimension;

    public SmartCamera(){
        super();
        this.resolution = new Integer[2];
        this.dimension = 0;
    }

    public SmartCamera(String id, Integer[] resolution, Integer dimension) {
        super(id, dimension * 100 + resolution[0] + resolution[1], 2);
        this.resolution = resolution.clone();
        this.dimension = dimension;
    }

    public SmartCamera(String id, boolean on, Integer[] resolution, Integer dimension) {
        super(id, on,dimension * 100 + resolution[0] + resolution[1],2 );
        this.resolution = resolution.clone();
        this.dimension = dimension;
    }

    public SmartCamera(String id, double baseConsumption, Integer[] resolution, Integer dimension) {
        super(id, baseConsumption, dimension * 100 + resolution[0] + resolution[1]);
        this.resolution = resolution.clone();
        this.dimension = dimension;
    }

    public SmartCamera(String id, boolean on, double installationCost, Integer[] resolution, Integer dimension) {
        super(id, on, installationCost, 2);
        this.resolution = resolution.clone();
        this.dimension = dimension;
    }

    public SmartCamera(String id, boolean on, double installationCost, double baseConsumption, Integer[] resolution, Integer dimension) {
        super(id, on, installationCost, baseConsumption);
        this.resolution = resolution.clone();
        this.dimension = dimension;
    }

    public SmartCamera(SmartCamera smartCamera) {
        super(smartCamera);
        this.resolution = smartCamera.getResolution();
        this.dimension = smartCamera.getDimension();
    }

    public Integer[] getResolution() {
        return this.resolution.clone();
    }

    public void setResolution(Integer[] resolution) {
        this.resolution = resolution.clone();
    }

    public Integer getDimension() {
        return this.dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() { // toString default
        return super.toString() +
                "SmartCamera{" +
                "resolution=" + Arrays.toString(resolution) +
                ", dimension=" + dimension +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return Arrays.equals(resolution, that.resolution) && Objects.equals(dimension, that.dimension);
    }

    @Override
    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    @Override
    public double dailyConsumption(){
        return this.dimension * this.resolution[0] * this.resolution[1];
    }

}
