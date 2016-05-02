/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.classifiers.boosting;

import java.util.ArrayList;
import java.util.HashSet;
import static lv.edgarsgars.classifiers.Assigment1.parseInput;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.vizualization.Visual;

/**
 *
 * @author Edgar_000
 */
public class BoostingClassifier {

    private ArrayList<SimpleClassifier> classifiers = new ArrayList<SimpleClassifier>();
    private ArrayList<SimpleClassifier> trainedClassfiers = new ArrayList<SimpleClassifier>();
    private HashSet<Integer> uniqueClasses = new HashSet<Integer>();

    private Matrix weights;

    public BoostingClassifier() {

    }

    public BoostingClassifier(ArrayList<SimpleClassifier> classifiers) {
        this.classifiers = classifiers;
    }

    public void train(Matrix trainData, Matrix trainClasses) {
        weights = MatrixUtils.getOnes(trainData.getRowCount(), 1).scalar(1.0 / trainData.getRowCount());
        while (!classifiers.isEmpty()) {

            for (SimpleClassifier cl : classifiers) {
                cl.setError(0);
            }

            for (SimpleClassifier classifier : classifiers) {
                int classifierClass = classifier.getClassIndex();
                uniqueClasses.add(classifierClass);
                Matrix yh = classifier.evaluate(trainData);
                Matrix expected = trainClasses.conditon(" x == " + classifierClass);
                Matrix wrongclasses = yh.conditon("x != y", expected);
                double error = weights.scalar(wrongclasses).sum();
                classifier.setError(classifier.getError() + error);
            }

            SimpleClassifier best = classifiers.get(0);
            for (int i = 1; i < classifiers.size(); i++) {
                if (best.getError() > classifiers.get(i).getError()) {
                    best = classifiers.get(i);
                }
            }

            double aplha = 0.5 * Math.log((1.0 - best.getError()) / (best.getError() + Double.MIN_VALUE));
            // System.out.println(Math.log((1.0 - best.getError()) / (best.getError() + Double.MIN_VALUE)));
            best.setAlpha(aplha);
            trainedClassfiers.add(best);
            classifiers.remove(best);

            Matrix yh = best.evaluate(trainData);
            Matrix expected = trainClasses.conditon(" x == " + (int) best.getClassIndex());
            Matrix wrongclasses = yh.conditon("x != y", expected);
            for (int i = 0; i < wrongclasses.getRowCount(); i++) {
                if (wrongclasses.get(i, 0) == 0.0d) {
                    weights.set(0.5 * weights.get(i, 0) / (1.0 - best.getError() + Double.MIN_VALUE), i, 0);
                } else {
                    weights.set(0.5 * weights.get(i, 0) / (best.getError() + Double.MIN_VALUE), i, 0);
                }
            }
        }
        System.out.println(weights);
        System.out.println(trainedClassfiers);
        //System.out.println(trainedClassfiers);
    }

    public void addSimpleClassifier(SimpleClassifier cl) {
        classifiers.add(cl);
    }

    public Matrix classify(Matrix x) {
        Matrix y = new Matrix(x.getRowCount(), 1);
        Matrix votes = new Matrix(x.getRowCount(), uniqueClasses.size());
        // System.out.println("Classes " + uniqueClasses.size());
        //System.out.println(votes);
        //System.out.println(uniqueClasses.size());
        for (SimpleClassifier classifier : trainedClassfiers) {
            Matrix pred = classifier.evaluate(x);
            for (int i = 0; i < pred.getRowCount(); i++) {
                if (pred.get(i, 0) == 1.0d && classifier.getError() <= 0.7) {
                    votes.addTo(classifier.getError() * classifier.getAlpha(), i, classifier.getClassIndex());
                }
            }
        }

        for (int i = 0; i < y.getRowCount(); i++) {
            Matrix summary = votes.getRow(i);
            double max = summary.max();
            int index = votes.getRow(i).indexOf(0, max);
            y.set(index, i, 0);
        }

        System.out.println("Votes = \n" + votes.toStringExcel());
        return y;
    }

    public static void main(String[] args) {
        Matrix x = new Matrix("[1 6;2 1; 5 4; 7 5;9 1;10 2;5 1;1 5;4 0; 5 -3]");
        Matrix c = new Matrix("[0;0;1;1;0;0;1;0;2;2]");
        BoostingClassifier boost = new BoostingClassifier();

        /*
        boost.addSimpleClassifier(new SimpleClassifier(" x <= 3", 0, 0));
        boost.addSimpleClassifier(new SimpleClassifier(" x >= 8", 0, 0));
        boost.addSimpleClassifier(new SimpleClassifier(" x <= 3", 1, 0));
        boost.addSimpleClassifier(new SimpleClassifier(" x >= 0", 1, 0));
        boost.addSimpleClassifier(new SimpleClassifier(" x >= 3", 1, 0));

        boost.addSimpleClassifier(new SimpleClassifier(" x > 3", 0, 1));
        boost.addSimpleClassifier(new SimpleClassifier(" x < 8", 0, 1));
        boost.addSimpleClassifier(new SimpleClassifier(" x > 3", 1, 1));
        boost.addSimpleClassifier(new SimpleClassifier(" x < 0", 1, 1));
        boost.addSimpleClassifier(new SimpleClassifier(" x < 3", 1, 1));

        boost.addSimpleClassifier(new SimpleClassifier(" x < 8 && x > 3", 0, 2));
        boost.addSimpleClassifier(new SimpleClassifier(" x > -2 && x < 1", 1, 2));
        boost.addSimpleClassifier(new SimpleClassifier(" x > 7", 0, 2));
        System.out.println(x);
        for (int i = 0; i < x.getCollumCount(); i++) {
            System.out.println(x.getCol(i));
            Matrix dim = x.getCol(i);
            for (int j = 0; j < MatrixUtils.unique(c).size()[0]; j++) {
                Matrix members = dim.get(c.conditon("x == " + j));
                double mean = members.mean();
                double max = members.max();
                double min = members.min();
                double std = MatrixUtils.std(members);
                boost.addSimpleClassifier(new SimpleClassifier("x >= " + min, i, j));
                boost.addSimpleClassifier(new SimpleClassifier("x <= " + max, i, j));
                boost.addSimpleClassifier(new SimpleClassifier("x >= " + (mean - std) + " &&  x <=" + mean, i, j));
                boost.addSimpleClassifier(new SimpleClassifier("x <= " + (mean + std) + " &&  x >=" + mean, i, j));
                boost.addSimpleClassifier(new SimpleClassifier("x > " + mean, i, j));
                boost.addSimpleClassifier(new SimpleClassifier("x < " + mean, i, j));
                //boost.addSimpleClassifier(new SimpleClassifier("x <= " + max, i, j));
            }

        }*/
        Matrix stundetMat = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-mat.csv");
        Matrix stundetPor = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\student-por.csv");
        //Matrix both = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\both.csv");
        Matrix data = stundetMat.vcat(stundetPor);
        int classCollum = data.getCollumCount() - 1;
        final Matrix classes = data.getCol(classCollum);
        //System.out.println(MatrixUtils.cov(MatrixUtils.zscore(data).T()).toStringExcel());
        data.removeCol(classCollum);

        final Matrix normalizedData = MatrixUtils.zscore(data);
        Matrix pcaData = MatrixUtils.pca(normalizedData.T(), 4).T();
        Matrix[] trainTestData = MatrixUtils.splitData(pcaData, classes, 0.75);
        System.out.println("Data prepared");
        for (int i = 0; i < 120; i++) {
            boost.addSimpleClassifier(new SimpleClassifier(" x > " + (Math.random() * 2 - 1), (int) (Math.random()) * normalizedData.getCollumCount(), (int) (Math.random() * 4)));
            boost.addSimpleClassifier(new SimpleClassifier(" x <= " + (Math.random() * 2 - 1), (int) (Math.random()) * normalizedData.getCollumCount(), (int) (Math.random() * 4)));

        }
        System.out.println("Classifiers created");
        boost.train(trainTestData[0], trainTestData[1]);
        System.out.println("Training ended");
        Matrix y = boost.classify(trainTestData[2]);

        //System.out.println(trainTestData[3]);
        //System.out.println(y);
        Matrix conf = MatrixUtils.confusionMatrix(y, trainTestData[3]);
        System.out.println(conf.toStringExcel());
        Visual.showConfusionMatrix(conf);

    }

}
