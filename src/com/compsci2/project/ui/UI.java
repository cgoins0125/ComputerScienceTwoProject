package com.compsci2.project.ui;

import javax.swing.*;
import java.awt.*;

public class UI {

    //this is set as static because I had trouble closing the stock page otherwise
    private static JFrame stockFrame;
    private static JFrame addStockFrame;
    private static JFrame frame;

    public UI() {
    }

    public void createStockPage() {
        StockPage sp = new StockPage();
        JPanel root = sp.getRootPanel();
        stockFrame = new JFrame();
        stockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockFrame.setLocationRelativeTo(null);
        stockFrame.setVisible(true);
        stockFrame.setContentPane(root);
        stockFrame.pack();
    }

    public void closeStockPage() {
        stockFrame.dispose();
    }

    public void addStockWindow() {
        AddStockWindow sp = new AddStockWindow();
        JPanel root = sp.getRootPanel();
        addStockFrame = new JFrame();
        addStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addStockFrame.setLocationRelativeTo(null);
        addStockFrame.setVisible(true);
        addStockFrame.setContentPane(root);
        addStockFrame.pack();
    }

    //Template for creating new windows
    public void newWindow() {
        frame = new JFrame();
        frame.setSize(400,400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JLabel label = new JLabel("Hello");
        label.setBounds(0,0,100,50);
        label.setFont(new Font(null,Font.PLAIN,25));
        frame.add(label);
    }

}
