Feature: Ulta search, cart and recommendation validation

  Background:
    Given the user opens the Ulta website

  @AC1 @smoke
  Scenario: AC1 - Verify Ulta landing page
    Then the Ulta landing page should be displayed
    And the search field should be visible and enabled

  @AC2 @search
  Scenario Outline: AC2 - Search for a beauty product
    When the user searches for "<product>"
    Then relevant search results should be displayed for "<product>"

    Examples:
      | product       |
      | moisturizer   |
      | mascara       |
      | vitamin c     |

  @AC3 @cart
  Scenario: AC3 - Add a product to cart
    When the user searches for "moisturizer"
    And the user opens the first available product
    And the user adds the product to the bag
    Then the cart should contain at least 1 item

  @AC4 @cart
  Scenario: AC4 - Verify cart contents
    When the user searches for "moisturizer"
    And the user adds the first available product to the bag
    And the user searches for "mascara"
    And the user adds the first available product to the bag
    And the user searches for "vitamin c"
    And the user adds the first available product to the bag
    When the user opens the cart
    Then the cart should contain at least 3 products
    And each expected product category should be represented in the cart

  @AC5 @recommendations
  Scenario: AC5 - Validate product page recommendations
    When the user searches for "moisturizer"
    And the user opens the first available product
    Then product page recommendations should meet the 30 percent relevance threshold for "moisturizer"

  @AC6 @recommendations
  Scenario: AC6 - Validate cart page recommendations
    When the user searches for "moisturizer"
    And the user adds the first available product to the bag
    And the user searches for "mascara"
    And the user adds the first available product to the bag
    And the user opens the cart
    Then cart recommendations should meet the 25 percent relevance threshold for "moisturizer,mascara"

  @AC7 @e2e
  Scenario: AC7 - Validate end to end recommendation consistency
    When the user searches for "moisturizer"
    And the user opens the first available product
    And the user collects product page recommendations for "moisturizer"
    And the user searches for "mascara"
    And the user opens the first available product
    And the user collects product page recommendations for "mascara"
    Then the collected recommendations should meet the 20 percent beauty relevance threshold
