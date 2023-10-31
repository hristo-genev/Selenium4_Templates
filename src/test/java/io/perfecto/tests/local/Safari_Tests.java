package io.perfecto.tests.local;

import io.perfecto.tests.scenarios.Chanel;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Safari_Tests {

  private SafariDriver driver;

  @BeforeClass
  public void setUp() throws Exception {

    SafariOptions browserOptions = new SafariOptions();

    browserOptions.setPlatformName("MAC");
//    browserOptions.setBrowserVersion("17");

    driver = new SafariDriver(browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);
  }

  @AfterClass
  public void tearDown() {
    try {
      if (driver != null)
        driver.quit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void safariTest1() throws Exception {
    Chanel.addProductToCart(driver);
  }
}
