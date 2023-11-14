package io.perfecto.tests.scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Chanel {
  private static final Logger logger = LoggerFactory.getLogger(Chanel.class);

  public static void addProductToCart(RemoteWebDriver driver) throws InterruptedException {
    try {
      WebElement el;

//      driver.get("https://www.chanel.com/kr/");
//      wait.until(ExpectedConditions.titleIs("샤넬 공식 웹사이트 - 패션, 향수, 뷰티, 시계, 화인 주얼리 | CHANEL 샤넬"));
//      System.out.println("Title matches");

//      Thread.sleep(13000);
//      el = wait.until(ExpectedConditions.elementToBeClickable(
//          By.xpath("//ul[@class='nav-right']/li/button[contains(@class,'js-search-button')]")));
//      Thread.sleep(3000);
//      System.out.println("el " + el + " found, clicking it");
//      el.click();
//      ((JavascriptExecutor)driver).executeScript("arguments[0].click()", el);

//      Thread.sleep(3000);
//
//      el = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#searchInput")));
//      el.clear();
//      el.sendKeys("120400");
//
//
//      el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'search__submit')]")));
//      System.out.println("el " + el + " found, clicking it");
//      Thread.sleep(1000);
//      el.click();


      driver.get("https://www.chanel.com/kr/fragrance/p/120400/gabrielle-chanel-eau-de-parfum-twist-and-spray/");
//      driver.manage().window().maximize();
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      logger.info("Waiting to add to cart");
      el = wait.until(ExpectedConditions.elementToBeClickable(
          By.xpath("//*[@class='product-details__ctas']//*[@class='product-ctas-wrapper']//*[@id='pos-cnc-btn']")));
      logger.info("Added to cart");
      el.click();
      logger.info("Waiting for add to cart validation");
      wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='cart-product__quantity'][contains(@data-test,'120400')]")));

      Thread.sleep(5000);


    }
    catch (Exception ex) {
      ex.printStackTrace();

      if (driver != null)
      {
        System.out.println(driver.getPageSource());
        driver.quit();
      }

      throw ex;
    }
  }
}
