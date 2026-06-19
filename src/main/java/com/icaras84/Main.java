package com.icaras84;

import com.icaras84.beziertestvisualizer.core.math.curve.bezier.BezierBases;
import com.icaras84.beziertestvisualizer.core.math.curve.bezier.BezierCurveSupplier;


public class Main {
    static void main() {
        BezierBases.generateBezierCharacteristicMatrix(4).print();
    }
}
