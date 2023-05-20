package ir.alidev.hesabdar;

import java.util.Calendar;

public class Transaction {

    String type;
    int amount;
    String detail;
    String date;

    public Transaction(String type, int amount, String detail) {
        this.type = type;
        this.amount = amount;
        this.detail = detail;
        // later edited
        Calendar c = Calendar.getInstance();
        PersianCalender pc = new PersianCalender();
        pc.GregorianToPersian(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        date = pc.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
