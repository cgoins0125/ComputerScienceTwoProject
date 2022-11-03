package com.compsci2.project.ui;

import com.compsci2.project.Data;
import com.compsci2.project.SalesReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;

public class SalesPage implements ActionListener {
    private JButton saleButton;
    private JButton inventoryButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JButton newSaleButton;
    private JPanel rootPanel;
    private JButton usersButton;
    private JButton customersButton;
    private JTextField fromMonthTextField;
    private JTextField fromDayTextField;
    private JTextField fromYearTextField;
    private JTextField toMonthTextField;
    private JTextField toDayTextField;
    private JTextField toYearTextField;
    private JButton generateSalesReportButton;
    private JTable salesTable;
    private JButton clearReportButton;
    private DefaultTableModel tableModel;
    private JFrame salesFrame;

    public SalesPage(Point p) {
        createFocusListeners();
        createActionListeners();
        createTable();
        salesFrame = new JFrame();
        salesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        salesFrame.setContentPane(rootPanel);
        salesFrame.pack();
        salesFrame.setLocation(p);
        salesFrame.setVisible(true);
    }

    private void createTable() {
        //This creates the table model by getting the data from the ArrayList inventory and storing it in a table
        String[] salesHeader = {"Sale Id", "Customer Id", "Subtotal", "Sales Tax", "Total Sale", "Cost", "Profit", "Sell Made"};
        Object[][] data = new Object[Data.sales.size()][8];
        int i = 0;
        for (SalesReport sale : Data.sales) {
            data[i][0] = sale.getSaleId();
            data[i][1] = sale.getCustomerId();
            data[i][2] = sale.getSubtotal();
            data[i][3] = sale.getSalesTax();
            data[i][4] = sale.getTotalSale();
            data[i][5] = sale.getCostExpenditure();
            data[i][6] = sale.getProfit();
            data[i][7] = sale.getSellDate();
            i++;
        }
        tableModel = new DefaultTableModel(data, salesHeader) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        salesTable.setModel(tableModel);
        salesTable.getTableHeader().setReorderingAllowed(false);
        //Sales table should not be edited
    }

    private void updateTable() {

        ArrayList<SalesReport> salesReport = new ArrayList<>();

        int fromMonth = 0;
        int fromDay = 0;
        int fromYear = 0;
        int toMonth = 0;
        int toDay = 0;
        int toYear = 0;

        try {
            fromMonth = Integer.parseInt(fromMonthTextField.getText());
            fromDay = Integer.parseInt(fromDayTextField.getText());
            fromYear = Integer.parseInt(fromYearTextField.getText());
            toMonth = Integer.parseInt(toMonthTextField.getText());
            toDay = Integer.parseInt(toDayTextField.getText());
            toYear = Integer.parseInt(toYearTextField.getText());

            if (fromMonth > 12 || fromMonth < 1 || fromYear < 999 || fromYear > 9999 ||
                    toMonth > 12 || toMonth < 1 || toYear < 999 || toYear > 9999) {
                JOptionPane.showMessageDialog(null, "Invalid Input\nMM/DD/YYYY", "", JOptionPane.ERROR_MESSAGE);
                createTable();
            } else if (fromYear > toYear) {
                JOptionPane.showMessageDialog(null, "Invalid Input\nFrom date can not be after to date", "", JOptionPane.ERROR_MESSAGE);
                createTable();
            } else if (fromYear == toYear && fromMonth > toMonth) {
                JOptionPane.showMessageDialog(null, "Invalid Input\nFrom date can not be after to date", "", JOptionPane.ERROR_MESSAGE);
                createTable();
            } else if (fromYear == toYear && fromMonth == toMonth && fromDay > toDay) {
                JOptionPane.showMessageDialog(null, "Invalid Input\nFrom date can not be after to date", "", JOptionPane.ERROR_MESSAGE);
                createTable();
            } else if (fromMonth == 1 || fromMonth == 3 || fromMonth == 5 || fromMonth == 7 || fromMonth == 8 || fromMonth == 10 || fromMonth == 12) {
                        if (fromDay > 31 || fromDay < 1) {
                            JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 31 days in the from month", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        }
            } else if (toMonth == 1 || toMonth == 3 || toMonth == 5 || toMonth == 7 || toMonth == 8 || toMonth == 10 || toMonth == 12) {
                        if (toDay > 31 || toDay < 1) {
                            JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 31 days in the to month", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        }
            } else if (fromMonth == 4 || fromMonth == 6 || fromMonth == 9 || fromMonth == 11) {
                        if (fromDay > 30 || fromDay < 1) {
                            JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 30 days in the from month", "", JOptionPane.ERROR_MESSAGE);
                            createTable();
                        }
            } else if (toMonth == 4 || toMonth == 6 || toMonth == 9 || toMonth == 11) {
                    if (toDay > 30 || toDay < 1) {
                        JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 30 days in the to month", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
            } else if (fromMonth == 2) {
                    if (fromDay > 28 || fromDay < 1) {
                        JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 28 days in from February", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
            } else if (toMonth == 2) {
                    if (toDay > 28 || toDay < 1) {
                        JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 28 days in to February", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
            } else if (fromYear%4 == 0 && fromMonth==2) {
                    if (fromDay < 1 || fromDay > 29) {
                        JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 29 days in from February", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
            } else if (toYear%4 == 0 && toMonth==2) {
                    if (toDay < 1 || toDay > 29) {
                        JOptionPane.showMessageDialog(null, "Invalid Input\nThere are between 1 and 29 days in to February", "", JOptionPane.ERROR_MESSAGE);
                        createTable();
                    }
            }

        } catch (InputMismatchException | NumberFormatException | ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input","",JOptionPane.ERROR_MESSAGE);
                createTable();
        }

        for (SalesReport sale : Data.sales) {

            String sellDate = sale.getSellDate();
            int sellMonth = Integer.parseInt(sellDate.substring(0, sellDate.indexOf("/")));
            int sellDay = Integer.parseInt(sellDate.substring(sellDate.indexOf("/") + 1, sellDate.lastIndexOf("/")));
            int sellYear = Integer.parseInt(sellDate.substring(sellDate.lastIndexOf("/") + 1));

            if (sellYear > fromYear && sellYear < toYear) {
                salesReport.add(sale);
            } else if (sellYear == fromYear) {
                if (sellMonth > fromMonth) {
                    salesReport.add(sale);
                } else if (sellMonth == fromMonth) {
                    if (sellDay > fromDay) {
                        salesReport.add(sale);
                    }
                }
            } else if (sellYear == toYear) {
                if (sellMonth < toMonth) {
                    salesReport.add(sale);
                } else if (sellMonth == toMonth) {
                    if (sellDay < toDay) {
                        salesReport.add(sale);
                    }
                }
            }
        }
        Collections.sort(salesReport);

        String[] salesHeader = {"Sale Id", "Customer Id", "Subtotal", "Sales Tax", "Total Sale", "Cost Expenditure", "Profit", "Sell Made"};
        Object[][] data = new Object[salesReport.size()][8];
        int i = 0;
        for (SalesReport sale : salesReport) {
            data[i][0] = sale.getSaleId();
            data[i][1] = sale.getCustomerId();
            data[i][2] = sale.getSubtotal();
            data[i][3] = sale.getSalesTax();
            data[i][4] = sale.getTotalSale();
            data[i][5] = sale.getCostExpenditure();
            data[i][6] = sale.getProfit();
            data[i][7] = sale.getSellDate();
            i++;
        }
        tableModel = new DefaultTableModel(data, salesHeader) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;

            }
        };

        salesTable.setModel(tableModel);
        salesTable.getTableHeader().setReorderingAllowed(false);
    }

    private void createFocusListeners() {

        fromMonthTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fromMonthTextField.setFont(new Font(null,Font.PLAIN,12));
                fromMonthTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        fromDayTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fromDayTextField.setFont(new Font(null,Font.PLAIN,12));
                fromDayTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        fromYearTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fromYearTextField.setFont(new Font(null,Font.PLAIN,12));
                fromYearTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        toMonthTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                toMonthTextField.setFont(new Font(null,Font.PLAIN,12));
                toMonthTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        toDayTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                toDayTextField.setFont(new Font(null,Font.PLAIN,12));
                toDayTextField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        toYearTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                toYearTextField.setFont(new Font(null,Font.PLAIN,12));
                toYearTextField.setText(null);
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
        newSaleButton.addActionListener(this);
        customersButton.addActionListener(this);
        generateSalesReportButton.addActionListener(this);
        clearReportButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //for getting a new page and closing the one that is clicked on
        if (e.getSource() == inventoryButton) {
            StockPage sp = new StockPage(salesFrame.getLocation());
            salesFrame.dispose();
        }

        if (e.getSource() == suppliersButton) {
            SuppliersPage sp = new SuppliersPage(salesFrame.getLocation());
            salesFrame.dispose();
        }

        if (e.getSource() == saleButton) {
            createTable();
        }

        if (e.getSource() == usersButton) {
            UsersPage up = new UsersPage(salesFrame.getLocation());
            salesFrame.dispose();
        }

        if (e.getSource() == logOutButton) {
            LoginPage lp = new LoginPage();
            salesFrame.dispose();
        }

        if (e.getSource() == customersButton) {
            CustomersPage cp = new CustomersPage(salesFrame.getLocation());
            salesFrame.dispose();
        }

        if (e.getSource() == generateSalesReportButton) {
            updateTable();
        }

        if (e.getSource() == clearReportButton) {
            createTable();
        }

        if (e.getSource() == newSaleButton) {
            //NEW SALES!!
        }

    }

}