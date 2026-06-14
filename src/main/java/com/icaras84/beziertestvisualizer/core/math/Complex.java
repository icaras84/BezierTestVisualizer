package com.icaras84.beziertestvisualizer.core.math;


import com.icaras84.beziertestvisualizer.utils.Copyable;
import org.ejml.data.DMatrixRMaj;

import java.awt.geom.Point2D;

/**
 * A class to represent complex numbers by storing the real and imaginary coefficients
 */
public class Complex implements Copyable<Complex> {
    public static final Complex REAL_AXIS = new Complex(1, 0);
    public static final Complex IMAG_AXIS = new Complex(0, 1);


    /** The real part of <code>a + bi</code> */
    private double real;
    /** The imaginary part of <code>a + bi</code> */
    private double imag;

    /**
     * Construct a complex number with a real and imaginary part
     * a + bi
     * @param real a - real coefficient
     * @param imag b - imaginary coefficient
     */
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /**
     * Construct a 0 in the form of a complex number
     * <code>0 + 0i</code>
     */
    public Complex(){
        this(0, 0);
    }

    /**
     * A method to return a deep copy of this complex
     * @return new Complex with the same real and imag coefficients
     */
    public Complex copy(){
        return new Complex(this.real, this.imag);
    }

    /**
     * A method to turn this complex number into a Point2D object
     * @return Point2D w/ real -> x; imag -> y
     */
    public Point2D toPoint(){
        return new Point2D.Double(this.real, this.imag);
    }

    /**
     * A method to fetch an element at a specified index if the real and imaginary coefficients
     * were in an array
     * @param idx 0 or 1
     * @return real or imaginary coefficient
     */
    public double get(int idx){
        return switch (idx) {
            case 0 -> this.real;
            case 1 -> this.imag;
            default -> throw new IndexOutOfBoundsException("A Complex has only 2 elements: [real, imag].");
        };
    }

    /**
     * A method to set an element at a specified index to the value if the
     * real and imaginary coefficients were in an array
     * @param idx 0 or 1
     */
    public void set(int idx, double val){
        switch (idx) {
            case 0 -> this.real = val;
            case 1 -> this.imag = val;
            default -> throw new IndexOutOfBoundsException("A Complex has only 2 elements: [real, imag].");
        }
    }

    /**
     * A method to put the real and imaginary coefficients of this Complex into a 2 element double array
     * @return <code>new double[]{this.getReal(), this.getImag}</code>
     */
    public double[] toArray(){
        return new double[]{this.real, this.imag};
    }

    public DMatrixRMaj toColumnVector(){
        return new DMatrixRMaj(toArray());
    }

    public DMatrixRMaj toRowVector(){
        return new DMatrixRMaj(1, 2, true, toArray());
    }

    /**
     * Get the real component of the Complex
     * @return double
     */
    public double getReal() {
        return real;
    }

    /**
     * Sets the real component of the Complex
     * @param real new coefficient
     */
    public void setReal(double real) {
        this.real = real;
    }

    /**
     * Returns a complex that has the new real coefficient and this imaginary coefficient
     * @param real new real coefficient
     * @return new Complex
     */
    public Complex withReal(double real) {
        return new Complex(real, this.imag);
    }

    /**
     * Get the imaginary component of the Complex
     * @return double
     */
    public double getImag() {
        return imag;
    }

    /**
     * Sets the imaginary component of the Complex
     * @param imag new imaginary coefficient
     */
    public void setImag(double imag) {
        this.imag = imag;
    }

    /**
     * Returns a complex that has the new imaginary coefficient and this real coefficient
     * @param imag new imaginary coefficient
     * @return new Complex
     */
    public Complex withImag(double imag) {
        return new Complex(this.real, imag);
    }

    /**
     * Method to get the sum this and another complex number
     * @param other the other complex number
     * @return new Complex that is the sum of the complexes
     */
    public Complex plus(Complex other){
        return new Complex(this.real + other.real, this.imag + other.imag);
    }

    /**
     * Method to get the difference between this and another complex number
     * @param other the other complex number
     * @return new Complex that is the difference of the complexes
     */
    public Complex minus(Complex other){
        return new Complex(this.real - other.real, this.imag - other.imag);
    }

    /**
     * Method to multiply this and another complex number
     * @param other the other complex number
     * @return new Complex that is the product of the complexes
     */
    public Complex times(Complex other){
        return new Complex(this.real * other.real - this.imag * other.imag,  this.real * other.imag + this.imag * other.real);
    }

    /**
     * Method to multiply this and a real scalar
     * @param scalar scalar number
     * @return new Complex with the real and imaginary coefficients scaled by the scalar
     */
    public Complex times(double scalar){
        return new Complex(this.real * scalar, this.imag * scalar);
    }

    /**
     * Method to divide this and another complex number
     * @param other the other complex number
     * @return new Complex that is the quotient of the complexes
     */
    public Complex div(Complex other){
        double denominator = other.squaredLength();
        return new Complex((this.real * other.real + this.imag * other.imag) / denominator, (this.imag * other.real - this.real * other.imag) / denominator);
    }

    /**
     * Method to divide this complex by a divisor
     * @param divisor the divisor
     * @return new Complex that is the quotient of the operation
     */
    public Complex div(double divisor){
        return new Complex(this.real / divisor, this.imag / divisor);
    }

    /**
     * Method to return a complex number with both the real and imaginary
     * coefficients with their signs flipped
     * @return new Complex representing <code>-a - bi</code>
     */
    public Complex unaryMinus() {
        return new Complex(-this.real, -this.imag);
    }

    /**
     * Treat the complexes like vectors and perform a dot product on this and the other complex number
     * @param other the other complex number
     * @return dot product of complex numbers z1 and z2
     */
    public double dotp(Complex other){
        return this.real * other.real + this.imag * other.imag;
    }

    /**
     * Complex conjugate
     * @return new Complex representing <code>a - bi</code>
     */
    public Complex conjugate(){
        return new Complex(this.real, -this.imag);
    }

    /**
     * Method to return the square of this complex number
     * @return new Complex with <code>(a^2 - b^2) + (2ab)i</code>
     */
    public Complex squared(){
        return new Complex(this.real * this.real - this.imag * this.imag, 2 * this.real * this.imag);
    }

    /**
     * Method to get the length of this complex number from <code>0 + 0i</code>
     * @return Euclidean distance from <code>0 + 0i</code>
     */
    public double length(){
        return Math.hypot(this.real, this.imag);
    }

    /**
     * Method to get the length squared of this complex number
     * @return squared Euclidean distance
     */
    public double squaredLength(){
        return this.real * this.real + this.imag * this.imag;
    }

    public Complex normalized(){
        double length = this.length();
        return new Complex(this.real / length, this.imag / length);
    }

    public void normalize(){
        double length = this.length();
        this.real /= length;
        this.imag /= length;
    }

    /**
     * Method to get the angle between the complex number and the positive of the real axis
     * @return angle (radians)
     */
    public double getAngle(){
        return Math.atan2(this.imag, this.real);
    }

    /**
     * Method to get the shadow of this complex on the other complex
     * @param other complex number to project a shadow onto
     * @return new Complex number that is the scaled version of the input complex number
     */
    public Complex projectOnto(Complex other){
        double sLen = other.squaredLength();
        return other.times(this.dotp(other) / sLen);
    }

    /**
     * Method to get the complex number orthogonal to the projection of this complex onto the other complex number
     * @param other complex number that the projectOnto method will use
     * @return new Complex number orthogonal to the complex number that this complex was projected onto
     */
    public Complex orthogonalTo(Complex other){
        Complex projection = this.projectOnto(other);
        return this.minus(projection);
    }

    /**
     * Method to create a String representation of the complex number
     * @return string in the form of "a + bi"
     */
    public String toString() {
        return "(" + this.real + " + " + this.imag + "i)";
    }

    /**
     * A static method to create a complex number from <code>re^t</code>
     * @param magnitude r
     * @param angle t (radians)
     * @return <code>r(cos(t) + i sin(t))</code>
     */
    public static Complex exp(double magnitude, double angle) {
        return new Complex(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    /**
     * A static method to create a complex number from <code>(r cis t)^n</code>
     * @param magnitude r
     * @param angle t
     * @param n integer power
     * @return <code>(r^n)(cos(nt) + i sin(nt))</code>
     */
    public static Complex cis(double magnitude, double angle, int n) {
        double nMagnitude = Math.pow(magnitude, n);
        double nAngle = angle * n;
        return new Complex(Math.cos(nAngle) * nMagnitude, Math.sin(nAngle) * nMagnitude);
    }

    /**
     * A static method to create a complex number from a Point2D object
     * @param point Point2D object
     * @return Complex with x -> real; y -> imag
     */
    public static Complex fromPoint(Point2D point){
        return new Complex(point.getX(), point.getY());
    }
}
