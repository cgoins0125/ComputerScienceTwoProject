package com.compscit.project;

import javax.swing.*;
import java.io.File;

public class ReceiptPathSelector {
    private JFileChooser jfc;
    private String receiptPath;

        public ReceiptPathSelector() {
            jfc = new JFileChooser();
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
