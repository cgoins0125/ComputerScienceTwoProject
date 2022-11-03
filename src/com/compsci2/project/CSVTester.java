package com.compsci2.project;

import com.compsci2.project.ui.LoginPage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVTester {

    public static void main(String[] args) {

        /**
         * the following is for selecting a path to store digital receipts
         * only asks for you to do this if a path has not been previously selected
         */
        String path = "";
        File receiptPath = new File("receiptPath.txt");
        try (Scanner in = new Scanner(receiptPath)) {
            path = in.nextLine();
        } catch (FileNotFoundException e) {
            ReceiptPathSelector rps = new ReceiptPathSelector();
            path = rps.getReceiptPath();
            try (FileWriter fw = new FileWriter("receiptPath.txt")) {
                fw.write(path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            CSVReader reader = new CSVReader(path);
        }

        /**
         * insert test commands below ie add stock, add sale.
         * I have included some example tests
         * successful tests should appropriately update their respective CSV files when the CSVReaderWriter is closed
         */

        //inventory test
        //Data.inventory.add(new Stock("pear",1000,2,10));

        //sale test
        /*int[][] sale1 = {{1,4},{2,7}};
        Data.sales.add(new Sale(1,sale1));*/
        //Receipt receipt = new Receipt(Data.sales.get(0));

        //user test
        /*LoginAttempt login = new LoginAttempt();*/

        //third party test
        /* Data.suppliers.add(new Supplier("U.S. Foods", null, "4231112222", "111 US FOODS DR"));
        Data.customers.add(new Customer("Cecily", null, null, null));*/


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage lp = new LoginPage();
            }
        });


    }

}


