package gigabank.gigabank.Entities;

import java.util.Date;

public class EntityAccount {

    int account_id;
    int user_id;
    int currency_id;
    double balance;
    String account_number;
    Date create_date;
    String permission;

    public EntityAccount(int account_id, int user_id, int currency_id, double balance, String account_number, Date create_date, String permission) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.currency_id = currency_id;
        this.balance = balance;
        this.account_number = account_number;
        this.create_date = create_date;
        this.permission = permission;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", user_id=" + user_id +
                ", currency_id=" + currency_id +
                ", balance=" + balance +
                ", account_number='" + account_number + '\'' +
                ", create_date=" + create_date +
                ", permission='" + permission + '\'' +
                '}';
    }
}
