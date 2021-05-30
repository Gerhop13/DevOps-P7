package revolut;

import java.util.Currency;

public class DebitAccount {
    private Currency accCurrency;
    private double balance;

    public DebitAccount(Currency currency, double balance){
        this.balance = balance;
        this.accCurrency = currency;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void addFunds(double topUpAmount) {
        this.balance += topUpAmount;
    }
}
