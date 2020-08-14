package model;

import exception.LackOfFundsException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class BankAccount extends Account{

    private Scanner sc = new Scanner(System.in);
    private ArrayList<Customer> owners = new ArrayList<>();
    private Customer customer;
    public ArrayList<Customer> getOwners() {
        return owners;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BankAccount() {

    }

    public BankAccount (Customer customer , double accountBalance) {
        super(accountBalance);
        this.customer = customer;
    }

    public void addCustomer(Customer customer) {
        owners.add(customer);
    }

    public void printCustomer() {
        for(Customer customer : owners) {
            System.out.println("[" + owners.indexOf(customer) + "]" + " "+ customer.getFirstName() + " " + customer.getLastName());
        }
    }

    @Override
    public void payment(double amount) throws InterruptedException {
       setAccountBalance(getAccountBalance() + amount);
    }

    @Override
    public void withdraw(double amount) throws LackOfFundsException, InterruptedException {
        if(getAccountBalance() - amount < 0) {
            throw new LackOfFundsException("Brak środków na koncie");
        } else {
            setAccountBalance(getAccountBalance() - amount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(sc, that.sc) &&
                Objects.equals(owners, that.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sc, owners);
    }
}
