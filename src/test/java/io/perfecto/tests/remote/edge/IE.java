package io.perfecto.tests.remote.edge;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IE {

  private RemoteWebDriver driver;

  @BeforeClass
  public void setUpDriver() throws Exception {
    String host = "demo.perfectomobile.com";
    var browserOptions = new EdgeOptions();

//    browserOptions.setCapability("browserName", "Edge");
//    browserOptions.ignoreZoomSettings();
//    browserOptions.attachToEdgeChrome();
    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("ie-mode", true);

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);
  }

  @AfterClass
  public void tearDown() {
    try {
      if (driver != null)
      {
        driver.quit();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void webTest1() throws Exception {

    driver.get("https://www.imperial.ac.uk/admin-services/ict/self-service/computers-printing/windows-10/ie-mode/test-page/");
//    driver.getScreenshotAs(OutputType.FILE);
//    Thread.sleep(5000);
  }
}
