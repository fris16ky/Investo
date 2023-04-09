package com.senior.capstone.Investo;

public class Bills {
    private int id;
    private int userID;
    private int amount;
    private String note;
    private String date;

    public Bills(){}

    public Bills(int id, int userID, int amount, String note, String date) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    public Bills(int id, int userID, int amount, String note) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.note = note;
    }

    public Bills(int userID, int amount, String note) {
        this.userID = userID;
        this.amount = amount;
        this.note = note;
    }

    public Bills(int userID, int amount, String note, String date) {
        this.userID = userID;
        this.amount = amount;
        this.note = note;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bills{" +
                "id=" + id +
                ", userID=" + userID +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
