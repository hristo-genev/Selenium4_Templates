package VisualAnalysisTests;

import VisualAnalysisTests.scenarios.AuthenticationPopup;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import io.perfecto.PerfectoTokenProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Chrome_Latest_Tests {

  private RemoteWebDriver driver;
  private ReportiumClient reportiumClient;

  @BeforeClass
  public void setUpDriver() throws Exception {
    System.setProperty("otel.sdk.disabled", "true");
    String host = "ulta";
    ChromeOptions browserOptions = new ChromeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), browserOptions);

    PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withProject(new Project("Perfecto.Support Web tests", "1.0"))
        .withJob(new Job("Perfecto.Support Web tests", 1))
        .withWebDriver(driver)
        .build();
    reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

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
          java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void authenticate() throws Exception {
    AuthenticationPopup.authenticateBlink(driver, reportiumClient);
  }
}
