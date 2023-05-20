package ir.alidev.hesabdar;

import java.util.ArrayList;
import java.util.Date;

public class Account {

    String name;
    int balance;
    Date created_at;
    ArrayList<Transaction> transactions;

    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.created_at = new Date();
        this.transactions = new ArrayList<>();
    }

    public ArrayList<Transaction> search(String detail, String type) {
        ArrayList<Transaction> results = new ArrayList<>();
        if (detail.length() > 0) {
            for (Transaction t: getTransactions()) {
                if (t.type.equals(type) && t.detail.contains(detail)) {
                    results.add(t);
                }
            }
        }else {
            for (Transaction t: getTransactions()) {
                if (t.type.equals(type)) {
                    results.add(t);
                }
            }
        }
        return results;
    }

    public ArrayList<Transaction> search(String detail) {
        ArrayList<Transaction> results = new ArrayList<>();
        if (detail.length() > 0) {
            for (Transaction t: getTransactions()) {
                if (t.detail.contains(detail)) {
                    results.add(t);
                }
            }
        }else {
            results.addAll(getTransactions());
        }
        return results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

}
