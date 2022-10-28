package com.compsci2.project.ui;

import com.compsci2.project.CSVWriter;
import com.compsci2.project.Data;
import com.compsci2.project.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.InputMismatchException;

public class AddStockWindow implements ActionListener {
    private JPanel rootPanel;
    private JTextField itemNameTextField;
    private JTextField quantityTextField;
    private JTextField costExpenditureTextField;
    private JTextField salePriceTextField;
    private JButton addStockButton;
    private CSVWriter writer;

    public AddStockWindow() {
        writer = new CSVWriter();
        createFocusListeners();
        createActionListeners();
    }

    public JPanel getRootPanel() {
        return rootPanel;
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
                String name = itemNameTextField.getText().toLowerCase()
                        .trim();
                //Should throw an exception when a "." is placed in the text box
                int quantity = Integer.parseInt(quantityTextField.getText()
                        .trim().replaceAll("[^a-zA-Z0-9]", ""));
                double cost = Double.parseDouble(costExpenditureTextField.getText()
                        .trim().replaceAll("[^a-zA-Z0-9.]", ""));
                double sale = Double.parseDouble(salePriceTextField.getText()
                        .trim().replaceAll("[^a-zA-Z0-9.]", ""));
                Data.inventory.add(new Stock(name, quantity, cost, sale));
            } catch (NumberFormatException | InputMismatchException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input","",JOptionPane.ERROR_MESSAGE);
            }
            writer.updateInventory();
            itemNameTextField.setText("Item Name");
            quantityTextField.setText("Quantity");
            costExpenditureTextField.setText("Cost");
            salePriceTextField.setText("Sale Price");
            //Should also add the new item to the table
        }
    }
}
