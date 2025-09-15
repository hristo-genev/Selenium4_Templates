package io.perfecto.tests.local;

import io.perfecto.tests.scenarios.Chanel;
import io.perfecto.tests.scenarios.Kodi;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Kodi_Website {

  private RemoteWebDriver driver;
  private WebDriverWait wait;

  @BeforeClass
  public void setUpDriver() throws Exception {
    System.setProperty("webdriver.chrome.driver", "/Users/hgenev/appium/drivers/chromedriver");

    ChromeOptions browserOptions = new ChromeOptions();
    driver = new ChromeDriver(browserOptions);
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    System.out.println(driver);

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
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
  public void websiteIsOnline() throws Exception {
    Kodi.checkIfWebsiteIsOnline(driver);
  }


  @Test
  public void isLoginWorking() throws Exception {
    Kodi.checkIfLoginWorks(driver);
  }
}
