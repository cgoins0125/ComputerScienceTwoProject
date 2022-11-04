package com.compscit.project.ui;

import com.compscit.project.CSVWriter;
import com.compscit.project.Data;
import com.compscit.project.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.InputMismatchException;

public class AddStockWindow implements ActionListener {

    private JFrame addStockFrame;
    private JPanel rootPanel;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField costExpenditureTextField;
    private JTextField salePriceTextField;
    private JButton addStockButton;
    private CSVWriter writer;

    public AddStockWindow(Point p) {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
        addStockFrame = new JFrame();
        createWindowListener();
        addStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addStockFrame.setLocationRelativeTo(null);
        addStockFrame.setContentPane(rootPanel);
        addStockFrame.pack();
        addStockFrame.setVisible(true);
    }

    private void createWindowListener() {
        addStockFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                StockPage sp = new StockPage(new Point(425,225));
            }
        });
    }

    //Focus listeners are for when text boxes are clicked on
    private void createFocusListeners() {
        itemNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                itemNameTextField.setFont(new Font(null, Font.PLAIN, 12));
                itemNameTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        quantityTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                quantityTextField.setFont(new Font(null, Font.PLAIN, 12));
                quantityTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        costExpenditureTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                costExpenditureTextField.setFont(new Font(null, Font.PLAIN, 12));
                costExpenditureTextField.setText("$");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        salePriceTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                salePriceTextField.setFont(new Font(null, Font.PLAIN, 12));
                salePriceTextField.setText("$");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    //Action Listeners are for when buttons are pressed
    private void createActionListeners() {
        addStockButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStockButton) {
            try {
                double cost = Double.parseDouble(costExpenditureTextField.getText()
                        .trim().replaceAll("[^a-zA-Z0-9.]", ""));
                double sale = Double.parseDouble(salePriceTextField.getText()
                        .trim().replaceAll("[^a-zA-Z0-9.]", ""));
                String name = itemNameTextField.getText();
                int quantity = 0;
                if (quantityTextField.getText().contains(".")) {
                    throw new NumberFormatException();
                } else if (name.isBlank()) {
                    throw new InputMismatchException();
                } else {
                    quantity = Integer.parseInt(quantityTextField.getText().trim().replaceAll("[^a-zA-Z0-9]", ""));
                    name.toLowerCase().trim().replaceAll(",", "");
                    Data.inventory.add(new Stock(name, quantity, cost, sale));
                    writer.updateInventory();
                }
            } catch (NumberFormatException | InputMismatchException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input","",JOptionPane.ERROR_MESSAGE);
            } finally {
                itemNameTextField.setText("Item Name");
                quantityTextField.setText("Quantity");
                costExpenditureTextField.setText("Cost");
                salePriceTextField.setText("Sale Price");
            }
        }
    }
}
