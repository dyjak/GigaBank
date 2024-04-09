package gigabank.gigabank.Entities;

public class EntityCurrency {

    int currency_id;
    String currency;
    double usd_conversion;

    public EntityCurrency(int currency_id, String currency, double usd_conversion) {
        this.currency_id = currency_id;
        this.currency = currency;
        this.usd_conversion = usd_conversion;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getUsd_conversion() {
        return usd_conversion;
    }

    public void setUsd_conversion(double usd_conversion) {
        this.usd_conversion = usd_conversion;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currency_id=" + currency_id +
                ", currency='" + currency + '\'' +
                ", usd_conversion=" + usd_conversion +
                '}';
    }
}
