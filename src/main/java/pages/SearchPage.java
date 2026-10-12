package pages;

import org.openqa.selenium.*;
import utils.*;
import java.util.*;

public class SearchPage {
    private final WebDriver driver;
    private final By productLinks=By.cssSelector("a[href*='/p/']");
    public SearchPage(WebDriver d){driver=d;}
    public boolean hasResults(String keyword){
        WaitUtils.wait(driver).until(d->d.findElements(productLinks).size()>0);
        String body=driver.findElement(By.tagName("body")).getText().toLowerCase();
        return body.contains(keyword.toLowerCase()) || driver.findElements(productLinks).size()>0;
    }
    public void openFirstProduct(){
        List<WebElement> links=driver.findElements(productLinks);
        for(WebElement l:links){
            try{String href=l.getAttribute("href"); String txt=l.getText(); if(href!=null && href.contains("/p/") && txt!=null && !txt.isBlank()){ ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});",l); ((JavascriptExecutor)driver).executeScript("arguments[0].click();",l); WaitUtils.wait(driver).until(d->d.getCurrentUrl().contains("/p/")); return;}}catch(Exception ignored){}
        }
        throw new AssertionError("No product link was found");
    }
}
