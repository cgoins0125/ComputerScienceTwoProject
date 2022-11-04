package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Customer;
import com.compscit.project.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewCustomerWindow implements ActionListener {

    private JFrame newCustomerWindow;
    private JPanel rootPanel;
    private JTextField customerNameTextField;
    private JTextField emailTextField;
    private JTextField phoneNumberTextField;
    private JTextField streetAddressTextField;
    private JTextField aptNumberTextField;
    private JTextField cityTextField;
    private JComboBox stateComboBox;
    private JTextField zipCodeTextField;
    private JCheckBox emailCheckBox;
    private JCheckBox phoneNumberCheckBox;
    private JCheckBox addressCheckBox;
    private JButton createCustomerButton;
    private CSVWriter writer;

    //constructor
    public NewCustomerWindow(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        createItemListeners();
        createKeyListener();
        newCustomerWindow = new JFrame();
        createWindowListener(); //must be called after the JFrame is instantiated
        newCustomerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newCustomerWindow.setLocationRelativeTo(null);
        newCustomerWindow.setContentPane(rootPanel);
        newCustomerWindow.pack();
        newCustomerWindow.setVisible(true);
    }

    //for window closing
    private void createWindowListener() {
        newCustomerWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                CustomersPage cp = new CustomersPage(new Point(425, 225));
            }
        });
    }

    //For text box focus
    private void createFocusListeners() {
        customerNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                customerNameTextField.setFont(new Font(null, Font.PLAIN, 12));
                customerNameTextField.setText(null);
                customerNameTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        emailTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                emailTextField.setFont(new Font(null, Font.PLAIN, 12));
                emailTextField.setText(null);
                emailTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        phoneNumberTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                phoneNumberTextField.setFont(new Font(null, Font.PLAIN, 12));
                phoneNumberTextField.setText(null);
                phoneNumberTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        streetAddressTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                streetAddressTextField.setFont(new Font(null, Font.PLAIN, 12));
                streetAddressTextField.setText(null);
                streetAddressTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        aptNumberTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                aptNumberTextField.setFont(new Font(null, Font.PLAIN, 12));
                aptNumberTextField.setText(null);
                aptNumberTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        cityTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cityTextField.setFont(new Font(null, Font.PLAIN, 12));
                cityTextField.setText(null);
                cityTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        zipCodeTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                zipCodeTextField.setFont(new Font(null, Font.PLAIN, 12));
                zipCodeTextField.setText(null);
                zipCodeTextField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

    }

    //for keystrokes
    private void createKeyListener() {
        phoneNumberTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (phoneNumberTextField.getText().length() == 3) {
                    phoneNumberTextField.setText(phoneNumberTextField.getText() + "-");
                }
                if (phoneNumberTextField.getText().length() == 7) {
                    if (!phoneNumberTextField.getText().endsWith("-")) {
                        phoneNumberTextField.setText(phoneNumberTextField.getText() + "-");
                    }
                }
                if (phoneNumberTextField.getText().length() >= 12) {
                    phoneNumberTextField.setText(phoneNumberTextField.getText().substring(0, 11));
                }
                if (phoneNumberTextField.getCaretPosition() < phoneNumberTextField.getText().length()) {
                    phoneNumberTextField.setCaretPosition(phoneNumberTextField.getText().length());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 8) { //the delete button is pressed
                    if (phoneNumberTextField.getText().length() == 8) {
                        phoneNumberTextField.setText(phoneNumberTextField.getText().substring(0, 7));
                    } else if (phoneNumberTextField.getText().length() == 4) {
                        phoneNumberTextField.setText(phoneNumberTextField.getText().substring(0, 3));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 45) {
                    String input = phoneNumberTextField.getText();
                    phoneNumberTextField.setText(input.substring(0, input.length() - 1));
                }
                if (e.getKeyCode() < 48 || e.getKeyCode() > 57) {
                    phoneNumberTextField.setText(phoneNumberTextField.getText().replaceAll("[^0-9-]", ""));
                }
            }
        });

        zipCodeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (zipCodeTextField.getText().length() >= 5) {
                    zipCodeTextField.setText(zipCodeTextField.getText().substring(0, 4));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() < 48 || e.getKeyCode() > 57) {
                    zipCodeTextField.setText(zipCodeTextField.getText().replaceAll("[^0-9]", ""));
                }
            }
        });

    }

    //For check boxes
    private void createItemListeners() {
        emailCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!emailTextField.isEnabled()) {
                    emailTextField.setEnabled(true);
                    emailTextField.setBackground(Color.WHITE);
                } else {
                    emailTextField.setEnabled(false);
                    emailTextField.setBackground(new Color(187, 187, 187));
                }
            }
        });

        phoneNumberCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!phoneNumberTextField.isEnabled()) {
                    phoneNumberTextField.setEnabled(true);
                    phoneNumberTextField.setBackground(Color.WHITE);
                } else {
                    phoneNumberTextField.setEnabled(false);
                    phoneNumberTextField.setBackground(new Color(187, 187, 187));
                }
            }
        });

        addressCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!streetAddressTextField.isEnabled()) {
                    streetAddressTextField.setEnabled(true);
                    streetAddressTextField.setBackground(Color.WHITE);
                    aptNumberTextField.setEnabled(true);
                    aptNumberTextField.setBackground(Color.WHITE);
                    cityTextField.setEnabled(true);
                    cityTextField.setBackground(Color.WHITE);
                    zipCodeTextField.setEnabled(true);
                    zipCodeTextField.setBackground(Color.WHITE);
                    stateComboBox.setEnabled(true);
                } else {
                    streetAddressTextField.setEnabled(false);
                    streetAddressTextField.setBackground(new Color(187, 187, 187));
                    aptNumberTextField.setEnabled(false);
                    aptNumberTextField.setBackground(new Color(187, 187, 187));
                    cityTextField.setEnabled(false);
                    cityTextField.setBackground(new Color(187, 187, 187));
                    zipCodeTextField.setEnabled(false);
                    zipCodeTextField.setBackground(new Color(187, 187, 187));
                    stateComboBox.setEnabled(false);
                }
            }
        });
    }

    //For button press
    private void createActionListeners() {
        createCustomerButton.addActionListener(this);
    }

    //What happens when button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createCustomerButton) {
            String name = customerNameTextField.getText().trim().replaceAll("[^A-Za-z ]", "");

            String email = null;
            if (emailTextField.isEnabled()) {
                email = emailTextField.getText().trim().replaceAll(",", "");
            }

            String phoneNumber = null;
            if (phoneNumberTextField.isEnabled()) {
                phoneNumber = phoneNumberTextField.getText();
            }

            String fullAddress = null;
            String streetAddress = "";
            String aptNumber = "";
            String city = "";
            String state = "";
            String zipCode = "";

            if (streetAddressTextField.isEnabled()) {

                streetAddress = streetAddressTextField.getText().trim().replaceAll(",", "");
                aptNumber = aptNumberTextField.getText().replaceAll(",", "");
                city = cityTextField.getText().trim().replaceAll(",", "");
                state = stateComboBox.getSelectedItem().toString();
                zipCode = zipCodeTextField.getText();

                if (aptNumber.equals("") || aptNumber.equals("Apt Number")) {
                    fullAddress = streetAddress + "," + city + "," + state + "," + zipCode + ",";
                    fullAddress.replaceAll("[^A-Za-z0-9,.]", "");
                } else {
                    fullAddress = streetAddress + " " + aptNumber + "," + city + "," + state + "," + zipCode;
                    fullAddress.replaceAll("[^A-Za-z0-9,.]", "");

                }
            }

            //error checks
            boolean nameValid = false;
            boolean emailValid = false;
            boolean phoneNumberValid = false;
            boolean addressValid = false;

                if (name.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Name is required", "", JOptionPane.ERROR_MESSAGE);
                } else nameValid = true;

                if (emailTextField.isEnabled()) {
                    if (!email.contains("@") || !email.contains(".") || email.equals("abc@abc.com")) {
                        emailTextField.setText("abc@abc.com");
                        emailTextField.setForeground(Color.RED);
                    } else emailValid = true;
                } else emailValid = true;

                if (phoneNumberTextField.isEnabled()) {
                    if (phoneNumber.length() < 12 || phoneNumber.substring(0, 3).contains("-") || phoneNumber.substring(4, 7).contains("/") ||
                            phoneNumber.equals("###-###-####") || phoneNumber.isBlank()) {
                        phoneNumberTextField.setText("###-###-####");
                        phoneNumberTextField.setForeground(Color.RED);
                    } else phoneNumberValid = true;
                } else phoneNumberValid = true;

                if (streetAddressTextField.isEnabled()) {

                    boolean zipCodeValid = false;
                    boolean streetAddressValid = false;
                    boolean cityValid = false;

                    if (streetAddress.isEmpty() || streetAddress.equals("123 ChattState Dr")) {
                        streetAddressTextField.setText("123 ChattState Dr");
                        streetAddressTextField.setForeground(Color.RED);
                    } else streetAddressValid = true;
                    if (city.isEmpty() || city.equals("City")) {
                        cityTextField.setText("City");
                        cityTextField.setForeground(Color.RED);
                    } else cityValid = true;
                    if (zipCodeTextField.getText().length() < 5 || zipCode.equals("Zip Code")) {
                        zipCodeTextField.setText("Zip Code");
                        zipCodeTextField.setForeground(Color.RED);
                    } else zipCodeValid = true;

                    if (streetAddressValid && cityValid && zipCodeValid) addressValid = true;

                } else addressValid = true;


            if (nameValid && emailValid && phoneNumberValid && addressValid) {
                Data.customers.add(new Customer(name, email, phoneNumber, fullAddress));
                writer.updateCustomers();
                NewCustomerWindow nw = new NewCustomerWindow(newCustomerWindow.getLocation());
                newCustomerWindow.dispose();
            }
        }
    }
}
