package io.perfecto.tests.remote.firefox;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.perfecto.tests.scenarios.Chanel;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Firefox_122_Tests {

  private RemoteWebDriver driver;
  private ReportiumClient reportiumClient;

  @BeforeClass
  public void setUpDriver() throws Exception {

    String host = "citizens.perfectomobile.com";
    FirefoxOptions browserOptions = new FirefoxOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("122");
    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("captureHAR", true);
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

    PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withProject(new Project("Perfecto.Support Web tests", "1.0"))
        .withJob(new Job("Perfecto.Support Web tests", 1))
        .withWebDriver(driver)
        .build();

    reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);


    System.out.println(driver);
    System.out.println(browserOptions);
  }


  @AfterMethod
  public void testEnded(ITestResult result) throws URISyntaxException, IOException {
    if (result.getStatus() == ITestResult.FAILURE) {
      reportiumClient.testStop(TestResultFactory.createFailure(result.getThrowable()));
    }
    else {
      reportiumClient.testStop(TestResultFactory.createSuccess());
    }
    var reportUrl = reportiumClient.getReportUrl();
    if (reportUrl != null)
      java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
  }

  @AfterClass
  public void tearDown() {
    if (driver != null)
      driver.quit();
  }

  @Test
  public void test1() throws Exception {
    Chanel.addProductToCart((RemoteWebDriver) driver);
  }
}

