package io.perfecto.tests.remote.chrome;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.perfecto.tests.scenarios.DuckDuckGo;
import io.perfecto.tests.scenarios.Leumiqa;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Chrome_Any_Version {

  private RemoteWebDriver driver;
  private ReportiumClient reportiumClient;
  private final static Logger log = LoggerFactory.getLogger("");

  @BeforeClass
  public void setUpDriver() throws Exception {
    String host = "demo.perfectomobile.com";
    ChromeOptions browserOptions = new ChromeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("118");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "11");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("browserVersion", "118");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    ClientConfig config = ClientConfig.defaultConfig()
        .connectionTimeout(Duration.ofMinutes(5))
        .readTimeout(Duration.ofMinutes(4));

    log.info("Starting driver initialization");

    driver = (RemoteWebDriver) RemoteWebDriver.builder()
        .config(config)
        .address("https://" + host + "/nexperience/perfectomobile/wd/hub/")
        .oneOf(browserOptions)
        .build();

    //    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

    log.info("Finished driver initialization");

    log.info("Starting initialization of Reportium client");

    PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withProject(new Project("Perfecto.Support Web tests", "1.0"))
        .withJob(new Job("Perfecto.Support Web tests", 1))
        .withWebDriver(driver)
        .build();

    reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

    log.info("Finished initialization of Reportium client");
    log.info(driver.toString());
    log.info(browserOptions.toString());
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
    if (reportUrl != null) {
      log.info("Report URL: " + reportUrl);
      java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
    }
  }


  @AfterClass
  public void tearDown() {
      if (driver != null)
      {
        log.info("Starting driver quit command!");
        driver.quit();
        log.info("Finished driver quit command");
      }
  }

  @Test
  public void authenticateWithVisualAnalysis() throws Exception {
    reportiumClient.testStart("Handle basic authentication popup", new TestContext());
    AuthenticationPopup.authenticateBlink(driver, reportiumClient);
  }


  @Test
  public void searchInDuckDuckGo() throws Exception {
    reportiumClient.testStart("Search in DuckDuckGo", new TestContext());
    DuckDuckGo.searchForPerfecto(driver, reportiumClient);
  }
  @Test
  public void leumiTest() throws Exception {
    reportiumClient.testStart("Leumi", new TestContext());
    Leumiqa.leumiMorgageRequest(driver, reportiumClient);
  }
}
