package com.app.voting.model;


public class VoterInfo {

    private int year;

    private String branch;

    private int totalVoters;


    public VoterInfo(int year, String branch, int totalVoters) {

        this.year = year;

        this.branch = branch;

        this.totalVoters = totalVoters;

    }


    public int getYear() { return year; }

    public String getBranch() { return branch; }

    public int getTotalVoters() { return totalVoters; }

}