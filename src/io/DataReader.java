package io;

import model.Customer;

import java.io.Serializable;
import java.util.Scanner;

public class DataReader implements Serializable {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public Customer createCustomer() {
        System.out.println("Imię");
        String firstName = sc.nextLine();
        System.out.println("Nazwisko ");
        String lastName = sc.nextLine();
        return new Customer(firstName, lastName);
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public double getDouble() {
        try {
            return sc.nextDouble();
        } finally {
            sc.nextLine();
        }
    }

    public void close() {
        sc.close();
    }
}
