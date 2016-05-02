/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers.boosting;

import lv.edgarsgars.mathematics.Matrix;

/**
 *
 * @author Edgar_000
 */
public class SimpleClassifier {

    private String condition;
    private double alpha;
    private int dim;
    private int cl;
    private double error;

    public SimpleClassifier(String condition, int dim, int cl) {
        this.condition = condition;
        this.dim = dim;
        this.cl = cl;
    }

    public Matrix evaluate(Matrix x) {
        return x.getCol(dim).conditon(condition);
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }

    public int getClassIndex() {
        return cl;
    }

    public void setClassIndex(int cl) {
        this.cl = cl;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getError() {
        return error;
    }

    @Override
    public String toString() {
        return "SimpleClassifier{" + "condition=" + condition + ", alpha=" + alpha + ", dim=" + dim + ", cl=" + cl + ", error=" + error + '}';
    }

}
