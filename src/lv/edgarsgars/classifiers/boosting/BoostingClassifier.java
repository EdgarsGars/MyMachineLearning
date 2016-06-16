package lv.edgarsgars.classifiers.boosting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 * Boosting classifier implementation
 *
 * @author edgars.garsneks
 */
public class BoostingClassifier {

    private List<SimpleClassifier> classifiers = new ArrayList<SimpleClassifier>();
    private List<SimpleClassifier> trainedClassfiers = new ArrayList<SimpleClassifier>();
    private HashSet<Integer> uniqueClasses = new HashSet<Integer>();
    private Matrix weights;

    /**
     * Creates empty boosting classifier
     */
    public BoostingClassifier() {

    }

    /**
     * Creates boosting classifier from given simple classifiers
     *
     * @param classifiers list of simple classifiers
     */
    public BoostingClassifier(List<SimpleClassifier> classifiers) {
        this.classifiers = classifiers;
    }

    /**
     * Trains boosting classifier with training data. Example : X = [1 2 3; 4 5
     * 6; 3 6 7; 3 6 7]; Y = [1;2;0;0];
     *
     * @param trainData matrix with training data. Row corresponds to one point
     * @param trainClasses column vector with corresponding classes for training
     * data points
     */
    public void train(Matrix trainData, Matrix trainClasses) {
        trainedClassfiers.clear();
        uniqueClasses.clear();
        // intializes weights with error 1/totalPointNumber
        weights = MatrixUtils.ones(trainData.getRowCount(), 1).scalar(1.0 / trainData.getRowCount());
        //Do while all alpha coefficients for classifiers are not calcullated 
        while (!classifiers.isEmpty()) {
            //Initialize all classifiers with errors 0
            for (SimpleClassifier cl : classifiers) {
                cl.setError(0);
            }
            //For each classifier calculate error
            for (SimpleClassifier classifier : classifiers) {
                int classifierClass = classifier.getClassIndex();
                uniqueClasses.add(classifierClass);
                //Evaluates train data against classifiers
                Matrix yh = classifier.evaluate(trainData);
                //Gets logical matrix where train classes matches classifier class
                Matrix expected = trainClasses.conditon(" x == " + classifierClass);
                //Finds where expected classes dosn't match with classified
                Matrix wrongclasses = yh.conditon("x != y", expected);
                //Gets error count and multiplies by weights
                double error = weights.scalar(wrongclasses).sum();
                classifier.setError(classifier.getError() + error);
            }

            //Finds best classifier in this iteration
            SimpleClassifier best = classifiers.get(0);
            for (int i = 1; i < classifiers.size(); i++) {
                if (best.getError() > classifiers.get(i).getError()) {
                    best = classifiers.get(i);
                }
            }
            //Assigns alpha for best classifier
            double aplha = 0.5 * Math.log((1.0 - best.getError()) / (best.getError() + 0.00001));
            best.setAlpha(aplha);
            //Adds classifier for checked list
            trainedClassfiers.add(best);
            classifiers.remove(best);
            //Calculates error once again -_-
            Matrix yh = best.evaluate(trainData);
            Matrix expected = trainClasses.conditon(" x == " + (int) best.getClassIndex());
            // 0 1 0 1 1 1 1 0 1 
            // 1 1 0 0 0 1 1 1 1
            //-------------------
            // 1 0 0 1 1 0 0 1 0
            Matrix wrongclasses = yh.conditon("x != y", expected);
            // Adjust point weights
            for (int i = 0; i < wrongclasses.getRowCount(); i++) {
                if (wrongclasses.get(i, 0) == 0.0d) {
                    weights.set(0.5 * weights.get(i, 0) / (1.0 - best.getError() + 0.0001), i, 0);
                } else {
                    weights.set(0.5 * weights.get(i, 0) / (best.getError() + 0.0001), i, 0);
                }
            }
        }

    }

    /**
     * Adds simple classifier to classifier list
     *
     * @param cl
     */
    public void addSimpleClassifier(SimpleClassifier cl) {
        classifiers.add(cl);
    }

    /**
     * Adds simple classifier with specific condition on specific dimension for
     * specific class. Example new Classifier("x > 3 && x < 5", 2, 3) @pa
     *
     *
     * ram condition condition to evaluate. Must be valid boolean expression
     * with x as variable. @param dim dimension which will be used for
     * evaluation @param cl target class
     */
    public void addSimpleClassifier(String condition, int dim, int cl) {
        classifiers.add(new SimpleClassifier(condition, dim, cl));
    }

    /**
     * Classifies matrix with rows representing points only by those classifiers
     * with error rate below specified
     *
     * @param x data matrix
     * @param errorRate allowed maximum error rate for classifiers
     * @return column matrix with assigned classes and matrix with votes
     */
    public Matrix[] classify(Matrix x, double errorRate) {
        Matrix y = new Matrix(x.getRowCount(), 1);
        Matrix votes = new Matrix(x.getRowCount(), uniqueClasses.size());
        votes.add(0.0000001);
        //For each point all classifiers vote to which class it belongs
        for (SimpleClassifier classifier : trainedClassfiers) {
            if (classifier.getError() <= errorRate) {
                Matrix pred = classifier.evaluate(x);
                for (int i = 0; i < pred.getRowCount(); i++) {
                    if (pred.get(i, 0) == 1.0d) {
                        votes.addTo(classifier.getError() * classifier.getAlpha(), i, classifier.getClassIndex());
                    }
                }
            }
        }
        //Finds maximum vote and assigns that class
        for (int i = 0; i < y.getRowCount(); i++) {

            Matrix summary = votes.getRow(i);
            double max = summary.max();
            int index = votes.indexOf(i, max);
            y.set(index, i, 0);
        }

        return new Matrix[]{y, votes};
    }

    /**
     * Sets list of simple classifiers
     *
     * @param classifiers
     */
    public void setSimpleClassifiers(List<SimpleClassifier> classifiers) {
        this.classifiers = classifiers;
    }

}
