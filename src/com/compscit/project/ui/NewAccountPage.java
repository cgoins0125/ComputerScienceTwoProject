package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Data;
import com.compscit.project.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class NewAccountPage implements ActionListener {

    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton createAccountButton;
    private JPasswordField passwordField2;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JFrame newAccountFrame;
    private CSVWriter writer;

    public NewAccountPage(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        newAccountFrame = new JFrame();
        newAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newAccountFrame.setLocation(p);
        newAccountFrame.setContentPane(rootPanel);
        newAccountFrame.pack();
        newAccountFrame.setVisible(true);
    }
    private void createFocusListeners() {
        firstNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                firstNameTextField.setFont(new Font(null,Font.PLAIN,12));
                firstNameTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        lastNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                lastNameTextField.setFont(new Font(null,Font.PLAIN,12));
                lastNameTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

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

        passwordField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField1.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        passwordField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField2.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void createActionListeners() {
        //this - gets the action performed method in this class
        createAccountButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == createAccountButton) {
            String firstName = firstNameTextField.getText().trim();
            String lastName = lastNameTextField.getText().trim();
            String enteredUsername = usernameTextField.getText().trim();
            char[] pw1 = passwordField1.getPassword();
            StringBuilder enteredPassword1 = new StringBuilder();
            for (char value : pw1) {
                enteredPassword1.append(value);
            }
            char[] pw2 = passwordField2.getPassword();
            StringBuilder enteredPassword2 = new StringBuilder();
            for (char c : pw2) {
                enteredPassword2.append(c);
            }

            //password check not working!!

            //if all inputs are acceptable, account is created
            if (enteredUsername.length()<5) JOptionPane.showMessageDialog(null, "Username must be at least 5 characters", "", JOptionPane.ERROR_MESSAGE);
            else if (enteredPassword1.length()<5) JOptionPane.showMessageDialog(null, "Password must be at least 5 characters", "", JOptionPane.ERROR_MESSAGE);
            else if (enteredPassword1.toString().contains(" ")) JOptionPane.showMessageDialog(null, "Password cannot contain spaces", "", JOptionPane.ERROR_MESSAGE);
            else if (firstName.contains(",") || lastName.contains(",") || enteredUsername.contains(",") || enteredPassword1.toString().contains(","))
                JOptionPane.showMessageDialog(null, "Commas are not acceptable", "", JOptionPane.ERROR_MESSAGE);
            else if (enteredPassword1.toString().equals(enteredPassword2.toString())) {
                if (!User.usernameExists(enteredUsername)) {
                    Data.users.add(new User(enteredUsername, enteredPassword1.toString(), firstName, lastName));
                    writer.updateUsers();
                    JOptionPane.showMessageDialog(null, "Account created successfully");
                    LoginPage lp = new LoginPage();
                    newAccountFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists", "", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}