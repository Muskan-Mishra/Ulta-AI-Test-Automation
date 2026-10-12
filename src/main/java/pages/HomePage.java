package pages;

import org.openqa.selenium.*;
import utils.*;

public class HomePage {
    private final WebDriver driver;
    private final By searchInput=By.cssSelector("input[placeholder*='Search'], input[aria-label*='Search'], input[type='search']");
    public HomePage(WebDriver d){driver=d;}
    public void open(){driver.get(ConfigReader.baseUrl()); handlePopups();}
    public boolean isLoaded(){ return driver.getTitle().toLowerCase().contains("ulta") || driver.getCurrentUrl().contains("ulta.com"); }
    public boolean searchVisible(){ try{return WaitUtils.visible(driver,searchInput).isDisplayed();}catch(Exception e){return false;} }
    public boolean searchEnabled(){ try{return WaitUtils.visible(driver,searchInput).isEnabled();}catch(Exception e){return false;} }
    public void search(String text){
        handlePopups(); WebElement input=WaitUtils.visible(driver,searchInput); input.click(); input.clear(); input.sendKeys(text); input.sendKeys(Keys.ENTER);
        try{WaitUtils.wait(driver).until(d->d.getCurrentUrl().contains("search") || d.findElements(By.cssSelector("a[href*='/p/']")).size()>0);}catch(Exception ignored){}
    }
    public void handlePopups(){
        String[] labels={"Accept","Accept All","I Accept","No Thanks","Close","Dismiss"};
        for(String label:labels){
            try{By b=By.xpath("//button[normalize-space()='"+label+"'] | //button[contains(@aria-label,'"+label+"')]"); if(driver.findElements(b).size()>0){driver.findElements(b).get(0).click();}}catch(Exception ignored){}
        }
    }
}
