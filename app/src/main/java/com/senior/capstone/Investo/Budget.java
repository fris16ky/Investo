package com.senior.capstone.Investo;

public class Budget {
    private int id;
    private int userID;
    private int amount;
    private String note;

    public Budget(){}

    public Budget(int id, int userID, int amount, String note) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.note = note;
    }

    public Budget(int userID, int amount, String note) {
        this.userID = userID;
        this.amount = amount;
        this.note = note;
    }

    public Budget(int amount, String note) {
        this.amount = amount;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", userID=" + userID +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                '}';
    }
}
