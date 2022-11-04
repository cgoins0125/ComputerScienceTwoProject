package com.compscit.project;

import javax.swing.*;

public class ReceiptPathSelector {

    private String receiptPath;

        public ReceiptPathSelector() {
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            JOptionPane.showMessageDialog(new JFrame(), "Please select where you would like\n" +
                    "to store digital copies of receipts");
            int option = jfc.showOpenDialog(null);
            receiptPath = jfc.getSelectedFile().getAbsolutePath();

        }

        public String getReceiptPath() {
            return receiptPath;
        }
}
