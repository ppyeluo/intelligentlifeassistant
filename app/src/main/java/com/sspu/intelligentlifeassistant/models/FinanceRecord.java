package com.sspu.intelligentlifeassistant.models;

public class FinanceRecord {
    private String type;
    private String image;
    private String note;
    private double amount;
    private boolean isExpense;

    public FinanceRecord(String type, String image, String note, double amount, boolean isExpense) {
        this.type = type;
        this.image = image;
        this.note = note;
        this.amount = amount;
        this.isExpense = isExpense;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }
}