package io.perfecto.tests.scenarios;

import com.perfecto.reportium.client.ReportiumClient;
import io.perfecto.utilities.reporting.Report;
import io.perfecto.utilities.reporting.ReportBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DuckDuckGo {

  public static void searchForPerfecto(RemoteWebDriver driver, ReportiumClient reportiumClient) throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    driver.get("https://duckduckgo.com/");
    wait.until(ExpectedConditions.titleContains("DuckDuckGo"));
    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchbox_input")));
    element.sendKeys("Perfecto");
    driver.findElement(By.xpath("//button[@type='submit']"))
        .click();
    Thread.sleep(5000);
    driver.getScreenshotAs(OutputType.FILE);
  }
}
