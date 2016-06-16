package lv.edgarsgars.classifiers.boosting;

import lv.edgarsgars.mathematics.Matrix;

/**
 * Simple classifier that can evaluate simple conditions
 * @author edgars.garsneks
 */
public class SimpleClassifier {

    private String condition;
    private double alpha;
    private int dim;
    private int cl;
    private double error;

    /**
     * Creates simple classifier with specific condition on specific dimension
     * for specific class. Example new Classifier("x > 3 && x < 5", 2, 3) 
     * @param condition condition to evaluate. Must be valid boolean expression
     * with x as variable. @param dim dimension which will be used for
     * evaluation
     * @param cl target class
     */
    public SimpleClassifier(String condition, int dim, int cl) {
        this.condition = condition;
        this.dim = dim;
        this.cl = cl;

    }

    /**
     * Evaluates matrix against condition
     *
     * @param x data matrix with single row / column or column count as
     * specified dimension
     * @return logical matrix with 1's where condition was met
     */
    public Matrix evaluate(Matrix x) {
        if (x.getCollumCount() == 1) {
            return x.conditon(condition);
        } else if (x.getRowCount() == 1) {
            return x.T().conditon(condition).T();
        }
        return x.getCol(dim).conditon(condition);
    }

    /**
     * @return classifier condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets classifier condition
     *
     * @param condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return Return simple classifier voting alpha coefficient
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets classifier voting alpha coefficient
     *
     * @param alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return classifier dimension
     */
    public int getDim() {
        return dim;
    }

    /**
     * Sets classifier dimension
     *
     * @param dim must be non-negative value
     */
    public void setDim(int dim) {
        this.dim = dim;
    }

    /**
     * @return classifier class number
     */
    public int getClassIndex() {
        return cl;
    }

    /**
     * Sets classifier class
     *
     * @param cl non-negative class index
     */
    public void setClassIndex(int cl) {
        this.cl = cl;
    }

    /**
     * Sets classifier error
     *
     * @param error
     */
    public void setError(double error) {
        this.error = error;
    }

    /**
     * @return classifier error
     */
    public double getError() {
        return error;
    }

    @Override
    public String toString() {
        return "SimpleClassifier{" + "condition=" + condition + ", dim=" + dim + ", class=" + cl + ", alpha=" + alpha + ", error=" + error + '}';
    }

}
