Feature: Basic UCITs 5-10-40 Compliance
  Background: 
    Given system is clean
    And basic reference data

  Scenario: run an empty portfolio
    Given an existing portfolio for affiliate "LONDON" and partyCode "CITI" and currency "USD" like
      | assetCode | assetName | quantity | priceInPortfolioCcy | valueInPortfolioCcy |
    When I run basic UCITS validation in valueholder "val1" 
    Then the UCITS validation lines for "val1" look like
      | assetCode | allocationWeight |

  Scenario: run single line portfolio
    Given an existing portfolio for affiliate "LONDON" and partyCode "CITI" and currency "USD" like
      | assetCode | assetName | quantity | priceInPortfolioCcy | valueInPortfolioCcy |
      | code-1    | name-1    | 100      | 10                  | 1000                |
    When I run basic UCITS validation in valueholder "val1" 
    Then the UCITS validation lines for "val1" look like
      | assetCode | assetName | allocationWeight |
      | code-1    | name-1    | 1                |
