package io.perfecto.tests.remote.chrome;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResult;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.perfecto.utilities.tokensprovider.PerfectoTokenProvider;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class Chrome {

  private RemoteWebDriver driver;
  private ReportiumClient reportiumClient;
  private WebDriverWait wait;

  @BeforeClass
  public void setUpDriver() throws Exception {

    String host = "citi.perfectomobile.com";
    ChromeOptions browserOptions = new ChromeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");
//    browserOptions.addArguments("--disable-blink-features=AutomationControlled");
//    browserOptions.addArguments("--remote-allow-origins=*");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
//    perfectoOptions.put("location", "US East");
    perfectoOptions.put("location", "EU Frankfurt");
//    perfectoOptions.put("location", "AP Sydney");
    perfectoOptions.put("resolution", "1920x1080");
//    perfectoOptions.put("pageLoadStrategy", "normal");
//    perfectoOptions.put("seleniumVersion", "3.8.1");
//    perfectoOptions.put("captureHAR", true);
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), browserOptions);

    PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withProject(new Project("Perfecto.Support Web tests", "1.0"))
        .withJob(new Job("Perfecto.Support Web tests", 1))
        .withWebDriver(driver)
        .build();

    reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

    wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
      String reportUrl = reportiumClient.getReportUrl();
      driver.quit();
      System.out.println(reportUrl);
      java.awt.Desktop.getDesktop().browse(new URI(reportUrl));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test1() throws Exception {
    reportiumClient.testStart("Web Test", new TestContext());

    driver.get("https://ifconfig.co");
    driver.get("https://perforce.com");

    Thread.sleep(3000);
  }
}
