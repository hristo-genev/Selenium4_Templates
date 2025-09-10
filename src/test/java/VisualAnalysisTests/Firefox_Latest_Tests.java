package VisualAnalysisTests;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Firefox_Latest_Tests {

  private RemoteWebDriver driver;
  private Report report;

  @BeforeClass
  public void setUpDriver() throws Exception {
    String host = "jpmc";
    FirefoxOptions browserOptions = new FirefoxOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "11");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);
  }

  @AfterClass
  public void tearDown() {
    try {
      driver.quit();
      } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void authenticate() throws Exception {
    AuthenticationPopup.authenticateGecko(driver);
  }
}
