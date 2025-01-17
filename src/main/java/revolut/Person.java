package revolut;

import java.util.Currency;
import java.util.HashMap;

public class Person {

    private String name;
    private double billSplit;
    //Accounts currency & balance
    // EUR 30
    // USD 50
    // STG 30
    private HashMap<String, Account> userAccounts = new HashMap<String, Account>();
    private HashMap<String, DebitAccount> debit = new HashMap<String, DebitAccount>();

    public Person(String name){
        this.name = name;
        this.billSplit =0;
        //Create a default euro account and add it the our userAccounts container
        Currency accCurrency = Currency.getInstance("EUR");
        Account euroAccount = new Account(accCurrency, 0);
        DebitAccount debitAccount = new DebitAccount(accCurrency, 0);
        debit.put("EUR",debitAccount);
        userAccounts.put("EUR", euroAccount);
    }

    public void setAccountBalance(double startingBalance) {
        userAccounts.get("EUR").setBalance(startingBalance);
    }

    public double getAccountBalance(String eur) {
        return userAccounts.get(eur).getBalance();
    }

    public void setDebitAccountBalance(double startingBalance) {
        debit.get("EUR").setBalance(startingBalance);
    }

    public double getDebitAccountBalance(String eur) {
        return userAccounts.get(eur).getBalance();
    }

    public DebitAccount getDebitAccount(String account) {
        return debit.get(account);
    }

    public Account getAccount(String account) {
        return userAccounts.get(account);
    }

    public double makePayment(double payBillAmount){
        if(getAccountBalance("EUR") >= payBillAmount){
            double balance = userAccounts.get("EUR").getBalance();
            double newBalance = balance-payBillAmount;
            setAccountBalance(newBalance);
            return newBalance;
        }
        else{
            System.out.println("insufficient funds");
            return getAccountBalance("EUR");
        }

    }

    public void receiveBillSplit(double billAmount){
        this.billSplit = billAmount;
        System.out.println("Amount owed: " + this.billSplit);
    }

    public double getBillSplit() {
        return billSplit;
    }

    public void transferFunds(Person receiver){
        if(getAccountBalance("EUR")>=getBillSplit()){
            double myNewBalance = getAccountBalance("EUR") - getBillSplit();
            double receiverNewBalance = receiver.getAccountBalance("EUR") + getBillSplit();
            setAccountBalance(myNewBalance);
            receiver.setAccountBalance(receiverNewBalance);
            //System.out.println("my: " + myNewBalance + "  R: " + receiverNewBalance);
            billSplit=0;
            System.out.println("Successful transfer");
        }
        else {
            System.out.println("Insufficient Funds");
        }

    }
}
