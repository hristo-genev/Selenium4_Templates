package io.perfecto.tests.local;

import io.perfecto.tests.scenarios.Chanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chrome {

  private WebDriver driver;

  @BeforeClass
  public void setUpDriver() throws Exception {
//    System.setProperty("webdriver.chrome.driver", "/home/user/chromedriver");

    ChromeOptions browserOptions = new ChromeOptions();
//    browserOptions.addArguments("--disable-blink-features=AutomationControlled");
    browserOptions.addArguments("--remote-allow-origins=*");

    driver = new ChromeDriver(browserOptions);

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
