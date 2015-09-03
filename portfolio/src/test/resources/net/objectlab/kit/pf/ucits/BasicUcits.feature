Feature: Basic UCITs 5-10-40 Compliance

Scenario: run an empty portfolio
Given an existing portfolio for affiliate "LONDON" and partyCode "CITI" like
	| assetCode | assetName | quantity | price | value |
And usual reference data
Then the UCITS validation lines look like
	| assetCode | allocationWeight | 
	