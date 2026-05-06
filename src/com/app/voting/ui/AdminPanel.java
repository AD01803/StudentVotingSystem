package com.app.voting.ui;

import com.app.voting.model.Candidate;

import com.app.voting.model.VoterInfo;

import com.app.voting.service.ElectionService;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

import java.util.List;


public class AdminPanel extends JFrame {

    private final ElectionService service;


    public AdminPanel() {

        service = new ElectionService();

        String user = JOptionPane.showInputDialog("Username:");

        String pass = JOptionPane.showInputDialog("Password:");

        if (!user.equals("Admin") || !pass.equals("Admin")) {

            JOptionPane.showMessageDialog(this, "Invalid Credentials");

            return;

        }


        setTitle("Admin Panel");

        setSize(500, 400);

        setLayout(new GridLayout(4, 1));


        JButton newElectionBtn = new JButton("Create New Election");

        JButton loadBtn = new JButton("Load Election");

        JButton resultBtn = new JButton("Show Result");

        JButton logoutBtn = new JButton("Logout");


        newElectionBtn.addActionListener(e -> createElection());

        loadBtn.addActionListener(e -> service.loadElectionInfo());

        resultBtn.addActionListener(e -> showResult());

        logoutBtn.addActionListener(e -> dispose());


        add(newElectionBtn);

        add(loadBtn);

        add(resultBtn);

        add(logoutBtn);


        setVisible(true);

    }


    private void createElection() { // Validations and Exception Handling can be introduced as enhancement/improvement

        int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Year:"));

        String branch = JOptionPane.showInputDialog("Enter Branch Code:");

        int total = Integer.parseInt(JOptionPane.showInputDialog("Enter Total Voters:"));


        int n = 0;

        do {

            n = Integer.parseInt(JOptionPane.showInputDialog("Number of Candidates (2-5):"));

        } while (n < 2 || n > 5);


        List<String> names = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            names.add(JOptionPane.showInputDialog("Name of Candidate " + (i + 1)));

        }


        VoterInfo info = new VoterInfo(year, branch.toUpperCase(), total);

        service.createElection(info, names);

        JOptionPane.showMessageDialog(this, "Election Created!");

    }


    private void showResult() {

        if (!service.loadElectionInfo()) {

            JOptionPane.showMessageDialog(this, "No election data found.");

            return;

        }


        StringBuilder result = new StringBuilder("RESULTS:\n");

        Candidate winner = service.getWinner();

        if (winner != null) {

            result.append("Winner: ").append(winner.getName()).append(" with ")

                    .append(winner.getVotes()).append(" votes\n\n");

        } else {

            result.append("No Result: It's a TIE!\n\n");

        }


        for (Candidate c : service.getCandidates()) {

            result.append(c.toString(true)).append("\n");

        }


        double percent = (service.getTotalVotesCast() * 100.0) / service.getVoterInfo().getTotalVoters();

        result.append("\nVoting Percentage: ").append(percent).append("%");


        JOptionPane.showMessageDialog(this, result.toString());

    }

}

