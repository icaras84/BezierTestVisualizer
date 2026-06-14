package com.icaras84.beziertestvisualizer.core.math.curve.mrcurve;

import com.icaras84.beziertestvisualizer.core.math.Complex;
import com.icaras84.beziertestvisualizer.core.math.curve.Curve;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;

public class MRCurve implements Curve {

    private ControlParameterMatrix controlMatrix;
    private DMatrixRMaj basisMatrix;
    private PolynomialMatrix polynomialMatrix;

    public MRCurve(ControlParameterMatrix controlMatrix, DMatrixRMaj basisMatrix, PolynomialMatrix polynomialMatrix) {
        this.controlMatrix = controlMatrix;
        this.basisMatrix = basisMatrix;
        this.polynomialMatrix = polynomialMatrix;
    }

    public ControlParameterMatrix getControlMatrix() {
        return controlMatrix;
    }

    public void setControlMatrix(ControlParameterMatrix controlMatrix) {
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
        return CommonOps_DDRM.mult(this.basisMatrix, this.controlMatrix.getControlParameterMatrix(), null);
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
        return 0;
    }

    @Override
    public Complex[] getPositions(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(0, tValues), this.premultipliedBasisAndControl(), output);
        Complex[] outArray = new Complex[tValues.length];

        for (int i = 0; i < tValues.length; i++) {
            outArray[i] = new Complex(output.get(i, 0), output.get(i, 1));
        }

        return outArray;
    }

    @Override
    public Complex[] getTangents(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(1, tValues), this.premultipliedBasisAndControl(), output);
        Complex[] outArray = new Complex[tValues.length];

        for (int i = 0; i < tValues.length; i++) {
            outArray[i] = new Complex(output.get(i, 0), output.get(i, 1));
        }

        return outArray;
    }

    @Override
    public Complex[] getTangentGradients(double[] tValues) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(2, tValues), this.premultipliedBasisAndControl(), output);
        Complex[] outArray = new Complex[tValues.length];

        for (int i = 0; i < tValues.length; i++) {
            outArray[i] = new Complex(output.get(i, 0), output.get(i, 1));
        }

        return outArray;
    }

    @Override
    public double[] getCurvatures(double[] tValues) {
        return new double[0];
    }

    @Override
    public Complex[] getPointCharacteristics(double t) {
        DMatrixRMaj output = new DMatrixRMaj();
        CommonOps_DDRM.mult(this.polynomialMatrix.getTMatrix(new int[]{0, 1, 2}, t), this.premultipliedBasisAndControl(), output);
        Complex[] outArray = new Complex[3];

        for (int i = 0; i < outArray.length; i++) {
            outArray[i] = new Complex(output.get(i, 0), output.get(i, 1));
        }

        return outArray;
    }
}
