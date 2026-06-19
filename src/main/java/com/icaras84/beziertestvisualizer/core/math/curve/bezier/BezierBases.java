package com.icaras84.beziertestvisualizer.core.math.curve.bezier;

import org.ejml.data.DMatrixRMaj;

import java.util.HashMap;

public class BezierBases {

    private static final HashMap<Integer, DMatrixRMaj> basesCache = new HashMap<>();

    /**
     * This method generate Pascal's triangle with alternating signs (all values are left-aligned in the matrix)
     * @param layers how many layers of the triangle to generate; the minimum should be 1
     * @return Pascal's triangle (left-aligned) w/ alternating signs
     */
    public static double[][] generatePascalTriangle(int layers) {
        double[][] output = new double[layers][layers];

        // seed the first value of pascal's triangle
        output[0][0] = 1;

        for (int row = 1; row < output.length; row++) {
            // since col is 0 and [row][col - 1] is out of bounds at this moment,
            // the default should be a 0 to solve edge cases
            double a = 0.0;

            for (int col = 0; col <= row; col++) {
                double b = output[row - 1][col];
                // the ruleset for the typical pascal's triangle is a + b.
                // but, we want alternating signs for cheap without iterating again,
                // so the rule goes to b - a
                output[row][col] = b - a;
                // assign 'a' as 'b' since it will be the correct previous [row - 1] value
                // for the next iteration
                a = b;
            }
        }

        return output;
    }

    /**
     * This method generates the characteristic matrix based on the degree of a requested Bézier curve.
     * 2 for quadratic, 3 for cubic, 4 for quartic, 5 for quintic... etc.
     * @param controlPointCount Bézier curve's control point count
     * @return characteristic matrix of the Matrix class
     */
    public static DMatrixRMaj generateBezierCharacteristicMatrix(int controlPointCount){
        // get a square matrix that contains Pascal's triangle
        double[][] output = generatePascalTriangle(controlPointCount);

        // sample the last row and multiply all other rows by the corresponding value
        double[] sampledRow = output[output.length - 1];
        // first row of the matrix is always positive, so start on the second row
        for (int i = 1; i < output.length; i++) {
            // only go to up to the 'i'th element in row because the rest are zeroes
            for (int j = 0; j <= i; j++) {
                output[i][j] = output[i][j] * sampledRow[i];
            }
        }

        return new DMatrixRMaj(output);
    }

    /**
     * This method gets a characteristic matrix that is stored. If it doesn't exist, generate and return it.
     * @param controlPointCount Bézier curve's control point count
     * @return characteristic matrix of the Matrix class
     */
    public static DMatrixRMaj getBezierCharacteristicMatrix(int controlPointCount){
        return BezierBases.basesCache.computeIfAbsent(controlPointCount, BezierBases::generateBezierCharacteristicMatrix);
    }
}
