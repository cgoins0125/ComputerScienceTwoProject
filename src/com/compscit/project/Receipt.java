package com.compscit.project;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;



/**
 * The purpose of this class is to format and digitally store receipts for sales that are passed in
 * It can also provide the path where the receipt is stored so that a copy may be printed for customers
 */

public class Receipt {

    private static File receiptPath;
    private Sale sale;
    String receiptFileName;

    /**
     * This constructor should be called when the program starts, it sets the path to store digital copies of receipts
     * @param receiptPath is the String directory path where receipts should be stored. It is immediately passed into the
     *                    organizeReceipts method
     */
    public Receipt (String receiptPath) {

            this.receiptPath = organizeReceipts(receiptPath);

    }

    /**
     * Constructor for receipts, receiptLocation is the String representation of receiptPath
     * @param sale the sale that needs a receipt printed
     */
    public Receipt(Sale sale) {
        this.sale = sale;
        receiptFileName = "Receipt"+sale.getSaleId()+".txt";
        storeReceipt();
        printReceipt();
    }

    /**This method is for organizing digital copies of receipts
     * if the folders have not already been created, then this method will create them
     * Functionality for Mac and Windows
     * Organization method Windows: [@param receiptPath]\receipts\year\month\date
     * Organization method Mac: [@param receiptPath]/receipts/year/month/date
     @param receiptPath - the desired path for receipts to be stored
    */
    private File organizeReceipts(String receiptPath) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        File returnPath = new File("");
        if (System.getProperty("os.name").startsWith("Windows")) {
            receiptPath += "\\receipts";
            File addReceipts = new File(receiptPath);
            if (!addReceipts.exists()) addReceipts.mkdir();
            File addYear = new File(receiptPath + "\\" + year);
            if (!addYear.exists()) addYear.mkdir();
            File addMonth = new File(addYear + "\\" + month);
            if (!addMonth.exists()) addMonth.mkdir();
            File addDay = new File(addMonth + "\\" + day);
            if (!addDay.exists()) addDay.mkdir();
            returnPath=addDay;
        } else { //for Mac
            receiptPath += "/receipts";
            File addReceipts = new File(receiptPath);
            if (!addReceipts.exists()) addReceipts.mkdir();
            File addYear = new File(receiptPath + "/" + year);
            if (!addYear.exists()) addYear.mkdir();
            File addMonth = new File(addYear + "/" + month);
            if (!addMonth.exists()) addMonth.mkdir();
            File addDay = new File(addMonth + "/" + day);
            if (!addDay.exists()) addDay.mkdir();
            returnPath=addDay;
        }

        return returnPath;
    }

    /**
     * Formats receipts
     * @return the formatted text for receipts
     */
    private String getReceiptFormat() {
        int itemId = 0;
        int quantitySold = 0;
        StringBuilder receiptLine = new StringBuilder(String.format("%20s", "Receipt " + sale.getSaleId() + "\n"));
        int [][] soldItemId_quantity = sale.getSoldItemId_quantity();
        for (int[] ints : soldItemId_quantity) {
            itemId = ints[0];
            quantitySold = ints[1];
            for (Stock item : Data.inventory) {
                if (itemId == item.getItemId()) {
                    receiptLine.append(quantitySold).append(" ").append(item.getItemName().toUpperCase());
                    receiptLine.append(String.format("%25.2f", (item.getSalePrice() * quantitySold)));
                    receiptLine.append("\n");
                    //breaks out of for loop once item is found
                    break;
                }
            }
        }
        receiptLine.append(String.format("Subtotal: %23.2f", sale.getSubtotal()));
        receiptLine.append("\n");
        receiptLine.append(String.format("Tax: %28.2f", sale.getTax()));
        receiptLine.append("\n");
        receiptLine.append(String.format("Total: %26.2f", sale.getTotal()));
        receiptLine.append("\n");
        return receiptLine.toString();
    }

    /**
     * Creates a .txt file for digital receipt storage
     * receiptPath is the dir path where the receipts are stored
     * receiptFileName is the name of the .txt file to be stored
     */
    private void storeReceipt() {
        try (FileWriter fw = new FileWriter(new File(receiptPath,receiptFileName))) {
            fw.write(getReceiptFormat());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens the receipt for the sale, or displays where the file path where the receipt is saved.
     */
    private void printReceipt() {
        //some desktops to supported by the Desktop class
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (System.getProperty("os.name").startsWith("Windows")) {
                File windowsReceipt = new File(receiptPath.getAbsolutePath() + "\\" + receiptFileName);
                try {
                    desktop.open(windowsReceipt);
                } catch (IOException e) {
                }
            } else { //for mac
                File macReceipt = new File(receiptPath.getAbsolutePath() + "/" + receiptFileName);
                try {
                    desktop.open(macReceipt);
                } catch (IOException e) {
                }
            }
        }
    }
}
