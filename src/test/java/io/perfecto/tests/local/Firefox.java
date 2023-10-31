package io.perfecto.tests.local;

import io.perfecto.tests.scenarios.Chanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Firefox {

  private WebDriver driver;

  @BeforeClass
  public void setUpDriver() throws Exception {
//    System.setProperty("webdriver.gecko.driver", "/home/user/geckdriver");

    FirefoxOptions browserOptions = new FirefoxOptions();
    driver = new FirefoxDriver(browserOptions);

    System.out.println(driver);
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
  public void localChromeTest() throws Exception {
    Chanel.addProductToCart((RemoteWebDriver) driver);
  }
}
