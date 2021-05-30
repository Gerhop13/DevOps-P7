Feature: TopUp Account
  This feature describes various scenarios for users adding funds to their revolut account(s)

  #As a user, I can topup my Revolut account using my debit card

  Scenario: Add money to Revolut account using debit card
    Given Danny has 10 euro in his euro Revolut account
    And Danny selects 100 euro as the topUp amount
    And  Danny selects his DebitCard as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up test 100
    Then The balance in his euro account should be 110


  Scenario: Add money to Revolut account using bank account
    Given Danny has 20 euro in his euro Revolut account
    And Danny selects 230 euro as the topUp amount
    And  Danny selects his BankAccount as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up test 230
    Then The balance in his euro account should be 250



  #ToDo implement the remaining scenarios listed below

  #To implement this scenario you will need to use data tables
    # https://cucumber.io/docs/cucumber/api/
  Scenario Outline: Add various amounts to Revolut account
    Given Danny has <startBalance> euro in his euro Revolut account
    And Danny selects his DebitCard as his topUp method
    When Danny tops up test <topUpAmount>
    Then The balance in his euro account should be <newBalance>
    Examples:
      | startBalance| topUpAmount | newBalance  |
      | 0           | 100         | 100         |
      | 14          | 20          | 34          |
      | 23          | 30          | 53          |


  Scenario Outline: Add various amounts to Revolut account
    Given Danny has <startBalance> euro in his euro Revolut account
    And Danny selects his DebitCard as his topUp method
    And Danny has a DebitCard balance of <debitBalance>
    When Danny tops up <topUpAmount>
    Then The balance in his euro account should be <newBalance>
    Examples:
      | startBalance | topUpAmount | newBalance | debitBalance |
      | 0            | 100         | 100        | 150          |
      | 14           | 20          | 14         | 15           |
      | 23           | 30          | 53         | 30           |
  Rule: The account balance shouldn't change if the topup payment request is rejected by the payment service

    #The scenarios below will need a payment service that accepts or rejects a request to add funds
    Scenario Outline: Payment service rejects request
      Given Danny has a DebitCard balance of <debitBalance>
      And Danny has <revolutBalance> euro in his euro Revolut account
      And Danny selects his DebitCard as his topUp method
      Then Danny tops up <transferAmount>
      Then The balance in his euro account should be <newRevolutBalance>
      Examples:
        | revolutBalance | transferAmount | debitBalance | newRevolutBalance |
        | 0              | 100            | 50           | 0                 |
        | 50             | 50             | 49           | 50                |
        | 12             | 1              | 0            | 12                |


    Scenario Outline: Payment service accepts the request
      Given Danny has a DebitCard balance of <debitBalance>
      And Danny has <revolutBalance> euro in his euro Revolut account
      And Danny selects his DebitCard as his topUp method
      When Danny tops up <transferAmount>
      Then The balance in his euro account should be <newRevolutBalance>
      Then The balance in the debit account should be <newDebitBalance>
      Examples:
        | revolutBalance | transferAmount | debitBalance | newRevolutBalance | newDebitBalance |
        | 0              | 100            | 150          | 100               | 50              |
        | 50             | 50             | 50           | 100               | 0               |
        | 12             | 1              | 10           | 13                | 9               |


      Scenario Outline: Splitting the bill with friends on Revolut successfully
        Given Danny has <DannyBalance> euro in his euro Revolut account
        And Jeff has <JeffBalance> euro in his euro account
        And Danny makes a payment of <bill>
        And Danny requests split bill with Jeff <requestedAmount>
        When Jeff transfers <requestedAmount> to Danny
        Then The balance in his euro account should be <newDannyBalance>
        Then The balance in Jeff euro account should now be <newJeffBalance>
        Examples:
          | JeffBalance | DannyBalance | requestedAmount | newJeffBalance | newDannyBalance | bill |
          | 150         | 200          | 50              | 100            | 150             | 100  |
          | 200         | 250          | 75              | 125            | 175             | 150  |
          | 250         | 300          | 100             | 150            | 200             | 200  |


    Scenario Outline: Splitting the bill with friends on Revolut rejected
      Given Danny has <DannyBalance> euro in his euro Revolut account
      And Jeff has <JeffBalance> euro in his euro account
      And Danny makes a payment of <bill>
      And Danny requests split bill with Jeff <requestedAmount>
      When Jeff transfers <requestedAmount> to Danny
      Then The balance in his euro account should be <newDannyBalance>
      Then The balance in Jeff euro account should now be <newJeffBalance>
      Examples:
        | JeffBalance | DannyBalance | requestedAmount | newJeffBalance | newDannyBalance | bill |
        | 150         | 200          | 0               | 150            | 200             | 250  |
        | 50          | 250          | 75              | 50             | 100             | 150  |