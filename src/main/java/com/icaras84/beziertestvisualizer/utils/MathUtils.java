package com.icaras84.beziertestvisualizer.utils;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.SingularValueDecomposition;

import java.util.Optional;
import java.util.function.Function;

/**
 * A class of general but useful utility methods for computations.
 */
public class MathUtils {

    /**
     * Numerically stable LERP
     * @param a start
     * @param b end
     * @param t interpolation percentage (decimal)
     * @return value between a and b
     */
    public static double lerp(double a, double b, double t) {
        return (1 - t) * a + t * b;
    }

    /**
     * Compare two values and see if they are within a threshold of each other
     * @param a first number
     * @param b second number
     * @param eps epsilon / error radius
     * @return true if a and b are close enough, else false
     */
    public static boolean epsEquals(double a, double b, double eps) {
        return Math.abs(a - b) < eps;
    }

    /**
     * Same as <code>epsEquals(a, b, eps)</>, however, eps defaults to 1e-9
     * @param a first number
     * @param b second number
     * @return true if a and b are close enough, else false
     */
    public static boolean epsEquals(double a, double b) {
        return MathUtils.epsEquals(a, b, 1e-9);
    }

    /**
     * A method to create a double array that is the length of the number of samples with the first element
     * as the start (a), the end element (b), and the rest of the elements are evenly spaced subdivisions.
     * @param a start value
     * @param b end value
     * @param samples number of samples (>= 2 since the start and end values count as samples)
     * @return new double array with evenly spaced values in each element where [0] is the start value and [samples - 1] is the end value
     */
    public static double[] linspace(double a, double b, int samples) {
        if (samples < 2) {
            throw new IllegalArgumentException("Samples must be >= 2");
        }
        double[] result = new double[samples];
        result[0] = a;
        result[result.length - 1] = b;

        double t;
        for (int i = 1; i < samples - 1; i++) {
            t = i / (samples - 1d);
            result[i] = MathUtils.lerp(a, b, t);
        }

        return result;
    }

    /**
     * A method to create a linear mapping function so that an input maps to an output
     * @param inputMin input minimum
     * @param inputMax input maximum
     * @param outputMin output minimum
     * @param outputMax output maximum
     * @return function that accepts one argument and outputs one value mapped into the output range
     */
    public static Function<Double, Double> createLinearMappingFunction(double inputMin, double inputMax, double outputMin, double outputMax){
        double rangeRatio = (outputMax - outputMin) / (inputMax - inputMin);
        return input -> Math.fma(rangeRatio, input - inputMin, outputMin);
    }

    /**
     * A method that maps an input value into an output range
     * @param inputMin input minimum
     * @param inputMax input maximum
     * @param outputMin output minimum
     * @param outputMax output maximum
     * @param inputValue value in input range
     * @return value in output space
     */
    public static double linearlyMap(double inputMin, double inputMax, double outputMin, double outputMax, double inputValue){
        return Math.fma((outputMax - outputMin) / (inputMax - inputMin), inputValue - inputMin, outputMin);
    }

    public static DMatrixRMaj extrudeVertically(double[] values, int depth){
        double[] extrudedValues = new double[values.length * depth];
        for (int i = 0; i < values.length; i++){
            for (int j = 0; j < depth; j++) {
                extrudedValues[i * depth + j] = values[i];
            }
        }
        return new DMatrixRMaj(depth, values.length, false, extrudedValues);
    }

    public static DMatrixRMaj extrudeHorizontally(double[] values, int length){
        double[] extrudedValues = new double[values.length * length];
        for (int i = 0; i < values.length; i++){
            for (int j = 0; j < length; j++) {
                extrudedValues[i * length + j] = values[i];
            }
        }
        return new DMatrixRMaj(values.length, length, true, extrudedValues);
    }

    public static Optional<SingularValueDecomposition<DMatrixRMaj>> svd(DMatrixRMaj decompTarget){
        SingularValueDecomposition<DMatrixRMaj> out = DecompositionFactory_DDRM.svd(decompTarget.numRows, decompTarget.numCols, true, true, false);
        if (out.decompose(decompTarget)){
            return Optional.of(out);
        }
        return Optional.empty();
    }

    public static DMatrixRMaj covariance(DMatrixRMaj data, boolean bias){
        DMatrixRMaj out = new DMatrixRMaj(data.numRows, data.numCols);
        DMatrixRMaj means = new DMatrixRMaj(1, data.numCols);

        for (int i = 0; i < data.numCols; i++) {
            DMatrixRMaj col = new DMatrixRMaj(data.numRows, 1);
            CommonOps_DDRM.extractColumn(data, i, col);

            means.data[i] = CommonOps_DDRM.sumCols(col, null).data[0] / data.numRows;
        }

        means = extrudeVertically(means.data, data.numRows);

        CommonOps_DDRM.subtract(data, means, out);
        CommonOps_DDRM.mult(CommonOps_DDRM.transpose(out, null), out.copy(), out);
        final double denom = bias ? data.numRows : data.numRows - 1d;
        CommonOps_DDRM.apply(out, ele -> ele / denom);

        return out;
    }
}
