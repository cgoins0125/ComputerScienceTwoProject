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

    private void createTable() {
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
        supplierTable.setShowGrid(false);
        supplierTable.setShowVerticalLines(true);
        supplierTable.setGridColor(Color.BLACK);


        //For resizing the table based on data in the columns
        supplierTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int column = 0; column < supplierTable.getColumnCount(); column++) {
            TableColumn tc = supplierTable.getColumnModel().getColumn(column);
            int preferredWidth = tc.getMinWidth()+25;
            int maxWidth = tc.getMaxWidth();

            for (int row = 0; row < supplierTable.getRowCount(); row++) {
                TableCellRenderer cr = supplierTable.getCellRenderer(row, column);
                Component c = supplierTable.prepareRenderer(cr, row, column);
                int width = c.getPreferredSize().width + supplierTable.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);


                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tc.setPreferredWidth(preferredWidth);
        }

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
                if (column == 0) {
                    JOptionPane.showMessageDialog(null, "ID NUMBER CAN NOT BE MODIFIED \nTHE CHANGE WILL NOT BE SAVED","",JOptionPane.ERROR_MESSAGE);
                }
                //Do not allow changes to ID number when column == 0 *changes can be made but will not be saved - unless we can prevent them
                if (column == 1) { //name
                    Data.suppliers.get(row).setName((String)tableModel.getValueAt(row, column));
                    writer.updateSuppliers();
                }

                else if (column == 2) { //email
                    Data.suppliers.get(row).setEmail((String)tableModel.getValueAt(row, column));
                    writer.updateSuppliers();
                }

                else if (column == 3) { //phone number
                    Data.suppliers.get(row).setPhoneNumber((String)tableModel.getValueAt(row, column));
                    writer.updateSuppliers();
                }

                else if (column == 4) { //address
                    Data.suppliers.get(row).setAddress((String)tableModel.getValueAt(row, column));
                    writer.updateSuppliers();
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
            //NEW SUPPLIERS!!
        }

        if (e.getSource() == deleteButton) {
            int row = supplierTable.getSelectedRow();
            int supplierId = Integer.parseInt(supplierTable.getValueAt(row,0).toString());
            String name = (String)supplierTable.getValueAt(row,1);
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + name + "?","Confirm delete",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
            if (choice == 0) {//0 is yes
                int index = 0;
                for (Supplier supplier : Data.suppliers) {
                    if (supplierId != supplier.getId()) {
                        index++;
                    }
                    else break;
                }
                Data.suppliers.remove(index);
                writer.updateSuppliers();
                createTable();
            }
        }
    }
}