package pages;

import org.openqa.selenium.*;
import utils.*;
import java.util.*;

public class ProductPage {
    private final WebDriver driver;
    private final By addToBag=By.xpath("//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to bag') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]");
    public ProductPage(WebDriver d){driver=d;}
    public boolean isLoaded(){return driver.getCurrentUrl().contains("/p/");}
    public void addToBag(){
        WaitUtils.wait(driver).until(d->d.findElements(addToBag).size()>0);
        List<WebElement> buttons=driver.findElements(addToBag);
        for(WebElement b:buttons){try{if(b.isDisplayed()&&b.isEnabled()){((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});",b); ((JavascriptExecutor)driver).executeScript("arguments[0].click();",b); return;}}catch(Exception ignored){}}
        throw new AssertionError("Add to Bag button was not available");
    }
    public List<String> collectRecommendations(){
        List<String> result=(List<String>)((JavascriptExecutor)driver).executeScript(""+
            "const heads=[...document.querySelectorAll('h2,h3,h4,[role=heading]')];"+
            "const wanted=['you may also like','similar products','we think you\\'ll like','recommended for you','you might also like'];"+
            "let h=heads.find(x=>wanted.some(w=>x.innerText.toLowerCase().includes(w)));"+
            "if(!h)return []; let root=h.parentElement; for(let i=0;i<5&&root;i++,root=root.parentElement){let a=[...root.querySelectorAll('a[href*=\\\"/p/\\\"]')]; if(a.length>=2)return a.map(x=>x.innerText.trim()).filter(Boolean).slice(0,20);} return [];"
        );
        return result==null?List.of():result;
    }
}
