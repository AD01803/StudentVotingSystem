package com.app.voting.model;


public class Candidate {

    private int id; // Guid

    private String name;

    private int votes;


    public Candidate(int id, String name) {

        this.id = id;

        this.name = name;

        this.votes = 0;

    }


    public int getId() { return id; }

    public String getName() { return name; }

    public int getVotes() { return votes; }


    public void incrementVotes() {

        this.votes++;

    }


    public void setVotes(int votes) {

        this.votes = votes;

    }


    @Override

    public String toString() {

        return id + ". " + name;

    }

   

    public String toString(boolean requireVotes) {

        return (requireVotes) ? id + ". " + name + " -> " + votes + " votes" : toString();

    }

}

