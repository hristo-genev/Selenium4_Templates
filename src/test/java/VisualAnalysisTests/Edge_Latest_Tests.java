package VisualAnalysisTests;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Edge_Latest_Tests {

  private RemoteWebDriver driver;
  private Report report;

  @BeforeClass
  public void setUpDriver() throws Exception {
    String host = "demo.perfectomobile.com";
    EdgeOptions browserOptions = new EdgeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

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
  public void authenticate() throws Exception {
    AuthenticationPopup.authenticateBlink(driver);
  }
}
