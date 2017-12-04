/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DataViewer.java
 *
 * Created on Oct 24, 2009, 2:38:19 PM
 */
package wekimini.gui;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import wekimini.DataManager;
import wekimini.DataTableModel;
import wekimini.Wekinator;
import wekimini.util.Util;

/**
 *
 * @author rebecca
 */
public class DatasetViewer extends javax.swing.JFrame {

    private final DataManager dataManager;
    private final Wekinator w;
    private  javax.swing.JTable table;
    private  DataTableModel model;
    private  TableRowSorter sorter;
    private static final Logger logger = Logger.getLogger(DatasetViewer.class.getName());

    public DatasetViewer(DataManager dataManager, Wekinator w) {
        initComponents();
        this.dataManager = dataManager;
        this.w = w;
        setComboBoxOptions();
        populateTable();
    }

    private void setComboBoxOptions() {
        String[] options = new String[dataManager.getNumOutputs() + 1];
        options[0] = "All examples";
        for (int i = 0; i < dataManager.getNumOutputs(); i++) {
            options[i+1] = "Show only examples for " + dataManager.getOutputName(i);
        }
        
        DefaultComboBoxModel m = new DefaultComboBoxModel(options);
        comboWhichOutputs.setModel(m);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        scrollTable = new javax.swing.JScrollPane();
        buttonDone = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        buttonListen = new javax.swing.JButton();
        buttonSaveArff = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        comboWhichOutputs = new javax.swing.JComboBox();
        buttonLoadArff = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        buttonDone.setText("Done");
        buttonDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDoneActionPerformed(evt);
            }
        });

        buttonDelete.setText("Delete selected");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonAdd.setText("Add row");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        buttonListen.setText("Listen");
        buttonListen.setEnabled(false);
        buttonListen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonListenActionPerformed(evt);
            }
        });

        buttonSaveArff.setText("Save to ARFF...");
        buttonSaveArff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveArffActionPerformed(evt);
            }
        });

        jLabel2.setText("Show:");

        comboWhichOutputs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All examples", "Only output 1" }));
        comboWhichOutputs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboWhichOutputsActionPerformed(evt);
            }
        });

        buttonLoadArff.setText("Load from ARFF...");
        buttonLoadArff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadArffActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(scrollTable)
            .add(layout.createSequentialGroup()
                .add(buttonDelete, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 146, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonAdd)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(buttonListen)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(buttonLoadArff)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonSaveArff)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonDone))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(comboWhichOutputs, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(scrollTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonDone)
                    .add(buttonDelete)
                    .add(buttonAdd)
                    .add(buttonListen)
                    .add(buttonSaveArff)
                    .add(buttonLoadArff))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(comboWhichOutputs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDoneActionPerformed
        /* if (WekinatorRunner.isLogging()) {
         Plog.log(Msg.DATA_VIEWER_DONE);
         } */
        model.prepareToDie();

        this.dispose();
    }//GEN-LAST:event_buttonDoneActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        int rs[] = table.getSelectedRows();
        model.deleteRows(table.getSelectedRows());
        /* if (WekinatorRunner.isLogging()) {
         Plog.log(Msg.DATA_VIWER_DELETE_SELECTED, "numDel=" + rs.length);
         } */
        table.repaint();
}//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        model.addRow(-1); //Use -1 as "recording round" for right now
       /* if (WekinatorRunner.isLogging()) {
         Plog.log(Msg.DATA_VIEWER_ADD_ROW);
         } */
}//GEN-LAST:event_buttonAddActionPerformed

    private void buttonListenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonListenActionPerformed
        /*
         if (table.getSelectedRow() == -1) {
         return;
         }


         int row = table.getSelectedRow();
         double d[] = model.getSelectedParams(row);
         for (int i = 0; i < d.length; i++) {
         Double dd = new Double(d[i]);
         if (dd.isNaN()) {
         d[i] = WekinatorLearningManager.getInstance().getParams(i);
         }
         }
         //gui.listenToValues(f);
         WekinatorLearningManager.getInstance().setParams(d);

         OscHandler.getOscHandler().startSound();
         //OscHandler.getOscHandler().sendParamsToSynth(d); //TODO: Want dist
         OscHandler.getOscHandler().packageDistAndSendParamsToSynth(d);
         if (WekinatorRunner.isLogging()) {
         Plog.log(Msg.DATA_VIEWER_LISTEN);
         } */
}//GEN-LAST:event_buttonListenActionPerformed

    private void buttonSaveArffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveArffActionPerformed
        /*try {
         File file = findArffFileToSave();
         if (file != null) {
         myDataset.writeInstancesToArff(file);
         }
         } catch (Exception ex) {
         JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid feature configuration", JOptionPane.ERROR_MESSAGE);
         } */
        if (dataManager != null) {

            File file = Util.findSaveFile("arff",
                    "data",
                    "arff file",
                    this);
            if (file != null) {
                try {
                    dataManager.writeInstancesToArff(file, false);
                    /* if (WekinatorRunner.isLogging()) {
                     Plog.log(Msg.DATA_VIEWER_SAVE_ARFF_BUTTON, file.getAbsolutePath() + "/" + file.getName());
                     } */
                    // Util.setLastFile(SimpleDataset.getFileExtension(), file);
                } catch (Exception ex) {
                    Logger.getLogger(DatasetViewer.class.getName()).log(Level.WARNING, null, ex);
                    Util.showPrettyErrorPane(this, "Could not save to file: " + ex.getMessage());
                }
            }
        }
    }//GEN-LAST:event_buttonSaveArffActionPerformed

    private void comboWhichOutputsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboWhichOutputsActionPerformed
        int selected = comboWhichOutputs.getSelectedIndex();
        if (selected == 0) {
           //showAllOutputs(); //This is buggy!
           showAllOutputs2();
        } else {
            updateRowFilter(selected-1);
           //hideAllOutputsBut(selected-1); //this is buggy!
        }
        table.repaint();
        scrollTable.revalidate();
        scrollTable.repaint();
        

    }//GEN-LAST:event_comboWhichOutputsActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        model.prepareToDie();
    }//GEN-LAST:event_formWindowClosing

    private void buttonLoadArffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadArffActionPerformed
        w.getMainGUI().showArffLoader();
    }//GEN-LAST:event_buttonLoadArffActionPerformed

    private void hideAllOutputsBut(int which) {
        int j = 0;
        for (int i = 3 + model.getNumInputs(); i < table.getColumnCount(); i++) {
            if (j == which) {
                TableColumn c = table.getColumnModel().getColumn(i);
                c.setPreferredWidth(80);
                c.setMinWidth(20);
                c.setMaxWidth(100);
                c.setResizable(true);
            } else {
                TableColumn c = table.getColumnModel().getColumn(i);
                c.setPreferredWidth(0);
                c.setMinWidth(0);
                c.setMaxWidth(0);
                c.setResizable(true);
            }
            j++;
        }
    }
    
    /*private File findArffFileToSave() throws IOException {
     return null;
     //TODOTODOTODO: handle this after get fileext support in.
     } */
    /**
     * @param args the command line arguments
     */
   /* public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                boolean isDiscrete[] = {true, false};
                int numVals[] = {3, 3};
                //String featureNames[] = {"F1", "f2", "F3", "f4", "f5"};
                String featureNames[] = new String[100];
                for (int i = 0; i < featureNames.length; i++) {
                    featureNames[i] = "F" + i;
                }
                String paramNames[] = {"P1", "p2"};
                SimpleDataset s = new SimpleDataset(featureNames.length, 2, isDiscrete, numVals, featureNames, paramNames);

                new DatasetViewer(s).setVisible(true);
            }
        });
    } */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonDone;
    private javax.swing.JButton buttonListen;
    private javax.swing.JButton buttonLoadArff;
    private javax.swing.JButton buttonSaveArff;
    private javax.swing.JComboBox comboWhichOutputs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane scrollTable;
    // End of variables declaration//GEN-END:variables

    private void populateTable() {
        model = new DataTableModel(dataManager);
        table = new JTable(model);
        //table.setAutoCreateRowSorter(true);
        
        setTableColumns();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollTable.setViewportView(table);
        table.repaint();
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    private void updateRowFilter(final int whichOutput) {
        RowFilter<DataTableModel, Integer> rf = null;
        //If current expression doesn't parse, don't update.
       // try {
            rf = new RowFilter<DataTableModel, Integer>() {

            @Override
            public boolean include(RowFilter.Entry<? extends DataTableModel, ? extends Integer> entry) {
                DataTableModel m = entry.getModel();
                int i = entry.getIdentifier();
                return !dataManager.isOutputMissing(i, whichOutput);
            }
                
            };
        sorter.setRowFilter(rf);
    }
    
    private void showAllOutputs2() {
        RowFilter<DataTableModel, Integer> rf = null;
        //If current expression doesn't parse, don't update.
       // try {
            rf = new RowFilter<DataTableModel, Integer>() {

            @Override
            public boolean include(RowFilter.Entry<? extends DataTableModel, ? extends Integer> entry) {
                return true;
            }
                
            };
        sorter.setRowFilter(rf);
    }
    
    private void showAllOutputs() {
        sorter.setRowFilter(null);
        for (int i = 3 + model.getNumInputs(); i < table.getColumnCount(); i++) {
            TableColumn c = table.getColumnModel().getColumn(i);
            c.setPreferredWidth(80);
            c.setMinWidth(20);
            c.setMaxWidth(100);
            c.setWidth(80);
            c.setResizable(true);
        }
    }
    
    private void setTableColumns() {
        // table.getColumnModel().get
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        for (int i = 3; i < table.getColumnCount(); i++) {
            TableColumn c = table.getColumnModel().getColumn(i);
            c.setPreferredWidth(80);
            c.setMinWidth(20);
            c.setResizable(true);
        }
    }
}
