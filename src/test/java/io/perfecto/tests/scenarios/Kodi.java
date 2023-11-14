package io.perfecto.tests.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Kodi {

  public static void checkIfWebsiteIsOnline(RemoteWebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    driver.get("https://kodibg.org/forum/");
    wait.until(ExpectedConditions.titleIs("Коди Фен Форум България"));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("container")));
    driver.getScreenshotAs(OutputType.FILE);
  }

  public static void checkIfLoginWorks(RemoteWebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    driver.get("https://kodibg.org/forum/");
    wait.until(ExpectedConditions.titleIs("Коди Фен Форум България"));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("container")));

    driver.findElement(By.cssSelector(".login")).click();

    driver.findElement(By.id("quick_login_username")).sendKeys("testuser1");
    driver.findElement(By.id("quick_login_password")).sendKeys("testP@ssw0rd1");
    driver.findElement(By.cssSelector("input[value=Вход]")).click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[class='welcome'] a:nth-child(2)"))).click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("quick_login_username")));
    driver.getScreenshotAs(OutputType.FILE);
  }

  public static void checkIfSignUpIsWorking(RemoteWebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    driver.get("https://kodibg.org/forum/");
    wait.until(ExpectedConditions.titleIs("Коди Фен Форум България"));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("container")));

    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span[class='welcome'] a:nth-child(2)"))).click();
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='agree']"))).click();

    driver.getScreenshotAs(OutputType.FILE);
  }
}
