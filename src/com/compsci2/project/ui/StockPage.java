package com.compsci2.project.ui;

import com.compsci2.project.Data;
import com.compsci2.project.Stock;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class StockPage implements ActionListener {
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton thirdPartyButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable inventoryTable;
    private JPanel rootPanel;
    private JButton usersButton;
    private DefaultTableModel tableModel;

    public StockPage() {
        createFocusListeners();
        createActionListeners();
        createTable();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void createTable() {
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

            //The following makes the table and the search bar dynamically update
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(inventoryTable.getModel());
            inventoryTable.setRowSorter(rowSorter);
            searchTextField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    String text = searchTextField.getText();
                    if (text.trim().length()==0 || text.equals("Search")) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    String text = searchTextField.getText();
                    if (text.trim().length()==0 || text.equals("Search")) {
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

    public void updateTable() {

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
        thirdPartyButton.addActionListener(this);
        saleButton.addActionListener(this);
        usersButton.addActionListener(this);
        logOutButton.addActionListener(this);
        newButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == inventoryButton) {
            //when inventory button is pressed while on stock page, nothing should happen
        }

        if (e.getSource() == thirdPartyButton) {
            UI ui = new UI();
            ui.newWindow();
            ui.closeStockPage();
        }

        if (e.getSource() == saleButton) {
            UI ui = new UI();
            ui.newWindow();
            ui.closeStockPage();
        }

        if (e.getSource() == usersButton) {
            UI ui = new UI();
            ui.newWindow();
            ui.closeStockPage();
        }

        if (e.getSource() == logOutButton) {
            UI ui = new UI();
            ui.newWindow();
            ui.closeStockPage();
        }

        if (e.getSource() == newButton) {
            UI ui = new UI();
            ui.addStockWindow();
        }

        if (e.getSource() == deleteButton) {
            System.out.println(inventoryTable.getSelectedColumn());
        }

        if (e.getSource() == updateButton) {
            //What happens when the update button is pressed - consider allowing modifications to happen in the table (We wouldn't want to edit item id or name though)
            //We may not need the update button
        }
    }

}