package com.icaras84.beziertestvisualizer.core.math.curve;

import com.icaras84.beziertestvisualizer.utils.Copyable;

import java.util.Objects;

public class ControlPoint implements Copyable<ControlPoint> {

    private double x;
    private double y;

    public ControlPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ControlPoint() {
        this(0, 0);
    }

    @Override
    public ControlPoint copy() {
        return new ControlPoint(this.x, this.y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double get(int idx){
        return switch (idx) {
            case 0 -> x;
            case 1 -> y;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    public void set(int idx, double val){
        switch (idx) {
            case 0 -> x = val;
            case 1 -> y = val;
            default -> throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ControlPoint that)) return false;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
