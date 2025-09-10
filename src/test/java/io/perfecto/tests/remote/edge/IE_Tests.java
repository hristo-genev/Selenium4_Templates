package io.perfecto.tests.remote.edge;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class IE_Tests {

  private RemoteWebDriver driver;
  private ReportiumClient reportiumClient;

  @BeforeClass
  public void setUpDriver() throws Exception {
    var host = "btdigital";

    var browserOptions = new EdgeOptions();
    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");
    browserOptions.setCapability("browserName", "Edge");
//    browserOptions.ignoreZoomSettings();
//    browserOptions.attachToEdgeChrome();

    var perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "11");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));
    perfectoOptions.put("ie-mode", true);

//    browserOptions.setCapability("perfecto:ie-mode", true);
    browserOptions.setCapability("perfecto:options", perfectoOptions);
    browserOptions.setCapability("se:ieOptions", new HashMap<String, Object>() {
      {
        put("ignoreProtectedModeSettings", true);
        put("ignoreZoomSetting", true);
        put("ignoreCertificateErrors", true);
      }
    });

    browserOptions.addArguments("--ignore-certificate-errors", "--allow-insecure-localhost");

    driver = new RemoteWebDriver(new URL("https://" + host + ".hub.perfectomobile.com/nexperience/perfectomobile/wd/hub"), browserOptions);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

    var perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withProject(new Project("Perfecto.Support Web tests", "1.0"))
        .withJob(new Job("Perfecto.Support Web tests", 1))
        .withWebDriver(driver)
        .build();
    reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

    System.out.println(driver);
    System.out.println(browserOptions);
  }
  @AfterMethod
  public void testEnded(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
      reportiumClient.testStop(TestResultFactory.createFailure(result.getThrowable()));
    }
    else {
      reportiumClient.testStop(TestResultFactory.createSuccess());
    }
  }

  @AfterClass
  public void tearDown() {
    try {
      var reportUrl = reportiumClient.getReportUrl();
      driver.quit();
      System.out.println(reportUrl);
      java.awt.Desktop.getDesktop().browse(new URI(reportUrl));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void webTest1() throws Exception {

    driver.get("https://www.imperial.ac.uk/admin-services/ict/self-service/computers-printing/windows-10/ie-mode/test-page/");
//    driver.manage().window().maximize();
    Thread.sleep(1000);
    driver.getScreenshotAs(OutputType.FILE);
  }
  
  @Test
  public void webTest2() throws Exception {
    driver.get("https://duckduckgo.com/");
    Thread.sleep(2000);
    driver.get("https://www.bt.com/");
//    driver.manage().window().maximize();
    Thread.sleep(2000);
    driver.getScreenshotAs(OutputType.FILE);
  }

  @Test
  public void webTest3() throws Exception {

    driver.get("https://www.duckduckgo.com/");
    driver.manage().window().maximize();
    Thread.sleep(1000);
    driver.getScreenshotAs(OutputType.FILE);
  }
}
