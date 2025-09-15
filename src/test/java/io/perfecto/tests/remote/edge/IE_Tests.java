package io.perfecto.tests.remote.edge;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.edge.EdgeOptions;
import io.perfecto.PerfectoTokenProvider;
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
    perfectoOptions.put("takesScreenshot", true);
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));
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
  public void testIEPage() throws Exception {

    driver.get("https://the-internet.herokuapp.com/login");
    Thread.sleep(3000);
    driver.manage().window().maximize();
    Thread.sleep(1000);

    var element = driver.findElement(By.cssSelector("#username"));
    element.click();
    element.sendKeys("tomsmith");

    driver.findElement(By.cssSelector("#password"))
        .sendKeys("SuperSecretPassword!");

    driver.findElement(By.xpath("(//button)[1]"))
            .click();

    Thread.sleep(5000);
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
  public void badSSlTest() throws Exception {

    driver.get("https://untrusted-root.badssl.com/");
//    driver.manage().window().maximize();
    Thread.sleep(1000);

    driver.findElement(By.cssSelector("#infoBlockIDImage"))
        .click();

    driver.findElement(By.cssSelector("#overridelink"))
        .click();

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
