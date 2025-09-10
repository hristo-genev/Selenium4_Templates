package io.perfecto.tests.local;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;

public class Chrome_CRX_Extension {

  private WebDriver driver;

  @BeforeClass
  public void setUpDriver() throws Exception {
    System.setProperty("webdriver.chrome.driver", "/Users/hgenev/appium/drivers/chromedrivers/chromedriver-138.0.7204.94");

    var browserOptions = new ChromeOptions();
//    browserOptions.addExtensions(new File("/Users/hgenev/Downloads/modheader.crx"));
//    browserOptions.addArguments("--disable-features=DisableLoadExtensionCommandLineSwitch");
    browserOptions.setBinary("/Users/hgenev/appium/drivers/chromedrivers/chromedriver-138.0.7204.94");
//    browserOptions.addArguments("--disable-blink-features=AutomationControlled");
//    browserOptions.addArguments("--remote-allow-origins=*");
    browserOptions.addArguments("--log-path=/Users/hgenev/chromedriver.log");
    browserOptions.addArguments("--log-level=DEBUG");
//    driver = new ChromeDriver(browserOptions);
    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"), browserOptions);

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
    driver.get("https://perforce.com");

    Thread.sleep(3000);
  }
}
