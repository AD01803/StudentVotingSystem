package com.app.voting.ui;


import javax.swing.*;

import java.awt.*;


public class MainFrame extends JFrame {


    public MainFrame() {

        setTitle("Online Voting System");

        setSize(400, 300);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);


        JButton studentBtn = new JButton("Student Panel");

        JButton adminBtn = new JButton("Admin Panel");

        JButton exitBtn = new JButton("Exit");


        studentBtn.addActionListener(e -> new StudentPanel());

        adminBtn.addActionListener(e -> new AdminPanel());

        exitBtn.addActionListener(e -> System.exit(0));


        setLayout(new GridLayout(3, 1, 10, 10));

        add(studentBtn);

        add(adminBtn);

        add(exitBtn);

    }

}