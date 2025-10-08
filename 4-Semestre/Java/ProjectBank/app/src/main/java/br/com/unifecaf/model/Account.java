package br.com.unifecaf.model;

public class Account {
    private String number;
    private double balance;

    private Client client;

    public Account(String number, String branch, Client client) {
        this.number = number;
        this.balance = 0.0;

        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return "Deposit successful. New balance: " + this.balance;
        } else {
            return "Deposit amount must be positive.";
        }
    }

    public String withdraw(double amount) {
        if (amount > 0) {
            if (amount <= this.balance) {
                this.balance -= amount;
                return "Withdrawal successful. New balance: " + this.balance;
            } else {
                return "Insufficient funds.";
            }
        } else {
            return "Withdrawal amount must be positive.";
        }
    }
}
