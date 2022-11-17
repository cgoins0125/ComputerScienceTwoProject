package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Customer;
import com.compscit.project.Data;
import com.compscit.project.ThirdParty;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Scanner;

public class CustomersPage implements ActionListener {
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton deleteButton;
    private JTable customerTable;
    private JPanel rootPanel;
    private JButton usersButton;
    private JButton customersButton;
    private DefaultTableModel tableModel;
    private CSVWriter writer;

    private JFrame customersFrame;

    public CustomersPage(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        createTable();
        customersFrame = new JFrame();
        customersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customersFrame.setContentPane(rootPanel);
        customersFrame.setLocation(p);
        customersFrame.pack();
        customersFrame.setVisible(true);
    }

    public void createTable() {

        //This creates the table model by getting the data from the ArrayList suppliers and storing it in a tabla
        String[] thirdPartyHeader = {"Id", "Name", "Email", "Phone Number", "Address"};
        Object[][] customersData = new Object[Data.customers.size()][5];
        int i = 0;
        for (ThirdParty party : Data.customers) {
            customersData[i][0] = party.getId();
            customersData[i][1] = party.getName();
            customersData[i][2] = party.getEmail();
            customersData[i][3] = party.getPhoneNumber();
            customersData[i][4] = party.getAddress();
            i++;
        }
        tableModel = new DefaultTableModel(customersData, thirdPartyHeader);
        customerTable.setModel(tableModel);
        customerTable.getTableHeader().setReorderingAllowed(false);

        //Size the table columns
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customerTable.getColumnModel().getColumn(0).setMinWidth(30);
        customerTable.getColumnModel().getColumn(0).setMaxWidth(35);
        customerTable.getColumnModel().getColumn(1).setMinWidth(100);
        customerTable.getColumnModel().getColumn(1).setMaxWidth(250);
        customerTable.getColumnModel().getColumn(2).setMinWidth(175);
        customerTable.getColumnModel().getColumn(2).setMaxWidth(225);
        customerTable.getColumnModel().getColumn(3).setMinWidth(120);
        customerTable.getColumnModel().getColumn(3).setMaxWidth(120);
        customerTable.getColumnModel().getColumn(4).setMinWidth(400);
        customerTable.getColumnModel().getColumn(4).setMaxWidth(600);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        customerTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        customerTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        customerTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        customerTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        customerTable.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);

        //The following makes the table dynamically update based on what is typed in the search bar
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(customerTable.getModel());
        customerTable.setRowSorter(rowSorter);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchTextField.getText();
                if (text.trim().length() == 0 || text.equals("Search")) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchTextField.getText();
                if (text.trim().length() == 0 || text.equals("Search")) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet");
            }
        });

        createTableModelListener();

    }

    private void createTableModelListener() {
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                //Do not allow changes to ID number when column == 0
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "ID NUMBER CAN NOT BE MODIFIED \nTHE CHANGE WILL NOT BE SAVED", "", JOptionPane.ERROR_MESSAGE);
                    createTable();

                } else if (column == 1) { //name
                    String name = ((String)tableModel.getValueAt(row, column)).trim();
                    if (name.isBlank()) name = null;
                    try {
                        if (name.contains(",")) {
                            JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        } else {
                            Data.customers.get(row).setName(name);
                            writer.updateCustomers();
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "Name should not be null", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }

                } else if (column == 2) { //email
                    String email = ((String)tableModel.getValueAt(row, column)).trim();
                    if (email.isBlank()) email = null;
                    try {
                        if (email.contains(",")) {
                            JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        } else {
                            Data.customers.get(row).setEmail(email);
                            writer.updateCustomers();
                        }
                    } catch (NullPointerException ex) {
                        Data.customers.get(row).setEmail(null);
                        writer.updateCustomers();
                    }

                } else if (column == 3) { //phone number
                    String phoneNumber = ((String)tableModel.getValueAt(row, column)).trim();
                    try (Scanner in = new Scanner(phoneNumber)) {
                        if (phoneNumber.isBlank()) throw new NullPointerException();
                        in.useDelimiter("-");
                        int i = 0;
                        while (in.hasNext()) {
                            i++;
                            in.next();
                        }
                        if (i != 3 || phoneNumber.length() != 12 || phoneNumber.charAt(3) != '-' || phoneNumber.charAt(7) != '-') {
                            JOptionPane.showMessageDialog(null, "Phone Number format should follow ###-###-####", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        } else {
                            if (phoneNumber.contains(",")) {
                                JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                                createTable();
                            } else {
                                Data.suppliers.get(row).setPhoneNumber(phoneNumber);
                                writer.updateSuppliers();
                            }
                        }
                    } catch (NullPointerException ex) {
                        Data.suppliers.get(row).setPhoneNumber(null);
                        writer.updateSuppliers();
                    }

                } else if (column == 4) { //address
                    String address = ((String) tableModel.getValueAt(row, column)).trim();
                    try (Scanner in = new Scanner(address)) {
                        if (address.isBlank()) throw new NullPointerException();
                        in.useDelimiter(",");
                        int i = 0;
                        while (in.hasNext()) {
                            i++;
                            in.next();
                        }
                        if (i != 4) { // 1) street address apt number, 2) city, 3) state, 4) zip code
                            JOptionPane.showMessageDialog(null, "Check that the address is formatted properly \n" +
                                    "Street address, City, State, Zip Code", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        } else Data.customers.get(row).setAddress(address);
                        writer.updateCustomers();
                    } catch (NullPointerException ex) {
                        Data.customers.get(row).setAddress(null);
                        writer.updateCustomers();
                    }

                }
            }
        });
    }

    private void createFocusListeners() {
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchTextField.setFont(new Font(null,Font.PLAIN,12));
                searchTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void createActionListeners() {
        //this - gets the action performed method in this class
        inventoryButton.addActionListener(this);
        suppliersButton.addActionListener(this);
        saleButton.addActionListener(this);
        usersButton.addActionListener(this);
        logOutButton.addActionListener(this);
        newButton.addActionListener(this);
        deleteButton.addActionListener(this);
        customersButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == inventoryButton) {
            StockPage sp = new StockPage(customersFrame.getLocation());
            customersFrame.dispose();
        }

        if (e.getSource() == suppliersButton) {
            SuppliersPage sp = new SuppliersPage(customersFrame.getLocation());
            customersFrame.dispose();
        }

        if (e.getSource() == saleButton) {
            SalesPage sp = new SalesPage(customersFrame.getLocation());
            customersFrame.dispose();
        }

        if (e.getSource() == usersButton) {
            UsersPage up = new UsersPage(customersFrame.getLocation());
            customersFrame.dispose();
        }

        if (e.getSource() == logOutButton) {
            LoginPage lp = new LoginPage();
            customersFrame.dispose();
        }

        if (e.getSource() == customersButton) {
            createTable();
        }

        if (e.getSource() == newButton) {
            NewCustomerWindow nc = new NewCustomerWindow(customersFrame.getLocation());
            customersFrame.dispose();
        }

        if (e.getSource() == deleteButton) {
            if (customerTable.getSelectedRow() != -1) {
                int row = customerTable.getSelectedRow();
                int customerId = Integer.parseInt(customerTable.getValueAt(row, 0).toString());
                String name = (String) customerTable.getValueAt(row, 1);
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name + "?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (choice == 0) {//0 is yes
                    int index = 0;
                    for (Customer customer : Data.customers) {
                        if (customerId != customer.getId()) {
                            index++;
                        } else break;
                    }
                    Data.customers.remove(index);
                    writer.updateCustomers();
                    createTable();
                }
            }
        }
    }
}