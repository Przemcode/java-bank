package app;

import java.io.Serializable;

public class Bank implements Serializable {
    public static void main(String[] args) throws InterruptedException {

        BankControl bc = new BankControl();

        bc.controlLoop();
    }






}
