package com.app.voting.ui;


import com.app.voting.model.Candidate;

import com.app.voting.service.ElectionService;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.util.List;


public class StudentPanel extends JFrame {


    private final ElectionService service;

    private int voteCastedTo = 0;

    private JButton[] voteBtns;

    private JButton submitVoteBtn;

    public StudentPanel() {


        service = new ElectionService();

        if (!service.loadElectionInfo()) {

            JOptionPane.showMessageDialog(this, "No active election. Contact admin.");

            return;

        }


        setTitle("Student Voting Panel");

        setSize(500, 400);

        setLayout(new BorderLayout());


        JTextField prnField = new JTextField();

        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        topPanel.add(new JLabel("Enter your PRN:"));

        topPanel.add(prnField);


        JTextArea candidatesArea = new JTextArea();

        candidatesArea.setEditable(false);

        List<Candidate> candidates = service.getCandidates();

        voteBtns = new JButton[candidates.size()];


        JPanel voteButtonsPanel = new JPanel();

        voteButtonsPanel.setLayout(new GridLayout(5,1,1,5));

       

        int i = 0;

        for (Candidate c : candidates) {

            candidatesArea.append(c.toString() + "\n");

            voteBtns[i] = new JButton(c.getName());

            voteBtns[i].addActionListener(ae -> setVoteForCandidate(ae));

            voteButtonsPanel.add(voteBtns[i++]);

        }



        submitVoteBtn = new JButton("Submit Vote");

        submitVoteBtn.setEnabled(false);

        submitVoteBtn.addActionListener(e -> {

            try {

                String prn = prnField.getText().trim();

                int cid = voteCastedTo;


                if (!service.isValid(prn)) {

                    JOptionPane.showMessageDialog(this, "Invalid PRN");

                } else if (service.hasVoted(prn)) {

                    JOptionPane.showMessageDialog(this, "Already voted!");

                } else if (cid < 1 || cid > candidates.size()) {

                    JOptionPane.showMessageDialog(this, "Invalid candidate ID");

                } else {

                    service.vote(prn, cid);

                    JOptionPane.showMessageDialog(this, "Vote recorded successfully!");

                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage());

            } finally {

                this.dispose();

            }

        });

       

        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(candidatesArea), BorderLayout.CENTER); // JScrollPane automatically introduces the Scrollbars

        add(voteButtonsPanel, BorderLayout.EAST);

        add(submitVoteBtn, BorderLayout.SOUTH);


        setVisible(true);

    }

   

    public void setVoteForCandidate(ActionEvent ae) {

        for(int i=0;i<voteBtns.length; i++) {

            if(ae.getSource() == voteBtns[i]) {

                voteCastedTo = i+1;

                voteBtns[i].setBackground(Color.green);

            }

            else {

                voteBtns[i].setBackground(Color.red);

            }

        }

        submitVoteBtn.setEnabled(true);

    }

}


