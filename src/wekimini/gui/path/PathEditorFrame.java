/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.gui.path;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import wekimini.Path;
import wekimini.gui.path.ModelEditorFrame.ModelBuilderReceiver;
import wekimini.gui.path.OutputEditFrame.OutputEditReceiver;
import wekimini.learning.ModelBuilder;
import wekimini.learning.ModelBuilderEditorPanel;
import wekimini.osc.OSCClassificationOutput;
import wekimini.osc.OSCNumericOutput;
import wekimini.osc.OSCOutput;
import wekimini.util.Util;

/**
 *
 * @author rebecca
 */
public class PathEditorFrame extends javax.swing.JFrame {

    private final static HashMap<Path, PathEditorFrame> pathsBeingEdited = new HashMap<>();
    private final Path p;
    private static final Logger logger = Logger.getLogger(PathEditorFrame.class.getName());
    private OutputEditFrame outputEditor = null;
    private OSCOutput newOutput = null;
    private ModelEditorFrame modelEditor = null;
    private ModelBuilder newModelBuilder = null;
    private boolean hasValidModelType = false;
    private JCheckBox inputs[] = null;
    private String[] inputNames = null;

    /**
     * Creates new form PathEditorFrame
     */
    public PathEditorFrame() {
        initComponents();
        p = null;
    }

    public PathEditorFrame(Path p, String[] inputNames) {
        initComponents();
        this.p = p;
        initFormForPath();
        /*p.addInputSelectionChangeListener(new ChangeListener() {

         @Override
         public void stateChanged(ChangeEvent e) {
         initInputList();
         }
         }); */
        initInputsPanel(p, inputNames);
    }

    private void initInputsPanel(Path p, String[] inputNames) {
        panelInputList.removeAll();
        this.inputNames = new String[inputNames.length];
        System.arraycopy(inputNames, 0, this.inputNames, 0, inputNames.length);
        inputs = new JCheckBox[inputNames.length];
        for (int i = 0; i < inputNames.length; i++) {
            final int which = i;
            inputs[i] = new JCheckBox(inputNames[i]);
            inputs[i].setSelected(p.isUsingInput(inputNames[i]));
            inputs[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    mouseClick(which);
                }
            });
            inputs[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    updateNumInputs();
                }
            });
            panelInputList.add(inputs[i]);
        }
        updateNumInputs();
    }

    private void mouseClick(int whichCheckbox) {
        System.out.println("Click on " + whichCheckbox);
    }

    private void initFormForPath() {
        initModelType();
        updateFormForOutput(p.getOSCOutput());
    }

    private void initModelType() {
        labelModelType.setText(p.getModelBuilder().getPrettyName());
        hasValidModelType = true;
    }

    private void updateNumInputs() {
        labelConnectedInputs.setText(getNumberInputsSelected() + " connected inputs:");
    }

    private void updateFormForOutput(OSCOutput o) {
        labelOutputName.setText("Name: " + o.getName());

        StringBuilder sb = new StringBuilder("<html>");
        if (o instanceof OSCNumericOutput) {
            OSCNumericOutput no = (OSCNumericOutput) o;
            sb.append("Continous output, ");
            if (no.getOutputType() == OSCNumericOutput.NumericOutputType.REAL) {
                sb.append("real values<br>");
            } else {
                sb.append("integer values<br>");
            }

            sb.append("Values between min=");
            sb.append(Util.prettyDecimalFormat(no.getMin(), 2));
            sb.append(" and max=").append(Util.prettyDecimalFormat(no.getMax(), 2));

            if (no.getLimitType() == OSCNumericOutput.LimitType.HARD) {
                sb.append(" (hard limits)</html>");
            } else {
                sb.append(" (soft limits)</html>");
            }
        } else if (o instanceof OSCClassificationOutput) {
            sb.append("Classification output with ");
            sb.append(((OSCClassificationOutput) o).getNumClasses());
            sb.append(" classes</html");
        } else {
            sb.append("Unknown type</html>");
            logger.log(Level.SEVERE, "Uknown output type: {0}", o.getClass().getCanonicalName());
        }
        labelOutputType.setText(sb.toString());
    }

    /*public void initInputList() {
     String[] inputNames = p.getSelectedInputs();
     labelConnectedInputs.setText(inputNames.length + " connected inputs:");
        
     panelInputList.removeAll();
     for (int i = 0; i < inputNames.length; i++) {
     panelInputList.add(new JLabel(inputNames[i]));
     }
     panelInputList.repaint();
     panelInputList.validate();
     scrollPaneInputs.revalidate();
        
     } */
    public static boolean pathEditorExists(Path p) {
        return pathsBeingEdited.containsKey(p);
    }

    public static PathEditorFrame getEditorForPath(Path p, String[] inputs) {
        if (pathEditorExists(p)) {
            return pathsBeingEdited.get(p);
        } else {
            return new PathEditorFrame(p, inputs);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        buttonEditOutput = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        labelOutputType = new javax.swing.JLabel();
        labelOutputName = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buttonEditModelType = new javax.swing.JButton();
        labelModelType = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        labelConnectedInputs = new javax.swing.JLabel();
        scrollPaneInputs = new javax.swing.JScrollPane();
        panelInputList = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Load from file...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Save to file...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Apply changes");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonEditOutput.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        buttonEditOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wekimini/icons/pencil2.png"))); // NOI18N
        buttonEditOutput.setToolTipText("Edit this model");
        buttonEditOutput.setMaximumSize(new java.awt.Dimension(30, 30));
        buttonEditOutput.setMinimumSize(new java.awt.Dimension(30, 30));
        buttonEditOutput.setPreferredSize(new java.awt.Dimension(30, 30));
        buttonEditOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditOutputActionPerformed(evt);
            }
        });

        jLabel3.setText("Type:");

        labelOutputType.setText("<html>Continuous output, real values<br>Values between Min=0.2, Max=0.3 (soft limits)<br></html>");

        labelOutputName.setText("Name: Output 1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonEditOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelOutputName, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                        .addContainerGap(81, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelOutputType, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonEditOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOutputName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOutputType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Model type:");

        buttonEditModelType.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        buttonEditModelType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wekimini/icons/pencil2.png"))); // NOI18N
        buttonEditModelType.setToolTipText("Edit this model");
        buttonEditModelType.setMaximumSize(new java.awt.Dimension(30, 30));
        buttonEditModelType.setMinimumSize(new java.awt.Dimension(30, 30));
        buttonEditModelType.setPreferredSize(new java.awt.Dimension(30, 30));
        buttonEditModelType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditModelTypeActionPerformed(evt);
            }
        });

        labelModelType.setText("Neural network");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonEditModelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelModelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(193, 193, 193))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(buttonEditModelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelModelType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelConnectedInputs.setText("12 Connected inputs:");

        panelInputList.setBackground(new java.awt.Color(255, 255, 255));
        panelInputList.setLayout(new javax.swing.BoxLayout(panelInputList, javax.swing.BoxLayout.Y_AXIS));

        jCheckBox1.setText("jCheckBox1");
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        panelInputList.add(jCheckBox1);

        jCheckBox2.setText("jCheckBox1");
        panelInputList.add(jCheckBox2);

        jCheckBox3.setText("jCheckBox1");
        panelInputList.add(jCheckBox3);

        scrollPaneInputs.setViewportView(panelInputList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(labelConnectedInputs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addComponent(scrollPaneInputs, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelConnectedInputs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneInputs, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, 0)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(0, 0, 0)
                .addComponent(jButton3))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEditModelTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditModelTypeActionPerformed
        showModelBuilderEditor();
    }//GEN-LAST:event_buttonEditModelTypeActionPerformed

    private void buttonEditOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditOutputActionPerformed
        showOutputEditor();
    }//GEN-LAST:event_buttonEditOutputActionPerformed

    private void showOutputEditor() {
        if (outputEditor != null) {
            outputEditor.dispose();
        }
        //if (outputEditor == null) {
        OutputEditReceiver r = new OutputEditFrame.OutputEditReceiver() {
            @Override
            public void outputReady(OSCOutput o) {
                newOutputReceived(o);
            }

            @Override
            public void outputEditorCancelled() {
                outputEditor = null;
            }

        };
        if (newOutput != null) {
            outputEditor = new OutputEditFrame(newOutput, r);
        } else {
            outputEditor = new OutputEditFrame(p.getOSCOutput(), r);
        }
        outputEditor.setVisible(true);
        /*  } else {
         outputEditor.setVisible(true);
         outputEditor.toFront();
         } */
    }

    private void newOutputReceived(OSCOutput o) {
        newOutput = o;
        updateFormForOutput(o);
        updateModelForOutput(o);
        outputEditor = null;
    }

    private void updateModelForOutput(OSCOutput o) {
        if (p.getModelBuilder().isCompatible(o)) {
            hasValidModelType = true;
            labelModelType.setText(p.getModelBuilder().getPrettyName());
        } else {
            hasValidModelType = false;
            labelModelType.setText("None - Please edit");
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (validateForm()) {
            applyChanges();
            dispose();
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadModelFromFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        saveModelToFile();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(PathEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PathEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PathEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PathEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PathEditorFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEditModelType;
    private javax.swing.JButton buttonEditOutput;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelConnectedInputs;
    private javax.swing.JLabel labelModelType;
    private javax.swing.JLabel labelOutputName;
    private javax.swing.JLabel labelOutputType;
    private javax.swing.JPanel panelInputList;
    private javax.swing.JScrollPane scrollPaneInputs;
    // End of variables declaration//GEN-END:variables

    private void showModelBuilderEditor() {
        if (modelEditor != null) {
            modelEditor.dispose();
        }

        // if (modelEditor == null) {
        ModelBuilderReceiver r = new ModelBuilderReceiver() {
            @Override
            public void modelBuilderReady(ModelBuilder mb) {
                newModelBuilderReady(mb);
            }

            @Override
            public void modelBuilderCancelled() {
                modelEditor = null;
            }
        };

        boolean isClassifier;
        if (newOutput != null) {
            isClassifier = (newOutput instanceof OSCClassificationOutput);
        } else {
            isClassifier = (p.getOSCOutput() instanceof OSCClassificationOutput);
        }
        modelEditor = new ModelEditorFrame(p.getModelBuilder(), r, isClassifier);
        modelEditor.setVisible(true);
        /* } else {
         modelEditor.setVisible(true);
         modelEditor.toFront();
         } */
    }

    private void newModelBuilderReady(ModelBuilder mb) {
        newModelBuilder = mb;
        labelModelType.setText(mb.getPrettyName());
        //Error check:
        if (newOutput == null) {
            if (!mb.isCompatible(p.getOSCOutput()))  {
                logger.log(Level.WARNING, "Trying to set incompatible model and output");
            }
        } else if (!mb.isCompatible(newOutput)) {
            logger.log(Level.WARNING, "Trying to set incompatible model and output");
        }
        modelEditor = null;
    }

    private boolean validateForm() {
        if (getNumberInputsSelected() == 0) {
            Util.showPrettyErrorPane(this, "At least one input must be selected");
            return false;
        }

        OSCOutput o;
        if (newOutput != null) {
            o = newOutput;
        } else {
            o = p.getOSCOutput();
        }

        ModelBuilder mb;
        if (newModelBuilder != null) {
            mb = newModelBuilder;
        } else {
            mb = p.getModelBuilder();
        }
        if (!mb.isCompatible(o)) {
            Util.showPrettyErrorPane(this, "This output cannot be used your previous model type (" + mb.getPrettyName()
                    + "). Please select a different model type.");
            return false;
        }
        return true;
    }

    private int getNumberInputsSelected() {
        int sum = 0;
        for (JCheckBox j : inputs) {
            if (j.isSelected()) {
                sum++;
            }
        }
        return sum;
    }

    private void applyChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadModelFromFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void saveModelToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}