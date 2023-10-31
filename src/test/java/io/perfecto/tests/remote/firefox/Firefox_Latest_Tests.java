package io.perfecto.tests.remote.firefox;

    import io.perfecto.tests.scenarios.Chanel;
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

    String host = "demo.perfectomobile.com";
    FirefoxOptions browserOptions = new FirefoxOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");
    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("captureHAR", true);
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
      {
        String reportUrl = ((String)driver.getCapabilities().getCapability("testGridReportUrl"));
        driver.quit();
        if (reportUrl != null)
        {
          System.out.println(reportUrl);
          java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test1() throws Exception {
    Chanel.addProductToCart((RemoteWebDriver) driver);
  }
}

