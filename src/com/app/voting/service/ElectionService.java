package com.app.voting.service;


import com.app.voting.model.Candidate;

import com.app.voting.model.VoterInfo;

import com.app.voting.utils.FileUtil;

import java.io.*;

import java.util.*;


public class ElectionService {

    private VoterInfo voterInfo;

    private List<Candidate> candidates;

    private Map<Integer, Integer> voteRecord; // rollNo -> candidateId

    private final String baseDir = "data";


    public ElectionService() {

        this.candidates = new ArrayList<>();

        this.voteRecord = new HashMap<>();

    }


    public void createElection(VoterInfo info, List<String> candidateNames) {

        this.voterInfo = info;

        this.candidates.clear();

        this.voteRecord.clear();


        for (int i = 0; i < candidateNames.size(); i++) {

            this.candidates.add(new Candidate(i + 1, candidateNames.get(i)));

        }


        saveElectionInfo();

    }


    public boolean loadElectionInfo() {

        try {

            List<String> infoLines = FileUtil.readLinesFromFile(baseDir + "/ElectionInfo.txt");

            if (infoLines.size() < 4) return false;


            int year = Integer.parseInt(infoLines.get(0));

            String branch = infoLines.get(1);

            int totalVoters = Integer.parseInt(infoLines.get(2));

            int numCandidates = Integer.parseInt(infoLines.get(3));


            this.voterInfo = new VoterInfo(year, branch, totalVoters);

            this.candidates.clear();

            this.voteRecord.clear();


            for (int i = 1; i <= numCandidates; i++) {

                List<String> candLines = FileUtil.readLinesFromFile(baseDir + "/candidate" + i + ".txt");

                if (candLines.size() < 2) continue;

                Candidate c = new Candidate(i, candLines.get(1));

                c.setVotes(Integer.parseInt(candLines.get(0)));

                this.candidates.add(c);


                for (int j = 2; j < candLines.size(); j++) {

                    int roll = Integer.parseInt(candLines.get(j));

                    voteRecord.put(roll, i);

                }

            }


            return true;

        } catch (Exception e) {

            return false;

        }

    }


    public void saveElectionInfo() {

        try {

            new File(baseDir).mkdirs();

            List<String> info = Arrays.asList(

                String.valueOf(voterInfo.getYear()),

                voterInfo.getBranch(),

                String.valueOf(voterInfo.getTotalVoters()),

                String.valueOf(candidates.size())

            );

            FileUtil.writeLinesToFile(baseDir + "/ElectionInfo.txt", info);


            for (Candidate c : candidates) {

                List<String> lines = new ArrayList<>();

                lines.add(String.valueOf(c.getVotes()));

                lines.add(c.getName());

                for (Map.Entry<Integer, Integer> entry : voteRecord.entrySet()) {

                    if (entry.getValue() == c.getId()) {

                        lines.add(String.valueOf(entry.getKey()));

                    }

                }

                FileUtil.writeLinesToFile(baseDir + "/candidate" + c.getId() + ".txt", lines);

            }


        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public boolean vote(String prn, int candidateId) {

        int rollNo = extractRollNo(prn);

        if (voteRecord.containsKey(rollNo)) return false;


        voteRecord.put(rollNo, candidateId);

        candidates.get(candidateId - 1).incrementVotes();

        saveElectionInfo();

        return true;

    }


    public boolean hasVoted(String prn) {

        int rollNo = extractRollNo(prn);

        return voteRecord.containsKey(rollNo);

    }


    public boolean isValid(String prn) {

        return prn.length() == 14 &&

               extractYear(prn) == voterInfo.getYear() &&

               extractBranch(prn).equals(voterInfo.getBranch()) &&

               extractRollNo(prn) <= voterInfo.getTotalVoters();

    }

    // Following three methods are checking for valid PRN e.g. 2023BTCSE00098

    public int extractYear(String prn) {

        return Integer.parseInt(prn.substring(0, 4));

    }


    public String extractBranch(String prn) {

        return prn.substring(4, 9);

    }


    public int extractRollNo(String prn) {

        return Integer.parseInt(prn.substring(9, 14));

    }


    public List<Candidate> getCandidates() {

        return candidates;

    }


    public VoterInfo getVoterInfo() {

        return voterInfo;

    }


    public int getTotalVotesCast() {

        return voteRecord.size();

    }


    public Candidate getWinner() {

        Candidate winner = null;

        boolean tie = false;

        int maxVotes = -1;

        // e.g. getVotes for 5 candidates = 4,34,34,23,41

        for (Candidate c : candidates) {

            if (c.getVotes() > maxVotes) {

                winner = c;

                maxVotes = c.getVotes();

                tie = false;

            } else if (c.getVotes() == maxVotes) {

                tie = true;

            }

        }


        return tie ? null : winner;

    }

}


