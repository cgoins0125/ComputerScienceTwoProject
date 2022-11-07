package com.compscit.project.ui;

import com.compscit.project.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewSalePage implements ActionListener {
    private JTable inventoryTable;
    private JTable cartTable;
    private JButton addToCartButton;
    private JButton clearCartButton;
    private JSpinner quantitySpinner;
    private JButton removeFromCartButton;
    private JCheckBox guestCheckoutCheckBox;
    private JButton completeSaleButton;
    private JComboBox customersComboBox;
    private JPanel rootPanel;
    private JTextField searchTextField;
    private CSVWriter writer;
    private JFrame newSaleFrame;
    private SpinnerNumberModel spinModel;
    private DefaultTableModel cartTableModel;
    private DefaultTableModel inventoryTableModel;
    private ArrayList<CartItem> cart;

    public NewSalePage(Point p) {
        cart = new ArrayList<>();
        writer = new CSVWriter();
        createSpinner();
        createComboBox();
        createCartTable();
        createInventoryTable();
        createFocusListeners();
        createActionListeners();
        createItemListeners();
        createWindowListener();
        newSaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newSaleFrame.setLocation(p);
        newSaleFrame.setContentPane(rootPanel);
        newSaleFrame.pack();
        newSaleFrame.setVisible(true);
    }

    private void createSpinner() {
        spinModel = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
        quantitySpinner.setModel(spinModel);
    }

    private void createComboBox() {
        for (Customer customer : Data.customers) {
            customersComboBox.addItem(customer.getId() + ", " + customer.getName());
        }
    }

    private void createItemListeners() {
        guestCheckoutCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!customersComboBox.isEnabled()) {
                    customersComboBox.setEnabled(true);
                    customersComboBox.setBackground(Color.WHITE);
                } else {
                    customersComboBox.setEnabled(false);
                    customersComboBox.setBackground(new Color(187, 187, 187));
                }
            }
        });
    }

    private void createWindowListener() {
        newSaleFrame = new JFrame();
        newSaleFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                SalesPage sp = new SalesPage(newSaleFrame.getLocation());
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void createInventoryTable() {
        //This creates the table model by getting the data from the ArrayList inventory and storing it in a tabla
        String[] inventoryHeader = {"Id", "Name", "Quantity", "Sale Price"};
        Object[][] data = new Object[Data.inventory.size()][4];
        int i = 0;
        for (Stock item : Data.inventory) {
            data[i][0] = item.getItemId();
            data[i][1] = item.getItemName();
            data[i][2] = item.getQuantityOnHand();
            data[i][3] = item.getSalePrice();
            i++;
        }
        inventoryTableModel = new DefaultTableModel(data, inventoryHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        inventoryTable.setModel(inventoryTableModel);
        inventoryTable.getTableHeader().setReorderingAllowed(false);

        //The following makes the table dynamically update based on what is typed in the search bar
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(inventoryTable.getModel());
        inventoryTable.setRowSorter(rowSorter);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchTextField.getText();
                if (text.trim().length() == 0 || text.equals("Search Inventory")) {
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

    private void createCartTable() {
        //This creates the table model by getting the data from the ArrayList inventory and storing it in a tabla
        String[] cartHeader = {"Id", "Name", "Quantity", "Price Per Item"};
        Object[][] data = new Object[cart.size() + 1][4];

        int i = 0;
        double subtotal = 0;
        for (CartItem item : cart) {
            data[i][0] = item.getItemId();
            data[i][1] = item.getItemName();
            data[i][2] = item.getQuantity();
            data[i][3] = item.getPrice();
            subtotal += item.getSubtotal();
            i++;
        }

        data[i][0] = "Subtotal:";
        data[i][1] = null;
        data[i][2] = null;
        data[i][3] = Double.toString(subtotal);

        cartTableModel = new DefaultTableModel(data, cartHeader) {

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2 && row != data.length-1) return true; //Quantity is editable
                else return false; //otherwise fields should not be changed
            }

        };
        createTableModelListener();
        cartTable.setModel(cartTableModel);
        cartTable.getTableHeader().setReorderingAllowed(false);
    }

    private void createTableModelListener() {
        cartTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                    try { //only editable column is 2 - quantity
                        int id = Integer.parseInt(cartTableModel.getValueAt(row,0).toString());
                        String input = (String)cartTableModel.getValueAt(row,2);
                        int quantity = Integer.parseInt(input.trim().replaceAll("[^a-zA-Z0-9.]", ""));
                        int maxQuantity = 0;
                        for (Stock item : Data.inventory) {
                            if (id == item.getItemId()) {
                                maxQuantity = item.getQuantityOnHand();
                            }
                        }
                        if (quantity <= maxQuantity) {
                            cart.get(row).setQuantity(quantity);
                        } else {
                            cart.get(row).setQuantity(maxQuantity);
                            JOptionPane.showMessageDialog(null, "Max quantity is " + maxQuantity,"",JOptionPane.ERROR_MESSAGE);
                        }
                        createCartTable();
                    } catch (ClassCastException | NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Input \nPlease enter a valid integer for quantity","",JOptionPane.ERROR_MESSAGE);
                    }

                }

        });
    }

    private void createFocusListeners() {
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchTextField.setFont(new Font(null, Font.PLAIN, 12));
                searchTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void createActionListeners() {
        //this - gets the action performed method in this class
        addToCartButton.addActionListener(this);
        removeFromCartButton.addActionListener(this);
        clearCartButton.addActionListener(this);
        completeSaleButton.addActionListener(this);

    }

    private void createSale(int customerId) {
        int[][] saleItemId_quantity = new int[cart.size()][2];
        int i = 0;
        for (CartItem item : cart) {
            saleItemId_quantity[i][0] = item.getItemId();
            saleItemId_quantity[i][1] = item.getQuantity();
            i++;
        }
        Sale newSale = new Sale(customerId,saleItemId_quantity);
        writer.updateSales();
        cart.clear();
        createCartTable();
        createInventoryTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == completeSaleButton) {
            int customerId = 0;
            if (!cart.isEmpty()) {
                if (customersComboBox.isEnabled()) {
                    if (customersComboBox.getSelectedItem().equals("Select Customer")) {
                        JOptionPane.showMessageDialog(null, "No Customer Selected\n" +
                                "If customer is not shown, please select\n" +
                                "guest checkout or add a new customer", "", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String str = customersComboBox.getSelectedItem().toString();
                        str = str.substring(0, str.indexOf(",")).trim();
                        customerId = Integer.parseInt(str);
                        createSale(customerId);
                    }
                } else createSale(customerId); //guest id is zero
            } else JOptionPane.showMessageDialog(null, "No items in the cart", "", JOptionPane.ERROR_MESSAGE);
        }

        if (e.getSource() == addToCartButton) {
            if (inventoryTable.getSelectedRow() != -1) {
                int row = inventoryTable.getSelectedRow();
                int itemId = Integer.parseInt(inventoryTable.getValueAt(row, 0).toString());
                String itemName = (String) inventoryTable.getValueAt(row, 1);
                double price = Double.parseDouble(inventoryTable.getValueAt(row, 3).toString());
                int quantity = Integer.parseInt(quantitySpinner.getValue().toString());

                boolean isInCart = false;
                int cartIndex = -1;

                for (CartItem item : cart) {
                    cartIndex++;
                    if (itemId == item.getItemId()) {
                        //if the itemId is already in the cart, add to quantity
                        isInCart = true;
                        break;
                    }
                }

                int availableQuantity = Integer.parseInt(inventoryTable.getValueAt(row,2).toString());
                if (quantity > 0) {
                    if (isInCart) {
                        int newQuantity = cart.get(cartIndex).getQuantity() + quantity;
                        if (newQuantity < availableQuantity) {
                            cart.get(cartIndex).setQuantity(newQuantity);
                        } else {
                            cart.get(cartIndex).setQuantity(availableQuantity);
                            JOptionPane.showMessageDialog(null, "Max quantity is " + availableQuantity,"",JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (quantity <= availableQuantity) {
                            cart.add(new CartItem(itemId, itemName, quantity, price));
                        } else {
                            cart.add(new CartItem(itemId, itemName, availableQuantity, price));
                            JOptionPane.showMessageDialog(null, "Max quantity is " + availableQuantity,"",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else JOptionPane.showMessageDialog(null, "Quantity selected is 0 or negative","",JOptionPane.ERROR_MESSAGE);
                createCartTable();
                quantitySpinner.setValue(1);
            }
        }

        if (e.getSource() == removeFromCartButton) {
            int row = cartTable.getSelectedRow();
            cart.remove(row);
            createCartTable();
        }

        if (e.getSource() == clearCartButton) {
            cart.clear();
            createCartTable();
        }
    }


}




