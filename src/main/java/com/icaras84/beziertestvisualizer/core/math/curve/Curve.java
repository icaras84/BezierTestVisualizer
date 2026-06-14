package com.icaras84.beziertestvisualizer.core.math.curve;

import com.icaras84.beziertestvisualizer.core.math.Complex;

public interface Curve {

    Complex getPosition(double t);
    Complex getTangent(double t);
    Complex getTangentGradient(double t);
    double getCurvature(double t);

    Complex[] getPositions(double[] tValues);
    Complex[] getTangents(double[] tValues);
    Complex[] getTangentGradients(double[] tValues);
    double[] getCurvatures(double[] tValues);

    Complex[] getPointCharacteristics(double t);
}
