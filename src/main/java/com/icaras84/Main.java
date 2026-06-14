package com.icaras84;

import com.icaras84.beziertestvisualizer.core.math.curve.mrcurve.PolynomialMatrix;


public class Main {
    static void main() {
        PolynomialMatrix polynomialMatrix = new PolynomialMatrix(5);

        polynomialMatrix.getTMatrix(new int[]{0, 1, 2}, 1).print();
        polynomialMatrix.getTMatrix(new int[]{0, 1, 2}, 0.5).print();
    }

    public static void print2DArray(int[][] array) {
        for (int[] objs : array) {
            System.out.print("[");
            for (int obj : objs) {
                System.out.print(obj + " ");
            }
            System.out.println("\b]");
        }
    }
}
