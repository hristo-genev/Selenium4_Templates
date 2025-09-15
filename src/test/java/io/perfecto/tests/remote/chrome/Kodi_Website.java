package io.perfecto.tests.remote.chrome;

import io.perfecto.PerfectoTokenProvider;
import io.perfecto.tests.scenarios.Kodi;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Kodi_Website {

  private RemoteWebDriver driver;
  private WebDriverWait wait;
  private Report report;

  @BeforeClass
  public void setUpDriver() throws Exception {
    String host = "demo.perfectomobile.com";
    ChromeOptions browserOptions = new ChromeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

    report = new ReportBuilder(driver)
        .withReportName("Kodi Website Tests")
        .build();

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
  }

  @AfterClass
  public void tearDown() {
    try {
      if (driver != null)
        driver.quit();

      if (report != null)
        report.open();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void websiteIsOnline() throws Exception {
    report.startTest("Is website online?");
    Kodi.checkIfWebsiteIsOnline(driver);
    report.endTest();
  }

  @Test
  public void isLoginWorking() throws Exception {
    report.startTest("Is login working?");
    Kodi.checkIfLoginWorks(driver);
    report.endTest();
  }
}
