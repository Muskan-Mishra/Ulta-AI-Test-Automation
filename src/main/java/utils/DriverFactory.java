package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private DriverFactory() {}
    public static void start(){
        String browser=ConfigReader.browser().toLowerCase();
        WebDriver driver;
        if(browser.equals("edge")){
            EdgeOptions o=new EdgeOptions(); if(ConfigReader.headless()) o.addArguments("--headless=new");
            o.addArguments("--start-maximized","--disable-notifications"); driver=new EdgeDriver(o);
        } else {
            ChromeOptions o=new ChromeOptions(); if(ConfigReader.headless()) o.addArguments("--headless=new");
            o.addArguments("--start-maximized","--disable-notifications","--disable-popup-blocking"); driver=new ChromeDriver(o);
        }
        DRIVER.set(driver); driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }
    public static WebDriver get(){ if(DRIVER.get()==null) start(); return DRIVER.get(); }
    public static void stop(){ if(DRIVER.get()!=null){ DRIVER.get().quit(); DRIVER.remove(); } }
}
