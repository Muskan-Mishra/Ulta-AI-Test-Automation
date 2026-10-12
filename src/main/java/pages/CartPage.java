package pages;

import org.openqa.selenium.*;
import utils.*;
import java.util.*;

public class CartPage {
    private final WebDriver driver;
    private final By cartLink=By.xpath("//a[contains(@href,'cart') or contains(@aria-label,'Cart')] | //button[contains(@aria-label,'Cart')]");
    private final By productLinks=By.cssSelector("a[href*='/p/']");
    public CartPage(WebDriver d){driver=d;}
    public void open(){
        List<WebElement> els=driver.findElements(cartLink);
        if(!els.isEmpty()){((JavascriptExecutor)driver).executeScript("arguments[0].click();",els.get(0));}
        else driver.get("https://www.ulta.com/bag");
        WaitUtils.wait(driver).until(d->d.getCurrentUrl().contains("bag")||d.getCurrentUrl().contains("cart"));
    }
    public int productCount(){
        String text=driver.findElement(By.tagName("body")).getText();
        java.util.regex.Matcher m=java.util.regex.Pattern.compile("(?i)(\\d+)\\s*(?:items?|products?)").matcher(text); int max=0; while(m.find())max=Math.max(max,Integer.parseInt(m.group(1)));
        return max>0?max:driver.findElements(productLinks).size();
    }
    public List<String> collectRecommendations(){
        return (List<String>)((JavascriptExecutor)driver).executeScript(""+
          "const hs=[...document.querySelectorAll('h2,h3,h4,[role=heading]')];"+
          "const wanted=['you may also like','recommended for you','we think you\\'ll like','complete your look'];"+
          "let out=[]; for(const h of hs){if(wanted.some(w=>h.innerText.toLowerCase().includes(w))){let r=h.parentElement;for(let i=0;i<5&&r;i++,r=r.parentElement){let a=[...r.querySelectorAll('a[href*=\\\"/p/\\\"]')];if(a.length){out.push(...a.map(x=>x.innerText.trim()).filter(Boolean).slice(0,20));break;}}}} return [...new Set(out)].slice(0,30);"
        );
    }
}
