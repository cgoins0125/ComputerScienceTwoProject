package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Data;
import com.compscit.project.Stock;
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

public class StockPage implements ActionListener {
    private JFrame stockFrame;
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton deleteButton;
    private JTable inventoryTable;
    private JPanel rootPanel;
    private JButton usersButton;
    private JButton customersButton;
    private DefaultTableModel tableModel;
    private CSVWriter writer;

    public StockPage(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        createInventoryTable();
        createTableModelListener();
        stockFrame = new JFrame();
        stockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockFrame.setLocation(p);
        stockFrame.setContentPane(rootPanel);
        stockFrame.pack();
        stockFrame.setVisible(true);
    }

    private void createInventoryTable() {
        //This creates the table model by getting the data from the ArrayList inventory and storing it in a tabla
        String[] inventoryHeader = {"Id", "Name", "Quantity", "Cost", "Sale Price"};
        Object[][] data = new Object[Data.inventory.size()][5];
        int i = 0;
        for (Stock item : Data.inventory) {
            data[i][0] = item.getItemId();
            data[i][1] = item.getItemName();
            data[i][2] = item.getQuantityOnHand();
            data[i][3] = item.getCostExpenditure();
            data[i][4] = item.getSalePrice();
            i++;
        }
        tableModel = new DefaultTableModel(data, inventoryHeader);
        inventoryTable.setModel(tableModel);
        inventoryTable.getTableHeader().setReorderingAllowed(false);

        //The following makes the table dynamically update based on what is typed in the search bar
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(inventoryTable.getModel());
        inventoryTable.setRowSorter(rowSorter);
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
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "ITEM ID CAN NOT BE MODIFIED \nTHE CHANGE WILL NOT BE SAVED","",JOptionPane.ERROR_MESSAGE);
                }
                //Do not allow changes to ID number when column == 0 *changes can be made but will not be saved - unless we can prevent them
                if (column == 1) { //item name
                    String name = ((String)tableModel.getValueAt(row, column)).trim();
                    if (name.isBlank()) JOptionPane.showMessageDialog(null, "ITEM NAME CANNOT BE NULL","",JOptionPane.ERROR_MESSAGE);
                    else {
                        Data.inventory.get(row).setItemName(name);
                        writer.updateInventory();
                    }
                }

                else if (column == 2) { //quantity
                    try {
                        String input = (String)tableModel.getValueAt(row,column);
                        int quantity = Integer.parseInt(input.trim().replaceAll("[^a-zA-Z0-9.]", ""));
                        Data.inventory.get(row).setQuantityOnHand(quantity);
                        writer.updateInventory();
                    } catch (ClassCastException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Input \nPlease enter a valid integer for quantity","",JOptionPane.ERROR_MESSAGE);
                    }
                }

                else if (column == 3) { //cost expenditure
                    try {
                        String input = (String)tableModel.getValueAt(row, column);
                        input = input.trim().replaceAll("[^a-zA-Z0-9.]", "");
                        Data.inventory.get(row).setCostExpenditure(Double.parseDouble(input));
                        writer.updateInventory();
                    } catch (ClassCastException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Input \nPlease enter a valid input for cost expenditure", "", JOptionPane.ERROR_MESSAGE);
                    }
                }

                else if (column == 4) { //sale price
                    try {
                        String input = (String)tableModel.getValueAt(row, column);
                        input = input.trim().replaceAll("[^a-zA-Z0-9.]", "");
                        Data.inventory.get(row).setSalePrice(Double.parseDouble(input));
                        writer.updateInventory();
                    } catch (ClassCastException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Input \nPlease enter a valid input for sale price", "", JOptionPane.ERROR_MESSAGE);
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
            createInventoryTable();
        }

        if (e.getSource() == suppliersButton) {
            SuppliersPage sp = new SuppliersPage(stockFrame.getLocation());
            stockFrame.dispose();
        }

        if (e.getSource() == saleButton) {
            SalesPage sp = new SalesPage(stockFrame.getLocation());
            stockFrame.dispose();
        }

        if (e.getSource() == usersButton) {
            UsersPage up = new UsersPage(stockFrame.getLocation());
            stockFrame.dispose();
        }

        if (e.getSource() == logOutButton) {
            LoginPage lp = new LoginPage();
            stockFrame.dispose();
        }

        if (e.getSource() == customersButton) {
            CustomersPage cp = new CustomersPage(stockFrame.getLocation());
            stockFrame.dispose();
        }

        if (e.getSource() == newButton) {
            AddStockWindow sw = new AddStockWindow(stockFrame.getLocation());
            stockFrame.dispose();
        }

        if (e.getSource() == deleteButton) {
            if (inventoryTable.getSelectedRow() != -1) {
                int row = inventoryTable.getSelectedRow();
                int itemId = Integer.parseInt(inventoryTable.getValueAt(row, 0).toString());
                String itemName = (String) inventoryTable.getValueAt(row, 1);
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + itemName + "?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (choice == 0) {//0 is yes
                    int index = 0;
                    for (Stock item : Data.inventory) {
                        if (itemId != item.getItemId()) {
                            index++;
                        } else break;
                    }
                    Data.inventory.remove(index);
                    writer.updateInventory();
                    createInventoryTable();
                }
            }
        }
    }

}