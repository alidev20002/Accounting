package ir.alidev.hesabdar;

import java.util.ArrayList;

public class User {

    ArrayList<Account> accounts;

    public User() {
        this.accounts = new ArrayList<>();
    }

    public boolean hasAccount(String name) {
        for (Account a: getAccounts()) {
            if (a.getName().equals(name))
                return true;
        }
        return false;
    }

    public Account getAccount(String name) {
        for (Account a: getAccounts()) {
            if (a.getName().equals(name))
                return a;
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

}
