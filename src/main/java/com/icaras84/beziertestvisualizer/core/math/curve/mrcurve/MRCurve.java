package com.icaras84.beziertestvisualizer.core.math.curve.mrcurve;

import com.icaras84.beziertestvisualizer.core.math.Complex;
import com.icaras84.beziertestvisualizer.core.math.curve.Curve;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class MRCurve implements Curve {

    private DMatrixRMaj controlMatrix;
    private DMatrixRMaj basisMatrix;
    private PolynomialMatrix polynomialMatrix;

    public MRCurve(DMatrixRMaj controlMatrix, DMatrixRMaj basisMatrix, PolynomialMatrix polynomialMatrix) {
        this.controlMatrix = controlMatrix;
        this.basisMatrix = basisMatrix;
        this.polynomialMatrix = polynomialMatrix;
    }

    public DMatrixRMaj getControlMatrix() {
        return controlMatrix;
    }

    public void setControlMatrix(DMatrixRMaj controlMatrix) {
        this.controlMatrix = controlMatrix;
    }

    public DMatrixRMaj getBasisMatrix() {
        return basisMatrix;
    }

    public void setBasisMatrix(DMatrixRMaj basisMatrix) {
        this.basisMatrix = basisMatrix;
    }

    public PolynomialMatrix getPolynomialMatrix() {
        return polynomialMatrix;
    }

    public void setPolynomialMatrix(PolynomialMatrix polynomialMatrix) {
        this.polynomialMatrix = polynomialMatrix;
    }

    public DMatrixRMaj premultipliedBasisAndControl() {
        return CommonOps_DDRM.mult(this.basisMatrix, this.controlMatrix, null);
    }

    @Override
    public Complex getPosition(double t) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(0, t), this.premultipliedBasisAndControl(), output);
        return new Complex(output.data[0],  output.data[1]);
    }

    @Override
    public Complex getTangent(double t) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(1, t), this.premultipliedBasisAndControl(), output);
        return new Complex(output.data[0],  output.data[1]);
    }

    @Override
    public Complex getTangentGradient(double t) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(2, t), this.premultipliedBasisAndControl(), output);
        return new Complex(output.data[0],  output.data[1]);
    }

    @Override
    public double getCurvature(double t) {
        Complex vel = this.getTangent(t);
        Complex accel = this.getTangentGradient(t);
        double numerator = vel.getReal() * accel.getImag() -  vel.getImag() * accel.getReal();
        if (numerator == 0.0) {
            return 0.0;
        }
        double denominator = vel.length();
        return numerator / (denominator * denominator * denominator);
    }

    private double curvature(double vx, double vy, double ax, double ay) {
        double numerator = vx * ay - vy * ax;
        if (numerator == 0.0) {
            return 0.0;
        }
        double denominator = Math.hypot(vx, vy);
        return numerator / (denominator * denominator * denominator);
    }

    @Override
    public DMatrixRMaj getPositions(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(0, tValues), this.premultipliedBasisAndControl(), output);
        return output;
    }

    @Override
    public DMatrixRMaj getTangents(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(1, tValues), this.premultipliedBasisAndControl(), output);
        return output;
    }

    @Override
    public DMatrixRMaj getTangentGradients(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(2, tValues), this.premultipliedBasisAndControl(), output);
        return output;
    }

    @Override
    public DMatrixRMaj getCurvatures(double[] tValues) {
        DMatrixRMaj velocities = this.getTangents(tValues);
        DMatrixRMaj accelerations = this.getTangentGradients(tValues);

        DMatrixRMaj output = new DMatrixRMaj(tValues.length, 1);

        for (int i = 0; i < tValues.length; i++) {
            int xIdx = 2 * i;
            int yIdx = xIdx + 1;
            output.data[i] = curvature(velocities.data[xIdx],  velocities.data[yIdx], accelerations.data[xIdx], accelerations.data[yIdx]);
        }

        return output;
    }

    @Override
    public DMatrixRMaj getPointCharacteristics(double t) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(new int[]{0, 1, 2}, t), this.premultipliedBasisAndControl(), output);
        return output;
    }
}
