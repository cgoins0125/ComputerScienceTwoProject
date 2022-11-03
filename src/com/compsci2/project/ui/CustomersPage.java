package com.compsci2.project.ui;

import com.compsci2.project.*;

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
        createTableModelListener();
        customersFrame = new JFrame();
        customersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customersFrame.setContentPane(rootPanel);
        customersFrame.setLocation(p);
        customersFrame.pack();
        customersFrame.setVisible(true);
    }

    private void createTable() {

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
        customerTable.setShowGrid(false);
        customerTable.setShowVerticalLines(true);
        customerTable.setGridColor(Color.BLACK);

        //For resizing the table based on data in the columns
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int column = 0; column < customerTable.getColumnCount(); column++) {
            TableColumn tc = customerTable.getColumnModel().getColumn(column);
            int preferredWidth = tc.getMinWidth()+25;
            int maxWidth = tc.getMaxWidth();

            for (int row = 0; row < customerTable.getRowCount(); row++) {
                TableCellRenderer cr = customerTable.getCellRenderer(row, column);
                Component c = customerTable.prepareRenderer(cr, row, column);
                int width = c.getPreferredSize().width + customerTable.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);


                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tc.setPreferredWidth(preferredWidth);
        }

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

    }

    private void createTableModelListener() {
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "ID NUMBER CAN NOT BE MODIFIED \nTHE CHANGE WILL NOT BE SAVED","",JOptionPane.ERROR_MESSAGE);
                }
                //Do not allow changes to ID number when column == 0 *changes can be made but will not be saved - unless we can prevent them
                if (column == 1) { //item name
                    Data.customers.get(row).setName((String)tableModel.getValueAt(row, column));
                    writer.updateCustomers();
                }
                if (column == 1) { //name
                    Data.customers.get(row).setName((String)tableModel.getValueAt(row, column));
                    writer.updateCustomers();
                }

                else if (column == 2) { //email
                    Data.customers.get(row).setEmail((String)tableModel.getValueAt(row, column));
                    writer.updateCustomers();
                }

                else if (column == 3) { //phone number
                    Data.customers.get(row).setPhoneNumber((String)tableModel.getValueAt(row, column));
                    writer.updateCustomers();
                }

                else if (column == 4) { //address
                    Data.customers.get(row).setAddress((String)tableModel.getValueAt(row, column));
                    writer.updateCustomers();
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
            //NEW CUSTOMER!!
        }

        if (e.getSource() == deleteButton) {
            int row = customerTable.getSelectedRow();
            int customerId = Integer.parseInt(customerTable.getValueAt(row,0).toString());
            String name = (String)customerTable.getValueAt(row,1);
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " +name + "?","Confirm delete",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
            if (choice == 0) {//0 is yes
                int index = 0;
                for (Customer customer : Data.customers) {
                    if (customerId != customer.getId()) {
                        index++;
                    }
                    else break;
                }
                Data.customers.remove(index);
                writer.updateCustomers();
                createTable();
            }
        }
    }
}