package VisualAnalysisTests.scenarios;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.test.TestContext;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import io.perfecto.utilities.visualanalysis.EditTextSet;
import io.perfecto.utilities.visualanalysis.LabelDirection;
import io.perfecto.utilities.visualanalysis.TextButtonClick;
import io.perfecto.utilities.visualanalysis.TextCheckpoint;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthenticationPopup {
  public static void authenticateBlink(RemoteWebDriver driver, ReportiumClient reportiumClient) throws Exception {

      driver.get("https://the-internet.herokuapp.com/basic_auth");
      driver.manage().window().maximize();

      Thread.sleep(3000);

      new TextButtonClick(driver)
          .setLabel("Username")
          .setLabelDirection(LabelDirection.LEFT)
          .setLabelOffset("50")
          .tryExecute();

      new EditTextSet(driver)
          .onLabel("Username")
          .setText("admin")
          .setLabelDirection(LabelDirection.LEFT)
          .setLabelOffset("50")
          .execute();

      new TextButtonClick(driver)
          .setLabel("Password")
          .setLabelDirection(LabelDirection.LEFT)
          .setLabelOffset("50")
          .tryExecute();

      new EditTextSet(driver)
          .onLabel("Password")
          .setText("admin")
          .setLabelDirection(LabelDirection.LEFT)
          .setLabelOffset("50")
          .execute();


      new TextButtonClick(driver)
          .setLabel("Sign in")
          .setIndex(2)
          .execute();

      Thread.sleep(3000);

      new TextCheckpoint(driver)
          .validateTextIsOnScreen("Congratulations!");

  }

  public static void authenticateGecko(RemoteWebDriver driver) throws Exception {
    Report report = null;

    try {
      report = new ReportBuilder(driver)
          .withReportName("Handle basic authentication popup")
          .build();

      report.startTest();
      driver.get("https://the-internet.herokuapp.com/basic_auth");

      Thread.sleep(3000);

      new EditTextSet(driver)
          .onLabel("Password")
          .setText("admin")
          .setLabelDirection("below")
          .setLabelOffset("30")
          .execute();

      Thread.sleep(1000);

      new TextButtonClick(driver)
          .setLabel("Password")
          .setLabelDirection(LabelDirection.ABOVE)
          .setLabelOffset("5")
          .tryExecute();

      Thread.sleep(1000);

      new EditTextSet(driver)
          .onLabel("Password")
          .setText("admin")
          .setLabelDirection(LabelDirection.ABOVE)
          .execute();

      Thread.sleep(1000);

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
//      throw e;
    }
  }
}
