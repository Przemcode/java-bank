package io;

import app.BankControl;

import java.io.*;

public class DiscIO implements Serializable {

    public void saveFile(BankControl bankControl) {
            String fileName = "bank.obj";

            try(
                    var fs = new FileOutputStream(fileName);
                    var os = new ObjectOutputStream(fs);
            ) {
                for(int i= 0; i <bankControl.getAccountList().size(); i++){
                    os.writeObject(bankControl.getAccountList().get(i).getCustomer().getFirstName() + " " + bankControl.getAccountList().get(i).getCustomer().getLastName() + " "+ bankControl.getAccountList().get(i).getAccountBalance());
                }
                System.out.println("Zapisano obiekt do pliku");

            } catch (IOException e) {
                System.err.println("Bład zapisu pliku " + fileName);
                e.printStackTrace();
            }
        }
}

