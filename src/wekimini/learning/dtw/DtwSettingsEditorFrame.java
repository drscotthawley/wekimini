/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini.learning.dtw;

import java.util.logging.Logger;
import wekimini.LearningModelBuilder;
import wekimini.dtw.gui.DtwEditorFrame;
import wekimini.dtw.gui.DtwEditorFrame.DtwSettingsReceiver;

/**
 *
 * @author rebecca
 */
public class DtwSettingsEditorFrame extends javax.swing.JFrame {
    private DtwEditorFrame.DtwSettingsReceiver receiver = null;
    private DtwSettings originalSettings = null;
    private static final Logger logger = Logger.getLogger(DtwSettingsEditorFrame.class.getName());
    DtwEditorPanel myPanel = null;
    /**
     * Creates new form ModelEditorFrame
     */
    public DtwSettingsEditorFrame() {
        initComponents();
    }
    
    /**
     * Creates new form ModelEditorFrame
     */
    public DtwSettingsEditorFrame(DtwSettings settings, DtwSettingsReceiver r) {
        initComponents();
        originalSettings = settings;
        panelModelEditor.removeAll();
        myPanel = new DtwEditorPanel(settings);
        panelModelEditor.add(myPanel);
        
        parentPanel.remove(0);
        parentPanel.add(myPanel, 0);
        
        this.receiver = r;
        revalidate();
        pack();
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentPanel = new javax.swing.JPanel();
        panelModelEditor = new javax.swing.JPanel();
        dtwEditorPanel1 = new wekimini.learning.dtw.DtwEditorPanel();
        jPanel3 = new javax.swing.JPanel();
        buttonCancel = new javax.swing.JButton();
        buttonOK = new javax.swing.JButton();

        parentPanel.setBackground(new java.awt.Color(255, 255, 255));
        parentPanel.setLayout(new javax.swing.BoxLayout(parentPanel, javax.swing.BoxLayout.Y_AXIS));

        panelModelEditor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelModelEditorLayout = new javax.swing.GroupLayout(panelModelEditor);
        panelModelEditor.setLayout(panelModelEditorLayout);
        panelModelEditorLayout.setHorizontalGroup(
            panelModelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dtwEditorPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        panelModelEditorLayout.setVerticalGroup(
            panelModelEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModelEditorLayout.createSequentialGroup()
                .addComponent(dtwEditorPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        parentPanel.add(panelModelEditor);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonOK.setText("OK");
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addComponent(buttonCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOK))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonOK))
                .addContainerGap())
        );

        parentPanel.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 374, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        if (myPanel.validateForm()) {
            DtwSettings s = myPanel.buildFromPanel();
            receiver.settingsReady(s);
            this.dispose();
        }
    }//GEN-LAST:event_buttonOKActionPerformed
    
    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        receiver.settingsBuilderCancelled();
        this.dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

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
            java.util.logging.Logger.getLogger(DtwSettingsEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DtwSettingsEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DtwSettingsEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DtwSettingsEditorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DtwSettingsEditorFrame().setVisible(true);
            }
        });
    }
    
    public interface ModelBuilderReceiver {
        void modelBuilderReady(LearningModelBuilder mb);
        void modelBuilderCancelled();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOK;
    private wekimini.learning.dtw.DtwEditorPanel dtwEditorPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelModelEditor;
    private javax.swing.JPanel parentPanel;
    // End of variables declaration//GEN-END:variables
}
