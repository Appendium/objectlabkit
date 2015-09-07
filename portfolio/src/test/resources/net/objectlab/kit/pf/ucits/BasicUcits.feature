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
      | assetCode | assetName | allocationWeight | valid |
      | code-1    | name-1    | 1                | false |

  @current
  Scenario: run 20 lines valid portfolio
    Given an existing portfolio for affiliate "LONDON" and partyCode "CITI" and currency "USD" like
      | assetCode | assetName | quantity | priceInPortfolioCcy | valueInPortfolioCcy |
      | code-1    | name-1    | 100      | 10                  | 1000                |
      | code-2    | name-2    | 100      | 10                  | 1000                |
      | code-3    | name-3    | 100      | 10                  | 1000                |
      | code-4    | name-4    | 100      | 10                  | 1000                |
      | code-5    | name-5    | 100      | 10                  | 1000                |
      | code-6    | name-6    | 100      | 10                  | 1000                |
      | code-7    | name-7    | 100      | 10                  | 1000                |
      | code-8    | name-8    | 100      | 10                  | 1000                |
      | code-9    | name-9    | 100      | 10                  | 1000                |
      | code-10   | name-10   | 100      | 10                  | 1000                |
      | code-11   | name-11   | 100      | 10                  | 1000                |
      | code-12   | name-12   | 100      | 10                  | 1000                |
      | code-13   | name-13   | 100      | 10                  | 1000                |
      | code-14   | name-14   | 100      | 10                  | 1000                |
      | code-15   | name-15   | 100      | 10                  | 1000                |
      | code-16   | name-16   | 100      | 10                  | 1000                |
      | code-17   | name-17   | 100      | 10                  | 1000                |
      | code-18   | name-18   | 100      | 10                  | 1000                |
      | code-19   | name-19   | 100      | 10                  | 1000                |
      | code-20   | name-20   | 100      | 10                  | 1000                |
    When I run basic UCITS validation in valueholder "val1"
    Then the UCITS validation lines for "val1" look like
      | assetCode | assetName | allocationWeight | valid |
      | code-1    | name-1    | 0.05             | true  |
      | code-2    | name-2    | 0.05             | true  |
      | code-3    | name-3    | 0.05             | true  |
      | code-4    | name-4    | 0.05             | true  |
      | code-5    | name-5    | 0.05             | true  |
      | code-6    | name-6    | 0.05             | true  |
      | code-7    | name-7    | 0.05             | true  |
      | code-8    | name-8    | 0.05             | true  |
      | code-9    | name-9    | 0.05             | true  |
      | code-10   | name-10   | 0.05             | true  |
      | code-11   | name-11   | 0.05             | true  |
      | code-12   | name-12   | 0.05             | true  |
      | code-13   | name-13   | 0.05             | true  |
      | code-14   | name-14   | 0.05             | true  |
      | code-15   | name-15   | 0.05             | true  |
      | code-16   | name-16   | 0.05             | true  |
      | code-17   | name-17   | 0.05             | true  |
      | code-18   | name-18   | 0.05             | true  |
      | code-19   | name-19   | 0.05             | true  |
      | code-20   | name-20   | 0.05             | true  |
