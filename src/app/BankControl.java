package app;

import exception.LackOfFundsException;
import exception.NoSuchOptionException;
import interfaces.BankOperations;
import io.ConsolePrinter;
import io.DataReader;
import io.DiscIO;
import model.Account;
import model.BankAccount;
import model.BankStatistics;
import model.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class BankControl implements BankOperations, Serializable {

    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader();
    private BankAccount customerList;
    private ArrayList<BankAccount> accountList = new ArrayList<>();
    public ArrayList<BankAccount> getAccountList() {
        return accountList;
    }
    private DiscIO disc = new DiscIO();
    private BankStatistics bs = new BankStatistics();

    public void setAccountList(ArrayList<BankAccount> accountList) {
        this.accountList = accountList;
    }

    public BankControl() {
        customerList = new BankAccount();
    }

    public void controlLoop() throws InterruptedException {
        Option option;
        do {
            int accNumber;
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_CUSTOMER:
                    addCustomer();
                    break;
                case ADD_BANK_ACCOUNT:
                    try {
                        accountList.add(addBankAccount());
                    }catch (Exception e) {
                        System.out.println("Błąd podczas tworzenia konta");
                    }

                    break;
                case ADD_MONEY:
                    try {
                        System.out.println("Wybierz konto bankowe: ");
                        printBankAccount();
                        accNumber = dataReader.getInt();
                        payment(accountList.get(accNumber));
                    } catch (Exception e) {
                        System.out.println("Błąd podczas wpłaty");
                    }
                    break;
                case WITHDRAW_MONEY:
                    System.out.println("Wybierz konto bankowe: ");
                    printBankAccount();
                    accNumber =dataReader.getInt();
                    try {
                        withdraw(accountList.get(accNumber));
                    } catch (LackOfFundsException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case TRANSFER_MONEY:
                    System.out.println("Wybierz konto nadawcy, następnie wybierz konto odbiorcy i podaj kwotę przelewu");
                    printBankAccount();
                    transfer((BankAccount) accountList.get(dataReader.getInt()), (BankAccount) accountList.get(dataReader.getInt()), dataReader.getDouble());
                    break;
                case PRINT_CLIENTS:
                    customerList.printCustomer();
                    break;
                case PRINT_ACCOUNTS:
                    printBankAccount();
                    break;
                case PRINT_BANK_STATS:
                   bs.printAvgAllAccounts(this);
                    break;
                case EXIT:
                    disc.saveFile(this);
                   exit();

                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        } while (option != Option.EXIT);
    }

    @Override
    public void transfer(Account sender, Account recipient, double amount) throws LackOfFundsException {
        try {
            sender.setAccountBalance(sender.getAccountBalance() - amount);
            recipient.setAccountBalance(recipient.getAccountBalance() + amount);
            System.out.println("Przelano kwotę: " + amount);
        } catch (LackOfFundsException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Błąd. Przelew nie może zostać wykonany");
        }
    }

    private enum Option {
        EXIT(0,"Wyjście z programu"),
        ADD_CUSTOMER(1,"Nowy klient"),
        ADD_BANK_ACCOUNT(2, "Utwórz konto bankowe"),
        ADD_MONEY(3,"Wpłać pieniądze"),
        WITHDRAW_MONEY(4, "Wypłać pieniądze"),
        TRANSFER_MONEY(5, "Przelej pieniądze na inne konto"),
        PRINT_CLIENTS(6, "Wyświetl klientów banku"),
        PRINT_ACCOUNTS(7,"Wyświetl konta bankowe"),
        PRINT_BANK_STATS(8, "Wyświetl statystyki banku");
        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value +" " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch(ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id: " + option);
            }
        }
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", podaj ponownie");
            } catch (InputMismatchException ignored) {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for(Option option: Option.values()){
            System.out.println(option);
        }
    }

    private void addCustomer() {
        Customer customer = dataReader.createCustomer();
        customerList.addCustomer(customer);

    }

    public BankAccount addBankAccount() {
        System.out.println("Wybierz klienta: ");
        customerList.printCustomer();
        int custNumber =dataReader.getInt();
        System.out.println("Wprowadź kwotę pierwszej wpłaty: ");
        double firstPayment = dataReader.getDouble();
        System.out.println("Utworzono konto bankowe dla: " + customerList.getOwners().get(custNumber).getFirstName() + " " + customerList.getOwners().get(custNumber).getLastName() + ". Saldo konta: " + firstPayment);
        return new BankAccount(customerList.getOwners().get(custNumber),firstPayment);
    }

    public void printBankAccount() {
        for(BankAccount bankAccount : accountList) {
            System.out.println("[" + accountList.indexOf(bankAccount) + "] Właściciel " + bankAccount.getCustomer().getFirstName() + " " +  bankAccount.getCustomer().getLastName() + " Saldo konta: " + bankAccount.getAccountBalance());
        }
    }

    public void payment(BankAccount bankAccount) throws InterruptedException {

        System.out.println("Wprowadź kwotę: ");
        double addMoney = dataReader.getDouble();
        bankAccount.payment(addMoney);
    }

    public void withdraw(BankAccount bankAccount) throws InterruptedException {
        System.out.println("Wprowadź kwotę: ");
        double withdrawMoney = dataReader.getDouble();
        bankAccount.withdraw(withdrawMoney);
    }

    private void exit() {
        dataReader.close();
        printer.printLine("Koniec programu");
    }
}
