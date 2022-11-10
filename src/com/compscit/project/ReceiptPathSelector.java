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
                try {
                    receiptPath = jfc.getSelectedFile().getAbsolutePath();
                } catch (NullPointerException ex) {
                    ReceiptPathSelector rcp = new ReceiptPathSelector(); // not writing to txt
                    receiptPath = rcp.getReceiptPath();
                }
            }

        public String getReceiptPath() {
            return receiptPath;
        }
}
