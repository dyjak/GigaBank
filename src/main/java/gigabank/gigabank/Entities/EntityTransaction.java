package gigabank.gigabank.Entities;

import java.sql.Timestamp;

public class EntityTransaction {

    int transaction_id;
    int account_id;
    double amount;
    String contractor_account_number;
    String execute_date;
    String description;

    public EntityTransaction(int transaction_id, int account_id, double amount, String contractor_account_number, String execute_date, String description) {
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.amount = amount;
        this.contractor_account_number = contractor_account_number;
        this.execute_date = execute_date;
        this.description = description;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getContractor_account_number() {
        return contractor_account_number;
    }

    public void setContractor_account_number(String contractor_account_number) {
        this.contractor_account_number = contractor_account_number;
    }

    public String getExecute_date() {
        return execute_date;
    }

    public void setExecute_date(String execute_date) {
        this.execute_date = execute_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transaction_id +
                ", account_id=" + account_id +
                ", amount=" + amount +
                ", contractor_account_number='" + contractor_account_number + '\'' +
                ", execute_date=" + execute_date +
                ", description='" + description + '\'' +
                '}';
    }
}
