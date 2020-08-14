package interfaces;

import exception.LackOfFundsException;
import model.Account;

public interface BankOperations {
    void transfer(Account sender, Account recipient, double amount) throws LackOfFundsException, InterruptedException;
}
