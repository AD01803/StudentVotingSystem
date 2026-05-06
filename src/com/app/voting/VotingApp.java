package com.app.voting;


import com.app.voting.ui.MainFrame;

import javax.swing.*;


public class VotingApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));

    }

}


