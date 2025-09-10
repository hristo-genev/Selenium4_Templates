package io.perfecto.tests.local;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;

public class Edge {

  private RemoteWebDriver driver;

  @BeforeClass
  public void setUpDriver() throws Exception {
//    System.setProperty("webdriver.chrome.driver", "/Users/hgenev/appium/drivers/msedge/chromedriver-126.0.6478.55");
    
    var browserOptions = new EdgeOptions();

//    browserOptions.setBinary("/Users/hgenev/appium/drivers/msedge/msedgedriver-137.0.3296.68");
//    browserOptions.setPlatformName("Windows");
//    browserOptions.setCapability("browserName", "Edge");
//    browserOptions.ignoreZoomSettings();
//    browserOptions.attachToEdgeChrome();

    driver = new RemoteWebDriver(new URL("http://192.168.1.13:4444"), browserOptions);

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
    driver.getScreenshotAs(OutputType.FILE);
    Thread.sleep(5000);
  }
}
