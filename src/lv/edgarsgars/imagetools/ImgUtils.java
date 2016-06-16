/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.imagetools;

import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;
import lv.edgarsgars.vizualization.Visual;

/**
 *
 * @author Edgar_000
 */
public class ImgUtils {

    public static Matrix imerode(Matrix bw, Matrix struct) {
        Matrix res = new Matrix(bw.getRowCount(), bw.getCollumCount());
        int originR = struct.getRowCount() / 2;
        int originK = struct.getCollumCount() / 2;
        for (int i = 0; i < bw.getRowCount(); i++) {
            for (int j = 0; j < bw.getCollumCount(); j++) {
                int expected = 0;
                int actual = 0;
                for (int r = 0; r < struct.getRowCount(); r++) {
                    for (int k = 0; k < struct.getCollumCount(); k++) {
                        int x = k - originK;
                        int y = r - originR;
                        int ny = i + y;
                        int nx = j + x;
                        if (nx >= 0 && nx < bw.getCollumCount() && ny >= 0 && ny < bw.getRowCount() && struct.get(r, k) == 1.0) {
                            expected++;
                            if (struct.get(r, k) == bw.get(ny, nx)) {
                                actual++;
                            }
                        }
                    }
                }
                if (expected == actual) {
                    res.set(1, i, j);
                }
            }
        }
        return res;
    }

    //Can be optimized to skip loops
    public static Matrix imdilate(Matrix bw, Matrix struct) {
        Matrix res = new Matrix(bw.getRowCount(), bw.getCollumCount());
        int originR = struct.getRowCount() / 2;
        int originK = struct.getCollumCount() / 2;
        for (int i = 0; i < bw.getRowCount(); i++) {
            for (int j = 0; j < bw.getCollumCount(); j++) {
                int actual = 0;
                for (int r = 0; r < struct.getRowCount(); r++) {
                    for (int k = 0; k < struct.getCollumCount(); k++) {
                        int x = k - originK;
                        int y = r - originR;
                        int ny = i + y;
                        int nx = j + x;
                        if (nx >= 0 && nx < bw.getCollumCount() && ny >= 0 && ny < bw.getRowCount() && struct.get(r, k) == 1.0) {
                            if (struct.get(r, k) == bw.get(ny, nx)) {
                                actual++;
                            }
                        }
                    }
                }
                if (actual > 0) {
                    res.set(1, i, j);
                }
            }
        }
        return res;
    }

    public static Matrix imopen(Matrix bw, Matrix struct) {
        return imdilate(imerode(bw, struct), struct);
    }

    public static Matrix imclose(Matrix bw, Matrix struct) {
        return imerode(imdilate(bw, struct), struct);
    }

    public static Matrix bwboundary(Matrix x) {
        return x.sub(imerode(x, MatrixUtils.ones(3, 3)));
    }

    public static Matrix bwfillholes(Matrix x, Matrix struct) {
        Matrix res = new Matrix(x.getRowCount(), x.getCollumCount());
        Matrix xc = x.conditon("!x == 1");
        Visual.bwshow(x, "A");
        Visual.bwshow(xc, "Ac");

        return res;
    }

    public static Matrix bwfillholes(Matrix m, Matrix struct, int y, int x) {
        Matrix res = new Matrix(m.getRowCount(), m.getCollumCount());

        return null;

    }

    public static Matrix bwlabel(Matrix bw) {
        int comp = 2;
        Matrix cc = new Matrix(bw.getRowCount(), bw.getCollumCount());
        for (int i = 0; i < bw.getRowCount(); i++) {
            for (int j = 0; j < bw.getCollumCount(); j++) {
                if (bw.get(i, j) == 1 && cc.get(i, j) == 0) {
                    Matrix b = connectedComponent(bw, i, j);
                    cc = cc.add(b.scalar(comp++));
                }
            }
        }
        cc.set("x == " + (comp - 1), 1);
        return cc;
    }

    public static Matrix connectedComponent(Matrix m, int y, int x) {
        Matrix res = new Matrix(m.getRowCount(), m.getCollumCount());
        Matrix struct = MatrixUtils.ones(3, 3);
        return connectedComponent(m, res, struct, y, x);
    }

    private static Matrix connectedComponent(Matrix m, Matrix res, Matrix struct, int y, int x) {

        Matrix match = getByStruct(m, y, x, struct);
        if (match.get(1, 1) == 1 && match.sum() > 0) {
            res.set(1, y, x);
            for (int i = 0; i < struct.getRowCount(); i++) {
                for (int j = 0; j < struct.getCollumCount(); j++) {
                    int r = y - 1 + i;
                    int k = x - 1 + j;
                    if (r >= 0 && k >= 0 && r < m.getRowCount() && k < m.getCollumCount() && res.get(r, k) != 1.0 && match.get(i, j) == 1) {
                        res = connectedComponent(m, res, struct, r, k);
                    }
                }
            }
        }
        return res;
    }

    public static Matrix getByStruct(Matrix m, int y, int x, Matrix struct) {
        Matrix res = new Matrix(struct.getRowCount(), struct.getCollumCount());
        for (int i = 0; i < struct.getRowCount(); i++) {
            for (int j = 0; j < struct.getCollumCount(); j++) {
                int r = y - 1 + i;
                int k = x - 1 + j;
                res.set((r >= 0 && k >= 0 && r < m.getRowCount() && k < m.getCollumCount() && struct.get(i, j) == 1 && m.get(r, k) == 1) ? 1 : 0, i, j);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Matrix x = new Matrix("[ 0 1 1 1; 0 1 0 1; 0 0 0 0; 0 1 0 1;  0 1 0 0 ;  1 1 1 1 ;  0 0 1 0; 1 0 1 0; 0 0 0 0; 1 1 1 1;]");
        //  Matrix struct = new Matrix("[1 1 1;1 0 1; 1 1 1]");
        // Visual.bwshow(getByStruct(x, 5, 1, struct), "Struct over");
        // Visual.bwshow(x, "Or");
        Matrix comp = bwlabel(x);
        System.out.println(comp.toStringExcel());
        Visual.bwshow(x, "original");
        //  Visual.bwshow(comp.conditon("x == 2"), "conn");
        ///   Visual.bwshow(comp.conditon("x == 3"), "conn");
        //  Visual.bwshow(comp.conditon("x == 4"), "conn");
        //   Visual.bwshow(comp.conditon("x == 5"), "conn");
        //   Visual.bwshow(comp.conditon("x == 1"), "conn");
        Visual.bwshow(comp, "das");
    }
}
