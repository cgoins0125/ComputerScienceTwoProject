package com.compscit.project;

import com.compscit.project.ui.LoginPage;

import javax.swing.*;

public class CSVTester {

    public static void main(String[] args) {

        CSVReader reader = new CSVReader();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage lp = new LoginPage();
            }
        });
    }

}


