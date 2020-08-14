package model;

import app.BankControl;

import java.io.Serializable;

public class BankStatistics {
    public void printAvgAllAccounts(BankControl bc) {
        double avg = 0;
        for(int i = 0; i < bc.getAccountList().size(); i++) {
            avg = avg +bc.getAccountList().get(i).getAccountBalance();
        }
        avg = avg/bc.getAccountList().size();
        System.out.println(avg);
    }
}
