package steps;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.*;
import ai.RecommendationValidator;
import utils.DriverFactory;
import java.util.*;

public class UltaSteps {
    private final HomePage home=new HomePage(DriverFactory.get());
    private final SearchPage search=new SearchPage(DriverFactory.get());
    private final ProductPage product=new ProductPage(DriverFactory.get());
    private final CartPage cart=new CartPage(DriverFactory.get());
    private final List<String> collected=new ArrayList<>();

    @Given("the user opens the Ulta website") public void open(){home.open();}
    @Then("the Ulta landing page should be displayed") public void landing(){Assert.assertTrue(home.isLoaded(),"Ulta landing page was not loaded");}
    @Then("the search field should be visible and enabled") public void searchField(){Assert.assertTrue(home.searchVisible());Assert.assertTrue(home.searchEnabled());}
    @When("the user searches for {string}") public void search(String p){home.search(p);}
    @Then("relevant search results should be displayed for {string}") public void results(String p){Assert.assertTrue(search.hasResults(p),"No relevant results found for "+p);}
    @When("the user opens the first available product") public void openProduct(){search.openFirstProduct();Assert.assertTrue(product.isLoaded());}
    @When("the user adds the product to the bag") public void add(){product.addToBag();}
    @When("the user adds the first available product to the bag") public void addFirst(){search.openFirstProduct();product.addToBag();}
    @Then("the cart should contain at least {int} item") public void minItem(int n){cart.open();Assert.assertTrue(cart.productCount()>=n,"Cart contains fewer than "+n+" items");}
    @Then("the cart should contain at least {int} products") public void minProducts(int n){Assert.assertTrue(cart.productCount()>=n,"Cart contains fewer than "+n+" products");}
    @Then("each expected product category should be represented in the cart") public void categories(){String body=DriverFactory.get().findElement(org.openqa.selenium.By.tagName("body")).getText().toLowerCase();Assert.assertTrue(body.contains("moistur")||body.contains("cream"));Assert.assertTrue(body.contains("mascara")||body.contains("lash"));Assert.assertTrue(body.contains("vitamin")||body.contains("serum"));}
    @Then("product page recommendations should meet the 30 percent relevance threshold for {string}") public void productRec(String context){List<String> r=product.collectRecommendations();Assert.assertFalse(r.isEmpty(),"Recommendation section not found");RecommendationValidator.assertThreshold(r,.30,context);}
    @Then("cart recommendations should meet the 25 percent relevance threshold for {string}") public void cartRec(String contexts){List<String> r=cart.collectRecommendations();Assert.assertFalse(r.isEmpty(),"Cart recommendation section not found");RecommendationValidator.assertThreshold(r,.25,contexts.split(","));}
    @When("the user collects product page recommendations for {string}") public void collect(String context){collected.addAll(product.collectRecommendations());}
    @Then("the collected recommendations should meet the 20 percent beauty relevance threshold") public void e2e(){Assert.assertFalse(collected.isEmpty(),"No recommendations were collected");RecommendationValidator.assertThreshold(collected,.20,"beauty","skin","makeup");}
    @When("the user opens the cart") public void openCart(){cart.open();}
}
