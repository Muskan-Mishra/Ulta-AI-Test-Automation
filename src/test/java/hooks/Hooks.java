package hooks;

import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import utils.*;

public class Hooks {
    @Before
    public void before(){DriverFactory.start();}
    @AfterStep
    public void afterStep(Scenario scenario){ if(scenario.isFailed()){byte[] b=ScreenshotUtils.take(DriverFactory.get(),"failure"); scenario.attach(b,"image/png","failure"); Allure.addAttachment("Failure screenshot","image/png",new java.io.ByteArrayInputStream(b),".png");}}
    @After
    public void after(){DriverFactory.stop();}
}
