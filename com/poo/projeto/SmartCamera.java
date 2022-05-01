package com.poo.projeto;

import java.util.Arrays;
import java.util.Objects;

public class SmartCamera extends SmartDevice{
    private Integer[] resolution;
    private Integer dimension;

    public SmartCamera(){
        super();
        this.resolution = new Integer[2];
        this.dimension = 0;
    }

    public SmartCamera(String id, boolean on, Integer[] resolution, Integer dimension) {
        super(id, on);
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

    public String toString() { // toString default
        return "SmartCamera{" +
                "resolution=" + Arrays.toString(resolution) +
                ", dimension=" + dimension +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return Arrays.equals(resolution, that.resolution) && Objects.equals(dimension, that.dimension);
    }

    public SmartCamera clone(){
        return new SmartCamera(this);
    }

    public int ConsumoDiario(){
        return this.dimension * this.resolution[0] * this.resolution[1];
    }

}
