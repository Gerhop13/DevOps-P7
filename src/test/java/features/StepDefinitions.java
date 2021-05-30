package features;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.lu.dann;
import org.junit.Assert;
import revolut.PaymentService;
import revolut.Person;

public class StepDefinitions {

    private double topUpAmount;
    //private String topUpMethod;
    PaymentService topUpMethod;
    Person danny;
    Person jeff;

    @Before//Before hooks run before the first step in each scenario
    public void setUp(){
        //We can use this to setup test data for each scenario
        danny = new Person("Danny");
        jeff = new Person("Jeff");
    }
    @Given("Danny has {double} euro in his euro Revolut account")
    public void dannyHasEuroInHisEuroRevolutAccount(double revolutStartingBalance) {
        //System.out.println("Danny has starting account balance of: " + String.valueOf(startingBalance));
        danny.setAccountBalance(revolutStartingBalance);
        System.out.println("Setting Danny's revolut balance to: " + revolutStartingBalance);
        //double newAccountBalance = danny.getAccountBalance("EUR");
        //System.out.println("Danny's new account balance if: " + String.valueOf(newAccountBalance));
    }

    @Given("Danny selects {double} euro as the topUp amount")
    public void danny_selects_euro_as_the_top_up_amount(double topUpAmount) {
        // Write code here that turns the phrase above into concrete actions
        this.topUpAmount = topUpAmount;
        System.out.println("Top up amount selected: " + topUpAmount);
        //throw new io.cucumber.java.PendingException();
    }

    //@Given("Danny selects his {word} as his topUp method")
    @Given("Danny selects his {paymentService} as his topUp method")
    //public void danny_selects_his_debit_card_as_his_top_up_method(String topUpSource) {
    public void danny_selects_his_debit_card_as_his_top_up_method(PaymentService topUpSource) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("The selected payment service type was " + topUpSource.getType());
        topUpMethod = topUpSource;
    }

//    @When("Danny tops up")
//    public void danny_tops_up() {
//        // Write code here that turns the phrase above into concrete actions
//        danny.getAccount("EUR").addFunds(topUpAmount);
//        //throw new io.cucumber.java.PendingException();
//    }

    @Then("The new balance of his euro account should now be {double}")
    public void the_new_balance_of_his_euro_account_should_now_be(double newBalance) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = danny.getAccount("EUR").getBalance();
        //Assert
        Assert.assertEquals(expectedResult, actualResult,0);
        System.out.println("The new final balance is: " + actualResult);
    }



    @When("Danny tops up test {double}")
    public void danny_tops_up_test(double topUpAmount) {
        // Write code here that turns the phrase above into concrete actions
        this.topUpAmount=topUpAmount;
        danny.getAccount("EUR").addFunds(this.topUpAmount);
        System.out.println("Danny topped up by: " + topUpAmount);
        //System.out.println("Danny's new balance is: " + danny.getAccountBalance("EUR"));
        //throw new io.cucumber.java.PendingException();
    }

    @Then("The balance in his euro account should be {double}")
    public void the_balance_in_his_euro_account_should_be(double newBalance) {
        // Write code here that turns the phrase above into concrete actions
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = danny.getAccountBalance("EUR");
        System.out.println("In assert on check euro account balance: " + actualResult +
                " : "+ expectedResult);
        //Assert
        Assert.assertEquals(expectedResult,actualResult,0);
        //throw new io.cucumber.java.PendingException();
    }

    @Then("The balance in the debit account should be {double}")
    public void the_balance_in_the_debit_account_should_be(double newBalance) {
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = danny.getDebitAccount("EUR").getBalance();
        System.out.println("In assert on check debit account balance: " + actualResult +
                " : "+ expectedResult);
        //Assert
        Assert.assertEquals(expectedResult,actualResult,0);
        //throw new io.cucumber.java.PendingException();
    }

    @Given("Danny has a DebitCard balance of {double}")
    public void danny_has_a_debit_card_balance_of(double debitBalance) {
        // Write code here that turns the phrase above into concrete actions
        danny.getDebitAccount("EUR").setBalance(debitBalance);
        System.out.println("The debitBalance has been set to: " + debitBalance);
        //throw new io.cucumber.java.PendingException();
    }


    @When("Danny tops up {double}")
    public void danny_tops_up(double transferAmount) {
        // Write code here that turns the phrase above into concrete actions
        this.topUpAmount = transferAmount;
        System.out.println("Debit balance: " + danny.getDebitAccount("EUR").getBalance()+
                "\nrevolut balance: " + danny.getAccount("EUR").getBalance() +
                 "\ntransfer amount: " + topUpAmount);
        //throw new io.cucumber.java.PendingException();
        if(this.topUpAmount>danny.getDebitAccount("EUR").getBalance()){
            System.out.println("Rejected: insufficient funds");
        }
        else {
            danny.getAccount("EUR").addFunds(transferAmount);
            danny.getDebitAccount("EUR").addFunds(-topUpAmount);
        }
    }


    @Given("Jeff has {double} euro in his euro account")
    public void jeff_has_euro_in_his_euro_account(double revolutStartingBalance) {
        jeff.setAccountBalance(revolutStartingBalance);
        System.out.println("Setting Jeff's revolut balance to: " + revolutStartingBalance);
    }

    @Given("Danny makes a payment of {double}")
    public void danny_makes_a_payment_of(double paymentAmount) {
        double newBalance = danny.makePayment(paymentAmount);
        System.out.println("made payment, new balance: " + newBalance);
    }

    @Given("Danny requests split bill with Jeff {double}")
    public void danny_requests_split_bill_with_jeff(double amountDue) {
       jeff.receiveBillSplit(amountDue);
    }

    @Then("Jeff transfers {double} to Danny")
    public void jeff_transfers_to_danny(double amountDue) {
        jeff.transferFunds(danny);
    }


    @Then("The new balance of Jeff euro account should now be {double}")
    public void the_new_balance_of_jeff_euro_account_should_now_be(double newBalance) {
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = jeff.getAccountBalance("EUR");
        System.out.println("In assert on check euro account balance: " + actualResult +
                " : "+ expectedResult);
        //Assert
        Assert.assertEquals(expectedResult,actualResult,0);
    }
}
