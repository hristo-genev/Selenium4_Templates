package io.perfecto.tests.remote.safari;

import io.perfecto.PerfectoTokenProvider;
import io.perfecto.tests.scenarios.Chanel;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.useractions.UserActions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Safari {

  private RemoteWebDriver driver;
  private Report report;
  private UserActions ua;

  @BeforeTest
  public void setUpDriver() throws Exception {
    String host = "demo";
    SafariOptions browserOptions = new SafariOptions();

    browserOptions.setPlatformName("Mac");
    browserOptions.setBrowserVersion("16");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "macOS Ventura");
    perfectoOptions.put("location", "NA-US-BOS");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void test1() throws Exception {
    Chanel.addProductToCart(driver);
  }
}
