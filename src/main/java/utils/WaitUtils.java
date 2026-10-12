package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public final class WaitUtils {
    private WaitUtils(){}
    public static WebDriverWait wait(WebDriver d){ return new WebDriverWait(d, Duration.ofSeconds(ConfigReader.timeout())); }
    public static WebElement visible(WebDriver d, By by){ return wait(d).until(ExpectedConditions.visibilityOfElementLocated(by)); }
    public static WebElement clickable(WebDriver d, By by){ return wait(d).until(ExpectedConditions.elementToBeClickable(by)); }
    public static boolean urlContains(WebDriver d,String s){ return wait(d).until(ExpectedConditions.urlContains(s)); }
    public static void safeClick(WebDriver d, By by){
        try{ clickable(d,by).click(); } catch(Exception e){
            WebElement el=visible(d,by); ((JavascriptExecutor)d).executeScript("arguments[0].scrollIntoView({block:'center'});",el); ((JavascriptExecutor)d).executeScript("arguments[0].click();",el);
        }
    }
}
