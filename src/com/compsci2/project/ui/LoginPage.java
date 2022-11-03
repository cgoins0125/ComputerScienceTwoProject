package com.compsci2.project.ui;

import com.compsci2.project.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginPage implements ActionListener {

    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton newAccountButton;
    private JFrame loginFrame;
    private Point startLocation;

    public LoginPage() {
        startLocation = new Point(425,150);
        createFocusListeners();
        createActionListeners();
        loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocation(startLocation);
        loginFrame.setContentPane(rootPanel);
        loginFrame.pack();
        loginFrame.setVisible(true);
    }
    private void createFocusListeners() {
        usernameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameTextField.setFont(new Font(null,Font.PLAIN,12));
                usernameTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void createActionListeners() {
        //this - gets the action performed method in this class
        loginButton.addActionListener(this);
        newAccountButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == loginButton) {
            //if login successful
            StockPage sp = new StockPage(loginFrame.getLocation());
            loginFrame.dispose();
            //else display incorrect login
        }

        if (e.getSource() == newAccountButton) {

        }

    }

}