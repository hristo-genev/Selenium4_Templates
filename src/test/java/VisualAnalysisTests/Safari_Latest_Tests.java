package VisualAnalysisTests;

import io.perfecto.PerfectoTokenProvider;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.visualanalysis.EditTextSet;
import io.perfecto.utilities.visualanalysis.LabelDirection;
import io.perfecto.utilities.visualanalysis.TextButtonClick;
import io.perfecto.utilities.visualanalysis.TextCheckpoint;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Safari_Latest_Tests {

  private RemoteWebDriver driver;
  private Report report;

  @BeforeTest
  public void setUpDriver() throws Exception {
    String host = "demo";
    SafariOptions browserOptions = new SafariOptions();

    browserOptions.setPlatformName("Mac");
    browserOptions.setBrowserVersion("14");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "macOS Big Sur");
//    perfectoOptions.put("platformVersion", "macOS Ventura");
//    perfectoOptions.put("platformVersion", "macOS Monterey");
    perfectoOptions.put("location", "NA-US-BOS");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenProvider.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"), browserOptions);

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
  public void handleBasicAuthenticationPopupOnSafari() throws Exception {
    Report report = null;

    try {
//      report = new ReportBuilder(driver)
//          .withReportName("Handle basic authentication popup")
//          .build();

//      report.startTest();

      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
//      driver.manage().window().maximize();
      try {
//        driver.get("https://the-internet.herokuapp.com/basic_auth");
        driver.executeScript("window.location.href='https://the-internet.herokuapp.com/basic_auth'");
      } catch (Exception ex) {}
      //driver.manage().window().maximize();

      Thread.sleep(3000);

      new TextButtonClick(driver)
          .setLabel("User Нame")
          .setLabelDirection(LabelDirection.LEFT)
          .setLabelOffset("50")
          .execute();


//      new TextButtonClick(driver)
//          .setLabel("User Нame")
////          .setLabelDirection(LabelDirection.LEFT)
////          .setLabelOffset("50")
//          .execute();

      new EditTextSet(driver)
          .onLabel("User Нame")
          .setText("admin")
          .execute();

      new TextButtonClick(driver)
          .setLabel("Continue Session")
          .execute();

      new EditTextSet(driver)
          .onLabel("User Нame")
          .setText("admin")
          .execute();

      new TextButtonClick(driver)
          .setLabel("Password")
//          .setLabelDirection(LabelDirection.LEFT)
//          .setLabelOffset("50")
          .tryExecute();

      new EditTextSet(driver)
          .onLabel("Password")
          .setText("admin")
//          .setLabelDirection(LabelDirection.LEFT)
//          .setLabelOffset("50")
          .execute();


      new TextButtonClick(driver)
          .setLabel("Sign in")
          .setIndex(2)
          .execute();

      Thread.sleep(3000);

      new TextCheckpoint(driver)
          .validateTextIsOnScreen("Congratulations");

      report.endTest();
    } catch (Exception e) {
      e.printStackTrace();
      report.endTestWithFailureMessage(e.getMessage());
    }
    report.open();
  }
}
