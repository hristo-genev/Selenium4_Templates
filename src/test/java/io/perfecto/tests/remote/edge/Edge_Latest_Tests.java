package io.perfecto.tests.remote.edge;

import io.perfecto.PerfectoTokenProvider;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
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
    String host = "demo";
    EdgeOptions browserOptions = new EdgeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("beta");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("browserName", "Edge");
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), browserOptions);

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
  public void goToPerfectoWebsite() throws Exception {
    Report report = new ReportBuilder(driver)
          .withReportName("Edge open website")
          .build();

    report.startTest();
    driver.get("https://perfecto.io/");

    Thread.sleep(4000);

    report.endTest();
    report.open();
  }
}
