package com.icaras84.beziertestvisualizer.core.math.curve.bezier;

import com.icaras84.beziertestvisualizer.core.math.curve.ControlPoint;
import org.ejml.data.DMatrixRMaj;

import java.util.List;
import java.util.Vector;

public class BezierCurveSupplier {

    private Vector<ControlPoint> controlPoints;

    public BezierCurveSupplier(Vector<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public BezierCurveSupplier(){
        this.controlPoints = new Vector<>(
                List.of(new ControlPoint(-1, -1),
                        new ControlPoint(-1, 0),
                        new ControlPoint(1, 0),
                        new ControlPoint(1, 1))
        );
    }

    public Vector<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void setControlPoints(Vector<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }

    public DMatrixRMaj createControlPointMatrix() {
        DMatrixRMaj controlMatrix = new DMatrixRMaj(controlPoints.size(), 2);
        for (int i = 0; i < this.controlPoints.size(); i++) {
            ControlPoint controlPoint = this.controlPoints.get(i);
            int xIdx = 2 * i;
            int yIdx = xIdx + 1;

            controlMatrix.data[xIdx] = controlPoint.getX();
            controlMatrix.data[yIdx] = controlPoint.getY();
        }

        return controlMatrix;
    }
}
