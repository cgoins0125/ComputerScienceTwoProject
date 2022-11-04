package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Data;
import com.compscit.project.Supplier;
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

public class SuppliersPage implements ActionListener {
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton deleteButton;
    private JTable supplierTable;
    private JPanel rootPanel;
    private JButton usersButton;
    private JButton customersButton;
    private DefaultTableModel tableModel;
    private CSVWriter writer;
    private JFrame suppliersFrame;

    public SuppliersPage(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        createTable();
        createTableModelListener();
        suppliersFrame = new JFrame();
        suppliersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        suppliersFrame.setContentPane(rootPanel);
        suppliersFrame.pack();
        suppliersFrame.setLocation(p);
        suppliersFrame.setVisible(true);
    }

    public void createTable() {
        //This creates the table model by getting the data from the ArrayList suppliers and storing it in a tabla
        String[] thirdPartyHeader = {"Id", "Name", "Email", "Phone Number", "Address"};
        Object[][] supplierData = new Object[Data.suppliers.size()][5];
        int i = 0;
        for (ThirdParty party : Data.suppliers) {
            supplierData[i][0] = party.getId();
            supplierData[i][1] = party.getName();
            supplierData[i][2] = party.getEmail();
            supplierData[i][3] = party.getPhoneNumber();
            supplierData[i][4] = party.getAddress();
            i++;
        }
        tableModel = new DefaultTableModel(supplierData, thirdPartyHeader);
        supplierTable.setModel(tableModel);
        supplierTable.getTableHeader().setReorderingAllowed(false);

        supplierTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        supplierTable.getColumnModel().getColumn(0).setMinWidth(30);
        supplierTable.getColumnModel().getColumn(0).setMaxWidth(35);
        supplierTable.getColumnModel().getColumn(1).setMinWidth(100);
        supplierTable.getColumnModel().getColumn(1).setMaxWidth(250);
        supplierTable.getColumnModel().getColumn(2).setMinWidth(175);
        supplierTable.getColumnModel().getColumn(2).setMaxWidth(225);
        supplierTable.getColumnModel().getColumn(3).setMinWidth(120);
        supplierTable.getColumnModel().getColumn(3).setMaxWidth(120);
        supplierTable.getColumnModel().getColumn(4).setMinWidth(400);
        supplierTable.getColumnModel().getColumn(4).setMaxWidth(600);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        supplierTable.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        supplierTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        supplierTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
        supplierTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
        supplierTable.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);

        //The following makes the table dynamically update based on what is typed in the search bar
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(supplierTable.getModel());
        supplierTable.setRowSorter(rowSorter);
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

                } else if (column == 1) { //name
                    String name = ((String) tableModel.getValueAt(row, column)).trim();
                    if (name.isBlank()) name = null;
                    try {
                        if (name.contains(",")) {
                            JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Data.suppliers.get(row).setName(name);
                            writer.updateSuppliers();
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null, "Name should not be null", "", JOptionPane.ERROR_MESSAGE);
                    }

                } else if (column == 2) { //email
                    String email = ((String) tableModel.getValueAt(row, column)).trim();
                    if (email.isBlank()) email = null;
                    try {
                        if (email.contains(",")) {
                            JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Data.suppliers.get(row).setEmail(email);
                            writer.updateSuppliers();
                        }
                    } catch (NullPointerException ex) {
                        Data.suppliers.get(row).setEmail(null);
                        writer.updateSuppliers();
                    }

                } else if (column == 3) { //phone number
                    String phoneNumber = ((String)tableModel.getValueAt(row, column)).trim();
                    phoneNumber = phoneNumber.replaceAll("[^0-9-,]", "");
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
                        } else {
                            if (phoneNumber.contains(",")) {
                                JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
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
                    address = address.replaceAll("[^A-Za-z0-9,.#]", "");
                    try (Scanner in = new Scanner(address)) {
                        if (address.isBlank()) throw new NullPointerException();
                        in.useDelimiter(",");
                        int i = 0;
                        while (in.hasNext()) {
                            i++;
                            in.next();
                        }
                        if (i != 4) { // 1) street address apt number, 2) city, 3) state, 4) zip code
                            JOptionPane.showMessageDialog(null, "Check that the address is formatted properly", "", JOptionPane.ERROR_MESSAGE);
                        } else Data.suppliers.get(row).setAddress(address);
                        writer.updateSuppliers();
                    } catch (NullPointerException ex) {
                        Data.suppliers.get(row).setAddress(null);
                        writer.updateSuppliers();
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
            StockPage sp = new StockPage(suppliersFrame.getLocation());
            suppliersFrame.dispose();
        }

        if (e.getSource() == suppliersButton) {
            createTable();
        }

        if (e.getSource() == saleButton) {
            SalesPage sp = new SalesPage(suppliersFrame.getLocation());
            suppliersFrame.dispose();
        }

        if (e.getSource() == usersButton) {
            UsersPage up = new UsersPage(suppliersFrame.getLocation());
            suppliersFrame.dispose();
        }

        if (e.getSource() == logOutButton) {
            LoginPage lp = new LoginPage();
            suppliersFrame.dispose();
        }

        if (e.getSource() == customersButton) {
            CustomersPage cp = new CustomersPage(suppliersFrame.getLocation());
            suppliersFrame.dispose();
        }

        if (e.getSource() == newButton) {
            NewSupplierWindow ns = new NewSupplierWindow(suppliersFrame.getLocation());
            suppliersFrame.dispose();
        }

        if (e.getSource() == deleteButton) {
            if (supplierTable.getSelectedRow() != -1) {
                int row = supplierTable.getSelectedRow();
                int supplierId = Integer.parseInt(supplierTable.getValueAt(row, 0).toString());
                String name = (String) supplierTable.getValueAt(row, 1);
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name + "?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (choice == 0) {//0 is yes
                    int index = 0;
                    for (Supplier supplier : Data.suppliers) {
                        if (supplierId != supplier.getId()) {
                            index++;
                        } else break;
                    }
                    Data.suppliers.remove(index);
                    writer.updateSuppliers();
                    createTable();
                }
            }
        }
    }
}