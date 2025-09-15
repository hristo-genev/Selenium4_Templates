package VisualAnalysisTests;

import io.perfecto.PerfectoTokenProvider;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import io.perfecto.utilities.visualanalysis.TextCheckpoint;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LanguageTest {
  @Test
  public static void textCheckPoint() throws Exception {

    String host = "demo.perfectomobile.com";
    ChromeOptions browserOptions = new ChromeOptions();

    browserOptions.setPlatformName("Windows");
    browserOptions.setBrowserVersion("latest");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "10");
    perfectoOptions.put("location", "US East");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub/fast"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);

    Report report = null;

    try {
      report = new ReportBuilder(driver)
          .withReportName("Language test")
          .build();

      report.startTest();
      driver.get("http://psy-supporter.or.kr/online_new/inquiry.asp");
      driver.manage().window().maximize();

      Thread.sleep(3000);

      new TextCheckpoint(driver)
          .validateTextIsOnScreen("온라인신청");

      report.endTest();
    } catch (Exception e) {
      e.printStackTrace();
      report.endTestWithFailureMessage(e.getMessage());
    }
    report.open();
  }
}
