package com.icaras84.beziertestvisualizer.core.math.curve;

import com.icaras84.beziertestvisualizer.core.math.Complex;
import org.ejml.data.DMatrixRMaj;

public interface Curve {

    Complex getPosition(double t);
    Complex getTangent(double t);
    Complex getTangentGradient(double t);
    double getCurvature(double t);

    DMatrixRMaj getPositions(double[] tValues);
    DMatrixRMaj getTangents(double[] tValues);
    DMatrixRMaj getTangentGradients(double[] tValues);
    DMatrixRMaj getCurvatures(double[] tValues);

    DMatrixRMaj getPointCharacteristics(double t);
}
