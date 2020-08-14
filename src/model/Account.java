package model;

import exception.LackOfFundsException;

import java.io.Serializable;

public abstract class Account implements Serializable {

    private double accountBalance;

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) throws InterruptedException {

        if(accountBalance < 0) {
            throw new LackOfFundsException("Brak środków na koncie");
        }else {
            this.accountBalance = accountBalance;
        }
    }

    public Account() {
    }

    public Account(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public abstract void payment(double amount) throws InterruptedException;

    public abstract void withdraw(double amount) throws LackOfFundsException, InterruptedException;
}
