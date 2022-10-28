package com.compsci2.project;

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
     * @param sale
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
            File addMonth = new File(addYear.toString() + "\\" + month);
            if (!addMonth.exists()) addMonth.mkdir();
            File addDay = new File(addMonth.toString() + "\\" + day);
            if (!addDay.exists()) addDay.mkdir();
            returnPath=addDay;
        } else { //for Mac
            receiptPath += "/receipts";
            File addReceipts = new File(receiptPath);
            if (!addReceipts.exists()) addReceipts.mkdir();
            File addYear = new File(receiptPath + "/" + year);
            if (!addYear.exists()) addYear.mkdir();
            File addMonth = new File(addYear.toString() + "/" + month);
            if (!addMonth.exists()) addMonth.mkdir();
            File addDay = new File(addMonth.toString() + "/" + day);
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
        String receiptLine = String.format("%20s","Receipt "+sale.getSaleId()+"\n");
        int [][] soldItemId_quantity = sale.getSoldItemId_quantity();
        for (int i = 0; i < soldItemId_quantity.length; i++) {
            itemId = soldItemId_quantity[i][0];
            quantitySold = soldItemId_quantity[i][1];
            for (Stock item : Data.inventory) {
                if (itemId == item.getItemId()) {
                    receiptLine += (quantitySold+" "+item.getItemName().toUpperCase());
                    receiptLine += String.format("%25.2f", (item.getSalePrice() * quantitySold));
                    receiptLine += "\n";
                    //breaks out of for loop once item is found
                    break;
                }
            }
        }
        receiptLine += String.format("Subtotal: %23.2f", sale.getSubtotal());
        receiptLine += "\n";
        receiptLine += String.format("Tax: %28.2f", sale.getTax());
        receiptLine += "\n";
        receiptLine += String.format("Total: %26.2f", sale.getTotal());
        receiptLine += "\n";
        return receiptLine;
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
     * Prints a receipt for the customer
     */
    private void printReceipt() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            System.out.println("Receipt can be printed from " + receiptPath.getAbsolutePath() + "\\" + receiptFileName);
        } else {
            System.out.println("Receipt can be printed from " + receiptPath.getAbsolutePath() + "/" + receiptFileName);
        }
    }


}
