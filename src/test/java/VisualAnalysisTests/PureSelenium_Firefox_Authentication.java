package VisualAnalysisTests;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PureSelenium_Firefox_Authentication {

  public static void main(String[] args) throws Exception {
    String host = "demo";
    FirefoxOptions browserOptions = new FirefoxOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);

    PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
        .withWebDriver(driver)
        .build();
    ReportiumClient reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    reportiumClient.testStart("Firefox Basic Authentication Popup", new TestContext.Builder().build());

    driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    try {
      driver.get("https://the-internet.herokuapp.com/basic_auth");
    } catch (Exception ex) {}

//    Thread.sleep(3000);
    Map<String, Object> params = new HashMap<>();

    params.put("label", "Password");
    params.put("label.offset", "30");
    params.put("label.direction", "below");
    driver.executeScript("mobile:button-text:set", params);

    params.clear();
    params.put("label", "Password");
    params.put("label.direction", "above");
    params.put("label.offset", "5");
    driver.executeScript("mobile:edit-text:click",params);

    params.clear();
    params.put("label", "Password");
    params.put("text", "admin");
    params.put("label.direction", "above");
    driver.executeScript("mobile:edit-text:set",params);

    params.clear();
    params.put("label", "Sign in");
    params.put("index", "2");
    driver.executeScript("mobile:button-text:click", params);

    Thread.sleep(3000);

    params.clear();
    params.put("content", "Congratulations");
    String res = (String)driver.executeScript("mobile:checkpoint:text", params);

    if (res.equalsIgnoreCase("true")) {
      reportiumClient.testStop(TestResultFactory.createSuccess());
    }
    else {
      reportiumClient.testStop(TestResultFactory.createFailure("Login failed"));
    }
    try {
      String reportUrl = ((String)driver.getCapabilities().getCapability("testGridReportUrl"));
      driver.quit();
      if (reportUrl != null)
        java.awt.Desktop.getDesktop().browse(new URI(reportUrl));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
