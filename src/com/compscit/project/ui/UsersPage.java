package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Data;
import com.compscit.project.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UsersPage implements ActionListener {
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JTextField searchTextField;
    private JButton deleteButton;
    private JTable usersTable;
    private JPanel rootPanel;
    private JButton usersButton;
    private JButton customersButton;
    private DefaultTableModel tableModel;
    private CSVWriter writer;
    private JFrame usersFrame;

    public UsersPage(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        createTable();
        usersFrame = new JFrame();
        usersFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usersFrame.setVisible(true);
        usersFrame.setLocation(p);
        usersFrame.setContentPane(rootPanel);
        usersFrame.pack();
    }

    private void createTable() {
        //This creates the table model by getting the data from the ArrayList inventory and storing it in a tabla
        String[] inventoryHeader = {"Username", "First Name", "Last Name"};
        Object[][] data = new Object[Data.users.size()][3];
        int i = 0;
        for (User user : Data.users) {
            data[i][0] = user.getUsername();
            data[i][1] = user.getFirstName();
            data[i][2] = user.getLastName();
            i++;
        }
            tableModel = new DefaultTableModel(data, inventoryHeader);
            usersTable.setModel(tableModel);
            usersTable.getTableHeader().setReorderingAllowed(false);

            //The following makes the table dynamically update based on what is typed in the search bar
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(usersTable.getModel());
            usersTable.setRowSorter(rowSorter);
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

            createTableModelListener();
    }

    private void createTableModelListener() {
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "USERNAME CAN NOT BE MODIFIED \nTHE CHANGE WILL NOT BE SAVED","",JOptionPane.ERROR_MESSAGE);
                    createTable();
                }
                //Do not allow changes to ID number when column == 0 *changes can be made but will not be saved - unless we can prevent them
                if (column == 1) { //first name
                    String firstName = ((String)tableModel.getValueAt(row, column)).trim();
                    if (firstName.contains(",")) {
                        JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
                    else {
                        Data.users.get(row).setFirstName(firstName);
                        writer.updateUsers();
                    }
                }

                else if (column == 2) { //last name
                    String lastName = ((String) tableModel.getValueAt(row, column)).trim();
                    if (lastName.contains(",")) {
                        JOptionPane.showMessageDialog(null, "Field can not contain commas", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
                    else {
                        Data.users.get(row).setLastName(lastName);
                        writer.updateUsers();
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
        deleteButton.addActionListener(this);
        customersButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == inventoryButton) {
            StockPage sp = new StockPage(usersFrame.getLocation());
            usersFrame.dispose();
        }

        if (e.getSource() == suppliersButton) {
            SuppliersPage sp = new SuppliersPage(usersFrame.getLocation());
            usersFrame.dispose();
        }

        if (e.getSource() == saleButton) {
            SalesPage sp = new SalesPage(usersFrame.getLocation());
            usersFrame.dispose();
        }

        if (e.getSource() == usersButton) {
            createTable();
        }

        if (e.getSource() == logOutButton) {
            LoginPage lp = new LoginPage();
            usersFrame.dispose();
        }

        if (e.getSource() == customersButton) {
            CustomersPage cp = new CustomersPage(usersFrame.getLocation());
            usersFrame.dispose();
        }

        if (e.getSource() == deleteButton) {
            int row = usersTable.getSelectedRow();
            String username = (String)usersTable.getValueAt(row,0);
            String firstName = (String)usersTable.getValueAt(row,1);
            String lastName = (String)usersTable.getValueAt(row,2);
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + firstName + " " + lastName + "?","Confirm delete",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
            if (choice == 0) {//0 is yes
                int index = 0;
                for (User user : Data.users) {
                    if (!username.equals(user.getUsername())) {
                        index++;
                    }
                    else break;
                }
                Data.users.remove(index);
                writer.updateUsers();
                createTable();
            }
        }
    }

}