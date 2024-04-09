package gigabank.gigabank.Entities;

import java.util.Date;

public class EntityLoan {

    int loan_id;
    String title;
    double amount;
    int currency_id;
    int interest_percentage;
    String description;
    int user_id;
    Date create_date;
    Date deadline;
    double status;

    public EntityLoan(int loan_id, String title, double amount, int currency_id, int interest_percentage, String description, int user_id, Date create_date, Date deadline, double status) {
        this.loan_id = loan_id;
        this.title = title;
        this.amount = amount;
        this.currency_id = currency_id;
        this.interest_percentage = interest_percentage;
        this.description = description;
        this.user_id = user_id;
        this.create_date = create_date;
        this.deadline = deadline;
        this.status = status;
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public int getInterest_percentage() {
        return interest_percentage;
    }

    public void setInterest_percentage(int interest_percentage) {
        this.interest_percentage = interest_percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loan_id=" + loan_id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", currency_id=" + currency_id +
                ", interest_percentage=" + interest_percentage +
                ", description='" + description + '\'' +
                ", user_id=" + user_id +
                ", create_date=" + create_date +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}
