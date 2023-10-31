package io.perfecto.tests.remote.safari;

import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import io.perfecto.utilities.tokenstorage.PerfectoTokenStorage;
import io.perfecto.utilities.useractions.UserActions;
import io.perfecto.utilities.visualanalysis.EditTextSet;
import io.perfecto.utilities.visualanalysis.LabelDirection;
import io.perfecto.utilities.visualanalysis.TextButtonClick;
import io.perfecto.utilities.visualanalysis.TextCheckpoint;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Safari_Ventura_Tests {

  private RemoteWebDriver driver;
  private Report report;
  private UserActions ua;

  @BeforeTest
  public void setUpDriver() throws Exception {
    String host = "web-demo-fra";
    host = "mobilecloud";
    SafariOptions browserOptions = new SafariOptions();

    browserOptions.setPlatformName("Mac");
    browserOptions.setBrowserVersion("16");

    Map<String, Object> perfectoOptions = new HashMap<>();
    perfectoOptions.put("platformVersion", "macOS Ventura");
    perfectoOptions.put("location", "NA-US-BOS");
    perfectoOptions.put("resolution", "1920x1080");
    perfectoOptions.put("securityToken", PerfectoTokenStorage.getTokenForCloud(host));

    browserOptions.setCapability("perfecto:options", perfectoOptions);

    driver = new RemoteWebDriver(new URL("https://" + host + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), browserOptions);

    System.out.println(driver);
    System.out.println(browserOptions);
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testAddRemoveElements() throws Exception {


    try {
      report = new ReportBuilder(driver)
          .withReportName("Safari Add-Remove elements")
          .build();

//      report.startTest();
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
      driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
      driver.manage().window().maximize();

      Thread.sleep(3000);

      driver.findElement(By.xpath("//button[text()='Add Element']")).click();
      driver.findElement(By.xpath("//button[text()='Add Element']")).click();
      driver.findElement(By.xpath("(//button[text()='Delete'])[2]")).click();
      driver.findElement(By.xpath("(//button[text()='Delete'])[1]")).click();

      report.endTest();
    } catch (Exception e) {
      e.printStackTrace();
      report.endTestWithFailureMessage(e.getMessage());
    }
    report.open();
  }
}
