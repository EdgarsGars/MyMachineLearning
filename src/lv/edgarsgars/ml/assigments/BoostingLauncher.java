/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.edgarsgars.ml.assigments;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import static lv.edgarsgars.classifiers.Assigment1.parseInput;
import lv.edgarsgars.classifiers.boosting.BoostingClassifier;
import lv.edgarsgars.classifiers.boosting.SimpleClassifier;
import lv.edgarsgars.mathematics.Matrix;
import lv.edgarsgars.utils.MatrixUtils;

/**
 *
 * @author Edgar_000
 */
public class BoostingLauncher extends javax.swing.JFrame {
    
    private JPanel panel = new JPanel();
    private final ArrayList<SimpleClassifierPanel> classifiersPanels = new ArrayList<>();
    private final BoostingClassifier boost = new BoostingClassifier();
    private Matrix originalData;
    private Matrix originalClasses;
    private Matrix workingData;
    private Matrix workingClasses;
    private Matrix[] testTrainData = new Matrix[4];

    /**
     * Creates new form BoostingLauncher
     */
    public BoostingLauncher() {
        initComponents();
        setResizable(false);
        setTitle("Boosting");
        panel.setBounds(0, 0, getWidth() / 2, getHeight());
        panel.setLayout(null);
        final BoostingLauncher b = this;
        
        JScrollPane scrool = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrool.setBounds(getWidth() / 2, 0, getWidth() / 2, 300);
        //  scrool.setPreferredSize(new Dimension(100, 100));
        add(scrool);
        //scrool.add(panel);
        JButton button = new JButton("Train");
        Matrix stundetMat = parseInput(".\\student-mat.csv");
        Matrix stundetPor = parseInput(".\\student-por.csv");
        //Matrix both = parseInput("C:\\Users\\Edgar_000\\Documents\\MATLAB\\both.csv");
        Matrix data = stundetMat.vcat(stundetPor);
        int classCollum = data.getCollumCount() - 1;
        final Matrix classes = data.getCol(classCollum);
        //System.out.println(MatrixUtils.cov(MatrixUtils.zscore(data).T()).toStringExcel());
        data.removeCol(classCollum);
        originalData = data;
        originalClasses = classes;
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<SimpleClassifier> classifiers = new ArrayList<SimpleClassifier>();
                workingData = originalData.copy();
                workingClasses = originalClasses.copy();
                System.out.println(workingData.getRowCount());
                if (jCheckBox1.isSelected()) {
                    workingData = MatrixUtils.zscore(workingData);
                }
                
                testTrainData = MatrixUtils.splitData(workingData, workingClasses, (95.0 - jComboBox1.getSelectedIndex() * 5.0) / 100.0);
                
                for (SimpleClassifierPanel classifier : classifiersPanels) {
                    classifier.updateClassfier();
                    classifiers.add(classifier.getSimpleClassifier());
                }
                boost.setSimpleClassifiers(classifiers);
                boost.train(testTrainData[0], testTrainData[1]);
                for (SimpleClassifierPanel classifier : classifiersPanels) {
                    classifier.updateVisual();
                }
                System.out.println("=> " + (100.0 - jComboBox2.getSelectedIndex() * 5.0) / 100.0);
                Matrix[] res = boost.classify(testTrainData[2], (100.0 - jComboBox2.getSelectedIndex() * 5.0) / 100.0);
                Matrix conf = MatrixUtils.confusionMatrix(testTrainData[3], res[0]);
                System.out.println("Conf  " + conf);
                jLabel3.setText("Error rate: " + (1.0 - MatrixUtils.diag(conf).sum() / res[0].getRowCount()));
                confMatrixPanel1.setConfusionMatrix(conf);
                confMatrixPanel1.repaint();
                
                DefaultTableModel tableModel = new DefaultTableModel(res[1].getRowCount(), res[1].getCollumCount());
                jTable1.setModel(tableModel);
                for (int j = 0; j < res[1].getCollumCount(); j++) {
                    jTable1.getColumnModel().getColumn(j).setHeaderValue("Class " + j);
                    for (int i = 0; i < res[1].getRowCount(); i++) {
                        tableModel.setValueAt(res[1].get(i, j), i, j);
                    }
                }
                
                jTable1.setModel(tableModel);
                jTable1.repaint();
                
            }
        });
        button.setBounds(getWidth() / 2, getHeight() - 80, getWidth() / 2, 50);
        add(button);
        
        JButton createButton = new JButton("(+)");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addnewclassifierPanel();
            }
        });
        createButton.setBounds(getWidth() - 100, 300, 100, 30);
        add(createButton);
        
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setBounds(0, 50, getWidth() / 2, getHeight() / 3);
        //revalidateClassifiersGUI(data[0], data[1]);

    }
    
    public void rebuiltList() {
        for (int i = 0; i < panel.getComponents().length; i++) {
            panel.getComponent(i).setBounds(0, i * 30, 500, 30);
        }
    }
    
    public void addnewclassifierPanel() {
        SimpleClassifierPanel p = new SimpleClassifierPanel();
        classifiersPanels.add(p);
        revalidateClassifiersGUI(p, originalData, originalClasses);
        
        final JPanel line = new JPanel();
        line.setBounds(0, panel.getComponentCount() * 30, 500, 30);
        line.setLayout(new FlowLayout());
        p.setBounds(0, 0, 500, 30);
        //line.add( classifiersPanels.get(i));
        JButton delete = new JButton("x");
        final SimpleClassifierPanel deleteTarget = p;
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(classifiersPanels.remove(deleteTarget) + " " + classifiersPanels.size());
                panel.remove(line);
                panel.revalidate();
                rebuiltList();
                repaint();
            }
        });
        delete.setBounds(350 + 10, panel.getComponentCount() * 30, 30, 30);
        line.add(p);
        line.add(delete);
        // classifiersPanels.add(p);
        panel.add(line);
    }
    
    public void revalidateClassifiersGUI(SimpleClassifierPanel p, Matrix x, Matrix classs) {
        int dim = x.getCollumCount();
        int classes = (int) classs.max();
        
        p.setDimAndClasses(dim, classes);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        confMatrixPanel1 = new lv.edgarsgars.ml.assigments.ConfMatrixPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout confMatrixPanel1Layout = new javax.swing.GroupLayout(confMatrixPanel1);
        confMatrixPanel1.setLayout(confMatrixPanel1Layout);
        confMatrixPanel1Layout.setHorizontalGroup(
            confMatrixPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        confMatrixPanel1Layout.setVerticalGroup(
            confMatrixPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );

        jLabel1.setText("Split data");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "95%", "90%", "85%", "80%", "75%", "70%", "65%", "60%", "55%", "50%", "45%", "40%", "35%", "30%", "25%", "20%", "15%", "10%", "5%" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Normalize data");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Accepted error");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100%", "95%", "90%", "85%", "80%", "75%", "70%", "65%", "60%", "55%", "50%", "45%", "40%", "35%", "30%", "25%", "20%", "15%", "10%", "5%" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Error rate :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(confMatrixPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(confMatrixPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(66, 66, 66))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                    
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BoostingLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BoostingLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BoostingLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BoostingLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BoostingLauncher().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private lv.edgarsgars.ml.assigments.ConfMatrixPanel confMatrixPanel1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
